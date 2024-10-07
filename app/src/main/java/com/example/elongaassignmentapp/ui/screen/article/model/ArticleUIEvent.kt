package com.example.elongaassignmentapp.ui.screen.article.model

sealed class ArticleUIEvent {
    data class ShowSnackbar(val message: String) : ArticleUIEvent()
}
