package com.example.crud_teste

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.crud_teste.telas.AcessibilidadeScreen
import com.example.crud_teste.telas.AdministradorScreen
import com.example.crud_teste.telas.ArtistasScreen
import com.example.crud_teste.telas.AtualizarArtistaScreen
import com.example.crud_teste.telas.AtualizarObraScreen
import com.example.crud_teste.telas.CadastrarArtistaScreen
import com.example.crud_teste.telas.ExposicaoScreen
import com.example.crud_teste.telas.HomeScreen
import com.example.crud_teste.telas.MainScreen
import com.example.crud_teste.telas.ObrasScreen
import com.example.crud_teste.telas.VisualizarArtistaScreen
import com.example.crud_teste.telas.VisualizarObraScreen
import com.example.crud_teste.ui.theme.Crud_TesteTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Crud_TesteTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "login") {
                    composable("login") { MainScreen(navController) }
                    composable("home") { HomeScreen(navController) }
                    composable("obras") { ObrasScreen(navController) }
                    composable("artistas") { ArtistasScreen(navController) }
                    composable("exposicao") { ExposicaoScreen(navController) }
                    composable("administrador") { AdministradorScreen(navController) }
                    composable("cadastrarArtista") { CadastrarArtistaScreen(navController) }
                    composable("acessibilidade") { AcessibilidadeScreen(navController) }
                    composable("atualizarArtista") { AtualizarArtistaScreen(navController) }
                    composable("atualizarObra") { AtualizarObraScreen(navController) }
                    composable("visualizarArtista") { VisualizarArtistaScreen(navController) }
                    composable("visualizarObra") { VisualizarObraScreen(navController) }
                }
            }
        }
    }
}


//@OptIn(SupabaseExperimental::class)
//@Composable
//fun MainScreen(
//    viewModel: SupabaseAuthViewModel=androidx.lifecycle.viewmodel.compose.viewModel(),
//){
//    val context = LocalContext.current
//    val userState by viewModel.userState
//
//    var userEmail by remember { mutableStateOf("") }
//    var userPassword by remember{ mutableStateOf("") }
//    var currentUserState by remember { mutableStateOf("") }
//
//    val action= SupabaseClient.client.composeAuth.rememberLoginWithGoogle(
//        onResult={result->viewModel.checkGoogleLoginStatus(context,result)},
//        fallback = {}
//    )
//
//    LaunchedEffect(Unit){
//        viewModel.isUserLoggedIn(
//            context,
//        )
//    }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(8.dp)
//    ){
//        TextField(
//            value=userEmail,
//            placeholder={
//                Text(text="Enter email")
//            },
//            onValueChange={
//                userEmail=it
//            }
//        )
//        Spacer(modifier = Modifier.padding(8.dp))
//        TextField(
//            value=userPassword,
//            placeholder={
//                Text(text="Enter password")
//            },
//            onValueChange={
//                userPassword=it
//            }
//        )
//        Spacer(modifier = Modifier.padding(8.dp))
//        //A importação do botão pode estar errada
//        Button(onClick={
//            viewModel.signUp(
//                context,
//                userEmail,
//                userPassword,
//            )
//        }){
//            Text(text="Sign Up")
//        }
//        Button(onClick={
//            viewModel.login(
//                context,
//                userEmail,
//                userPassword,
//            )
//        }){
//            Text(text="Login")
//        }
////        Button(onClick = {
////            action.startFlow()
////        }) {
////            Text(text="Login via Google")
////        }
//        OutlinedButton(
//            onClick = { action.startFlow() },
//            content={ProviderButtonContent(provider = Google)})
//
//        Button(
//            colors=ButtonDefaults.buttonColors(containerColor = Color.Red),
//            onClick={
//                viewModel.logout(context)
//            }){
//            Text(text="Logout")
//        }
//
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.SpaceBetween
//        ){
//            Button(onClick = {viewModel.saveNote()}) {
//                Text(text = "Save")
//            }
//            Button(onClick = {viewModel.getNote()}) {
//                Text(text = "Fetch")
//            }
//            Button(onClick = {viewModel.updateNote()}) {
//                Text(text = "Update")
//            }
//            Button(onClick = {viewModel.deleteNote()}) {
//                Text(text = "Delete")
//            }
//
//        }
//
//        when(userState){
//            is UserState.Loading -> {
//                LoadingComponent()
//            }
//            is UserState.Success ->{
//                val message =(userState as UserState.Success).message
//                currentUserState=message
//            }
//            is UserState.Error -> {
//                val message = (userState as UserState.Error).message
//                currentUserState=message
//            }
//        }
//
//        if(currentUserState.isNotEmpty()){
//            Text(text=currentUserState)
//        }
//    }
//
//}
