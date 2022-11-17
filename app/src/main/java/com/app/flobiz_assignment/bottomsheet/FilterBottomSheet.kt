package com.app.flobiz_assignment.bottomsheet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.flobiz_assignment.R
import com.app.flobiz_assignment.adapter.FilterAdapter
import com.app.flobiz_assignment.databinding.FragmentFilterBottomSheetBinding
import com.app.flobiz_assignment.models.AnyList
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import timber.log.Timber

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_FILTERS = "filters"

/**
 * A simple [Fragment] subclass.
 * Use the [FilterBottomSheet.newInstance] factory method to
 * create an instance of this fragment.
 */
class FilterBottomSheet : BottomSheetDialogFragment() {

    private var filters: ArrayList<String> ?= null
    private lateinit var binding : FragmentFilterBottomSheetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            filters = it.getStringArrayList(ARG_FILTERS)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFilterBottomSheetBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter =
            context?.let {
                filters?.let { it1 ->
                    FilterAdapter(context = it, it1,object : FilterAdapter.OnTagClickInterface{
                        override fun onTagSelected(tag: String?) {
                            super.onTagSelected(tag)
                            Timber.tag("Tag").d(tag)
                        }

                    })
                }
            }
        binding.rcvFilters.layoutManager = LinearLayoutManager(activity)
        binding.rcvFilters.adapter = adapter


    }

    companion object {
        @JvmStatic
        fun newInstance(filters: ArrayList<String>) =
            FilterBottomSheet().apply {
                arguments = Bundle().apply {
                    putStringArrayList(ARG_FILTERS,filters)
                }
            }
    }
}