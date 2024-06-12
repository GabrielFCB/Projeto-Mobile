package com.example.crud_teste.services

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import com.example.crud_teste.data.model.UserState

object AcessibilidadeSettings {
    var fontSize= mutableStateOf(14f) // tamanho padrão da fonte
    var fontColor=  mutableStateOf(Color.Black) // cor padrão da fonte


    fun setCorPadrao(){
        fontColor.value=Color.Black
    }

    fun setCorAlternativa(){
        fontColor.value=Color.Magenta
    }

    fun setFontePadrao() {
        fontSize.value=14f;
    }

    fun setFonteAlternativa(){
        fontSize.value=20f;
    }
}