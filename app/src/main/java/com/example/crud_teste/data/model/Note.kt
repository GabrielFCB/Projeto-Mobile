package com.example.crud_teste.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Note(
    val id:Int?=null,
    @SerialName("created_at")
    val createdAt:String?=null,
    val note:String=""
)
