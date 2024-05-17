package com.example.crud_teste.interfaces

import com.example.crud_teste.data.model.Artista

interface ICrudService<T> {

    suspend fun insert(item: T):Boolean
    suspend fun getById(id:Int): T
    suspend fun getAll(): List<T>
    suspend fun update(item: T):Boolean
    suspend fun delete(item: T): Boolean

}