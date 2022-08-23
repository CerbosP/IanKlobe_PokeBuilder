package com.example.ianklobe_pokebuilder.view.adapter

import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.ianklobe_pokebuilder.R
import com.example.ianklobe_pokebuilder.databinding.AbilityListItemBinding
import com.example.ianklobe_pokebuilder.databinding.PokeListItemBinding
import com.example.ianklobe_pokebuilder.model.response.AbilityPokemon
import com.example.ianklobe_pokebuilder.model.response.PokeResponseData
import com.example.ianklobe_pokebuilder.utils.formatName
import com.example.ianklobe_pokebuilder.utils.getPicUrl

class AbilityDetailAdapter(
    private val pokeList: MutableList<AbilityPokemon> = mutableListOf(),
    private val openDetails: (AbilityPokemon) -> Unit
) : RecyclerView.Adapter<AbilityDetailAdapter.AbilityDetailViewHolder>() {


    fun setPokeList(newList: List<AbilityPokemon>) {
        pokeList.clear()
        pokeList.addAll(newList)
        notifyDataSetChanged()
    }

    inner class AbilityDetailViewHolder(
        private val binding: PokeListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(data: AbilityPokemon) {
            binding.tvPokeName.text = data.pokemon.name.formatName()

            // Test
            Glide.with(binding.ivPokeSprite)
                .load(data.pokemon.url.getPicUrl(false))
                .placeholder(R.drawable.ball_loading)
                .thumbnail(Glide.with(binding.ivPokeSprite).load(R.drawable.ball_loading))
                .dontAnimate()
                .listener(object : RequestListener<Drawable> {
                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        var color: Int
                        val drawable = resource as BitmapDrawable
                        val bitmap = drawable.bitmap
                        Palette.Builder(bitmap).generate {
                            it?.let { palette ->
                                color = palette.getDominantColor(
                                    ContextCompat.getColor(
                                        binding.root.context,
                                        R.color.white
                                    )
                                )

                                binding.ivPokeSprite.setBackgroundColor(color)
                            }
                        }
                        return false
                    }

                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }

                })
                .into(binding.ivPokeSprite)

            binding.root.setOnClickListener {
                openDetails(data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbilityDetailAdapter.AbilityDetailViewHolder {
        return AbilityDetailViewHolder(
            PokeListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: AbilityDetailAdapter.AbilityDetailViewHolder, position: Int) {
        holder.onBind(pokeList[position])
    }

    override fun getItemCount(): Int {
        return pokeList.size
    }
}