package com.platzi.conf2.view.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.platzi.conf2.R
import com.platzi.conf2.model.Conference
import com.platzi.conf2.view.adapter.ScheduleAdapter
import com.platzi.conf2.view.adapter.ScheduluListener
import com.platzi.conf2.viewModel.ScheduleViewModel
import kotlinx.android.synthetic.main.fragment_schedule.*


/**
 * A simple [Fragment] subclass.
 * Use the [ScheduleFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ScheduleFragment : Fragment(), ScheduluListener {

    private lateinit var scheduleAdapter: ScheduleAdapter
    private lateinit var viewModel: ScheduleViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_schedule, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(ScheduleViewModel::class.java)
        viewModel.refresh()

        //crear una instancia de nuestro recycle view donde vamos a colocar nestra información
        scheduleAdapter = ScheduleAdapter(this)

        rwSchedule.apply {
            //le decimos al recyclerview que el acomodo de los items va ser verticalmente
            layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
            adapter = scheduleAdapter
        }

        observeViewModel()

    }

    private fun observeViewModel() {
        //creamos un observador para ver los cambios en listSchedule
        viewModel.listSchedule.observe(viewLifecycleOwner, Observer<List<Conference>> { schedule ->
            scheduleAdapter.updateData(schedule)
        })

        //creamos un observador para ver los cambios en isLoading
        viewModel.isLoading.observe(viewLifecycleOwner, Observer<Boolean> {
            if(it != null) {
                rlBaseSchedule.visibility = View.INVISIBLE
            }
        })
    }

    override fun onConferenceClicked(conference: Conference, position: Int) {
        //enviamos un objeto a trav'es de un objeto bundle
        //el bundle se llamar'a conference y lo enviará hacia conference
        val bundle = bundleOf("conference" to conference)
        //nos levará al fragmento scheduleDetailFragmentDialog pasandole el objeto bundle
        NavHostFragment.findNavController(this).navigate(R.id.action_navScheduleFragment_to_scheduleDetailFragmentDialog, bundle)
    }
}