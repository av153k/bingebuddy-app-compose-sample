package com.bingebuddy.app.ui.screens.account

import com.google.firebase.auth.UserInfo

enum class AuthProcess {
    LOGIN, REGISTER
}

sealed interface AccountUiState {
    data class Authenticated(val user: UserInfo): AccountUiState
    data class Authenticating(val currentProcess: AuthProcess, val email: String): AccountUiState
}