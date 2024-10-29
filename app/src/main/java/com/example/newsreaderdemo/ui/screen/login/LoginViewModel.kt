package com.example.newsreaderdemo.ui.screen.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsreaderdemo.data.repository.AuthRepository
import com.example.newsreaderdemo.ui.screen.login.model.LoginUIEvent
import com.example.newsreaderdemo.ui.screen.login.model.LoginUIState
import com.example.newsreaderdemo.ui.screen.login.model.UserCredentials
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

abstract class LoginViewModel : ViewModel() {
    abstract val uiState: LoginUIState
    abstract val oneTimeEvent: SharedFlow<LoginUIEvent>
    abstract fun onScreenLaunched()
    abstract fun onSubmit(credentials: UserCredentials)
}

class LoginViewModelImpl(
    private val authRepository: AuthRepository
) : LoginViewModel() {
    private val correctStaticUsername = "news@demo.com"
    private val correctStaticPassword = "Demo1234"

    override var uiState by mutableStateOf(LoginUIState())
        private set

    // Expose SharedFlow for one-time events
    private val _oneTimeEvent = MutableSharedFlow<LoginUIEvent>()
    override val oneTimeEvent: SharedFlow<LoginUIEvent> = _oneTimeEvent.asSharedFlow()

    override fun onScreenLaunched() {
        viewModelScope.launch(Dispatchers.IO) {
            if (authRepository.isAuthorized()) {
                _oneTimeEvent.emit(LoginUIEvent.NavigateToNews)
            }
        }
    }

    override fun onSubmit(credentials: UserCredentials) {
        authenticateUser(credentials.username, credentials.password)
    }

    private fun authenticateUser(username: String, password: String) {
        uiState = LoginUIState(isLoading = true)
        if (username == correctStaticUsername && password == correctStaticPassword) {
            viewModelScope.launch(Dispatchers.IO) {
                authRepository.setAuthorized(true)
                _oneTimeEvent.emit(LoginUIEvent.NavigateToNews)
            }
        } else {
            uiState = uiState.copy(errorMessage = "Wrong credentials. Try again.")
        }
        uiState = uiState.copy(isLoading = false)
    }
}
