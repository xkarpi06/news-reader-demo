package com.example.elongaassignmentapp.di

import com.example.elongaassignmentapp.data.repository.LoginRepository
import com.example.elongaassignmentapp.data.repository.LoginRepositoryImpl
import com.example.elongaassignmentapp.data.repository.NewsRepository
import com.example.elongaassignmentapp.data.repository.NewsRepositoryImpl
import com.example.elongaassignmentapp.ui.screen.article.ArticleViewModel
import com.example.elongaassignmentapp.ui.screen.article.ArticleViewModelImpl
import com.example.elongaassignmentapp.ui.screen.login.LoginViewModel
import com.example.elongaassignmentapp.ui.screen.login.LoginViewModelImpl
import com.example.elongaassignmentapp.ui.screen.news.NewsViewModel
import com.example.elongaassignmentapp.ui.screen.news.NewsViewModelImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<NewsRepository> { NewsRepositoryImpl() }
    single<LoginRepository> { LoginRepositoryImpl() }

    viewModel<NewsViewModel> {
        NewsViewModelImpl(
            newsRepository = get()
        )
    }

    viewModel<LoginViewModel> {
        LoginViewModelImpl(
            loginRepository = get()
        )
    }

    viewModel<ArticleViewModel> { (articleId: String) ->
        ArticleViewModelImpl(
            articleId = articleId,
            newsRepository = get()
        )
    }
}
