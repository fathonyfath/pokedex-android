package id.fathonyfath.pokedex.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.fathonyfath.pokedex.R
import id.fathonyfath.pokedex.model.Pokemon
import kotlinx.android.synthetic.main.item_pokemon.view.*
import id.fathonyfath.pokedex.module.*

/**
 * Created by fathonyfath on 17/11/17.
 */

class PokemonAdapter(var pokemonList: List<Pokemon>,
                     private val itemClick: (Pokemon) -> Unit) :
        RecyclerView.Adapter<PokemonAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bind(pokemonList[position])
    }

    override fun getItemCount(): Int = pokemonList.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_pokemon, parent, false);
        return ViewHolder(view, itemClick)
    }


    class ViewHolder(view: View,
                     private val itemCLick: (Pokemon) -> Unit) :
            RecyclerView.ViewHolder(view) {

        fun bind(pokemon: Pokemon) {
            with(pokemon) {
                itemView.pokemonName.text = pokemon.name
                GlideApp.with(itemView).load(this.imageUrl).into(itemView.pokemonImage)
                itemView.pokemonCard.setOnClickListener { itemCLick(this) }
            }
        }
    }
}