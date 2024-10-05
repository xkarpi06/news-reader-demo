package com.example.elongaassignmentapp.ui.screen.article.model

import com.example.elongaassignmentapp.domain.model.Article

sealed class ArticleUIState {
    abstract val isRefreshing: Boolean

    data class Idle(override val isRefreshing: Boolean = false) : ArticleUIState()
    data class Success(val article: Article, override val isRefreshing: Boolean = false) : ArticleUIState()
    data class Error(val message: String, override val isRefreshing: Boolean = false) : ArticleUIState()
}

fun ArticleUIState.setRefreshingTrue(): ArticleUIState {
    return when (this) {
        is ArticleUIState.Idle -> this.copy(isRefreshing = true)
        is ArticleUIState.Success -> this.copy(isRefreshing = true)
        is ArticleUIState.Error -> this.copy(isRefreshing = true)
    }
}
