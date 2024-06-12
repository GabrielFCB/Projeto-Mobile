package com.example.crud_teste.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Obra(
    val id: Int? = null,
    @SerialName("created_at")
    val createdAt: String? = null,
    val autor:String = "",
    val nome: String = "",
    val data: String = "",
    val descricao: String = ""
)