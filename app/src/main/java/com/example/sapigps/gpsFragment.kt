package com.example.sapigps

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import java.lang.Float
import com.google.firebase.database.*

class gpsFragment : AppCompatActivity(), OnMapReadyCallback {

    private val TAG = "MainActivityGoefence"
    private lateinit var geofencingClient: GeofencingClient
//    private var geofenceHelper: geofenceHelper ?= null
    private lateinit var mMap: GoogleMap

    private lateinit var map: GoogleMap


    private val GEOFENCE_RADIDUS = 200f
    private val GEOFENCE_ID = "SOME_GEOFENCE_ID"

    private val FINE_LOCATION_ACCESS_REQUEST_CODE = 10001
    private val BACKROUND_FINE_LOCATION_ACCESS_REQUEST_CODE = 10002

    private lateinit var database: DatabaseReference
    private lateinit var firebaseDatabase: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gps_fragment)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

//        val tvGps = findViewById<TextView>(R.id.tvLokasiSapiGpsActivity)
//        tvGps.text = intent.getStringExtra("LokasiSapi")

        BottomSheetBehavior.from(findViewById(R.id.sheet)).apply{
            peekHeight = 700
            this.state = BottomSheetBehavior.STATE_COLLAPSED
        }
        val detakJanntung = intent.getStringExtra("DetakJantungSapi").toString()
        val textDetakJantung = findViewById<TextView>(R.id.tvDetakJantungGpsVal)
        textDetakJantung.text = detakJanntung
        val suhu = intent.getStringExtra("suhuSapi").toString()
        val suhuSapi = findViewById<TextView>(R.id.tvSuhuGpsVal)
        suhuSapi.text = suhu
    }

    override fun onMapReady(googleMap: GoogleMap) {
//            getData()
        mMap = googleMap
        val lokasi = intent.getStringExtra("LokasiSapi").toString()
        var latlong = lokasi.split(',')
        var lat = Float.parseFloat(latlong[0])
        var lng = Float.parseFloat(latlong[1])
        var latlng = LatLng(lat.toDouble(), lng.toDouble())
        Toast.makeText(this, "$latlng", Toast.LENGTH_LONG).show()
        val id = intent.getStringExtra("IdSapi").toString()
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 16f))
        mMap.addMarker(MarkerOptions().position(latlng).title(id))?.showInfoWindow()
//        mMap.addCircle(circleOptions)
//        addMarker(latlng)
        val tvGps2 = findViewById<TextView>(R.id.tvLocationSapiVal)
        tvGps2.text = lokasi
//        addCircle(latlng, GEOFENCE_RADIDUS)

        firebaseDatabase = FirebaseDatabase.getInstance()
        database = firebaseDatabase.getReference("latlngSapi")
        database .addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
//                mMap.clear()
                val latitude = snapshot.value.toString()

                var latlong = latitude.split(',')
                var lat = Float.parseFloat(latlong[0])
                var lng = Float.parseFloat(latlong[1])
                var latlng = LatLng(lat.toDouble(), lng.toDouble())

                val radius = 100f
//                geofenceList.add(
//                    Geofence.Builder()
//                    .setRequestId("entry.key")
//                    .setCircularRegion(lat.toDouble(), lng.toDouble(), radius)
//                    .setExpirationDuration(Geofence.NEVER_EXPIRE)
//                    .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
//                    .build()
//                )
                val circleOptions = CircleOptions()
                    .center(latlng)
                    .radius(radius.toDouble())
                    .fillColor(0x40ff0000)
                    .strokeColor(Color.BLUE)
                    .strokeWidth(2f)

                val zoomLevel = 16f

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, zoomLevel))
//                mMap.addMarker(MarkerOptions().position(latlng).title("Current Location"))
//                    ?.showInfoWindow()
//                mMap.addCircle(circleOptions)

//                mMap.clear()
                addMarker(latlng)
                addCircle(latlng, GEOFENCE_RADIDUS)
//                addGeofence(latlng, GEOFENCE_RADIDUS)
//                enableUserLocation()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext,"Failed load data maps", Toast.LENGTH_LONG).show()
            }

        })

//            enableUserLocation()
//            mMap.setOnMapLongClickListener(this)

    }
    private fun addMarker(latLng: LatLng) {
        val markerOptions = (MarkerOptions().position(latLng).title(""))
        mMap.addMarker(markerOptions)?.showInfoWindow()
    }

    private fun addCircle(latLng: LatLng, radius: kotlin.Float) {
        val circleOptions = CircleOptions()
        circleOptions.center(latLng)
        circleOptions.radius(radius.toDouble())
        circleOptions.strokeColor(Color.argb(255, 255, 0, 0))
        circleOptions.fillColor(Color.argb(64, 255, 0, 0))
        circleOptions.strokeWidth(4f)
        mMap.addCircle(circleOptions)
    }
}