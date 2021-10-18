package com.platzi.conf2.view.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.platzi.conf2.R
import com.platzi.conf2.model.Speaker
import com.platzi.conf2.view.adapter.SpeakerAdapter
import com.platzi.conf2.view.adapter.SpeakerListener
import com.platzi.conf2.viewModel.SpeakerViewModel
import kotlinx.android.synthetic.main.fragment_speaker.*


/**
 * A simple [Fragment] subclass.
 * Use the [SpeakerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SpeakerFragment : Fragment(), SpeakerListener {

    private lateinit var speakerAdapter: SpeakerAdapter
    private lateinit var speakerViewModel : SpeakerViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_speaker, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //obtenemos los atributos de nuestro view model "SpeakerViewModel"
        speakerViewModel = ViewModelProvider(this).get(SpeakerViewModel::class.java)
        //Obtenemos la data de firestore
        speakerViewModel.refresh()

        //crear una instancia de nuestro recycle view donde vamos a colocar nestra información
        speakerAdapter = SpeakerAdapter(this)

        //controlamos las propiedades del recyclerView de esta forma
        rwSpeakers.apply {
            layoutManager = GridLayoutManager(view.context, 2)
            adapter = speakerAdapter
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        speakerViewModel.listSpeaker.observe(viewLifecycleOwner, Observer<List<Speaker>>{ speaker ->
            speakerAdapter.updateData(speaker)
        })

        speakerViewModel.isLoading.observe(viewLifecycleOwner, Observer<Boolean> {
            if (it != null) {
                rlSpeakerBase.visibility = View.INVISIBLE
            }
        })
    }

    override fun onSpeakerClicked(speaker: Speaker, position: Int) {
        //creamos un objeto bundle para ser enviado a "speakerDetailFragmentDialog
        val bundle = bundleOf("speaker" to speaker)
        findNavController().navigate(R.id.speakerDetailFragmentDialog, bundle)
    }

}