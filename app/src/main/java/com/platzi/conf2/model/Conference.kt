package com.platzi.conf2.model

import java.util.*
import java.io.Serializable

class Conference: Serializable {
    lateinit var title: String
    lateinit var description: String
    lateinit var speaker: String
    lateinit var tag: String
    lateinit var datetime: Date
}