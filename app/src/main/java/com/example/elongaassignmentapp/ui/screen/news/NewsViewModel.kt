package com.example.elongaassignmentapp.ui.screen.news

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.elongaassignmentapp.data.repository.AuthRepository
import com.example.elongaassignmentapp.data.repository.NewsRepository
import com.example.elongaassignmentapp.domain.model.Article
import com.example.elongaassignmentapp.domain.model.Result
import com.example.elongaassignmentapp.ui.screen.news.model.NewsUIEvent
import com.example.elongaassignmentapp.ui.screen.news.model.NewsUIState
import com.example.elongaassignmentapp.ui.screen.news.model.setRefreshing
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

abstract class NewsViewModel : ViewModel() {
    abstract val uiState: NewsUIState
    abstract val oneTimeEvent: SharedFlow<NewsUIEvent>
    abstract fun onScreenLaunched()
    abstract fun onRefresh()
}

class NewsViewModelImpl(
    private val authRepository: AuthRepository,
    private val newsRepository: NewsRepository,
) : NewsViewModel() {
    override var uiState by mutableStateOf<NewsUIState>(NewsUIState.Idle())
        private set

    // Expose SharedFlow for one-time events
    private val _oneTimeEvent = MutableSharedFlow<NewsUIEvent>()
    override val oneTimeEvent: SharedFlow<NewsUIEvent> = _oneTimeEvent.asSharedFlow()

    override fun onScreenLaunched() {
        refreshNews()
    }

    override fun onRefresh() = refreshNews()

    private fun refreshNews() {
        viewModelScope.launch(Dispatchers.IO) {
            uiState = uiState.setRefreshing(true)
            if (authRepository.isAuthorized()) {
                val result = newsRepository.fetchLatestNews()
                if (result is Result.Success) {
                    uiState = NewsUIState.Success(result.data)
                } else {
                    handleFailedRequest(result)
                }
            } else {
                uiState = NewsUIState.UnAuthorized()
            }
            uiState = uiState.setRefreshing(false)
        }
    }

    private suspend fun handleFailedRequest(result: Result<List<Article>>) {
        // Keep the existing news if already loaded
        if (uiState is NewsUIState.Success) {
            // Keep the previous news and just emit the error event
            _oneTimeEvent.emit(NewsUIEvent.ShowSnackbar("Failed to reload news"))
        } else {
            // No news loaded yet, so show error
            uiState = NewsUIState.Error()
            val snackbarText = when (result) {
                is Result.NetworkError -> "Network error"
                is Result.HttpError.ClientError.Unauthorized -> "Unauthorized"
                is Result.HttpError.ClientError.TooManyRequests -> "Newsdata.io plan limit exceeded"
                is Result.HttpError.ServerError -> "Internal server error"
                else -> "Something went wrong"
            }
            _oneTimeEvent.emit(NewsUIEvent.ShowSnackbar(snackbarText))
        }
    }
}
