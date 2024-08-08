package com.example.pruebamacropay.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.AbsListView
import android.widget.ListView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.pruebamacropay.R
import com.example.pruebamacropay.ui.adapter.MovieCatalogItemAdapter
import com.example.pruebamacropay.viewmodel.MovieViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView

/**
 * Actividad que muestra un catalogo de objetos Movie en una lista scroleable.
 * Utiliza un ViewModel para gestionar los datos de la lista de Movie y maneja paginacion para cargar
 * mas items cuando el usuario se desplaza hacia abajo.
 */
class MovieCatalogActivity : AppCompatActivity() {

    // ViewModel que maneja la logica de datos para obtener la lista de objeto Movie.
    private val viewModel: MovieViewModel by viewModels()
    // Inicializacion del objeto ListView que mostrara el listado de Movie
    private lateinit var listViewMovies: ListView
    // Adaptador para el ListView que maneja la visualizacion de la lista de objetos Movie
    private lateinit var adapter: MovieCatalogItemAdapter
    // Numero de página para la paginación del ListView
    private var currentPage = 1
    // Flag para indicar si es necesario cargar mas items Movie dentro del ListView
    private var isLoading = false

    /**
     * Metodo usado para crear la actividad y mostrarla.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Se carga el XML que representa la pantalla a mostrar.
        setContentView(R.layout.activity_movie_catalog)

        // Se inicializa el widget que mostrara el mensaje de bienvenida.
        findViewById<MaterialTextView>(R.id.welcome_message).text = getString(R.string.mensaje_bienvenida)

        // Inicializa el ListView y el adaptador para mostrar la lista de Movie.
        listViewMovies = findViewById(R.id.movie_list_view)
        adapter = MovieCatalogItemAdapter(this, mutableListOf())
        listViewMovies.adapter = adapter

        // Se obtiene el listado de Movie que se usara para poblar el ListView
        viewModel.fetchMovies(currentPage)

        // Como es un proceso asincrono, se observa los cambios en la lista de Movie proporcionada por el ViewModel
        viewModel.movieList.observe(this, Observer { movieList ->

            // Se actualiza la lista de Movie mostrada actualmente por el adaptador de acuerdo a la paginacion actual.
            if (currentPage == 1) {
                adapter.updateMovies(movieList)
            } else {
                adapter.addMovies(movieList)
            }
            isLoading = false
        })

        // Scroll listener que detectara cuando ocurra un scroll event dentro del ListView
        listViewMovies.setOnScrollListener(object : AbsListView.OnScrollListener {

            override fun onScrollStateChanged(view: AbsListView?, scrollState: Int) {
                // No se requiere acción para cambios en el estado de desplazamiento
            }

            // Metodo que sera invocado cuando occure un evento scroll.
            override fun onScroll(view: AbsListView?, firstVisibleItem: Int, visibleItemCount: Int, totalItemCount: Int) {

                val lastVisibleItem = firstVisibleItem + visibleItemCount

                // Verifica si se necesita cargar más objetos Movie para actualizar la lista (cuando se alcanza el final de la lista) pero cuidando
                // evitando multiples llamadas asincronas al mismo tiempo (ya que el usuario puede scrolear rapidamente la lista sin permitir que
                // el fetch anterior haya terminado).
                if (!isLoading && lastVisibleItem >= totalItemCount) {

                    // Se indica hay un proceso activo para obtener mas Movie.
                    isLoading = true
                    // Se incrementa el paginador para obtener el siguiente batch de Movie.
                    currentPage++
                    // Se invoca la funcion que traera una nueva lista de objetos Movie.
                    viewModel.fetchMovies(currentPage)
                }
            }
        })


        // Configura el listener para los clics en los ítems del ListView.
        listViewMovies.setOnItemClickListener { _, _, position, _ ->

            // Obtenemos el objeto Movie de acuerdo a la posicion en la lista del item presionado.
            val movie = adapter.getItem(position)

            // Inicia la actividad que mostrara el detalle del objeto Movie presionado.
            val intent = Intent(this, MovieDetailActivity::class.java)

            // Se envia a este activity el data del item presionado.
            intent.putExtra("movie", movie)

            // Se abre el activity
            startActivity(intent)
        }

        // Click listener usado para el boton de cierre de sesion.
        findViewById<MaterialButton>(R.id.logout_button).setOnClickListener {
            // Finaliza este activity y regresa al usuario a la pantalla de Login.
            finish()
        }

    }
}