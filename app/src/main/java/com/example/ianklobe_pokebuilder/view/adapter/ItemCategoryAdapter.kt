package com.example.ianklobe_pokebuilder.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ianklobe_pokebuilder.databinding.ItemListItemBinding
import com.example.ianklobe_pokebuilder.model.response.ItemCategoryResponseData
import com.example.ianklobe_pokebuilder.utils.formatName

class ItemCategoryAdapter(
    private val itemList: MutableList<ItemCategoryResponseData> = mutableListOf(),
    private val openDetails: (ItemCategoryResponseData) -> Unit
) : RecyclerView.Adapter<ItemCategoryAdapter.ItemViewHolder>() {


    fun setItemList(newList: List<ItemCategoryResponseData>) {
        itemList.clear()
        itemList.addAll(newList)
        itemList.sortWith(compareBy(String.CASE_INSENSITIVE_ORDER) { it.name })
        notifyDataSetChanged()
    }

    inner class ItemViewHolder(
        private val binding: ItemListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(data: ItemCategoryResponseData) {
            binding.tvItemList.text = data.name.formatName()

            binding.root.setOnClickListener {
                openDetails(data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemCategoryAdapter.ItemViewHolder {
        return ItemViewHolder(
            ItemListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemCategoryAdapter.ItemViewHolder, position: Int) {
        holder.onBind(itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}