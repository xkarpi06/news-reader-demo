package com.example.elongaassignmentapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.elongaassignmentapp.ui.screen.article.ArticleScreen
import com.example.elongaassignmentapp.ui.screen.login.LoginScreen
import com.example.elongaassignmentapp.ui.screen.news.NewsScreen
import com.example.elongaassignmentapp.ui.theme.AppTheme
import kotlinx.serialization.Serializable

internal class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppNavigation(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
internal fun AppNavigation(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Route.Login, modifier = modifier) {
        composable<Route.Login> { LoginScreen(navController) }
        composable<Route.News> { NewsScreen(navController) }
        composable<Route.Article> { ArticleScreen(navController) }
    }
}

internal object Route {
    @Serializable
    object Login
    @Serializable
    object News
    @Serializable
    object Article
}
