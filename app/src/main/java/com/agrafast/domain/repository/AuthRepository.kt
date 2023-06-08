package com.agrafast.domain.repository

import com.agrafast.domain.FetchStatus
import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    val currentUser: FirebaseUser?
    suspend fun login(email: String, password: String): FetchStatus<FirebaseUser>
    suspend fun signup(name: String, email: String, telpNumber: String, password: String): FetchStatus<FirebaseUser>
    fun saveSignedUserToDatabase(): FirebaseUser
    fun logout()

}