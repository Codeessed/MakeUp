package com.makeupapp.makeup.product.presentation.screens

import android.content.res.ColorStateList
import android.graphics.Color
import android.icu.number.NumberFormatter.with
import android.icu.number.NumberRangeFormatter.with
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import coil.load
import com.google.android.material.chip.Chip
import com.makeupapp.makeup.R
import com.makeupapp.makeup.common.MakeUpEvent
import com.makeupapp.makeup.common.observer
import com.makeupapp.makeup.databinding.ProductDetailsFragmentBinding
import com.makeupapp.makeup.product.data.model.MakeUpResponseModelItem
import com.makeupapp.makeup.product.presentation.viewmodel.MakeUpViewModel
import com.squareup.picasso.Picasso

class ProductDetailsFragment: Fragment() {

    private var _binding: ProductDetailsFragmentBinding? = null
    private val binding get() = _binding!!

    private val makeUpViewModel: MakeUpViewModel by activityViewModels()
    private var id: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        id = arguments?.getInt("id")
        _binding = ProductDetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observer(makeUpViewModel.makeUp){ makeUp ->
            when(makeUp){
                is MakeUpEvent.MakeUpSuccess ->{
                    val result = makeUp.makeUp
//                    loop through to get the id of the product type and get the details
                    for (i in 0..result.lastIndex){
                        if (result[i].id == id){
                            attachValues(result[i])
                            for (j in result[i].product_colors){
                                createChip(j.hex_value)
                            }
                            break
                        }
                    }
                }
                else -> Unit
            }
        }
    }

    fun setChipBackgroundColor(
        checkedColor: Int = Color.RED,
        unCheckedColor: Int = Color.RED
    ): ColorStateList {
        val states = arrayOf(
            intArrayOf(android.R.attr.state_checked),
            intArrayOf(-android.R.attr.state_checked)
        )

        val colors = intArrayOf(
            // chip checked background color
            checkedColor,
            // chip unchecked background color
            unCheckedColor
        )

        return ColorStateList(states, colors)
    }

//    unclickable chips for available colors
    private fun createChip(color: String) {
        val chip = Chip(requireContext())
        chip.apply {
            isClickable = false
            chipBackgroundColor = setChipBackgroundColor(
                checkedColor = Color.parseColor(color),
                unCheckedColor = Color.parseColor(color)
            )
                }
        binding.productColorChipGroup.addView(chip as View)
    }

    private fun attachValues(item: MakeUpResponseModelItem){
        binding.productDetailPrice.text = getString(R.string.detail_price, item.currency?:"", item.price_sign?:"", item.price)
        binding.productDetailBrand.text = getString(R.string.detail_brand, item.brand)
        binding.productDetailProduct.text = getString(R.string.detail_product, item.product_type)
        binding.productDetailCategory.text = getString(R.string.detail_category, item.category?:"")
        binding.productDetailDesc.text = getString(R.string.detail_desc, item.description)
        Picasso.get().load("https:${item.api_featured_image}").placeholder(R.drawable.ic_pic_placeholder).into(binding.productDetailImage)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}