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
        val message: String,
        override val isRefreshing: Boolean = false
    ) : NewsUIState()
}

fun NewsUIState.setRefreshingTrue(): NewsUIState {
    return when (this) {
        is NewsUIState.Idle -> this.copy(isRefreshing = true)
        is NewsUIState.Success -> this.copy(isRefreshing = true)
        is NewsUIState.Error -> this.copy(isRefreshing = true)
    }
}
