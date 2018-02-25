package id.fathonyfath.pokedex

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import id.fathonyfath.pokedex.data.repository.PokemonRepository
import id.fathonyfath.pokedex.model.Pokemon
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import javax.inject.Inject

/**
 * Created by fathonyfath on 04/02/18.
 */
class MainViewModel @Inject constructor(
        private val pokemonRepository: PokemonRepository
) : ViewModel() {

    private val _pokemonList: MutableLiveData<List<Pokemon>> = MutableLiveData()
    private val _hasMorePokemon: MutableLiveData<Boolean> = MutableLiveData()

    private var currentOffset: Int = 0

    val pokemonList: LiveData<List<Pokemon>>
        get() = _pokemonList

    val hasMorePokemon: LiveData<Boolean>
        get() = _hasMorePokemon

    init {
        _hasMorePokemon.postValue(true)
    }

    fun onNextPokemonList(pokemonList: List<Pokemon>) {
        if (pokemonList.isNotEmpty()) {
            mergePokemonListToLiveData(pokemonList)
            currentOffset += 20
        }

        _hasMorePokemon.postValue(pokemonList.isNotEmpty())
    }

    fun onError(throwable: Throwable) {

    }

    fun triggerLoadMore() {
        pokemonRepository.getPokemonList(currentOffset)
                .subscribe(::onNextPokemonList, ::onError)
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
}