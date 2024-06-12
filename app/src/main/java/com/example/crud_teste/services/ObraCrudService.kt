package com.example.crud_teste.services

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.crud_teste.data.model.Artista
import com.example.crud_teste.data.model.Note
import com.example.crud_teste.data.model.Obra
import com.example.crud_teste.data.model.UserState
import com.example.crud_teste.data.network.SupabaseClient
import com.example.crud_teste.interfaces.ICrudService
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonArray

class ObraCrudService(private val stateService: StateService): ICrudService<Obra> {
    private val _userState= mutableStateOf<UserState>(UserState.Loading)
    val userState: State<UserState>

    private val _obraState = mutableStateOf<List<Obra>>(emptyList())
    val obraState: State<List<Obra>> = _obraState
    var obraId by mutableStateOf<Int>(0)

    init{
        userState=_userState
    }


    override suspend fun insert(item: Obra):Boolean{
        Log.d(ContentValues.TAG, "Iniciando inserção da obra")
        var status =false
        try {
            _userState.value= UserState.Loading
            SupabaseClient.client.postgrest["Obras"].insert(
                Obra(
                    nome = item.nome,
                    data = item.data,
                    autor=item.autor,
                    descricao = item.descricao,
                    link=item.link
                ),
            )
            status=true
        } catch (e: Exception) {
            _userState.value = UserState.Error("Error: ${e.message}")
            Log.e(TAG, "Erro ao inserir obra", e)
        }
        return status
    }

    override suspend fun getAll(): List<Obra>{
        val obras = mutableListOf<Obra>()
        try {
            stateService.setLoading()
            val response = SupabaseClient.client.postgrest["Obras"].select()
            val obrasJsonArray = response.body?.jsonArray
            obrasJsonArray?.forEach { jsonElement ->
                val obra = Json.decodeFromJsonElement<Obra>(jsonElement)
                obras.add(obra)
            }
            _obraState.value=obras
            stateService.setSuccess("Obras carregadas com sucesso")
            println("Obras carregadas com sucesso")
        } catch (e: Exception) {
            println(e.message)
            stateService.setError("Erro: ${e.message}")
            println(e.message)
        }
        return obras
    }

    override suspend fun getById(id: Int): Obra {
        TODO("Not yet implemented")
    }

    override suspend fun delete(id: Int): Boolean {
        var status =false
        try {
            _userState.value= UserState.Loading
            SupabaseClient.client.postgrest["Obras"].delete {
                Obra::id eq id
            }
            status=true
        } catch (e: Exception) {
            _userState.value = UserState.Error("Error: ${e.message}")
        }
        return status
    }

    override suspend fun update(item: Obra,id:Int): Boolean {
        Log.d(ContentValues.TAG, "Iniciando inserção da obra")
        var status =false
        try {
            _userState.value= UserState.Loading
            SupabaseClient.client.postgrest["Obras"].update(
                Obra(
                    nome = item.nome,
                    data = item.data,
                    autor=item.autor,
                    descricao = item.descricao,
                    link=item.link
                ),
            ){
                Obra::id eq id
            }
            stateService.setSuccess("Obra atualizada com sucesso")
            println("Obra atualizada com sucesso")
            status=true
        } catch (e: Exception) {
            _userState.value = UserState.Error("Error: ${e.message}")
            Log.e(TAG, "Erro ao inserir obra", e)
        }
        return status
    }

}
