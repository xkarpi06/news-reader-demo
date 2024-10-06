package com.example.elongaassignmentapp.ui.screen.login

import androidx.lifecycle.ViewModel
import com.example.elongaassignmentapp.data.repository.LoginRepository
import com.example.elongaassignmentapp.ui.screen.login.model.LoginUIEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

abstract class LoginViewModel : ViewModel() {
    abstract val oneTimeEvent: SharedFlow<LoginUIEvent>
    abstract fun onLogInClick()
}

class LoginViewModelImpl(
    private val loginRepository: LoginRepository
) : LoginViewModel() {

    // Expose SharedFlow for one-time events
    private val _oneTimeEvent = MutableSharedFlow<LoginUIEvent>()
    override val oneTimeEvent: SharedFlow<LoginUIEvent> = _oneTimeEvent.asSharedFlow()

    override fun onLogInClick() {
        TODO("Not yet implemented")
    }
}
