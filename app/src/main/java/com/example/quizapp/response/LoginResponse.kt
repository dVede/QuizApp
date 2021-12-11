package com.example.quizapp.response

import com.example.quizapp.model.User

data class LoginResponse(val error: Boolean, val msg: String, val user: User)