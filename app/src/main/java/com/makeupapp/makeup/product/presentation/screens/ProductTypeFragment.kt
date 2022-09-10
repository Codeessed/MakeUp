package com.makeupapp.makeup.product.presentation.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.makeupapp.makeup.databinding.BrandFragmentBinding
import com.makeupapp.makeup.databinding.ProductTypesFragmentBinding

class ProductTypeFragment: Fragment() {

    private var _binding: ProductTypesFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ProductTypesFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}