package com.example.newsreaderdemo.ui.screen.news.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.newsreaderdemo.R
import com.example.newsreaderdemo.domain.model.Article
import com.example.newsreaderdemo.ui.preview.PreviewData
import com.example.newsreaderdemo.ui.theme.AppTheme
import com.example.newsreaderdemo.ui.theme.Dimens
import java.time.format.DateTimeFormatter

@Composable
fun NewsItem(
    article: Article?,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    article?.let {
        NewsItemLayout(
            article = article,
            modifier = modifier,
            onClick = onClick,
        )
    } ?: NewsItemPlaceholder(modifier)
}

@Composable
private fun NewsItemPlaceholder(modifier: Modifier = Modifier) {
    Surface(modifier = modifier.height(Dimens.News.Item.height)) {}
}

@Composable
private fun NewsItemLayout(
    article: Article,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    Surface(
        modifier = modifier
            .height(Dimens.News.Item.height)
            .clickable(onClick = onClick),
    ) {
        Row(modifier = Modifier.padding(Dimens.News.Padding.horizontal)) {
            AsyncImage(
                model = article.imageUrl,
                contentDescription = "Article image",
                modifier = Modifier
                    .fillMaxHeight()
                    .width(106.dp),
                fallback = painterResource(id = R.drawable.baseline_newspaper_24),
                placeholder = painterResource(id = R.drawable.baseline_newspaper_24),
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = article.title ?: "",
                    maxLines = 2,
                    style = MaterialTheme.typography.titleMedium,
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Bottom
                ) {
                    CompositionLocalProvider(LocalTextStyle provides MaterialTheme.typography.bodyMedium) {
                        article.pubDate?.let {
                            Text(text = it.format(DateTimeFormatter.ofPattern("d.M. H:mm")))
                            Spacer(modifier = Modifier.width(10.dp))
                        }
                        Text(
                            text = if (!article.creator.isNullOrEmpty()) article.creator.first() else "",
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.weight(1f),
                            maxLines = 1,
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    AsyncImage(
                        model = article.sourceIcon,
                        contentDescription = "Source icon",
                        modifier = Modifier.size(25.dp)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun ComponentPreview() {
    AppTheme {
        NewsItem(
            article = PreviewData.News.article,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Preview
@Composable
private fun PlaceholderPreview() {
    AppTheme {
        NewsItem(
            article = null,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
