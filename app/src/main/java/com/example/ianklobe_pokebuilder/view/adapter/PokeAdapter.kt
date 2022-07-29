package com.example.ianklobe_pokebuilder.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.ianklobe_pokebuilder.R
import com.example.ianklobe_pokebuilder.databinding.PokeListItemBinding
import com.example.ianklobe_pokebuilder.model.PokeResponseData
import com.example.ianklobe_pokebuilder.utils.BALL_GIF
import com.example.ianklobe_pokebuilder.utils.getPicUrl
import com.example.ianklobe_pokebuilder.utils.getPicUrlShiny

class PokeAdapter(
    private val pokeList: MutableList<PokeResponseData> = mutableListOf()
): RecyclerView.Adapter<PokeAdapter.PokeViewHolder>() {

    fun setPokeList(newList: List<PokeResponseData>) {
        pokeList.clear()
        pokeList.addAll(newList)
        notifyDataSetChanged()
    }

    inner class PokeViewHolder(
        private val binding: PokeListItemBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun onBind(data: PokeResponseData) {
            binding.tvPokeName.text = data.name.capitalize()

            Glide.with(binding.ivPokeSprite)
                .load(data.url.getPicUrl())
                .placeholder(R.drawable.ball_loading)
                .thumbnail(Glide.with(binding.ivPokeSprite).load(R.drawable.ball_loading))
                .dontAnimate()
                .into(binding.ivPokeSprite)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokeViewHolder {
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