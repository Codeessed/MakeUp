package com.makeupapp.makeup.product.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.memory.MemoryCache
import coil.transform.CircleCropTransformation
import com.makeupapp.makeup.R
import com.makeupapp.makeup.common.OnItemClickListener
import com.makeupapp.makeup.databinding.BrandItemBinding
import com.makeupapp.makeup.product.data.model.MakeUpResponseModelItem
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso
import java.io.File

class BrandAdapter(private val onItemClickListener: OnItemClickListener): RecyclerView.Adapter<BrandAdapter.BrandViewHolder>() {
    inner class BrandViewHolder(val binding: BrandItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(position: Int){
            val brand = differ.currentList[position]
            binding.apply {
                brandName.text = brand.brand
                brandImage.apply {
                    Picasso.get()
                        .apply {
                            load("https:${brand.api_featured_image}")
                                .placeholder(R.drawable.ic_pic_placeholder)
                                .into(brandImage)

                        }
                }
                brandItem.setOnClickListener {
                    onItemClickListener.onClick(brand.id, brand.brand)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BrandViewHolder {
        return BrandViewHolder(BrandItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: BrandViewHolder, position: Int) {
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