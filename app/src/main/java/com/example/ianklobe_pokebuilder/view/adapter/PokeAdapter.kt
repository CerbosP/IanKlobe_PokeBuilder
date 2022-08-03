package com.example.ianklobe_pokebuilder.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ianklobe_pokebuilder.R
import com.example.ianklobe_pokebuilder.databinding.PokeListItemBinding
import com.example.ianklobe_pokebuilder.model.response.PokeResponseData
import com.example.ianklobe_pokebuilder.utils.format
import com.example.ianklobe_pokebuilder.utils.getPicUrl
import com.example.ianklobe_pokebuilder.utils.getPicUrlShiny

class PokeAdapter(
    private val pokeList: MutableList<PokeResponseData> = mutableListOf(),
    private val openDetails: (PokeResponseData) -> Unit
) : RecyclerView.Adapter<PokeAdapter.PokeViewHolder>() {

    private var wantShiny: Boolean = false

    fun setShiny(value: Boolean) {
        wantShiny = value
    }

    fun getShiny(): Boolean {
        return wantShiny
    }

    fun setPokeList(newList: List<PokeResponseData>) {
        pokeList.clear()
        pokeList.addAll(newList)
        notifyDataSetChanged()
    }

    inner class PokeViewHolder(
        private val binding: PokeListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(data: PokeResponseData) {
            binding.tvPokeName.text = data.name.format()

            if (wantShiny) {
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokeAdapter.PokeViewHolder {
        return PokeViewHolder(
            PokeListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PokeViewHolder, position: Int) {
        holder.onBind(pokeList[position])
    }

    override fun getItemCount(): Int {
        return pokeList.size
    }
}