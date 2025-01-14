package com.example.newsreaderdemo.ui.screen.news

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.newsreaderdemo.R
import com.example.newsreaderdemo.domain.model.RequestError
import com.example.newsreaderdemo.ui.preview.PreviewData
import com.example.newsreaderdemo.ui.screen.news.component.NewsItem
import com.example.newsreaderdemo.ui.screen.news.model.NewsUIState
import com.example.newsreaderdemo.ui.theme.AppTheme
import com.example.newsreaderdemo.ui.theme.Dimens
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun NewsScreenLayout(
    uiState: NewsUIState,
    onArticleClick: (articleId: String) -> Unit = {},
    onTakeMeBack: () -> Unit = {},
) {
    when (uiState) {
        is NewsUIState.Idle -> {}

        is NewsUIState.ShowContent -> {
            NewsList(uiState, onArticleClick)
        }

        is NewsUIState.UnAuthorized -> {
            UnAuthorized(onTakeMeBack)
        }
    }
}

@Composable
private fun NewsList(
    uiState: NewsUIState.ShowContent,
    onArticleClick: (articleId: String) -> Unit
) {
    val news = uiState.newsFlow.collectAsLazyPagingItems()

    LazyColumn {
        items(news.itemCount) { index ->
            val item = news[index]
            NewsItem(
                article = item,
                modifier = Modifier.fillMaxWidth(),
                onClick = { item?.let { onArticleClick(it.articleId) } }
            )
        }

        // Handle loading and errors
        when {
            // refreshing
            news.loadState.refresh is LoadState.Loading ||
                    news.loadState.append is LoadState.Loading -> {
                // Show loading spinner at the end when loading first or appending more data
                item {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(modifier = Modifier.padding(Dimens.L))
                    }
                }
            }

            // init loading error
            news.loadState.refresh is LoadState.Error -> {
                item {
                    LoadingErrorTryAgain(
                        errorState = news.loadState.refresh as LoadState.Error,
                        onTryAgain = { news.retry() }
                    )
                }
            }

            // append loading error
            news.loadState.append is LoadState.Error -> {
                item {
                    LoadingErrorTryAgain(
                        errorState = news.loadState.append as LoadState.Error,
                        onTryAgain = { news.retry() }
                    )
                }
            }
        }
    }
}

@Composable
private fun UnAuthorized(onTakeMeBack: () -> Unit) {
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

@Composable
private fun LoadingErrorTryAgain(
    errorState: LoadState.Error,
    onTryAgain: () -> Unit = {},
) {
    val result = errorState.error as RequestError
    val msg = when (result) {
        is RequestError.NetworkError -> stringResource(R.string.error_network_error)
        is RequestError.HttpError.ClientError.Unauthorized -> stringResource(R.string.error_unauthorized)
        is RequestError.HttpError.ClientError.TooManyRequests -> stringResource(R.string.error_plan_limit_exceeded)
        is RequestError.HttpError.ServerError -> stringResource(R.string.error_internal_server_error)
        else -> stringResource(R.string.error_something_went_wrong)
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Dimens.L),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = stringResource(R.string.screen_news_loading_failed))
        Text(text = msg)
        Button(onClick = onTryAgain) {
            Text(text = stringResource(R.string.screen_news_try_again))
        }
    }
}

@Preview
@Composable
private fun UnAuthorizedPreview() {
    AppTheme {
        NewsScreenLayout(
            uiState = NewsUIState.UnAuthorized,
        )
    }
}

@Preview
@Composable
private fun ShowContentPreview() {
    AppTheme {
        val fakeData = List(10) { PreviewData.News.article }
        val pagingData = PagingData.from(fakeData)
        val fakeDataFlow = MutableStateFlow(pagingData)
        NewsScreenLayout(
            uiState = NewsUIState.ShowContent(fakeDataFlow),
        )
    }
}
