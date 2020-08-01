package id.fathonyfath.pokedex

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import id.fathonyfath.pokedex.data.repository.PokemonRepository
import id.fathonyfath.pokedex.model.Detail
import id.fathonyfath.pokedex.model.Pokemon
import id.fathonyfath.pokedex.model.Profile
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class MainViewModelTest {

    private lateinit var viewModel: MainViewModel

    @Mock
    private lateinit var mockPokemonRepository: PokemonRepository

    @Mock
    private lateinit var pokemonListObserver: Observer<List<Pokemon>>

    @Mock
    private lateinit var hasMoreItemObserver: Observer<Boolean>

    @Mock
    private lateinit var pokemonObserver: Observer<Pokemon>

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        val pokemonList = listOf<Pokemon>()
        val pokemonListObservable = Observable.just(pokemonList)

        `when`(mockPokemonRepository.listenToPokemonList()).thenReturn(pokemonListObservable)

        viewModel = MainViewModel(mockPokemonRepository)
    }

    @Test
    fun getPokemonList_isDispatched() {
        fun generateDummyPokemonData(howMuch: Int): List<Pokemon> {
            return (0 until howMuch).toList().map { Pokemon(it, "Pokemon-$it", "imageUrl-$it", null) }
        }

        `when`(mockPokemonRepository.listenToPokemonList()).thenReturn(Observable.just(generateDummyPokemonData(20)))
        viewModel = MainViewModel(mockPokemonRepository)
        viewModel.pokemonList.observeForever(pokemonListObserver)

        verify(pokemonListObserver).onChanged(generateDummyPokemonData(20))
    }

    @Test
    fun getHasMorePokemon_isDispatched() {
        viewModel.hasMorePokemon.observeForever(hasMoreItemObserver)

        `when`(mockPokemonRepository.fetchMorePokemon(ArgumentMatchers.anyInt())).thenReturn(Single.just(false))

        viewModel.triggerLoadMore(0)
        verify(hasMoreItemObserver).onChanged(false)

        `when`(mockPokemonRepository.fetchMorePokemon(ArgumentMatchers.anyInt())).thenReturn(Single.just(true))
        viewModel.triggerLoadMore(0)

        verify(hasMoreItemObserver, times(2)).onChanged(true)
    }

    @Test
    fun observePokemonWithId_isDispatched() {
        val pokemonSubject: Subject<Pokemon> = PublishSubject.create()
        `when`(mockPokemonRepository.listenToPokemonId(ArgumentMatchers.anyInt())).thenReturn(pokemonSubject)

        viewModel.observePokemonWithId(0).observeForever(pokemonObserver)

        val dispatchingPokemon = Pokemon(0, "Pokemon-0", "imageUrl-0", null)
        pokemonSubject.onNext(dispatchingPokemon)

        verify(pokemonObserver).onChanged(dispatchingPokemon)

        val anotherDispatchingPokemon = Pokemon(0, "Pokemon-0", "imageUrl-0",
                Detail(
                        listOf("Grass"),
                        listOf("Nothing"),
                        Profile(1, 1, 1),
                        mapOf("Base" to 1)
                )
        )

        pokemonSubject.onNext(anotherDispatchingPokemon)

        verify(pokemonObserver).onChanged(anotherDispatchingPokemon)
    }

    @Test
    fun fetchPokemonDetails_isCorrect() {
        `when`(mockPokemonRepository.fetchPokemonDetail(0)).thenReturn(Completable.complete())

        viewModel.fetchPokemonDetails(0)
    }
}