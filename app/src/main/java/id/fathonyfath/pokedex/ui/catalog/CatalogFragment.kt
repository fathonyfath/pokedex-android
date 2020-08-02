package id.fathonyfath.pokedex.ui.catalog

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import id.fathonyfath.pokedex.R
import id.fathonyfath.pokedex.databinding.FragmentCatalogBinding
import id.fathonyfath.pokedex.utils.viewBinding

class CatalogFragment : Fragment(R.layout.fragment_catalog) {

    private val binding by viewBinding(FragmentCatalogBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.detailButton.setOnClickListener {
            findNavController().navigate(R.id.action_catalogFragment_to_detailDialog)
        }
    }
}