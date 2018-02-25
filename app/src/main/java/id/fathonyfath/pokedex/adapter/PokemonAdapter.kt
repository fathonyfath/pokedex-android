package id.fathonyfath.pokedex.adapter

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import id.fathonyfath.pokedex.R
import id.fathonyfath.pokedex.model.Pokemon
import kotlinx.android.synthetic.main.item_pokemon.view.*
import id.fathonyfath.pokedex.module.*

/**
 * Created by fathonyfath on 17/11/17.
 */

class PokemonAdapter(var pokemonList: List<Pokemon>,
                     hasNextItem: Boolean,
                     private val itemClick: (Pokemon) -> Unit) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        val TYPE_ITEM = 0
        val TYPE_LOADING = 1
    }

    var hasNextItem: Boolean = hasNextItem
        set(value) {
            if(field != value) {
                field = value
                updateLoadingRecycler()
            }
        }

    var onLoadMore: (() -> Unit)? = null

    private fun updateLoadingRecycler() {
        if (hasNextItem) notifyItemInserted(pokemonList.size) else notifyItemRemoved(pokemonList.size)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)

        val layoutManager = recyclerView.layoutManager
        when (layoutManager) {
            is GridLayoutManager -> layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if (position == pokemonList.size) layoutManager.spanCount else 1
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int = when (position) {
        pokemonList.size -> TYPE_LOADING
        else -> TYPE_ITEM
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ItemViewHolder -> holder.bind(pokemonList[position])
            else -> onLoadMore?.invoke()
        }
    }

    override fun getItemCount(): Int = if (this.hasNextItem) pokemonList.size + 1 else pokemonList.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent?.context)

        return when (viewType) {
            TYPE_ITEM -> ItemViewHolder(inflater.inflate(R.layout.item_pokemon, parent, false), itemClick)
            TYPE_LOADING -> object : RecyclerView.ViewHolder(inflater.inflate(R.layout.item_loading, parent, false)) {}
            else -> throw IllegalStateException("Invalid item view type.")
        }
    }


    class ItemViewHolder(view: View,
                         private val itemCLick: (Pokemon) -> Unit) :
            RecyclerView.ViewHolder(view) {

        fun bind(pokemon: Pokemon) {
            with(pokemon) {
                itemView.pokemonName.text = pokemon.name
                GlideApp.with(itemView).load(this.imageUrl).transition(withCrossFade()).into(itemView.pokemonImage)
                itemView.pokemonCard.setOnClickListener { itemCLick(this) }
            }
        }
    }
}