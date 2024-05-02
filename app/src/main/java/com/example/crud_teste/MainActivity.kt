package com.example.crud_teste

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.crud_teste.data.model.UserState
import com.example.crud_teste.data.network.SupabaseClient
import com.example.crud_teste.ui.theme.Crud_TesteTheme
import io.github.jan.supabase.annotations.SupabaseExperimental
import io.github.jan.supabase.compose.auth.composable.rememberLoginWithGoogle
import io.github.jan.supabase.compose.auth.composeAuth
import io.github.jan.supabase.compose.auth.ui.ProviderButtonContent
import io.github.jan.supabase.gotrue.providers.Google


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Crud_TesteTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }


    }
}
@OptIn(SupabaseExperimental::class)
@Composable
fun MainScreen(
    viewModel: SupabaseAuthViewModel=androidx.lifecycle.viewmodel.compose.viewModel(),
){
    val context = LocalContext.current
    val userState by viewModel.userState

    var userEmail by remember { mutableStateOf("") }
    var userPassword by remember{ mutableStateOf("") }
    var currentUserState by remember { mutableStateOf("") }

    val action= SupabaseClient.client.composeAuth.rememberLoginWithGoogle(
        onResult={result->viewModel.checkGoogleLoginStatus(context,result)},
        fallback = {}
    )

    LaunchedEffect(Unit){
        viewModel.isUserLoggedIn(
            context,
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ){
        TextField(
            value=userEmail,
            placeholder={
                Text(text="Enter email")
            },
            onValueChange={
                userEmail=it
            }
        )
        Spacer(modifier = Modifier.padding(8.dp))
        TextField(
            value=userPassword,
            placeholder={
                Text(text="Enter password")
            },
            onValueChange={
                userPassword=it
            }
        )
        Spacer(modifier = Modifier.padding(8.dp))
        //A importação do botão pode estar errada
        Button(onClick={
            viewModel.signUp(
                context,
                userEmail,
                userPassword,
            )
        }){
            Text(text="Sign Up")
        }
        Button(onClick={
            viewModel.login(
                context,
                userEmail,
                userPassword,
            )
        }){
            Text(text="Login")
        }
//        Button(onClick = {
//            action.startFlow()
//        }) {
//            Text(text="Login via Google")
//        }
        OutlinedButton(
            onClick = { action.startFlow() },
            content={ProviderButtonContent(provider = Google)})

        Button(
            colors=ButtonDefaults.buttonColors(containerColor = Color.Red),
            onClick={
                viewModel.logout(context)
            }){
            Text(text="Logout")
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Button(onClick = {viewModel.saveNote()}) {
                Text(text = "Save")
            }
            Button(onClick = {viewModel.getNote()}) {
                Text(text = "Fetch")
            }
            Button(onClick = {viewModel.updateNote()}) {
                Text(text = "Update")
            }
            Button(onClick = {viewModel.deleteNote()}) {
                Text(text = "Delete")
            }

        }

        when(userState){
            is UserState.Loading -> {
                LoadingComponent()
            }
            is UserState.Success ->{
                val message =(userState as UserState.Success).message
                currentUserState=message
            }
            is UserState.Error -> {
                val message = (userState as UserState.Error).message
                currentUserState=message
            }
        }

        if(currentUserState.isNotEmpty()){
            Text(text=currentUserState)
        }
    }

}
