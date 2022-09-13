package com.makeupapp.makeup.product.presentation.screens

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.makeupapp.makeup.R
import com.makeupapp.makeup.common.MakeUpEvent
import com.makeupapp.makeup.common.OnItemClickListener
import com.makeupapp.makeup.common.observer
import com.makeupapp.makeup.databinding.BrandFragmentBinding
import com.makeupapp.makeup.product.data.model.MakeUpResponseModel
import com.makeupapp.makeup.product.data.model.MakeUpResponseModelItem
import com.makeupapp.makeup.product.presentation.adapter.BrandAdapter
import com.makeupapp.makeup.product.presentation.viewmodel.MakeUpViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class BrandFragment: Fragment(), OnItemClickListener {

    private var _binding: BrandFragmentBinding? = null
    private val binding get() = _binding!!

    private val makeUpViewModel: MakeUpViewModel by activityViewModels()
    private lateinit var brandRecyclerView: RecyclerView
    private lateinit var brandAdapter: BrandAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BrandFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpBrandRecyclerView()

//        retry on failure
        binding.brandRetry.setOnClickListener {
            makeUpViewModel.getMakeUp()
        }

//        observes network state
        observer(makeUpViewModel.makeUp){ makeUp ->
            when(makeUp){
                is MakeUpEvent.MakeUpSuccess -> {
                    Timber.e(makeUpViewModel.brandList.toString())
                    brandAdapter.differ.submitList(makeUpViewModel.brandList)
                    brandSuccess()
                }
                is MakeUpEvent.Error -> {
                    brandFailed()
                    Toast.makeText(requireContext(), makeUp.message, Toast.LENGTH_SHORT).show()
                }
                is MakeUpEvent.Loading -> {
                    brandLoading()
                }
                else -> makeUpViewModel.getMakeUp()
            }
        }
    }


    private fun brandLoading(){
        binding.brandPbar.isVisible = true
        binding.brandRv.isVisible = false
        binding.brandRetry.isVisible = false
    }
    private fun brandFailed(){
        binding.brandPbar.isVisible = false
        binding.brandRv.isVisible = false
        binding.brandRetry.isVisible = true
    }
    private fun brandSuccess(){
        binding.brandPbar.isVisible = false
        binding.brandRv.isVisible = true
        binding.brandRetry.isVisible = false
    }

    private fun setUpBrandRecyclerView(){
        brandRecyclerView = binding.brandRv
        brandAdapter = BrandAdapter(this)
        brandRecyclerView.adapter = brandAdapter
        val layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        layoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
        brandRecyclerView.layoutManager = layoutManager
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

//    updates the product brand id in the viewmodel to track other brand product types
    override fun onClick(id: Int, brand: String?) {
        if (brand != null) {
            makeUpViewModel.updateProductTypes(id, brand)
        }
        findNavController().navigate(R.id.action_brandFragment_to_productTypeFragment)
    }
}