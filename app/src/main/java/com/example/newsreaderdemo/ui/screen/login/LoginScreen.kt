package com.example.newsreaderdemo.ui.screen.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import com.example.newsreaderdemo.Route
import com.example.newsreaderdemo.ui.screen.login.model.LoginUIEvent
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(
    navController: NavController
) {
    val viewModel: LoginViewModel = koinViewModel()

    // navigate to news screen without possibility to go back via back button/gesture
    fun navigateToNews() = navController.navigate(Route.News) { popUpTo(Route.Login) { inclusive = true } }

    LaunchedEffect(Unit) {
        viewModel.onScreenLaunched()
        viewModel.oneTimeEvent.collect { event ->
            when (event) {
                is LoginUIEvent.NavigateToNews -> navigateToNews()
            }
        }
    }

    LoginScreenLayout(
        uiState = viewModel.uiState,
        onSubmit = viewModel::onSubmit,
        onSkip = { navigateToNews() },
    )
}
