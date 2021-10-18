package com.platzi.conf2.view.adapter

import com.platzi.conf2.model.Conference

interface ScheduluListener {
    fun onConferenceClicked(conference: Conference, position: Int)
}