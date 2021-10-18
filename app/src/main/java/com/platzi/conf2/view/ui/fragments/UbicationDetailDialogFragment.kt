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
import com.platzi.conf2.model.Ubication
import kotlinx.android.synthetic.main.fragment_speakers_detail_dialog.*
import kotlinx.android.synthetic.main.fragment_ubication_detail_dialog.*

/**
 * A simple [Fragment] subclass.
 * Use the [UbicationDetailDialogFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UbicationDetailDialogFragment : DialogFragment() {

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
        return inflater.inflate(R.layout.fragment_ubication_detail_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tbUbicationDetail.navigationIcon = ContextCompat.getDrawable(view.context, R.drawable.ic_baseline_close_24)
        tbUbicationDetail.navigationIcon?.setTint(Color.WHITE)
        tbUbicationDetail.setTitleTextColor(Color.WHITE)
        tbUbicationDetail.setNavigationOnClickListener {
            dismiss()
        }

        val ubication = Ubication()

        this.context?.let {
            Glide.with(it) //Contexto
                .load(ubication.photo) // URL imagen
                .into(ivUbicationDetail) // Donde lo va a mostrar
        }

        tbUbicationDetail.title = ubication.name
        tvUbicationDetailNamePlace.text = ubication.name
        tvUbicationDetailAddress.text = ubication.address
        tvUbicationDetailPhone.text = ubication.phone
        //Nos abrirá la aplicacion de marcado al hacer click al telefono
        tvUbicationDetailPhone.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:${ubication.phone}")
            }
            startActivity(intent)
        }
        tvUbicationDetailLink.text = ubication.website
        //Nos abrirá la pagina web del lugar
        tvUbicationDetailLink.setOnClickListener {
            val uri = Uri.parse(ubication.website)
            val lauchBrowser = Intent(Intent.ACTION_VIEW, uri)

            startActivity(lauchBrowser)
        }
    }

    override fun onStart() {
        super.onStart()
        //Asignamos el tamaño del Dialog Fragment del tamaño de toda la pantalla
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    }

}