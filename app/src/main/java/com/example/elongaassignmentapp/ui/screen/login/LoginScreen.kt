package com.example.elongaassignmentapp.ui.screen.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import com.example.elongaassignmentapp.Route
import com.example.elongaassignmentapp.ui.screen.login.model.LoginUIEvent
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(
    navController: NavController
) {
    val viewModel: LoginViewModel = koinViewModel()

    LaunchedEffect(Unit) { viewModel.onScreenLaunched() }

    LaunchedEffect(Unit) {
        viewModel.oneTimeEvent.collect { event ->
            when (event) {
                is LoginUIEvent.NavigateToNews -> {
                    // navigate to main screen without possibility to go back via back button/gesture
                    navController.navigate(Route.News) { popUpTo(Route.Login) { inclusive = true } }
                }
            }
        }
    }

    LoginScreenLayout(
        uiState = viewModel.uiState,
        onUserAction = viewModel::onUserAction
    )
}
