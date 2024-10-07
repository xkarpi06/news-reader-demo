package com.example.elongaassignmentapp.ui.screen.news

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.elongaassignmentapp.R
import com.example.elongaassignmentapp.ui.component.Error
import com.example.elongaassignmentapp.ui.preview.PreviewData
import com.example.elongaassignmentapp.ui.screen.news.component.NewsItem
import com.example.elongaassignmentapp.ui.screen.news.model.NewsUIState
import com.example.elongaassignmentapp.ui.theme.AppTheme
import com.example.elongaassignmentapp.ui.theme.Dimens

@Composable
fun NewsScreenLayout(
    uiState: NewsUIState,
    onArticleClick: (articleId: String) -> Unit = {},
    onTakeMeBack: () -> Unit = {},
) {
    Box(modifier = Modifier.fillMaxSize()) {
        if (uiState.isRefreshing) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
        when (uiState) {
            is NewsUIState.Idle -> {}

            is NewsUIState.UnAuthorized -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(Dimens.L),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = stringResource(R.string.screen_news_oops),
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        text = stringResource(R.string.screen_news_unauthorized_message),
                        modifier = Modifier.padding(top = Dimens.L)
                    )
                    Button(
                        onClick = onTakeMeBack,
                        modifier = Modifier.padding(top = Dimens.L)
                    ) {
                        Text(stringResource(R.string.screen_news_take_me_back))
                    }
                }
            }

            is NewsUIState.Success -> {
                LazyColumn {
                    items(uiState.news) { article ->
                        NewsItem(
                            article = article,
                            modifier = Modifier.fillMaxWidth(),
                            onClick = { onArticleClick(article.articleId) }
                        )
                        HorizontalDivider(
                            modifier = Modifier.padding(start = 126.dp, end = 10.dp)
                        )
                    }
                }
            }

            is NewsUIState.Error -> {
                Error(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

@Preview
@Composable
private fun LoadingPreview() {
    AppTheme {
        NewsScreenLayout(
            uiState = NewsUIState.Idle(true),
        )
    }
}

@Preview
@Composable
private fun UnAutorizedPreview() {
    AppTheme {
        NewsScreenLayout(
            uiState = NewsUIState.UnAuthorized(),
        )
    }
}

@Preview
@Composable
private fun ErrorPreview() {
    AppTheme {
        NewsScreenLayout(
            uiState = NewsUIState.Error(),
        )
    }
}

@Preview
@Composable
private fun SuccessPreview() {
    AppTheme {
        NewsScreenLayout(
            uiState = NewsUIState.Success(PreviewData.News.few),
        )
    }
}
