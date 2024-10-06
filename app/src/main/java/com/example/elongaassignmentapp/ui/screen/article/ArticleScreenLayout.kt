package com.example.elongaassignmentapp.ui.screen.article

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.elongaassignmentapp.domain.model.Article
import com.example.elongaassignmentapp.ui.screen.article.model.ArticleUIState
import com.example.elongaassignmentapp.ui.theme.AppTheme

@Composable
fun ArticleScreenLayout(
    uiState: ArticleUIState,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        if (uiState.isRefreshing) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
        Column {
            Text(text = "Article Screen")
            when (uiState) {
                is ArticleUIState.Idle -> {}

                is ArticleUIState.Success -> {
                    Text(text = "articleId: ${uiState.article.articleId}")
                }

                is ArticleUIState.Error -> {
                    Text(text = "Something went wrong")
                }
            }
        }
    }
}

@Preview
@Composable
private fun LoadingPreview() {
    AppTheme {
        ArticleScreenLayout(
            uiState = ArticleUIState.Idle(true),
        )
    }
}

@Preview
@Composable
private fun ErrorPreview() {
    AppTheme {
        ArticleScreenLayout(
            uiState = ArticleUIState.Error(),
        )
    }
}

@Preview
@Composable
private fun SuccessPreview() {
    AppTheme {
        ArticleScreenLayout(
            uiState = ArticleUIState.Success(Article("abcd1234")),
        )
    }
}
