package com.example.sapigps

import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.IOException
import java.lang.Float
import java.util.*

class item : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.item)

        val tvLokasiSapi: TextView = findViewById(R.id.tvLocaionVal)

        val tvLokasiSapiVal = tvLokasiSapi.text.toString()

        val latlong =tvLokasiSapiVal.split(',')
        var lat = Float.parseFloat(latlong[0])
        var lng = Float.parseFloat(latlong[1])

        val geocoder = Geocoder(this@item)
        val list = geocoder.getFromLocation(lat.toDouble(), lng.toDouble(),1)
        val address = list.get(0).getAddressLine(0)

        tvLokasiSapi.text = address

        Toast.makeText(this, tvLokasiSapiVal,Toast.LENGTH_SHORT).show()

        val mGeocoder = Geocoder(applicationContext, Locale.getDefault())
        var addressString =""

        try {
            val addressList: List<Address> = mGeocoder.getFromLocation(lat.toDouble(), lng.toDouble(), 1)

            if (addressList.isNotEmpty()){
                val address = addressList[0]
                val sb = StringBuilder()
                for (i in 0 until address.maxAddressLineIndex){
                    sb.append(address.getAddressLine(i)).append("\n")
                }
                if (address.premises != null){
                    sb.append(address.premises).append(", ")
                    sb.append(address.locality).append(", ")
                    sb.append(address.adminArea).append(", ")
                    sb.append(address.countryName).append(", ")
                    sb.append(address.postalCode)
                    addressString = sb.toString()

                }
            }
        } catch (e: IOException){
            Toast.makeText(applicationContext,"Unable connect to Geocoder", Toast.LENGTH_LONG).show()
        }
    }
}