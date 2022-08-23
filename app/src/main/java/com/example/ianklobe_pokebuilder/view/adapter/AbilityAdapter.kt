package com.example.ianklobe_pokebuilder.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ianklobe_pokebuilder.databinding.AbilityListItemBinding
import com.example.ianklobe_pokebuilder.model.response.AbilityResponseData
import com.example.ianklobe_pokebuilder.model.response.SpecificAbilityResponseData
import com.example.ianklobe_pokebuilder.utils.formatName

class AbilityAdapter(
    private val abilityList: MutableList<AbilityResponseData> = mutableListOf(),
    private val openDetails: (AbilityResponseData) -> Unit
) : RecyclerView.Adapter<AbilityAdapter.AbilityViewHolder>() {


    fun setAbilityList(newList: List<AbilityResponseData>) {
        abilityList.clear()
        abilityList.addAll(newList)
        abilityList.sortWith(compareBy(String.CASE_INSENSITIVE_ORDER) { it.name })
        notifyDataSetChanged()
    }

    inner class AbilityViewHolder(
        private val binding: AbilityListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(data: AbilityResponseData) {
            binding.tvAbilityList.text = data.name.formatName()

            binding.root.setOnClickListener {
                openDetails(data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbilityAdapter.AbilityViewHolder {
        return AbilityViewHolder(
            AbilityListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: AbilityAdapter.AbilityViewHolder, position: Int) {
        holder.onBind(abilityList[position])
    }

    override fun getItemCount(): Int {
        return abilityList.size
    }
}