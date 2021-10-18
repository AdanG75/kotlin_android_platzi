package com.platzi.conf2.view.ui.fragments

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.platzi.conf2.R
import com.platzi.conf2.model.Conference
import kotlinx.android.synthetic.main.fragment_schedule_detail_dialog.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * A simple [Fragment] subclass.
 * Use the [ScheduleDetailDialogFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ScheduleDetailDialogFragment : DialogFragment() {

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
        return inflater.inflate(R.layout.fragment_schedule_detail_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Cambiamos el icono del toolbar por un icono de cierre
        tbConferenceDetail.navigationIcon = ContextCompat.getDrawable(view.context, R.drawable.ic_baseline_close_24)
        tbConferenceDetail.setTitleTextColor(Color.WHITE)
        tbConferenceDetail.navigationIcon?.setTint(Color.WHITE)
        //Hace que al darle click al icono se cierre el dialog fragment
        tbConferenceDetail.setNavigationOnClickListener {
            dismiss()
        }

        //recuperamos el objeto bundle enviado en "schedule fragment" y lo
        //convertimos en un objeto conference
        val conference = arguments?.getSerializable("conference") as Conference
        tbConferenceDetail.title = conference.title

        tvScheduleDetailConferenceTitle.text = conference.title
        val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy hh:mm a")

        val cal = Calendar.getInstance()
        cal.time = conference.datetime

        val hourFormat = simpleDateFormat.format(cal.time)
        tvScheduleDetailConferenceHour.text = hourFormat

        tvScheduleDetailSpeakerName.text = conference.speaker
        tvScheduleDetailTag.text = conference.tag
        tvScheduleDetailDescription.text = conference.description
    }

    override fun onStart() {
        super.onStart()
        //Asignamos el tamaño del Dialog Fragment del tamaño de toda la pantalla
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    }

}