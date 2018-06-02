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
    private val _loadMoreResult: MutableLiveData<Pair<Result, Boolean>> = MutableLiveData()

    private val _fetchDetailResult: MutableLiveData<Result> = MutableLiveData()

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    val pokemonList: LiveData<List<Pokemon>>
        get() = _pokemonList

    val loadMoreResult: LiveData<Pair<Result, Boolean>>
        get() = _loadMoreResult

    val fetchDetailResult: LiveData<Result>
        get() = _fetchDetailResult

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
        _loadMoreResult.value = null
        compositeDisposable.add(
                pokemonRepository.fetchMorePokemon(offset).subscribe({
                    it.either({
                        _loadMoreResult.postValue(Result.Error() to true)
                    }, {
                        _loadMoreResult.postValue(Result.Success() to it)
                    })
                }, { })
        )
    }

    fun fetchPokemonDetails(pokemonId: Int) {
        _fetchDetailResult.value = null
        compositeDisposable.add(
                pokemonRepository.fetchPokemonDetail(pokemonId).subscribe({
                    it.either({
                        _fetchDetailResult.postValue(Result.Error())
                    }, {
                        _fetchDetailResult.postValue(Result.Success())
                    })
                }, { })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    sealed class Result {
        class Error : Result()
        class Success : Result()
    }
}