package com.example.newsreaderdemo.ui.screen.news

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.newsreaderdemo.data.repository.AuthRepository
import com.example.newsreaderdemo.data.repository.NewsRepository
import com.example.newsreaderdemo.ui.screen.news.model.NewsUIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class NewsViewModel : ViewModel() {
    abstract val uiState: NewsUIState
    abstract fun onScreenLaunched()
}

class NewsViewModelImpl(
    private val authRepository: AuthRepository,
    newsRepository: NewsRepository,
) : NewsViewModel() {
    override var uiState by mutableStateOf<NewsUIState>(NewsUIState.Idle)
        private set

    // Main data flow for this screen
    private val _latestNews = newsRepository.getLatestNewsFlow().cachedIn(viewModelScope)

    override fun onScreenLaunched() {
        initState()
    }

    private fun initState() {
        viewModelScope.launch(Dispatchers.IO) {
            uiState = if (authRepository.isAuthorized()) {
                NewsUIState.ShowContent(_latestNews)
            } else {
                NewsUIState.UnAuthorized
            }
        }
    }
}
