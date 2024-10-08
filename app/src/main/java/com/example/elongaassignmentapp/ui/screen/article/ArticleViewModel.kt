package com.example.elongaassignmentapp.ui.screen.article

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.elongaassignmentapp.data.repository.NewsRepository
import com.example.elongaassignmentapp.domain.model.Article
import com.example.elongaassignmentapp.domain.model.Result
import com.example.elongaassignmentapp.ui.screen.article.model.ArticleUIEvent
import com.example.elongaassignmentapp.ui.screen.article.model.ArticleUIState
import com.example.elongaassignmentapp.ui.screen.article.model.setRefreshing
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

abstract class ArticleViewModel : ViewModel() {
    abstract val uiState: ArticleUIState
    abstract val oneTimeEvent: SharedFlow<ArticleUIEvent>
    abstract fun onRefresh()
}

class ArticleViewModelImpl(
    private val articleId: String,
    private val newsRepository: NewsRepository
) : ArticleViewModel() {
    override var uiState by mutableStateOf<ArticleUIState>(ArticleUIState.Idle())
        private set

    // Expose SharedFlow for one-time events
    private val _oneTimeEvent = MutableSharedFlow<ArticleUIEvent>()
    override val oneTimeEvent: SharedFlow<ArticleUIEvent> = _oneTimeEvent.asSharedFlow()

    init {
        reloadArticle()
    }

    override fun onRefresh() = reloadArticle()

    private fun reloadArticle() {
        viewModelScope.launch {
            uiState = uiState.setRefreshing(true)
            val result = newsRepository.fetchArticleById(articleId)
            if (result is Result.Success) {
                uiState = ArticleUIState.Success(result.data, false)
            } else  {
                handleFailedRequest(result)
            }
            uiState = uiState.setRefreshing(false)
        }
    }

    private suspend fun handleFailedRequest(result: Result<Article>) {
        uiState = ArticleUIState.Error(false)
        val snackbarText = when (result) {
            is Result.NetworkError -> "Network error"
            is Result.HttpError.ClientError.Unauthorized -> "Unauthorized"
            is Result.HttpError.ClientError.TooManyRequests -> "Newsdata.io plan limit exceeded"
            is Result.HttpError.ServerError -> "Internal server error"
            else -> "Something went wrong"
        }
        _oneTimeEvent.emit(ArticleUIEvent.ShowSnackbar(snackbarText))
    }
}
