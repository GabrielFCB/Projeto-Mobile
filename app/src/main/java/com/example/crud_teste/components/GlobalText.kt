package com.example.crud_teste.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.crud_teste.services.AcessibilidadeSettings

@Composable
fun GlobalText(text: String, modifier: Modifier = Modifier ) {
    Text(
        text = text,
        fontSize = AcessibilidadeSettings.fontSize.value.sp, // aplica o tamanho da fonte global
        color = AcessibilidadeSettings.fontColor.value, // aplica a cor da fonte global
    )
}