package com.example.poilabschallange.presentation.home.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.poilabschallange.R
import com.example.poilabschallange.databinding.ItemCountryBinding
import com.example.poilabschallange.domain.entity.CountryEntity

class MyCountryListAdapter() :
    RecyclerView.Adapter<MyCountryListAdapter.MyCountryListAdapterViewHolder>() {

    var onItemClick: ((entity: CountryEntity) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyCountryListAdapterViewHolder {
        val layoutId = R.layout.item_country

        return MyCountryListAdapterViewHolder(
            parent.context,
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                layoutId,
                parent,
                false
            )
        )
    }

    companion object {
        private const val TYPE_NEWS = 1
    }

    override fun getItemViewType(position: Int): Int {
        return TYPE_NEWS
    }

    private val differCallback = object : DiffUtil.ItemCallback<CountryEntity>() {
        override fun areItemsTheSame(oldItem: CountryEntity, newItem: CountryEntity): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: CountryEntity, newItem: CountryEntity): Boolean {
            return oldItem.name?.official == newItem.name?.official
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onBindViewHolder(holder: MyCountryListAdapterViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class MyCountryListAdapterViewHolder(
        private val context: Context,
        private val binding: ViewDataBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {

        private fun bindNewsBodyItem(item: CountryEntity) {
            if (binding is ItemCountryBinding) {
                Glide.with(context).load(item.flags?.png).into(binding.iFlag)
                binding.tCountryName.text = item.name?.common
                binding.tCapitalName.text = context.getString(R.string.capital, item.capital?.joinToString(""))
                binding.tFlagInformation.text = item.flags?.alt
                binding.tRegion.text = item.region

                binding.root.setOnClickListener{
                    onItemClick?.invoke(item)
                }
            }
        }

        fun bind(model: CountryEntity) {
            bindNewsBodyItem(model)
        }
    }
}