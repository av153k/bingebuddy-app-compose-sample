package com.bingebuddy.app.ui.screens.account

import androidx.lifecycle.ViewModel
import com.bingebuddy.app.data.network.repository.AccountRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AccountViewmodel @Inject constructor(
    private val accountRepository: AccountRepository,
): ViewModel() {

}