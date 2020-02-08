package com.strum.app.models

import java.util.*

data class PersonalTaskModel(var taskId: Int, var priority: String, var taskName: String, var deadline: Date, var status: String)