package com.example.pruebamacropay.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pruebamacropay.model.Movie
import com.example.pruebamacropay.network.MovieNetwork
import kotlinx.coroutines.launch

/**
 * ViewModel que maneja la logica de negocio para obtener el listado de objetos Movie que sera usado
 * en el UI.
 */
class MovieViewModel : ViewModel() {

    // Instancia de la clase que nos ayudara a efectuar las peticiones HTTP.
    private val movieNetwork = MovieNetwork()

    // MutableLiveData que contendra la lista de objetos Movie una vez que el proceso asincrono haya terminado.
    private val _movieList = MutableLiveData<List<Movie>>()

    // LiveData que puede ser llamado por la clase que maneje el UI para usar el listado simple de objetos Movie una vez
    // que se tiene valores en el _movieList
    val movieList: LiveData<List<Movie>> get() = _movieList

    /**
     * Funcion que recupera una lista de objetos Movie con una paginacion en especifico y actualiza el LiveData correspondiente.
     * Se ejecuta en una corutina para no bloquear el hilo principal.
     * @param page Número de página para la paginación de las películas.
     */
    fun fetchMovies(page: Int) {
        // Se inicia una corutina dentro del ViewModel.
        viewModelScope.launch {
            _movieList.value = movieNetwork.getMovies(page)
        }
    }
}
