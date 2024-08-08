package com.example.pruebamacropay.network

import com.example.pruebamacropay.model.Genre
import com.example.pruebamacropay.model.GenreResponse
import com.example.pruebamacropay.model.Movie
import com.example.pruebamacropay.model.MovieResponse
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

/**
 * Clase usada para realizar peticiones HTTP de forma asincrona para no afectar el hilo principal de la aplicacion y causar bloqueos
 * o problemas de performance. Esta clase provee 2 metodos:
 * - getMovies
 * - getGenresByMovie
 * Ambos metodos retornara objetos mapeados de tipo Movie o Genre.
 */
class MovieNetwork {

    // Inicializamos the HTTP client object que sera usado para hacer un request GET al service.
    private val client = OkHttpClient()
    // Inicializamos el objeto que representa al plugin GSON que sera usado para parsear el objeto JSON a un data class especifico.
    private val gson = Gson()
    // API key usado para poder invocar los web servicios de themoviedb web API.
    private val apiKey = "c0823934438075d63f1dbda4023e76fc"

    /**
     * Funcion usada para invocar un HTTP request asincrono de un web service para obtener la lista de Movies paginada.
     *
     * @param page: Int que representa la pagina actual que delimitara el numero de Movies devuelto por el web service y asi evitar
     *              obtener todos ellos al mismo tiempo.
     */
    suspend fun getMovies(page: Int): List<Movie> {

        // Cambiamos el contexto de ejecucion del siguiente bloque de codigo para usar uno asincrono que permita hacer operaciones de lectura y asi no
        // afectar y/o bloquear el hilo principal de la aplicacion.
        return withContext(Dispatchers.IO) {

            // Preparamos el param object para invocar el HTTP request.
            // Para mas informacion de esta URL, checar la documentacion:
            // https://developer.themoviedb.org/reference/movie-now-playing-list
            val url = "https://api.themoviedb.org/3/movie/now_playing?api_key=$apiKey&page=$page&language=es"
            val request = Request.Builder()
                .url(url)
                .build()

            try {
                // Ejecutamos la solicitud HTTP
                val response: Response = client.newCall(request).execute()

                // Verificamos si la respuesta fue exitosa
                if (!response.isSuccessful) {
                    // En caso de error en la respuesta, se retorna una lista vacia.
                    return@withContext emptyList()
                }

                // Se parsea el objeto JSON regresado por el web service. Si no cuenta con un body, se regresa una lista vacia.
                val responseData = response.body?.string() ?: return@withContext emptyList()

                // Deserializamos la respuesta JSON en un objeto MovieResponse
                val movieResponse = gson.fromJson(responseData, MovieResponse::class.java)

                // Retornamos una lista de Movies creada con el response.
                movieResponse.results

            } catch (e: Exception) {
                e.printStackTrace()
                emptyList()
            }
        }
    }

    /**
     * Funcion usada para invocar una peticion HTTP asincrona para obtener la lista de generos de una Movie especificada.
     * @param movie Objeto Movie que contiene la lista de generos, pero que es necesario obtener su nombre para mostrarlo en la pantalla
     *              de detalle.
     */
    suspend fun getGenresByMovie(movie: Movie): List<Genre>{

        // Cambiamos el contexto de ejecucion del siguiente bloque de codigo para usar uno asincrono que permita hacer operaciones de lectura y asi no
        // afectar y/o bloquear el hilo principal de la aplicacion.
        return withContext(Dispatchers.IO) {

            // Preparamos el request object para obtener la lista de generos del API service.
            // Para mas informacion, checar la documentacion del endpoint:
            // https://developer.themoviedb.org/reference/genre-movie-list
            var language = "es"
            val url = "https://api.themoviedb.org/3/genre/movie/list?api_key=$apiKey&language=$language"
            val request = Request.Builder()
                .url(url)
                .build()

            try {
                // Ejecutamos la solucitud HTTP
                val response: Response = client.newCall(request).execute()

                // Verificamos si la respuesta fue exitosa
                if (!response.isSuccessful) {
                    // En caso de error en la respuesta, se retorna una lista vacia.
                    return@withContext emptyList()
                }

                // Se parsea el objeto JSON regresado por el web service. Si no cuenta con un body, se regresa una lista vacia.
                val responseData = response.body?.string() ?: return@withContext emptyList()

                // Deserializamos la respuesta JSON en un objeto GenreResponse
                val genreResponse = gson.fromJson(responseData, GenreResponse::class.java)

                // Returnamos una lista de Genres filtrados por los usados dentro del objeto Movie
                genreResponse.genres.filter { movie.genreIds.contains(it.id) }

            } catch (e: Exception) {
                e.printStackTrace()
                emptyList()
            }
        }

    }
}