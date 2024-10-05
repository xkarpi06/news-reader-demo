package com.example.elongaassignmentapp.ui.screen.news

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.elongaassignmentapp.data.repository.NewsRepository
import com.example.elongaassignmentapp.ui.screen.news.model.NewsUIEvent
import com.example.elongaassignmentapp.ui.screen.news.model.NewsUIState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

abstract class NewsViewModel : ViewModel() {
    abstract val uiState: NewsUIState
    abstract val oneTimeEvent: SharedFlow<NewsUIEvent>
    abstract fun onRefresh()
}

class NewsViewModelImpl(
    private val newsRepository: NewsRepository,
) : NewsViewModel() {
    override var uiState by mutableStateOf<NewsUIState>(NewsUIState.Idle())
        private set

    // Expose SharedFlow for one-time events
    private val _oneTimeEvent = MutableSharedFlow<NewsUIEvent>()
    override val oneTimeEvent: SharedFlow<NewsUIEvent> = _oneTimeEvent.asSharedFlow()

    init {
        refreshNews()
    }

    override fun onRefresh() = refreshNews()

    private fun refreshNews() {
        // TODO: implement 
    }
}
