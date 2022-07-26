package com.example.sapigps

//import androidx.test.core.app.ApplicationProvider.getApplicationContext

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.Constraints.TAG
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.util.*


class SecondFragment : Fragment() {

    private lateinit var dbRef : DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.secondfragment,container,false)
    }

    companion object{
        fun newInstanse():SecondFragment{
            val fragment=SecondFragment()
            val ards=Bundle()
            fragment.arguments = ards
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnLogout : Button = view.findViewById(R.id.btnLogout)

        val languages = listOf("Java", "Kotlin", "Javascript", "PHP", "Python")


        // memberikan adapter ke ListView
//
//        val arrayAdapter: ArrayAdapter<*>
//        val users = arrayOf(
//            val rootRef = FirebaseDatabase.getInstance().reference
//        val collionRef = rootRef.child("LokasiSapi")
//        val valueEventListener: ValueEventListener = object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                for (ds in dataSnapshot.children) {
//                    val collion: Sapi? = ds.getValue(Sapi::class.java)
//                    val text = view!!.findViewById<TextView>(R.id.tvSecond)
//                    text.text = collion!!.lokasi.toString()
//                    Log.d("TAG", collion!!.lokasi.toString())
//                }
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {}
//        }
//        collionRef.addListenerForSingleValueEvent(valueEventListener)
//        )

        // access the listView from xml file
//        var mListView = view.findViewById<ListView>(R.id.lv_languages)
//        arrayAdapter = ArrayAdapter(requireContext(),
//            android.R.layout.simple_list_item_1, users)
//        mListView.adapter = arrayAdapter

        btnLogout.setOnClickListener{
            signOut()
            requireActivity().finish()
        }

        val text = FirebaseDatabase.getInstance().getReference("LokasiSapi")

        val tv = view.findViewById<TextView>(R.id.tvSecond)
//        tv.text = text.toString()


    getData()
    }

    private fun getData(){
        val rootRef = FirebaseDatabase.getInstance().reference
        val collionRef = rootRef.child("LokasiSapi")
        val valueEventListener: ValueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (ds in dataSnapshot.children) {
                    val collion: Sapi? = ds.getValue(Sapi::class.java)
                    val text = view!!.findViewById<TextView>(R.id.tvSecond)
//                    text.text = collion!!.lokasi.toString()
                    Log.d("TAG", collion!!.lokasi.toString())

//                    val arrayAdapter:ArrayAdapter<*>
//                    var List = view!!.findViewById<ListView>(R.id.lv_languages)
//                    arrayAdapter = ArrayAdapter(
//                        activity,
//                        android.R.layout.simple_list_item_1, collion!!.lokasi)
//                    List.adapter = arrayAdapter
                }

//                val sapi: ArrayList<Sapi?> = ArrayList()
//                for (productSnapshot in dataSnapshot.children) {
//                    val lok = productSnapshot.getValue(Sapi::class.java)
//                    val lokk: Sapi? = productSnapshot.getValue(Sapi::class.java)
//                    sapi.add(lok)
//                }
////                System.out.println(sapi)
//                val text = view!!.findViewById<TextView>(R.id.tvSecond)
////                text.text = sapi.toString()
//                Log.d("Tag", sapi.toString())


//                val gson = Gson()
//                val listPersonType = object : TypeToken<List<Sapi>>(){}.type
//                var persons:List<Sapi> = gson.fromJson(sapi, listPersonType)

            }

            override fun onCancelled(databaseError: DatabaseError) {}
        }
        collionRef.addListenerForSingleValueEvent(valueEventListener)

        val userdetails: ArrayList<Sapi> = ArrayList()
        dbRef = FirebaseDatabase.getInstance().reference
        dbRef.child("LokasiSapi").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (postSnapshot in dataSnapshot.children) {
                    Log.e("Get Data", postSnapshot.getValue(Sapi::class.java).toString())
                    val text = view!!.findViewById<TextView>(R.id.tvSecond)
//                    text.text = postSnapshot.getValue(Sapi::class.java).toString()
                    userdetails.add(postSnapshot.getValue(Sapi::class.java)!!)
//                    text.text = userdetails.toString()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        })
    }

    private fun signOut() {
        FirebaseAuth.getInstance().signOut()
        startActivity(Intent(context,LoginActivity::class.java))
//        onDestroy()
    }
}