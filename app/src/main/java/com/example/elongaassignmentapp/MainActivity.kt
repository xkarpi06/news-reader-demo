package com.example.elongaassignmentapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.elongaassignmentapp.di.appModule
import com.example.elongaassignmentapp.di.networkModule
import com.example.elongaassignmentapp.ui.screen.article.ArticleScreen
import com.example.elongaassignmentapp.ui.screen.login.LoginScreen
import com.example.elongaassignmentapp.ui.screen.news.NewsScreen
import com.example.elongaassignmentapp.ui.theme.AppTheme
import kotlinx.serialization.Serializable
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        startKoin{
            androidLogger()
            androidContext(this@MainActivity)
            modules(listOf(appModule, networkModule))
        }

        setContent {
            val snackbarHostState = remember { SnackbarHostState() }

            AppTheme {
                val navController = rememberNavController()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    snackbarHost = { SnackbarHost(snackbarHostState) },
                    topBar = { MainTopAppBar(navController) }
                ) { innerPadding ->
                    AppNavigation(
                        navController = navController,
                        snackbarHostState = snackbarHostState,
                        modifier = Modifier.padding(innerPadding),
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopAppBar(navController: NavHostController) {
    val backStackEntry = navController.currentBackStackEntryAsState()
    val showBackButton = backStackEntry.value?.destination?.route?.endsWith(Route.Article.ROUTE) == true

    CenterAlignedTopAppBar(
        title = { Text(text = "NewsData.io demo") },
        navigationIcon = {
            if (showBackButton) {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        }
    )
}

@Composable
fun AppNavigation(
    navController: NavHostController,
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier
) {
    NavHost(navController = navController, startDestination = Route.Login, modifier = modifier) {
        composable<Route.Login> { LoginScreen(navController) }
        composable<Route.News> { NewsScreen(navController, snackbarHostState) }
        composable<Route.Article> { navBackStackEntry ->
            val article: Route.Article = navBackStackEntry.toRoute()
            ArticleScreen(navController, snackbarHostState, article.articleId)
        }
    }
}

object Route {
    @Serializable
    object Login
    @Serializable
    object News
    @Serializable
    data class Article(val articleId: String) {
        companion object {
            /**
             * This is only used to determine if the current destination is Article
             * and the back-button should be therefore shown in TopAppBar
             */
            const val ROUTE = "Route.Article/{articleId}"
        }
    }
}
