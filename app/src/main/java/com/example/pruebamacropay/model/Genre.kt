package com.example.pruebamacropay.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Data class que representa el objeto Gender que es traido del web service y que contiene el nombre a mostrar
 * para cada genre_ids contenido dentro del objeto Movie.+
 * @property id Identificador unico del objeto Genre.
 * @property name String, nombre de cada genero, como por ejemplo: "Action", "Adventure", etc.
 * Para mas detalles, checar documentacion:
 * https://developer.themoviedb.org/reference/genre-movie-list
 */
data class Genre (
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String
): Serializable