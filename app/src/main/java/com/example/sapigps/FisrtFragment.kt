package com.example.sapigps

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.ArrayList

class FisrtFragment : Fragment(), OnMapReadyCallback {
//    MyAdapter.OnItemClickListener
    private lateinit var dbRef : DatabaseReference
    private lateinit var sapiRecyclerView: RecyclerView
    private lateinit var sapiArray: ArrayList<Sapi>
//    private final var sapiList: ArrayList<Sapi>? = null
//    private lateinit var sapiList = generateDummyList(500)

//    private val adapter = MyAdapter(sapiArray, this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.firstfragment, container, false)
//        val mapFragment = childFragmentManager
//            .findFragmentById(R.id.map) as SupportMapFragment
//        mapFragment.getMapAsync(this)

    }
    companion object{
        fun newInstance():FisrtFragment{
            val fragment = FisrtFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sapiRecyclerView = view.findViewById(R.id.SapiList)
        sapiRecyclerView.layoutManager = LinearLayoutManager(context)
        sapiRecyclerView.setHasFixedSize(true)

        sapiArray = arrayListOf<Sapi>()
        getData()
//
    }

    private fun getData() {
        dbRef = FirebaseDatabase.getInstance().getReference("LokasiSapi")
        dbRef.addValueEventListener( object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (sapiSnapshot in snapshot.children){
                        val lokasiSapi  = sapiSnapshot.getValue(Sapi::class.java)
                        sapiArray.add(lokasiSapi!!)
                    }
                    var adapter = MyAdapter(sapiArray)
                    sapiRecyclerView.adapter = adapter

                    adapter.setOnItemClickListener(object : MyAdapter.OnItemClickListener{
                        @SuppressLint("ResourceType")
                        override fun onItemClick(position: Int) {

//                            val currentItem = sapiList!!.get(position)
//                            val itemLokasi = LokasiSapi[position]
//                            Toast.makeText(context, "you click ${sapiArray.indexOf(Sapi("lokasi"))} ", Toast.LENGTH_SHORT).show()
//                            Toast.makeText(context, "you click: ${position} ", Toast.LENGTH_SHORT).show()

//                            val bundle = Bundle()
//                            bundle.putString("LokasiSapi", sapiArray[position].lokasi)
//                            val fragment = FragmentGps()
//                            fragment.arguments = bundle
//                            fragmentManager?.beginTransaction()?.replace(R.id.navigation_satu, fragment)?.commit()
                            val intent = Intent(context, gpsFragment::class.java)
                            intent.putExtra("LokasiSapi", sapiArray[position].lokasi)
                            intent.putExtra("DetakJantungSapi", sapiArray[position].detakJantungSapi)
                            intent.putExtra("suhuSapi", sapiArray[position].suhuSapi)
                            intent.putExtra("IdSapi", sapiArray[position].IdSapi)
                            startActivity(intent)
                        }
                    })

                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "failed to get data", Toast.LENGTH_SHORT).show()
            }
        }

        )
    }

    override fun onMapReady(p0: GoogleMap) {
        TODO("Not yet implemented")
    }

//    override fun onItemClick(position: Int) {
//        Toast.makeText(context, "item $position Click", Toast.LENGTH_SHORT).show()
//        val clickItem:Sapi = sapiArray[position]
//        clickItem.IdSapi = "clikced"
//        adapter.notifyItemChanged(position)
//    }


}