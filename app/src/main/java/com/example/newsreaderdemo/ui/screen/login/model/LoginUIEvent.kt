package com.example.newsreaderdemo.ui.screen.login.model

sealed class LoginUIEvent {
    data object NavigateToNews : LoginUIEvent()
}
