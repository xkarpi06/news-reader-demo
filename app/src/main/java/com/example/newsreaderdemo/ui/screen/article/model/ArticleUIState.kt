package com.example.newsreaderdemo.ui.screen.article.model

import com.example.newsreaderdemo.domain.model.Article

sealed class ArticleUIState {
    abstract val isRefreshing: Boolean

    data class Idle(override val isRefreshing: Boolean = false) : ArticleUIState()
    data class Success(val article: Article, override val isRefreshing: Boolean = false) : ArticleUIState()
    data class Error(override val isRefreshing: Boolean = false) : ArticleUIState()
}

fun ArticleUIState.setRefreshing(value: Boolean): ArticleUIState {
    return when (this) {
        is ArticleUIState.Idle -> this.copy(isRefreshing = value)
        is ArticleUIState.Success -> this.copy(isRefreshing = value)
        is ArticleUIState.Error -> this.copy(isRefreshing = value)
    }
}
