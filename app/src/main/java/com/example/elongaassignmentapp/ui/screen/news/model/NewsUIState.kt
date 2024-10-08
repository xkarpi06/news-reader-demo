package com.example.elongaassignmentapp.ui.screen.news.model

import androidx.paging.PagingData
import com.example.elongaassignmentapp.domain.model.Article
import kotlinx.coroutines.flow.Flow

sealed class NewsUIState {

    /**
     * User has just arrived
     */
    data object Idle : NewsUIState()

    /**
     * User is not signed signed in (skipped sign in)
     */
    data object UnAuthorized : NewsUIState()

    /**
     * Show screen content (news)
     */
    data class ShowContent(val newsFlow: Flow<PagingData<Article>>) : NewsUIState()
}
