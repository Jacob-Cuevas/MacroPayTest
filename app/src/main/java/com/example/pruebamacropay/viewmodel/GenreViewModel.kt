package com.example.pruebamacropay.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pruebamacropay.model.Genre
import com.example.pruebamacropay.network.MovieNetwork
import androidx.lifecycle.viewModelScope
import com.example.pruebamacropay.model.Movie
import kotlinx.coroutines.launch

/**
 * ViewModel que maneja la logica de negocios para el Genre model
 * Proporciona el listado de Genre de acuerdo a un Movie indicado.
 */
class GenreViewModel: ViewModel() {

    // Inicializacion de la clase que ayudara a realizar las peticiones HTTP
    private val movieNetwork = MovieNetwork()
    // List Muteable que contendra la lista de objetos Genre
    private val _genresList = MutableLiveData<List<Genre>>()
    // LiveData que tendra un valor cuando observer que la lista de Genre esta disponible y devolvera una lista simple de objetos
    // Genre que pueda ser facilmente usado en el UI.
    val genresList: LiveData<List<Genre>> get() = _genresList

    /**
     * Funcion que recupera una lista de objetos Genre y actualiza el LiveData correspondiente.
     * Se ejecuta en una corutina para no bloquear el hilo principal.
     * @param movie Objeto Movie que contiene los IDs de los Genre que deben de ser recuperados.
     */
    fun fetchGenresByMovie(movie: Movie){
        // Se inicia una corutina dentro del ViewModel.
        viewModelScope.launch {
            _genresList.value = movieNetwork.getGenresByMovie(movie)
        }
    }
}