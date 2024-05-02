package com.example.crud_teste

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crud_teste.data.model.UserState
import com.example.crud_teste.data.network.SupabaseClient
import com.example.crud_teste.utils.SharedPreferenceHelper
import io.github.jan.supabase.gotrue.gotrue
import io.github.jan.supabase.gotrue.providers.builtin.Email
import kotlinx.coroutines.launch

class SupabaseAuthViewModel : ViewModel(){
    private val _userState=mutableStateOf<UserState>(UserState.Loading)
    val userState:State<UserState>

    init{
        userState=_userState
    }

    fun signUp(
        context: Context,
        userEmail: String,
        userPassword:String,
    ){
        viewModelScope.launch {
            _userState.value=UserState.Loading
            try {
                SupabaseClient.client.gotrue.signUpWith(Email){
                    email=userEmail
                    password=userPassword
                }
                saveToken(context)
                _userState.value=UserState.Success("Registered user successfully!")
            } catch (e:Exception){
                _userState.value=UserState.Error("Error: ${e.message}")
            }
        }
    }

    private fun saveToken(context: Context){
        viewModelScope.launch {
            val accessToken=SupabaseClient.client.gotrue.currentAccessTokenOrNull()
            val sharedPref= SharedPreferenceHelper(context)
            sharedPref.saveStringData("accessToken",accessToken)
        }
    }

    private fun getToken(context: Context):String? {
        val sharedPref=SharedPreferenceHelper(context)
        return sharedPref.getStringData("accessToken")
    }

    fun login(
        context: Context,
        userEmail: String,
        userPassword: String,
    ) {
        viewModelScope.launch {
            _userState.value=UserState.Loading
            try {
                SupabaseClient.client.gotrue.loginWith(Email){
                    email=userEmail
                    password=userPassword
                }
                saveToken(context)
                _userState.value=UserState.Success("Logged in successfully")
            } catch (e:Exception){
                _userState.value=UserState.Error("Error: ${e.message}")
            }
        }
    }

    fun logout(context: Context){
        val sharedPref= SharedPreferenceHelper(context)
       viewModelScope.launch {
           _userState.value=UserState.Loading
           try {
               SupabaseClient.client.gotrue.logout()
               sharedPref.clearPreferences()
               _userState.value=UserState.Success("Logged Out Successfully")
           }catch (e:Exception){
               _userState.value=UserState.Error("Error: ${e.message}")
           }
       }
    }

    fun isUserLoggedIn(
        context: Context
    ){
        viewModelScope.launch {
            try {
                val token=getToken(context)
                if(token.isNullOrEmpty()){
                    _userState.value=UserState.Error("User is not logged in")
                } else {
                    SupabaseClient.client.gotrue.retrieveUser(token)
                    SupabaseClient.client.gotrue.refreshCurrentSession()
                    saveToken(context)
                    _userState.value=UserState.Success("User is already logged in!")
                }
            }catch (e:Exception){
                _userState.value=UserState.Error("Error: ${e.message}")
            }
        }
    }


}