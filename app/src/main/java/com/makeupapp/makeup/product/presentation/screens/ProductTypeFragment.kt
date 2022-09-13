package com.makeupapp.makeup.product.presentation.screens

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.makeupapp.makeup.R
import com.makeupapp.makeup.common.MakeUpEvent
import com.makeupapp.makeup.common.OnItemClickListener
import com.makeupapp.makeup.common.observer
import com.makeupapp.makeup.databinding.BrandFragmentBinding
import com.makeupapp.makeup.databinding.ProductTypesFragmentBinding
import com.makeupapp.makeup.product.data.model.MakeUpResponseModelItem
import com.makeupapp.makeup.product.presentation.adapter.ProductTypeAdapter
import com.makeupapp.makeup.product.presentation.viewmodel.MakeUpViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductTypeFragment: Fragment(), OnItemClickListener {

    private var _binding: ProductTypesFragmentBinding? = null
    private val binding get() = _binding!!

    private val makeUpViewModel: MakeUpViewModel by activityViewModels()
    private lateinit var productTypeAdapter: ProductTypeAdapter
    private lateinit var productTypeRv: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ProductTypesFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpProductTypeRv()

//        observes all product in a particular brand
        observer(makeUpViewModel.productType){ productType ->
            when(productType){
                is MakeUpEvent.ProductTypeSuccess ->{
                    productTypeAdapter.differ.submitList(makeUpViewModel.productTypes)
                    productTypeSuccess()
                }
                is MakeUpEvent.Loading ->{
                    productTypeLoading()
                }
                else -> Unit
            }

        }
    }

    private fun setUpProductTypeRv(){
        productTypeRv = binding.productTypeRv
        productTypeAdapter = ProductTypeAdapter(this, requireContext())
        productTypeRv.adapter = productTypeAdapter
        productTypeRv.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun productTypeLoading(){
        binding.productTypeBtn.isVisible = false
        binding.productTypePbar.isVisible = true
        binding.productTypeRv.isVisible = false
    }

    private fun productTypeSuccess(){
        binding.productTypeBtn.isVisible = false
        binding.productTypePbar.isVisible = false
        binding.productTypeRv.isVisible = true
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onClick(id: Int, brand: String?) {
        val bundle = bundleOf("id" to id)
        findNavController().navigate(R.id.action_productTypeFragment_to_productDetailsFragment, bundle)
    }
}