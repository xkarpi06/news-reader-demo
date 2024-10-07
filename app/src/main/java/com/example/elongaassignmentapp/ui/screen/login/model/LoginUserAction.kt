package com.example.elongaassignmentapp.ui.screen.login.model

sealed class LoginUserAction {
    data class Authenticate(val user: String, val password: String) : LoginUserAction()
    data object SkipLogin : LoginUserAction()
}
