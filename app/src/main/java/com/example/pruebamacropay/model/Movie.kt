package com.example.pruebamacropay.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Data class que representa el objeto Movie regresado por el web API. Este incluye propiedades como las siguientes:
 * @property id Identificador unico del objeto Movie.
 * @property posterPath String, path donde se puede acceder a la imagen del poster de la pelicula.
 * @property title String, titulo de la pelicula.
 * @property duration Double, duracion de la pelicula en minutos.
 * @property releaseDate String, fecha de lanzamiento de la pelicula.
 * @property isAdult Boolean, indica si la pelicula es para adultos.
 * @property genreIds List<Int>, lista de IDs de los generos asociados a la pelicula.
 * @property description String, breve descripcion de una pelicula.
 * @property voteAverage Double, rating promedio de la pelicula.
 * @property backdropPath String, path de una imagen alternativa de la pelicula.
 * @property language String, lenguaje original de la pelicula.
 * Para mas informacion, checar la siguiente documentacion:
 * https://developer.themoviedb.org/reference/movie-now-playing-list
 */
data class Movie(
    @SerializedName("id") val id: Int,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("title") val title: String,
    // Esta propiedad no existe en el objeto Result devuelto por el endpoint, por lo que es definido
    // porque lo pide la prueba.
    @SerializedName("duration") val duration: Double,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("adult") val isAdult: Boolean,
    @SerializedName("genre_ids") val genreIds: List<Int>,
    @SerializedName("overview") val description: String,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("backdrop_path") val backdropPath: String,
    @SerializedName("original_language") val language: String
) : Serializable
