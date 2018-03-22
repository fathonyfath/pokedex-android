package id.fathonyfath.pokedex.data.api.response

import com.google.gson.annotations.SerializedName

/**
 * Created by fathonyfath on 13/03/18.
 */
data class PokemonDetail(
        @SerializedName("name") var name: String,
        @SerializedName("weight") var weight: Int,
        @SerializedName("height") var height: Int,
        @SerializedName("base_experience") var baseExperience: Int,
        @SerializedName("abilities") var abilities: List<PokemonAbility>,
        @SerializedName("stats") var stats: List<PokemonStat>,
        @SerializedName("types") var types: List<PokemonType>
)

data class PokemonAbility(
        @SerializedName("ability") var abilityDetail: PokemonAbilityDetail
)

data class PokemonAbilityDetail(
        @SerializedName("name") var name: String
)

data class PokemonStat(
        @SerializedName("stat") var statDetail: PokemonStatDetail,
        @SerializedName("base_stat") var baseStat: Int
)

data class PokemonStatDetail(
        @SerializedName("name") var name: String
)

data class PokemonType(
        @SerializedName("type") var typeDetail: PokemonTypeDetail
)

data class PokemonTypeDetail(
        @SerializedName("name") var name: String
)