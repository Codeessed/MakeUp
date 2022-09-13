package com.makeupapp.makeup.product.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.makeupapp.makeup.R
import com.makeupapp.makeup.common.OnItemClickListener
import com.makeupapp.makeup.databinding.BrandItemBinding
import com.makeupapp.makeup.databinding.ProductTypeItemBinding
import com.makeupapp.makeup.product.data.model.MakeUpResponseModelItem
import com.squareup.picasso.Picasso
import java.util.*

class ProductTypeAdapter(private val onItemClickListener: OnItemClickListener, private val context:Context): RecyclerView.Adapter<ProductTypeAdapter.ProductViewHolder>() {
    inner class ProductViewHolder(val binding: ProductTypeItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(position: Int){
            val brand = differ.currentList[position]
            binding.apply {
                productName.text = brand.product_type.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(
                        Locale.ROOT
                    ) else it.toString()
                }
                Picasso.get()
                    .apply {
                        load("https:${brand.api_featured_image}")
                            .placeholder(R.drawable.ic_pic_placeholder)
                            .into(productImage)
                    }
                productItem.setOnClickListener {
                    onItemClickListener.onClick(brand.id, brand.brand)
                }
                productCurrency.text = brand.currency
                productPriceSign.text = brand.price_sign
                productPrice.text = brand.price
                productDesc.text = brand.description
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductViewHolder {
        return ProductViewHolder(ProductTypeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private val differList = object: DiffUtil.ItemCallback<MakeUpResponseModelItem>(){
        override fun areItemsTheSame(
            oldItem: MakeUpResponseModelItem,
            newItem: MakeUpResponseModelItem
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: MakeUpResponseModelItem,
            newItem: MakeUpResponseModelItem
        ): Boolean {
            return oldItem == newItem
        }
    }

    var differ = AsyncListDiffer(this, differList)

}