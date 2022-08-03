package com.example.ianklobe_pokebuilder.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ianklobe_pokebuilder.R
import com.example.ianklobe_pokebuilder.databinding.PokeListItemBinding
import com.example.ianklobe_pokebuilder.model.response.PokeResponseData
import com.example.ianklobe_pokebuilder.model.response.TypeResponseData
import com.example.ianklobe_pokebuilder.utils.extractId
import com.example.ianklobe_pokebuilder.utils.format
import com.example.ianklobe_pokebuilder.utils.getPicUrl
import com.example.ianklobe_pokebuilder.utils.getPicUrlShiny

class TypeAdapter (
    private val typeList: MutableList<TypeResponseData> = mutableListOf(),
    private val openDetails: (PokeResponseData) -> Unit
): RecyclerView.Adapter<TypeAdapter.TypeViewHolder>() {

    private var wantShiny: Boolean = false
    private var genEnd: Int = 0
    private var genStart: Int = 0

    fun setShiny(value: Boolean) {
        wantShiny = value
    }

    fun setGenEnd(value: Int) {
        genEnd = value
    }

    fun setGenStart(value: Int) {
        genStart = value
    }

    fun setTypeList(newList: List<TypeResponseData>) {
        typeList.clear()
        for(data in newList) {
            if(data.pokemon.url.extractId() in (genStart + 1) until genEnd) {
                typeList.add(data)
            }
            notifyDataSetChanged()
        }
    }

    inner class TypeViewHolder(
        private val binding: PokeListItemBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun onBind(data: PokeResponseData) {
            binding.tvPokeName.text = data.name.format()

            if(wantShiny){
                Glide.with(binding.ivPokeSprite)
                    .load(data.url.getPicUrlShiny())
                    .placeholder(R.drawable.ball_loading)
                    .thumbnail(Glide.with(binding.ivPokeSprite).load(R.drawable.ball_loading))
                    .dontAnimate()
                    .into(binding.ivPokeSprite)
            } else {
                Glide.with(binding.ivPokeSprite)
                    .load(data.url.getPicUrl())
                    .placeholder(R.drawable.ball_loading)
                    .thumbnail(Glide.with(binding.ivPokeSprite).load(R.drawable.ball_loading))
                    .dontAnimate()
                    .into(binding.ivPokeSprite)
            }

            binding.root.setOnClickListener{
                openDetails(data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TypeAdapter.TypeViewHolder {
        return TypeViewHolder(
            PokeListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TypeAdapter.TypeViewHolder, position: Int) {
        holder.onBind(typeList[position].pokemon)
    }

    override fun getItemCount(): Int {
        return typeList.size
    }
}