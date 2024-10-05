package com.example.elongaassignmentapp.di

import com.example.elongaassignmentapp.data.repository.LoginRepository
import com.example.elongaassignmentapp.data.repository.LoginRepositoryImpl
import com.example.elongaassignmentapp.ui.screen.login.LoginViewModel
import com.example.elongaassignmentapp.ui.screen.login.LoginViewModelImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<LoginRepository> { LoginRepositoryImpl() }

    viewModel<LoginViewModel> {
        LoginViewModelImpl(
            loginRepository = get()
        )
    }
}
