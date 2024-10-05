package com.example.elongaassignmentapp.ui.screen.article

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.elongaassignmentapp.data.repository.NewsRepository
import com.example.elongaassignmentapp.domain.model.Article
import com.example.elongaassignmentapp.ui.screen.article.model.ArticleUIEvent
import com.example.elongaassignmentapp.ui.screen.article.model.ArticleUIState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

internal abstract class ArticleViewModel : ViewModel() {
    abstract val uiState: ArticleUIState
    abstract val oneTimeEvent: SharedFlow<ArticleUIEvent>
    abstract fun onRefresh()
}

internal class ArticleViewModelImpl(
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
        uiState = ArticleUIState.Success(Article(articleId))
        // TODO: implement 
    }
}
