package com.example.elongaassignmentapp.ui.preview

import com.example.elongaassignmentapp.domain.model.Article
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object PreviewData {
    object News {
        val article = Article(
            articleId = "68c0c5dbc904223a4d9617585cd687f5",
            title = "OpenAI closes the largest VC round of all time",
            link = "https://techcrunch.com/2024/10/05/openai-closes-the-largest-vc-round-of-all-time/",
            creator = listOf("Cody Corrall"),
            description = "Welcome back to Week in Review. This week, we’re diving into OpenAI’s $6.6 billion fundraising round, the fifth Cybertruck recall in less than a year, and a neat project that’s Shazam-ing songs heard on a San Francisco street. Let’s get into it. OpenAI closed the largest VC round of all time this week. The startup [...]© 2024 TechCrunch. All rights reserved. For personal use only.",
            pubDate = LocalDateTime.parse("2024-10-05 20:15:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
            imageUrl = "https://techcrunch.com/wp-content/uploads/2022/09/GettyImages-619650618.jpg?resize=1200%2C900",
            sourceName = "Tech Crunch",
            sourceIcon = "https://i.bytvi.com/domain_icons/techcrunch.png"
        )

        val few = listOf(
            article, article, article
        )
    }
}
