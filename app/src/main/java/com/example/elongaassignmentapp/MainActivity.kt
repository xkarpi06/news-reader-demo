package com.example.elongaassignmentapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.elongaassignmentapp.di.appModule
import com.example.elongaassignmentapp.ui.screen.article.ArticleScreen
import com.example.elongaassignmentapp.ui.screen.login.LoginScreen
import com.example.elongaassignmentapp.ui.screen.news.NewsScreen
import com.example.elongaassignmentapp.ui.theme.AppTheme
import kotlinx.serialization.Serializable
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

internal class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        startKoin{
            androidLogger()
            androidContext(this@MainActivity)
            modules(appModule)
        }

        setContent {
            val snackbarHostState = remember { SnackbarHostState() }

            AppTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    snackbarHost = { SnackbarHost(snackbarHostState) },
                ) { innerPadding ->
                    AppNavigation(
                        snackbarHostState = snackbarHostState,
                        modifier = Modifier.padding(innerPadding),
                    )
                }
            }
        }
    }
}

@Composable
internal fun AppNavigation(
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Route.Login, modifier = modifier) {
        composable<Route.Login> { LoginScreen(navController) }
        composable<Route.News> { NewsScreen(navController, snackbarHostState) }
        composable<Route.Article> { navBackStackEntry ->
            val article: Route.Article = navBackStackEntry.toRoute()
            ArticleScreen(navController, snackbarHostState, article.articleId)
        }
    }
}

internal object Route {
    @Serializable
    object Login
    @Serializable
    object News
    @Serializable
    data class Article(val articleId: String)
}
