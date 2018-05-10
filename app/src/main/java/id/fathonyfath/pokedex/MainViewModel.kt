package id.fathonyfath.pokedex

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import id.fathonyfath.pokedex.data.repository.PokemonRepository
import id.fathonyfath.pokedex.model.Pokemon
import id.fathonyfath.pokedex.utils.toLiveData
import io.reactivex.BackpressureStrategy
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by fathonyfath on 04/02/18.
 */
class MainViewModel @Inject constructor(
        private val pokemonRepository: PokemonRepository
) : ViewModel() {

    private val _pokemonList: MutableLiveData<List<Pokemon>> = MutableLiveData()
    private val _hasMorePokemon: MutableLiveData<Boolean> = MutableLiveData<Boolean>().apply { value = true }

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    val pokemonList: LiveData<List<Pokemon>>
        get() = _pokemonList

    val hasMorePokemon: LiveData<Boolean>
        get() = _hasMorePokemon

    init {
        val disposable = pokemonRepository.listenToPokemonList()
                .subscribe { _pokemonList.postValue(it) }
        compositeDisposable.add(disposable)
    }

    fun observePokemonWithId(pokemonId: Int): LiveData<Pokemon> {
        return pokemonRepository.listenToPokemonId(pokemonId)
                .toFlowable(BackpressureStrategy.BUFFER)
                .toLiveData()
    }

    fun triggerLoadMore(offset: Int) {
        compositeDisposable.add(
                pokemonRepository.fetchMorePokemon(offset).subscribe({ _hasMorePokemon.postValue(it) }, { })
        )
    }

    fun fetchPokemonDetails(pokemonId: Int) {
        compositeDisposable.add(
                pokemonRepository.fetchPokemonDetail(pokemonId).subscribe({ })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}