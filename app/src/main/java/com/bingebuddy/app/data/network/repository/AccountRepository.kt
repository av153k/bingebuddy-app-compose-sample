package com.bingebuddy.app.data.network.repository

import android.content.Context
import com.bingebuddy.app.data.BaseRepository
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class AccountRepository @Inject constructor(
    private val auth: FirebaseAuth,
    context: Context,
) : BaseRepository(context) {
     suspend fun createAnonymousAccount() {
        try {
            auth.signInAnonymously()

        } catch (e: Exception) {
            throw e
        }
    }

     suspend fun login() {
        TODO("Not yet implemented")
    }

     suspend fun linkWithAnonymousAccount() {
        TODO("Not yet implemented")
    }

     suspend fun updateUserDetails() {
        TODO("Not yet implemented")
    }

}