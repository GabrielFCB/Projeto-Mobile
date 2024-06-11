package com.example.crud_teste.services

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crud_teste.data.model.Artista
import com.example.crud_teste.data.model.Note
import com.example.crud_teste.data.model.UserState
import com.example.crud_teste.data.network.SupabaseClient
import com.example.crud_teste.interfaces.ICrudService
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonArray

class ArtistaCrudService(private val stateService: StateService): ICrudService<Artista> {
    private val _userState= mutableStateOf<UserState>(UserState.Loading)
    val userState: State<UserState>

    private val _artistaState = mutableStateOf<List<Artista>>(emptyList())
    val artistaState: State<List<Artista>> = _artistaState

    init{
        userState=_userState
    }

    suspend fun saveNote(){
            try{
                _userState.value= UserState.Loading
                SupabaseClient.client.postgrest["Mobile_teste"].insert(
                    Note(
                        note="This is my first note."
                    ),
                )
                _userState.value= UserState.Success("Note added successfully!")
            }catch (e: Exception){
                _userState.value= UserState.Error("Error: ${e.message}")
            }
    }

    override suspend fun insert(item: Artista):Boolean{
        var status =false
            try {
                _userState.value= UserState.Loading
                SupabaseClient.client.postgrest["Artistas"].insert(
                    Artista(
                        Nome = item.Nome,
                        Data = item.Data,
                        Biografia = item.Biografia
                    ),
                )
                status=true
            } catch (e: Exception) {
                _userState.value = UserState.Error("Error: ${e.message}")
            }
        return status
    }

    override suspend fun getAll(): List<Artista>{
        val artistas = mutableListOf<Artista>()
        try {
            stateService.setLoading()
            val response = SupabaseClient.client.postgrest["Artistas"].select()
            val artistasJsonArray = response.body?.jsonArray
            artistasJsonArray?.forEach { jsonElement ->
                val artista = Json.decodeFromJsonElement<Artista>(jsonElement)
                artistas.add(artista)
            }
            _artistaState.value=artistas
            stateService.setSuccess("Artistas carregados com sucesso")
        } catch (e: Exception) {
            println(e.message)
            stateService.setError("Erro: ${e.message}")
        }
        return artistas
    }



    suspend fun getNote(){
            try{
                _userState.value= UserState.Loading
                val data = SupabaseClient.client.postgrest["Mobile_teste"].select()
                    .decodeSingle<Note>()
                _userState.value= UserState.Success("Data: ${data.note}")
            }catch (e: Exception){
                _userState.value= UserState.Error("Error: ${e.message}")
            }
    }

    suspend fun updateNote(){
            try{
                _userState.value= UserState.Loading
                SupabaseClient.client.postgrest["Mobile_teste"].update(
                    {
                        Note::note setTo "Note updated."
                    }
                ){
                    Note::id eq 1
                }
                _userState.value= UserState.Success("Note updated successfully!")
            }catch (e: Exception){
                _userState.value= UserState.Error("Error: ${e.message}")
            }
    }

    suspend fun deleteNote(){
            try{
                _userState.value= UserState.Loading
                SupabaseClient.client.postgrest["Mobile_teste"].delete{
                    Note::id eq 1
                }
                _userState.value= UserState.Success("Note deleted successfully!")
            }catch (e: Exception){
                _userState.value= UserState.Error("Error: ${e.message}")
            }
    }

    override suspend fun getById(id: Int): Artista {
        TODO("Not yet implemented")
    }

    override suspend fun delete(item: Artista): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun update(item: Artista): Boolean {
        TODO("Not yet implemented")
    }

}