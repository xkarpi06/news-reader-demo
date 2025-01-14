package com.example.newsreaderdemo.ui.screen.article.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.newsreaderdemo.R
import com.example.newsreaderdemo.ui.component.ShareButton
import com.example.newsreaderdemo.ui.preview.PreviewData
import com.example.newsreaderdemo.ui.screen.article.model.ArticleUIState
import com.example.newsreaderdemo.ui.theme.AppTheme
import com.example.newsreaderdemo.ui.theme.Dimens
import java.time.format.DateTimeFormatter

@Composable
fun Article(
    uiState: ArticleUIState.Success,
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(Dimens.M),
    ) {
        Header(uiState)
        Body(uiState)
        ShareSection(uiState)
    }
}

@Composable
private fun Header(
    uiState: ArticleUIState.Success,
) {
    // title
    Text(
        text = uiState.article.title ?: "<title>",
        modifier = Modifier.padding(horizontal = Dimens.News.Padding.horizontal),
        style = MaterialTheme.typography.headlineMedium,
        fontWeight = FontWeight.Bold
    )

    // author
    if (!uiState.article.creator.isNullOrEmpty()) {
        Text(
            text = uiState.article.creator.first().uppercase(),
            modifier = Modifier.padding(horizontal = Dimens.News.Padding.horizontal),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.primary,
            maxLines = 1,
        )
    }

    // image
    AsyncImage(
        model = uiState.article.imageUrl,
        contentDescription = "Article image",
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(16 / 9f),
        contentScale = ContentScale.Crop,
        fallback = painterResource(id = R.drawable.baseline_newspaper_24),
        placeholder = painterResource(id = R.drawable.baseline_newspaper_24),
    )
}

@Composable
private fun Body(uiState: ArticleUIState.Success) {
    // source
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Dimens.News.Padding.horizontal),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = uiState.article.sourceName ?: "<source_name>",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleMedium,
            maxLines = 1,
        )
        Spacer(modifier = Modifier.width(10.dp))
        AsyncImage(
            model = uiState.article.sourceIcon,
            contentDescription = "Source icon",
            modifier = Modifier.size(25.dp),
        )
    }

    // date
    Text(
        text = uiState.article.pubDate?.format(DateTimeFormatter.ofPattern("d.M. H:mm"))
            ?: "<date_published>",
        modifier = Modifier.padding(horizontal = Dimens.News.Padding.horizontal),
        color = MaterialTheme.colorScheme.primary,
        style = MaterialTheme.typography.bodySmall,
        fontWeight = FontWeight.Bold,
    )

    // description
    Text(
        text = uiState.article.description ?: "<description>",
        modifier = Modifier.padding(horizontal = Dimens.News.Padding.horizontal),
    )
}

@Composable
private fun ShareSection(uiState: ArticleUIState.Success) {
    val context = LocalContext.current

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        ShareButton(
            textToShare = uiState.article.link ?: "",
            context = context,
            modifier = Modifier.padding(bottom = Dimens.L),
            enabled = uiState.article.link != null,
        )
    }
}

@Preview
@Composable
private fun ComponentPreview() {
    AppTheme {
        Article(
            uiState = ArticleUIState.Success(PreviewData.News.article),
        )
    }
}
