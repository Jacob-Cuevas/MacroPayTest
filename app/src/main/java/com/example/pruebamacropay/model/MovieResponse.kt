package com.example.pruebamacropay.model

/**
 * Data class que guardara de forma temporal el objeto "results" contenido dentro del JSON response obtenido del request al endpoint:
 * https://api.themoviedb.org/3/movie/now_playing y el cual tiene la siguiente estructura:
 * {
 *      "results": [
 *          {
 *              "poster_path": "...",
 *              "original_title": "...",
 *              "overview": "...",
 *              ...
 *          }
 *       ],
 *       ...
 * }
 * Para mas informacion, checar la documentacion del web service:
 * https://developer.themoviedb.org/reference/movie-now-playing-list
 */
data class MovieResponse (
    val results: List<Movie>
)