package com.example.newsreaderdemo.di

import com.example.newsreaderdemo.data.repository.AuthRepository
import com.example.newsreaderdemo.data.repository.AuthRepositoryImpl
import com.example.newsreaderdemo.data.repository.NewsRepository
import com.example.newsreaderdemo.data.repository.NewsRepositoryImpl
import com.example.newsreaderdemo.ui.screen.article.ArticleViewModel
import com.example.newsreaderdemo.ui.screen.article.ArticleViewModelImpl
import com.example.newsreaderdemo.ui.screen.login.LoginViewModel
import com.example.newsreaderdemo.ui.screen.login.LoginViewModelImpl
import com.example.newsreaderdemo.ui.screen.news.NewsViewModel
import com.example.newsreaderdemo.ui.screen.news.NewsViewModelImpl
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<NewsRepository> { NewsRepositoryImpl(newsApi = get()) }
    single<AuthRepository> { AuthRepositoryImpl(context = get()) }

    viewModel<NewsViewModel> {
        NewsViewModelImpl(
            authRepository = get(),
            newsRepository = get()
        )
    }

    viewModel<LoginViewModel> {
        LoginViewModelImpl(
            authRepository = get()
        )
    }

    viewModel<ArticleViewModel> { (articleId: String) ->
        ArticleViewModelImpl(
            articleId = articleId,
            newsRepository = get()
        )
    }
}
