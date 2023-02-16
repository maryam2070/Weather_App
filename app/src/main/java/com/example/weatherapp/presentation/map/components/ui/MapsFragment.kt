package com.example.weatherapp.presentation.map.components.ui

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Address
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentMapsBinding
import com.example.weatherapp.presentation.map.components.viewmodel.MapsViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import dagger.hilt.android.AndroidEntryPoint
import android.location.Geocoder
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.common.Resource
import com.example.weatherapp.domain.model.TimeZone
import com.example.weatherapp.presentation.current_day.components.ui.ModalBottomSheet.Companion.TAG
import com.google.android.gms.maps.model.*
import com.master.permissionhelper.PermissionHelper
import com.master.permissionhelper.PermissionHelper.PermissionCallback
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.IOException


@AndroidEntryPoint
class MapsFragment : Fragment() {

    lateinit var binding: FragmentMapsBinding
    private lateinit var viewModel: MapsViewModel

    lateinit var marker: LatLng
    lateinit var map:GoogleMap
    lateinit var permissionHelper :PermissionHelper

    lateinit var editor: SharedPreferences.Editor
    lateinit var sharedPreferences: SharedPreferences
    @SuppressLint("MissingPermission")
    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */

        map=googleMap
        googleMap.setOnMapClickListener {
            googleMap.clear()
            marker=it
            googleMap.addMarker(MarkerOptions().position(it))
            googleMap.animateCamera(CameraUpdateFactory.zoomIn())

            val cameraPosition = CameraPosition.Builder()
                .target(it)
                .zoom(17f)
                .tilt(30f)
                .build()
            googleMap.mapType=GoogleMap.MAP_TYPE_SATELLITE
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))

        }
        map.uiSettings.isCompassEnabled=false
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            map.isMyLocationEnabled = true
            Log.d(TAG, "Individual Permission Granted")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentMapsBinding.inflate(inflater,container,false)
        viewModel = ViewModelProvider(this).get(MapsViewModel::class.java)

        sharedPreferences=requireContext().getSharedPreferences("shared_file", Context.MODE_PRIVATE)
        editor=sharedPreferences.edit()

        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
                binding.myLocationCv.visibility=View.INVISIBLE
            } else {
                binding.myLocationCv.visibility=View.VISIBLE
        }

        binding.myLocationCv.setOnClickListener {
            showSettingDialog()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?

        binding.loadingPb.visibility = View.INVISIBLE
        binding.searchBar.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            @SuppressLint("CommitPrefEdits")
            override fun onQueryTextSubmit(query: String?): Boolean {
                val location: String = binding.searchBar.getQuery().toString()
                var addressList: List<Address>? = null

                if (location != null || location == "") {
                    val geocoder = Geocoder(requireContext())
                    try {
                        addressList = geocoder.getFromLocationName(location, 1)
                        if(addressList!!.size==0)
                            Toast.makeText(requireActivity().applicationContext,"Location not found",Toast.LENGTH_SHORT).show()
                        else {
                            map.clear()
                            val address: Address = addressList!![0]
                            val latLng = LatLng(address.getLatitude(), address.getLongitude())
                            map.addMarker(MarkerOptions().position(latLng).title(location))
                            map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10f))
                        }
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })

        binding.floatingActionButton.setOnClickListener {
            viewModel.getTimeZone(
                marker.latitude.toString()+","+marker.longitude.toString()
            )

            GlobalScope.launch(Dispatchers.Main) {
                viewModel.getTimeZoneResponse.collect { response ->
                    when (response) {
                        is Resource.Error -> {
                            binding.loadingPb.visibility = View.INVISIBLE
                            Toast.makeText(requireContext(), response.message, Toast.LENGTH_SHORT)
                                .show()
                        }
                        is Resource.Loading -> {

                            binding.loadingPb.visibility = View.VISIBLE
                        }
                        is Resource.Success -> {


                            if(sharedPreferences.getBoolean("have_fav",false)==false)
                            {
                                viewModel.insertTimeZone(TimeZone(response.data!!.latitude,response.data!!.longitude,response.data!!.timezone_id,true))
                            }else{
                                viewModel.insertTimeZone(TimeZone(response.data!!.latitude,response.data!!.longitude,response.data!!.timezone_id,false))
                            }

                            //navigation transaction
                            val args=Bundle()
                            args.putString("time_zone",response.data!!.timezone_id)
                            args.putString("latitude",response.data!!.latitude)
                            args.putString("longitude",response.data!!.longitude)
                            binding.loadingPb.visibility = View.INVISIBLE


                            findNavController().navigate(R.id.action_mapsFragment_to_weatherFragment,args)


                        }
                    }
                }
            }
        }
        mapFragment?.getMapAsync(callback)
    }



    private fun showSettingDialog() {
        AlertDialog.Builder(requireContext()).setMessage(
            "For better experience if you would to access your current location you can enable location permission"
        )
            .setPositiveButton("Request Permission")
            { dialog, _ ->
                try {
                    requestPermission()
                } catch (e: ActivityNotFoundException) {
                    e.printStackTrace()
                }
            }
            .setCancelable(false)
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }.show()
    }

    fun requestPermission()
    {

        val permissionHelper = PermissionHelper(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 100)

        permissionHelper?.requestIndividual {
            Log.d(TAG, "Individual Permission Granted")
        }
        permissionHelper?.denied {
            if (it) {
                Log.d(TAG, "Permission denied by system")
                //permissionHelper?.openAppDetailsActivity()
            } else {
                Log.d(TAG, "Permission denied")
            }
        }

    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode==100)
        {
            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                binding.myLocationCv.visibility = View.INVISIBLE
                map.isMyLocationEnabled = true
            }
        }


    }

}