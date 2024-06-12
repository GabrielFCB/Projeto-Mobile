package com.example.crud_teste.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import com.example.crud_teste.services.AcessibilidadeSettings

@Composable
fun GlobalTextColor(text: String, modifier: Modifier = Modifier, style: TextStyle) {
    Text(
        text = text,
        color = AcessibilidadeSettings.fontColor.value, // aplica a cor da fonte global
        textAlign = TextAlign.Center,
    )
}