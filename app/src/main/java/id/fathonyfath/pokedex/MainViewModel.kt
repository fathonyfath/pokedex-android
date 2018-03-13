package id.fathonyfath.pokedex

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import id.fathonyfath.pokedex.data.repository.PokemonRepository
import id.fathonyfath.pokedex.model.Detail
import id.fathonyfath.pokedex.model.Pokemon
import javax.inject.Inject

/**
 * Created by fathonyfath on 04/02/18.
 */
class MainViewModel @Inject constructor(
        private val pokemonRepository: PokemonRepository
) : ViewModel() {

    private val _pokemonList: MutableLiveData<List<Pokemon>> = MutableLiveData()
    private val _hasMorePokemon: MutableLiveData<Boolean> = MutableLiveData()
    private val _selectedPokemonDetail: MutableLiveData<Pokemon?> = MutableLiveData()

    private var currentOffset: Int = 0
    private var pokemonMap: MutableMap<Int, Pokemon> = mutableMapOf()

    val pokemonList: LiveData<List<Pokemon>>
        get() = _pokemonList

    val hasMorePokemon: LiveData<Boolean>
        get() = _hasMorePokemon

    val selectedPokemonDetail: LiveData<Pokemon?>
        get() = _selectedPokemonDetail

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
        _selectedPokemonDetail.postValue(pokemon)

        if(pokemon.detail == null) fetchPokemonDetail(pokemon)
    }

    private fun fetchPokemonDetail(pokemon: Pokemon) {
        pokemonRepository.getPokemonDetail(pokemon.id)
                .subscribe(::onNextPokemonDetail, ::onError)
    }

    private fun onNextPokemonDetail(detail: Detail?) {
        val oldSelectedPokemon = _selectedPokemonDetail.value
        oldSelectedPokemon?.let {
            it.detail = detail
            pokemonMap[it.id] = it

            _pokemonList.postValue(pokemonMap.values.toList())
            _selectedPokemonDetail.postValue(it)
        }

    }

    fun clearPokemonDetail() {
        _selectedPokemonDetail.postValue(null)
    }

    private fun mergePokemonListToLiveData(pokemonList: List<Pokemon>) {
        val oldMutable = _pokemonList.value?.toMutableList()
        if (oldMutable == null) {
            _pokemonList.postValue(pokemonList)
        } else {
            oldMutable.addAll(pokemonList)
            _pokemonList.postValue(oldMutable)
        }
    }

    private fun mergePokemonListToPokemonMapAndUpdateLiveData(pokemonList: List<Pokemon>) {
        pokemonList.forEach { pokemonMap[it.id] = it }

        _pokemonList.postValue(pokemonMap.values.toList())
    }
}