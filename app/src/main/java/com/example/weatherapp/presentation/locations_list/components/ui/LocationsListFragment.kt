package com.example.weatherapp.presentation.locations_list.components.ui

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.common.Resource
import com.example.weatherapp.databinding.FragmentLocationsListBinding
import com.example.weatherapp.domain.model.TimeZone
import com.example.weatherapp.presentation.current_day.components.adapter.TimeZoneAdapter
import com.example.weatherapp.presentation.locations_list.components.viewmodels.LocationsListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LocationsListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@SuppressLint("UseSupportActionBar")
@AndroidEntryPoint
class LocationsListFragment : Fragment() {

    lateinit var binding: FragmentLocationsListBinding
    private lateinit var viewModel: LocationsListViewModel
    init {
        setHasOptionsMenu(true)
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater){
        inflater.inflate(R.menu.menu, menu)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentLocationsListBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(LocationsListViewModel::class.java)

        viewModel.getAllTimeZones()
        binding.deleteIv.setOnClickListener {
            showDeleteAllDialog()
        }
        binding.backIv.setOnClickListener {
            findNavController().popBackStack()
        }

        CoroutineScope(Dispatchers.Main).launch {
            viewModel.getAllTimeZonesResponse.collect { response ->
                when (response) {
                    is Resource.Error -> {
                        Toast.makeText(requireContext(), response.message, Toast.LENGTH_SHORT)
                            .show()
                    }
                    is Resource.Loading -> {
                    }
                    is Resource.Success -> {
                        val adapter = TimeZoneAdapter(
                            response.data!! as ArrayList<TimeZone>,
                            requireContext(),
                            viewModel
                        )
                        binding.timezoneRv.adapter = adapter
                        binding.timezoneRv.layoutManager = LinearLayoutManager(requireContext())

                        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
                            override fun onMove(
                                recyclerView: RecyclerView,
                                viewHolder: RecyclerView.ViewHolder,
                                target: RecyclerView.ViewHolder
                            ): Boolean {
                                return false
                            }

                            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                                val deleted=response.data.get(viewHolder.adapterPosition)
                                showDeleteDialog(deleted)
                            }
                        }).attachToRecyclerView(binding.timezoneRv)
                    }
                }
            }

        }



        return binding.root
    }

    private fun showDeleteAllDialog() {
        AlertDialog.Builder(requireContext()).setMessage(
            "sure you want to delete all Locations"
        )
            .setPositiveButton("delete")
            { dialog, _ ->
                try {
                    viewModel.deleteAllTimeZones()
                } catch (e: ActivityNotFoundException) {
                    e.printStackTrace()
                }
            }
            .setCancelable(false)
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }.show()
    }

    private fun showDeleteDialog(deleted: TimeZone) {
        AlertDialog.Builder(requireContext()).setMessage(
            "sure you want to delete this Location"
        )
            .setPositiveButton("delete")
            { dialog, _ ->
                try {
                    viewModel.deleteTimeZone(deleted)
                } catch (e: ActivityNotFoundException) {
                    e.printStackTrace()
                }
            }
            .setCancelable(false)
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }.show()
    }
}