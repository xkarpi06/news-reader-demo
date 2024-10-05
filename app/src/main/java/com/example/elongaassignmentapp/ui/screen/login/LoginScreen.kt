package com.example.elongaassignmentapp.ui.screen.login

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.elongaassignmentapp.Route
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun LoginScreen(
    navController: NavController
) {
    val viewModel: LoginViewModel = koinViewModel()

    LoginScreenLayout(
        onLoginClick = { navController.navigate(Route.News) { popUpTo(Route.Login) { inclusive = true } } }
    )
}
