package id.fathonyfath.pokedex

import android.arch.lifecycle.ViewModelProviders
import android.content.res.Configuration
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.widget.Toast
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import id.fathonyfath.pokedex.adapter.PokemonAdapter
import id.fathonyfath.pokedex.di.Injectable
import id.fathonyfath.pokedex.di.ViewModelFactory
import id.fathonyfath.pokedex.model.Pokemon
import id.fathonyfath.pokedex.utils.GridSpacingItemDecoration
import id.fathonyfath.pokedex.utils.observe
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HasSupportFragmentInjector, Injectable {


    @Inject
    lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = fragmentDispatchingAndroidInjector

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    val viewModel: MainViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)
    }

    var pokemonAdapter: PokemonAdapter? = null

    companion object {
        val DIALOG_TAG = "Detail"
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

        viewModel.hasMorePokemon.observe(this) {
            it?.let {
                pokemonAdapter?.hasNextItem = it
            }
        }
    }

    private fun showDetailDialog(pokemonId: Int) {
        val detailDialog = DetailDialog.newInstance(pokemonId)
        detailDialog.show(supportFragmentManager, DIALOG_TAG)
    }

    private fun initializeRecyclerView() {
        val spanCount = if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) 4 else 2

        pokemonRecycler.layoutManager = GridLayoutManager(this, spanCount)
        pokemonAdapter = PokemonAdapter(listOf(), true) {
            showDetailDialog(it.id)
        }

        pokemonAdapter?.onLoadMore = {
            viewModel.triggerLoadMore(it)
        }

        viewModel.triggerLoadMore(0)

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
