package com.example.crud_teste.telas

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.crud_teste.Navigator
import com.example.crud_teste.SupabaseAuthViewModel
import com.example.crud_teste.data.model.UserState
import com.example.crud_teste.data.network.SupabaseClient
import io.github.jan.supabase.compose.auth.composable.rememberLoginWithGoogle
import io.github.jan.supabase.compose.auth.composeAuth

@Composable
fun MainScreen(navController: NavController, viewModel: SupabaseAuthViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    val context = LocalContext.current
    val userState by viewModel.userState

    var userEmail by remember { mutableStateOf("") }
    var userPassword by remember { mutableStateOf("") }

    val action = SupabaseClient.client.composeAuth.rememberLoginWithGoogle(
        onResult = { result -> viewModel.checkGoogleLoginStatus(context, result) },
        fallback = {}
    )

    LaunchedEffect(userState) {
        if (userState is UserState.Success) {
            Navigator.navigateToHome(navController)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        TextField(
            value = userEmail,
            onValueChange = { userEmail = it },
            placeholder = { Text("Enter email") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = userPassword,
            onValueChange = { userPassword = it },
            placeholder = { Text("Enter password") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { viewModel.signUp(context, userEmail, userPassword) }) {
            Text("Sign Up")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { viewModel.login(context, userEmail, userPassword) }) {
            Text("Login")
        }
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedButton(onClick = { action.startFlow() }) {
            Text("Login via Google")
        }
    }
}