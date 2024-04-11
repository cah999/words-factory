package com.example.wordsfactory.data.repository

import android.util.Log
import com.example.wordsfactory.data.model.User
import com.example.wordsfactory.data.model.UserLogin
import com.example.wordsfactory.domain.repository.AuthRepository
import com.example.wordsfactory.presentation.ui.utils.UiState
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.auth.userProfileChangeRequest

class AuthRepositoryImpl(
    private val auth: FirebaseAuth
) : AuthRepository {
    override fun login(loginData: UserLogin, result: (UiState) -> Unit) {
        auth.signInWithEmailAndPassword(loginData.email, loginData.password).addOnCompleteListener {
            if (it.isSuccessful) {
                val currentUser = Firebase.auth.currentUser
                Log.d("AuthRepository", "Current user name: ${currentUser?.displayName}")
                result.invoke(UiState.Success)
            } else {
                result.invoke(UiState.Error(it.exception?.message ?: "An error occurred"))
            }
        }
    }

    override fun register(
        loginData: UserLogin, user: User, result: (UiState) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(loginData.email, loginData.password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    updateProfile(user)
                    result.invoke(UiState.Success)
                } else {
                    result.invoke(UiState.Error(it.exception?.message ?: "An error occurred"))
                }
            }
    }

    override fun updateProfile(user: User) {
        val currentUser = Firebase.auth.currentUser
        Log.d("AuthRepository", "Current user: $currentUser user id ${currentUser?.uid}")

        val profileUpdates = userProfileChangeRequest {
            displayName = user.name
        }

        currentUser!!.updateProfile(profileUpdates).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("AuthRepository", "User profile updated.")
            }
        }
    }

    override fun logout() {
        auth.signOut()
    }

    override fun isUserLoggedIn(): Boolean {
        return auth.currentUser != null
    }

    override fun getCurrentUser(): User? {
        val currentUser = Firebase.auth.currentUser
        return if (currentUser != null) {
            User(currentUser.uid, currentUser.displayName ?: "")
        } else {
            null
        }
    }
}