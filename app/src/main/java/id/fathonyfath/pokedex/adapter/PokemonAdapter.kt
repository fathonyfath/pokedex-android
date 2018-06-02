package id.fathonyfath.pokedex.adapter

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import id.fathonyfath.pokedex.R
import id.fathonyfath.pokedex.model.Pokemon
import id.fathonyfath.pokedex.module.GlideApp
import kotlinx.android.synthetic.main.item_pokemon.view.*
import kotlinx.android.synthetic.main.item_retry.view.*

/**
 * Created by fathonyfath on 17/11/17.
 */

class PokemonAdapter(var pokemonList: List<Pokemon>,
                     private val itemClick: (Pokemon) -> Unit) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val TYPE_ITEM = 0
        const val TYPE_LOADING = 1
        const val TYPE_RETRY = 2
    }

    enum class State {
        NONE, LOADING, RETRY
    }

    var state: State = State.RETRY
        set(value) {
            if (field != value) {
                field = value
                updateRecyclerState()
            }
        }

    var onLoadMore: ((Int) -> Unit)? = null
    var onRetryClick: (() -> Unit)? = null

    private fun updateRecyclerState() {
        if (state == State.LOADING || state == State.RETRY) {
            notifyItemRemoved(pokemonList.size)
            notifyItemInserted(pokemonList.size)
        } else {
            notifyItemRemoved(pokemonList.size)
        }
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
        pokemonList.size -> {
            if (state == State.LOADING) TYPE_LOADING else TYPE_RETRY
        }
        else -> TYPE_ITEM
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ItemViewHolder -> holder.bind(pokemonList[position])
            is RetryViewHolder -> holder.bind()
            else -> onLoadMore?.invoke(pokemonList.size)
        }
    }

    override fun getItemCount(): Int = if (state == State.LOADING || state == State.RETRY) {
        pokemonList.size + 1
    } else {
        pokemonList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent?.context)

        return when (viewType) {
            TYPE_ITEM -> ItemViewHolder(inflater.inflate(R.layout.item_pokemon, parent, false), itemClick)
            TYPE_LOADING -> object : RecyclerView.ViewHolder(inflater.inflate(R.layout.item_loading, parent, false)) {}
            TYPE_RETRY -> RetryViewHolder(inflater.inflate(R.layout.item_retry, parent, false), onRetryClick)
            else -> throw IllegalStateException("Invalid item view type.")
        }
    }


    class ItemViewHolder(view: View,
                         private val itemClick: (Pokemon) -> Unit) :
            RecyclerView.ViewHolder(view) {

        fun bind(pokemon: Pokemon) {
            with(pokemon) {
                itemView.pokemonName.text = pokemon.name
                GlideApp.with(itemView).load(this.imageUrl).transition(withCrossFade()).into(itemView.pokemonImage)
                itemView.pokemonCard.setOnClickListener { itemClick.invoke(this) }
            }
        }
    }

    class RetryViewHolder(view: View,
                          private val retryClick: (() -> Unit)?) :
            RecyclerView.ViewHolder(view) {

        fun bind() {
            itemView.retryButton.setOnClickListener { retryClick?.invoke() }
        }
    }
}