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

    private val mutablePokemonList: MutableLiveData<List<Pokemon>> = MutableLiveData()
    private var currentOffset: Int = 0

    val pokemonList: LiveData<List<Pokemon>>
        get() = mutablePokemonList

    init {
        pokemonRepository.getPokemonList(currentOffset)
                .subscribe({ pokemonList ->
                    run {
                        if (pokemonList.isNotEmpty()) {
                            mergePokemonListToLiveData(pokemonList)
                            currentOffset += 20
                        }

                    }
                })
    }

    fun triggerLoadMore() {
        pokemonRepository.getPokemonList(currentOffset)
                .subscribe({ pokemonList ->
                    run {
                        if (pokemonList.isNotEmpty()) {
                            mergePokemonListToLiveData(pokemonList)
                            currentOffset += 20
                        }

                    }
                })
    }

    private fun mergePokemonListToLiveData(pokemonList: List<Pokemon>) {
        val oldMutable = mutablePokemonList.value?.toMutableList()
        if (oldMutable == null) {
            mutablePokemonList.postValue(pokemonList)
        } else {
            oldMutable.addAll(pokemonList)
            mutablePokemonList.postValue(oldMutable)
        }
    }
}