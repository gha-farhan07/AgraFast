package com.agrafast.domain.repository

import com.agrafast.domain.FetchStatus
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {

    private  val firebaseDb = Firebase.firestore
    private val usersRef = firebaseDb.collection("users")
    private val plantsRef = firebaseDb.collection("plants")

    override val currentUser: FirebaseUser?
        get() = firebaseAuth.currentUser

    override suspend fun login(email: String, password: String): FetchStatus<FirebaseUser> {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            FetchStatus.Success(result.user!!)
        } catch (e: Exception) {
            e.printStackTrace()
            FetchStatus.Error(e)
        }
    }

    override suspend fun signup(
        name: String,
        email: String,
        telpNumber: String,
        password: String
    ): FetchStatus<FirebaseUser> {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            result.user?.updateProfile(UserProfileChangeRequest.Builder().setDisplayName(name).build())?.await()
            return FetchStatus.Success(result.user!!)
        } catch (e: Exception) {
            e.printStackTrace()
            FetchStatus.Error(e)
        }
    }

    override fun saveSignedUserToDatabase(): FirebaseUser {
        TODO("Not yet implemented")
    }

    override fun logout() {
        firebaseAuth.signOut()
    }
}