package com.example.elongaassignmentapp.ui.screen.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.ViewModel
import com.example.elongaassignmentapp.data.repository.AuthRepository
import com.example.elongaassignmentapp.ui.screen.login.model.LoginUIEvent
import com.example.elongaassignmentapp.ui.screen.login.model.LoginUIState
import com.example.elongaassignmentapp.ui.screen.login.model.LoginUserAction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

abstract class LoginViewModel : ViewModel() {
    abstract val uiState: LoginUIState
    abstract val oneTimeEvent: SharedFlow<LoginUIEvent>
    abstract fun onScreenLaunched()
    abstract fun onUserAction(action: LoginUserAction)
}

class LoginViewModelImpl(
    private val authRepository: AuthRepository
) : LoginViewModel() {
    private val correctStaticUsername = "elonga@elonga.com"
    private val correctStaticPassword = "ElongaTheBest"

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

    override fun onUserAction(action: LoginUserAction) {
        when (action) {
            is LoginUserAction.Authenticate -> authenticateUser(action.user, action.password)
            is LoginUserAction.SkipLogin -> {
                viewModelScope.launch{ _oneTimeEvent.emit(LoginUIEvent.NavigateToNews) }
            }
        }
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
