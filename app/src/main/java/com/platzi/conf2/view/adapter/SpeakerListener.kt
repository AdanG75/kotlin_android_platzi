package com.platzi.conf2.view.adapter

import com.platzi.conf2.model.Speaker

interface SpeakerListener {
    fun onSpeakerClicked(speaker: Speaker, position: Int)
}