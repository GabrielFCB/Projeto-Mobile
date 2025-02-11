package com.example.crud_teste.data.network

import com.example.crud_teste.BuildConfig
import io.github.jan.supabase.compose.auth.ComposeAuth
import io.github.jan.supabase.compose.auth.googleNativeLogin
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.GoTrue
import io.github.jan.supabase.postgrest.Postgrest

object SupabaseClient {
    val client= createSupabaseClient(
        supabaseUrl= BuildConfig.supabaseUrl,
        supabaseKey=BuildConfig.supabaseKey,
    ) {
        install(GoTrue)
        install(ComposeAuth){
            googleNativeLogin(serverClientId = BuildConfig.googleClientId)
        }
        install(Postgrest)
    }
}