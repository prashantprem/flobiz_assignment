package com.app.flobiz_assignment.bottomsheet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.flobiz_assignment.R
import com.app.flobiz_assignment.adapter.FilterAdapter
import com.app.flobiz_assignment.databinding.FragmentFilterBottomSheetBinding
import com.app.flobiz_assignment.models.AnyList
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import timber.log.Timber

private const val ARG_FILTERS = "filters"

class FilterBottomSheet : BottomSheetDialogFragment() {

    private var filters: ArrayList<String> ?= null
    private lateinit var binding : FragmentFilterBottomSheetBinding
    private var listener : FilterAdapter.OnTagClickInterface ?= null

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
                            listener?.onTagSelected(tag)
                            Timber.tag("Tag").d(tag)
                        }

                    })
                }
            }
        binding.rcvFilters.layoutManager = GridLayoutManager(activity,2)
        binding.rcvFilters.adapter = adapter


    }

    companion object {
        @JvmStatic
        fun newInstance(filters: ArrayList<String>, listener : FilterAdapter.OnTagClickInterface) =
            FilterBottomSheet().apply {
                this.listener = listener
                arguments = Bundle().apply {
                    putStringArrayList(ARG_FILTERS,filters)
                }
            }
    }
}