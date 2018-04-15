package id.fathonyfath.pokedex

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import id.fathonyfath.pokedex.data.repository.PokemonRepository
import id.fathonyfath.pokedex.model.Detail
import id.fathonyfath.pokedex.model.Pokemon
import io.reactivex.disposables.Disposable
import javax.inject.Inject

/**
 * Created by fathonyfath on 04/02/18.
 */
class MainViewModel @Inject constructor(
        val pokemonRepository: PokemonRepository
) : ViewModel() {

    private val _pokemonList: MutableLiveData<List<Pokemon>> = MutableLiveData()
    private val _pokemonMap: MutableLiveData<Map<Int, Pokemon>> = MutableLiveData()
    private val _hasMorePokemon: MutableLiveData<Boolean> = MutableLiveData()

    private var currentOffset: Int = 0
    private var pokemonMapInternal: MutableMap<Int, Pokemon> = mutableMapOf()
    private var networkRequestQueue = mutableSetOf<Int>()

    val pokemonList: LiveData<List<Pokemon>>
        get() = _pokemonList

    val pokemonMap: LiveData<Map<Int, Pokemon>>
        get() = _pokemonMap

    val hasMorePokemon: LiveData<Boolean>
        get() = _hasMorePokemon

    init {
        _hasMorePokemon.postValue(true)
    }

    private fun onNextPokemonList(pokemonList: List<Pokemon>) {
        if (pokemonList.isNotEmpty()) {
            mergePokemonListToPokemonMapAndUpdateLiveData(pokemonList)
            currentOffset += 20
        }

        _hasMorePokemon.postValue(pokemonList.isNotEmpty())
    }

    private fun onError(throwable: Throwable) {

    }

    fun triggerLoadMore() {
        pokemonRepository.getPokemonList(currentOffset)
                .subscribe(::onNextPokemonList, ::onError)
    }

    fun getPokemonDetail(pokemon: Pokemon) {
        fetchPokemonDetail(pokemon)
    }

    private fun fetchPokemonDetail(pokemon: Pokemon) {
        if (!networkRequestQueue.contains(pokemon.id)) {
            pokemonRepository.getPokemonDetail(pokemon.id)
                    .subscribe(::onNextPokemonDetail, ::onError)

            networkRequestQueue.add(pokemon.id)
        }
    }

    private fun onNextPokemonDetail(detailWithId: Pair<Int, Detail>) {
        val (id, detail) = detailWithId
        pokemonMapInternal[id]?.detail = detail

        _pokemonList.postValue(pokemonMapInternal.values.toList())
        _pokemonMap.postValue(pokemonMapInternal)

        networkRequestQueue.remove(id)
    }

    private fun mergePokemonListToPokemonMapAndUpdateLiveData(pokemonList: List<Pokemon>) {
        pokemonList.forEach { pokemonMapInternal[it.id] = it }

        _pokemonList.postValue(pokemonMapInternal.values.toList())
        _pokemonMap.postValue(pokemonMapInternal)
    }
}