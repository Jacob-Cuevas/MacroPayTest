package com.example.pruebamacropay.model

/**
 * Data class que guardara de forma temporal el objeto "genres" contenido dentro del JSON response del request hecho a:
 * https://api.themoviedb.org/3/genre/movie/list y el cual tiene la siguiente estructura:
 * "genres": [
 *      {
 *          "id": 1,
 *          "name": "...."
 *      },
 *      ...
 * ]
 * Para mas informacion, checar la documentacion del web service:
 * https://developer.themoviedb.org/reference/genre-movie-list
 */
data class GenreResponse (
    val genres: List<Genre>
)