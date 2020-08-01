package id.fathonyfath.pokedex

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import id.fathonyfath.pokedex.adapter.PokemonAdapter
import id.fathonyfath.pokedex.di.Injectable
import id.fathonyfath.pokedex.di.ViewModelFactory
import id.fathonyfath.pokedex.model.Pokemon
import id.fathonyfath.pokedex.utils.GridSpacingItemDecoration
import id.fathonyfath.pokedex.utils.observe
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HasAndroidInjector, Injectable {


    @Inject
    lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> = fragmentDispatchingAndroidInjector

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    val viewModel: MainViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
    }

    var pokemonAdapter: PokemonAdapter? = null

    companion object {
        const val DIALOG_TAG = "Detail"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeRecyclerView()

        viewModel.pokemonList.observe(this) {
            it?.let {
                updateAdapterList(it)
            }
        }

        viewModel.loadMoreResult.observe(this) {
            it?.let {
                when (it.first) {
                    is MainViewModel.Result.Success -> {
                        pokemonAdapter?.state = if (it.second) PokemonAdapter.State.LOADING else PokemonAdapter.State.NONE
                    }
                    is MainViewModel.Result.Error -> {
                        pokemonAdapter?.state = PokemonAdapter.State.RETRY
                    }
                }
            }
        }
    }

    private fun showDetailDialog(pokemonId: Int) {
        val detailDialog = DetailDialog.newInstance(pokemonId)
        detailDialog.show(supportFragmentManager, DIALOG_TAG)
    }

    private fun initializeRecyclerView() {
        val spanCount = if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) 4 else 2

        pokemonRecycler.layoutManager = androidx.recyclerview.widget.GridLayoutManager(this, spanCount)
        pokemonAdapter = PokemonAdapter(listOf()) {
            showDetailDialog(it.id)
        }.apply {
            onLoadMore = {
                viewModel.triggerLoadMore(it)
            }

            onRetryClick = {
                this.state = PokemonAdapter.State.LOADING
            }

            state = PokemonAdapter.State.LOADING
        }

        pokemonRecycler.adapter = pokemonAdapter
        val spacingInPixel = resources.getDimensionPixelSize(R.dimen.spacingBetweenItem)
        pokemonRecycler.addItemDecoration(GridSpacingItemDecoration(spanCount, spacingInPixel, true, 0))
    }

    private fun updateAdapterList(pokemonList: List<Pokemon>) {
        pokemonAdapter?.let {
            it.pokemonList = pokemonList
            it.notifyDataSetChanged()
        }
    }

}
