package com.example.crud_teste.components
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.example.crud_teste.data.model.Artista
import io.ktor.websocket.Frame
import kotlin.reflect.full.memberProperties

inline fun <reified T : Any> GenericListView(list: List<T>) {
    list.forEach { element ->
        println("Element: ${element::class.simpleName}")
        T::class.memberProperties.forEach { property ->
            try {
                val propertyName = property.name
                val propertyValue = property.getter.call(element)
                println("$propertyName: $propertyValue")
            } catch (e: Exception) {
                println("Error accessing property: ${property.name}")
            }
        }
        println()
    }
}

@Composable
fun FieldView(fieldName: String, fieldValue: Any?) {
    Column {
        Frame.Text(text = "$fieldName: ${fieldValue.toString()}")
    }
}

inline fun <reified T : Any> renderFields(element: T): List<Pair<String, Any?>> {
    return T::class.memberProperties.map { property ->
        val propertyName = property.name
        val propertyValue = property.getter.call(element)
        propertyName to propertyValue
    }
}

@Composable
fun ArtistaView(artista: Artista) {
    val fields = renderFields(artista)

    Column {
        fields.forEach { field ->
            FieldView(fieldName = field.first, fieldValue = field.second)
        }
    }
}

@Composable
fun ArtistaList(artistas: List<Artista>) {
    LazyColumn {
        items(artistas) { artista ->
            ArtistaView(artista = artista)
        }
    }
}