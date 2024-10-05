package com.example.elongaassignmentapp.data.repository

interface LoginRepository {
    suspend fun logIn(username: String, password: String): Boolean
}

class LoginRepositoryImpl : LoginRepository {
    override suspend fun logIn(username: String, password: String): Boolean {
        return true // TODO: login
    }
}
