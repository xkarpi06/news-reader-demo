package com.example.elongaassignmentapp.ui.screen.login.model

sealed class LoginUIEvent {
    data object SuccessfulLogin : LoginUIEvent()
}