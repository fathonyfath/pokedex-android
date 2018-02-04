package id.fathonyfath.pokedex

import android.arch.lifecycle.ViewModel
import id.fathonyfath.pokedex.data.repository.PokemonRepository
import javax.inject.Inject

/**
 * Created by fathonyfath on 04/02/18.
 */
class MainViewModel @Inject constructor(
        private val pokemonRepository: PokemonRepository
) : ViewModel() {

}