package com.platzi.conf2.view.ui.fragments

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.platzi.conf2.R
import com.platzi.conf2.model.Conference
import com.platzi.conf2.model.Speaker
import kotlinx.android.synthetic.main.fragment_schedule_detail_dialog.*
import kotlinx.android.synthetic.main.fragment_speakers_detail_dialog.*

/**
 * A simple [Fragment] subclass.
 * Use the [SpeakersDetailDialogFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SpeakersDetailDialogFragment : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Le asignamos el style que creamos al Dialog Fragment
        setStyle(STYLE_NORMAL, R.style.fullScreenDialogStyle)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_speakers_detail_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Cambiamos el icono del toolbar por un icono de cierre
        tbSpeakerDetail.navigationIcon = ContextCompat.getDrawable(view.context, R.drawable.ic_baseline_close_24)
        tbSpeakerDetail.navigationIcon?.setTint(Color.WHITE)
        tbSpeakerDetail.setTitleTextColor(Color.WHITE)
        //Hace que al darle click al icono se cierre el dialog fragment
        tbSpeakerDetail.setNavigationOnClickListener {
            dismiss()
        }
        val speaker = arguments?.getSerializable("speaker") as Speaker
        this.context?.let {
            Glide.with(it) //Contexto
                .load(speaker.imageSpeaker) // URL imagen
                .into(ivSpeakerImageDetail) // Donde lo va a mostrar
        }

        tbSpeakerDetail.title = speaker.name
        tvSpeakerDetailSpeakerName.text = speaker.name
        tvSpeakerDetailJobTitle.text = speaker.jobtitle
        tvSpeakerDetailJob.text = speaker.workplace
        tvSpeakerDetailTwitter.setOnClickListener {
            val url = speaker.twitter
            val uri = Uri.parse("https://twitter.com/$url")
            val lauchBrowser = Intent(Intent.ACTION_VIEW, uri)

            startActivity(lauchBrowser)
        }
        tvSpeakerDetailDescription.text = speaker.biography

    }

    override fun onStart() {
        super.onStart()
        //Asignamos el tamaño del Dialog Fragment del tamaño de toda la pantalla
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    }
}