package com.example.elongaassignmentapp.ui.screen.news.model

sealed class NewsUIEvent {
    data class ShowSnackbar(val message: String) : NewsUIEvent()
}
