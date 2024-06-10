package com.example.crud_teste.services

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.example.crud_teste.data.model.UserState

class StateService {
    private val _userState= mutableStateOf<UserState>(UserState.Loading)
    val userState: State<UserState>
    init{
        userState=_userState
    }

    fun setLoading() {
        _userState.value = UserState.Loading
    }

    fun setSuccess(message: String) {
        _userState.value = UserState.Success(message)
    }

    fun setError(message: String) {
        _userState.value = UserState.Error(message)
    }

}