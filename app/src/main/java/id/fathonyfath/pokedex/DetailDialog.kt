package id.fathonyfath.pokedex

import android.arch.lifecycle.ViewModelProviders
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import id.fathonyfath.pokedex.di.Injectable
import id.fathonyfath.pokedex.di.ViewModelFactory
import id.fathonyfath.pokedex.model.Detail
import id.fathonyfath.pokedex.utils.observe
import kotlinx.android.synthetic.main.dialog_detail.*
import javax.inject.Inject
import id.fathonyfath.pokedex.module.GlideApp

/**
 * Created by fathonyfath on 17/03/18.
 */
class DetailDialog : DialogFragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    val viewModel: MainViewModel by lazy {
        ViewModelProviders.of(activity, viewModelFactory).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStyle(DialogFragment.STYLE_NORMAL, R.style.DetailDialogTheme)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        dialog?.window?.attributes?.windowAnimations = R.style.DetailDialogTheme
    }

    override fun onStart() {
        super.onStart()

        dialog?.apply {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            window?.setLayout(width, height)
        }


    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.dialog_detail, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar.setNavigationOnClickListener {
            viewModel.clearPokemonDetail()
        }

        viewModel.selectedPokemonDetail.observe(this) {
            it?.let {
                toolbar.title = "#${it.id} - ${it.name}"
                GlideApp.with(this).load(it.imageUrl).transition(withCrossFade()).into(pokemonImage)

                decideDetailOrLoading(it.detail)
            }
        }
    }

    private fun decideDetailOrLoading(detail: Detail?) {
        detail?.let {
            showDetail(it)
            return
        }

        showLoading()
    }

    private fun showDetail(detail: Detail) {
        val typesBuilder = StringBuilder()
        detail.types.forEach { typesBuilder.append("\u2022 $it\n") }
        pokemonTypes.text = typesBuilder.toString()

        val profileBuilder = StringBuilder()
        profileBuilder.append("${getString(R.string.pokemonWeight)}: ${detail.profile.weight}\n")
        profileBuilder.append("${getString(R.string.pokemonHeight)}: ${detail.profile.height}\n")
        profileBuilder.append("${getString(R.string.pokemonBaseExperience)}: ${detail.profile.baseExperience}\n")
        pokemonProfile.text = profileBuilder.toString()

        val abilitiesBuilder = StringBuilder()
        detail.abilities.forEach { abilitiesBuilder.append("\u2022 $it\n") }
        pokemonAbilities.text = abilitiesBuilder.toString()

        val statsBuilder = StringBuilder()
        detail.stat.forEach { statsBuilder.append("${it.key}: ${it.value}\n") }
        pokemonStats.text = statsBuilder.toString()

        detailLoading.visibility = View.GONE
        pokemonDetail.visibility = View.VISIBLE
    }

    private fun showLoading() {
        detailLoading.visibility = View.VISIBLE
        pokemonDetail.visibility = View.GONE
    }

    override fun onCancel(dialog: DialogInterface?) {
        super.onCancel(dialog)
        viewModel.clearPokemonDetail()
    }
}