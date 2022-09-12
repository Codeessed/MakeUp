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
import com.makeupapp.makeup.common.MakeUpEvent
import com.makeupapp.makeup.common.observer
import com.makeupapp.makeup.databinding.BrandFragmentBinding
import com.makeupapp.makeup.databinding.ProductTypesFragmentBinding
import com.makeupapp.makeup.product.presentation.adapter.ProductTypeAdapter
import com.makeupapp.makeup.product.presentation.viewmodel.MakeUpViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductTypeFragment: Fragment() {

    private var _binding: ProductTypesFragmentBinding? = null
    private val binding get() = _binding!!

    private val makeUpViewModel: MakeUpViewModel by activityViewModels()
    private lateinit var productTypeAdapter: ProductTypeAdapter


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
        observer(makeUpViewModel.productType){ productType ->
            when(productType){
                is MakeUpEvent.ProductTypeSuccess ->{

                }
                is MakeUpEvent.Loading ->{
                    productTypeLoading()
                }
                else -> Unit
            }

        }
//        observer(makeUpViewModel.makeUp){ makeUp ->
//            when(makeUp){
//                is MakeUpEvent.MakeUpSuccess -> {
//                    setUpBrandRecyclerView(makeUpViewModel.brandList)
//                    brandSuccess()
//                }
//                is MakeUpEvent.Error -> {
//                    brandFailed()
//                    Toast.makeText(requireContext(), makeUp.message, Toast.LENGTH_SHORT).show()
//                }
//                is MakeUpEvent.Loading -> {
//                    productTypeLoading()
//                }
//                else -> makeUpViewModel.getMakeUp()
//            }
//        }
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
}