package com.example.elongaassignmentapp.ui.screen.news.model

import com.example.elongaassignmentapp.domain.model.Article

sealed class NewsUIState {
    abstract val isRefreshing: Boolean

    data class Idle(override val isRefreshing: Boolean = false) : NewsUIState()
    data class Success(
        val news: List<Article>,
        override val isRefreshing: Boolean = false
    ) : NewsUIState()
    data class Error(
        override val isRefreshing: Boolean = false
    ) : NewsUIState()
}

fun NewsUIState.setRefreshing(value: Boolean): NewsUIState {
    return when (this) {
        is NewsUIState.Idle -> this.copy(isRefreshing = value)
        is NewsUIState.Success -> this.copy(isRefreshing = value)
        is NewsUIState.Error -> this.copy(isRefreshing = value)
    }
}
