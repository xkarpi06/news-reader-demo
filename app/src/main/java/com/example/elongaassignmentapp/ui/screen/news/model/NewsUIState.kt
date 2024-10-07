package com.example.elongaassignmentapp.ui.screen.news.model

import com.example.elongaassignmentapp.domain.model.Article

sealed class NewsUIState {
    abstract val isRefreshing: Boolean

    /**
     * User has just arrived and content is loading
     */
    data class Idle(override val isRefreshing: Boolean = false) : NewsUIState()

    /**
     * User is not signed signed in (skipped sign in)
     */
    data class UnAuthorized(override val isRefreshing: Boolean = false) : NewsUIState()

    /**
     * Content fetched successfuly
     */
    data class Success(
        val news: List<Article>,
        override val isRefreshing: Boolean = false
    ) : NewsUIState()

    /**
     * Content fetching failed
     */
    data class Error(
        override val isRefreshing: Boolean = false
    ) : NewsUIState()
}

fun NewsUIState.setRefreshing(value: Boolean): NewsUIState {
    return when (this) {
        is NewsUIState.Idle -> this.copy(isRefreshing = value)
        is NewsUIState.UnAuthorized -> this.copy(isRefreshing = value)
        is NewsUIState.Success -> this.copy(isRefreshing = value)
        is NewsUIState.Error -> this.copy(isRefreshing = value)
    }
}
