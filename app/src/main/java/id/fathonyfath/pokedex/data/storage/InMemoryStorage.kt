package id.fathonyfath.pokedex.data.storage

import id.fathonyfath.pokedex.model.Pokemon
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject

class InMemoryStorage {

    private val memoryStorage: MutableMap<Int, Pokemon> = mutableMapOf()
    private val subjectMemoryStorage: Subject<MutableMap<Int, Pokemon>> = BehaviorSubject.create()

    init {
        subjectMemoryStorage.onNext(memoryStorage)
    }

    fun clearCache() {
        memoryStorage.clear()
        subjectMemoryStorage.onNext(memoryStorage)
    }

    fun isExist(pokemonId: Int): Boolean {
        val pokemon = memoryStorage[pokemonId]
        return pokemon != null
    }

    fun getPokemon(pokemonId: Int): Pokemon? {
        return memoryStorage[pokemonId]
    }

    fun putPokemon(pokemonId: Int, pokemon: Pokemon) {
        memoryStorage[pokemonId] = pokemon
        subjectMemoryStorage.onNext(memoryStorage)
    }

    fun listenToPokemonMap(): Observable<MutableMap<Int, Pokemon>> {
        return subjectMemoryStorage
    }

    fun listenToPokemonId(pokemonId: Int): Observable<Pair<Int, Pokemon>> {
        return listenToPokemonMap()
                .filter { it.containsKey(pokemonId) }
                .map { pokemonId to it[pokemonId]!! }
    }

}