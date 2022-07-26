package com.example.sapigps

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import java.io.IOException
import java.util.*
import java.lang.Float
import kotlin.collections.ArrayList
import kotlin.coroutines.coroutineContext

class MyAdapter(private val sapiList: ArrayList<Sapi>
                 ):RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    private lateinit var mListener : OnItemClickListener

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener){
        mListener = listener
    }

//    inner class sapiViewHolder(
//        val itemSapiBinding: ItemSapiBinding
//    ) : RecyclerView.ViewHolder(itemSapiBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return MyViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = sapiList[position]

        holder.LokasiSapi.text = currentItem.lokasi
        holder.IdSapi.text = currentItem.IdSapi
        holder.DetakJantungSapi.text = currentItem.detakJantungSapi
        holder.suhuSapi.text = currentItem.suhuSapi

        val lokaiSapi = currentItem.lokasi
        var latLong = lokaiSapi?.split(',')
        var lat = Float.parseFloat(latLong!![0])
        var lng = Float.parseFloat(latLong!![1])



//        val geocoder = Geocoder(this)
//        val list = geocoder.getFromLocation(lat.toDouble(), lng.toDouble(), 1)
//        val addresses = list.get(0).getAddressLine(0);
////        tvLokasi.text = addresses
//        val mGeocoder = Geocoder(applicationContext, Locale.getDefault())
//        var addressString = ""
//        try {
//            val addressList: List<Address> = mGeocoder.getFromLocation(lat.toDouble(), lng.toDouble(), 1)
//
//            if (addressList.isNotEmpty()){
//                val address = addressList[0]
//                val sb = StringBuilder()
//                for (i in 0 until address.maxAddressLineIndex){
//                    sb.append(address.getAddressLine(i)).append("\n")
//                }
//                if (address.premises != null){
//                    sb.append(address.premises).append(", ")
//                    sb.append(address.locality).append(", ")
//                    sb.append(address.adminArea).append(", ")
//                    sb.append(address.countryName).append(", ")
//                    sb.append(address.postalCode)
//                    addressString = sb.toString()
//
//                }
//            }
//        } catch (e: IOException){
//            Toast.makeText(applicationContext,"Unable connect to Geocoder", Toast.LENGTH_LONG).show()
//        }

//        val sapi = sapiList.indexOf(Sapi("LokasiSapi"))


    }

    class MyViewHolder (itemView:View, clickListener: OnItemClickListener):RecyclerView.ViewHolder(itemView){
        val LokasiSapi:TextView = itemView.findViewById(R.id.tvLocaionVal)
        val IdSapi:TextView = itemView.findViewById(R.id.tvIdSapiVal)
        val DetakJantungSapi:TextView = itemView.findViewById(R.id.DetakJantungSapiVal)
        val suhuSapi:TextView = itemView.findViewById(R.id.SuhuSapiVal)
        init{
            itemView.setOnClickListener{
                clickListener.onItemClick(adapterPosition)
            }
        }
//        View.OnClickListener
//        override fun onClick(v: View?) {
//            val position:Int = adapterPosition
//            if (position!=RecyclerView.NO_POSITION) {
//                mListener.onItemClick(position)
//            }
//        }
    }

    override fun getItemCount(): Int {
        return sapiList.size
    }

}