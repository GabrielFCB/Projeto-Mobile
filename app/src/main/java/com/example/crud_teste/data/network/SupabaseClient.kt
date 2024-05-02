package com.example.crud_teste.data.network

import com.example.crud_teste.BuildConfig
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.GoTrue

object SupabaseClient {
    val client= createSupabaseClient(
        supabaseUrl= BuildConfig.supabaseUrl,
        supabaseKey=BuildConfig.supabaseKey,
    ) {
        install(GoTrue)
    }
}