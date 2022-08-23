package com.example.ianklobe_pokebuilder.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ianklobe_pokebuilder.databinding.ItemListItemBinding
import com.example.ianklobe_pokebuilder.model.response.ItemResponseData
import com.example.ianklobe_pokebuilder.utils.formatName

class ItemAdapter(
    private val itemList: MutableList<ItemResponseData> = mutableListOf(),
    private val openDetails: (ItemResponseData) -> Unit
) : RecyclerView.Adapter<ItemAdapter.ItemCategoryViewHolder>() {


    fun setItemList(newList: List<ItemResponseData>) {
        itemList.clear()
        itemList.addAll(newList)
        itemList.sortWith(compareBy(String.CASE_INSENSITIVE_ORDER) { it.name })
        notifyDataSetChanged()
    }

    inner class ItemCategoryViewHolder(
        private val binding: ItemListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(data: ItemResponseData) {
            binding.tvItemList.text = data.name.formatName()

            binding.root.setOnClickListener {
                openDetails(data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemAdapter.ItemCategoryViewHolder {
        return ItemCategoryViewHolder(
            ItemListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemAdapter.ItemCategoryViewHolder, position: Int) {
        holder.onBind(itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}