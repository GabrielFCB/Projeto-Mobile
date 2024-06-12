package com.example.crud_teste.services

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.example.crud_teste.data.model.UserState
import com.example.crud_teste.data.network.SupabaseClient
import com.example.crud_teste.interfaces.IAuthService
import com.example.crud_teste.utils.SharedPreferenceHelper
import io.github.jan.supabase.compose.auth.composable.NativeSignInResult
import io.github.jan.supabase.gotrue.gotrue
import io.github.jan.supabase.gotrue.providers.builtin.Email

class AuthService(private val stateService: StateService) : IAuthService{

     override suspend fun signUp(
        context: Context,
        userEmail: String,
        userPassword:String,
    ): Boolean{
        var status: Boolean =false
         stateService.setLoading()
        try {
            SupabaseClient.client.gotrue.signUpWith(Email){
                email=userEmail
                password=userPassword
            }
            saveToken(context)
            status=true
            stateService.setSuccess("Signed in successfully")
            println("Signed in successfully")
        } catch (e:Exception){
            stateService.setError("Error: ${e.message}")
            println("Error: ${e.message}")
        }
        return status
    }

    override suspend fun login(
        context: Context,
        userEmail: String,
        userPassword: String,
    ) : Boolean{
        var status:Boolean=false
        stateService.setLoading()
        try {
            SupabaseClient.client.gotrue.loginWith(Email){
                email=userEmail
                password=userPassword
            }
            saveToken(context)
            status=true
            stateService.setSuccess("Logged in successfully")
        } catch (e:Exception){
            stateService.setError("Error: ${e.message}")
            println("Error: ${e.message}")
        }
        return status
    }

    override suspend fun saveToken(context: Context){
            val accessToken=SupabaseClient.client.gotrue.currentAccessTokenOrNull()
            val sharedPref= SharedPreferenceHelper(context)
            sharedPref.saveStringData("accessToken",accessToken)
    }

    override fun getToken(context: Context):String? {
        val sharedPref=SharedPreferenceHelper(context)
        return sharedPref.getStringData("accessToken")
    }


    override suspend fun logout(context: Context){
        val sharedPref= SharedPreferenceHelper(context)
           //_userState.value=UserState.Loading
           try {
               SupabaseClient.client.gotrue.logout()
               sharedPref.clearPreferences()
               //_userState.value=UserState.Success("Logged Out Successfully")
           }catch (e:Exception){
               //_userState.value=UserState.Error("Error: ${e.message}")
       }
    }

    suspend fun checkGoogleLoginStatus(context:Context,result:NativeSignInResult){
        when (result){
            is NativeSignInResult.Success->{
                saveToken(context)
                //_userState.value=UserState.Success("Logged in via Google")
            }
            is NativeSignInResult.ClosedByUser->{}
            is NativeSignInResult.Error->{
                val message=result.message
                //_userState.value=UserState.Error(message)
            }
            is NativeSignInResult.NetworkError->{
                val message=result.message
                //_userState.value=UserState.Error(message)
            }
        }
    }

    override suspend fun isUserLoggedIn(
        context: Context
    ): Boolean{
        var status:Boolean=false
            try {
                val token=getToken(context)
                if(token.isNullOrEmpty()){
                    //_userState.value=UserState.Error("User is not logged in")
                } else {
                    SupabaseClient.client.gotrue.retrieveUser(token)
                    SupabaseClient.client.gotrue.refreshCurrentSession()
                    saveToken(context)
                    //_userState.value=UserState.Success("User is already logged in!")
                    status=true
                }
            }catch (e:Exception){
                //_userState.value=UserState.Error("Error: ${e.message}")
            }
        return status
    }



}