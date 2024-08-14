package com.example.crud_teste.interfaces

import android.content.Context

interface IAuthService {
    suspend fun signUp(context: Context,
                       userEmail: String,
                       userPassword:String,
               ): Boolean
    suspend fun login(context: Context,
              userEmail: String,
              userPassword: String,): Boolean
    suspend fun logout(context: Context): Unit
    suspend fun saveToken(context: Context): Unit
    fun getToken(context: Context): String?
    suspend fun isUserLoggedIn(context: Context):Boolean


}