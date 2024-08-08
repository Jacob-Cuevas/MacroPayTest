package com.example.pruebamacropay.ui.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.pruebamacropay.R
import com.example.pruebamacropay.model.Movie
import com.example.pruebamacropay.viewmodel.GenreViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView

/**
 * Actividad que muestra una pantalla con el detalle del objeto Movie que fue presionado en el ListView del Activity MovieCatalog.
 * Es responsable de mostrar el titulo, fecha de estreno, clasificacion, generos y descripcion.
 */
class MovieDetailActivity : AppCompatActivity() {

    // ViewModel utilizado para obtener el listado de items Genre
    private val viewModel: GenreViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Se carga el XML que representa la pantalla a mostrar.
        setContentView(R.layout.activity_movie_detail)

        // Se obtiene del bundle object pasado como parametro a esta actividad, el objeto Movie con los datos a mostrar.
        val movie: Movie = intent.getSerializableExtra("movie") as? Movie ?: return

        // Usamos la libreria Glide para cargar la imagen desde una URL que provee el webservice para obtener la imagen a mostrar.
        // Mientras se carga la imagen desde internet, se muestra una imagen custom.
        // Documentacion: https://developer.themoviedb.org/docs/image-basics
        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w500${movie.backdropPath}")
            .placeholder(R.drawable.loading_item_movie_catalog)
            .error(R.drawable.loading_item_movie_catalog)
            .into(findViewById(R.id.poster_image))

        // Se inicializa el widget que mostrara el titulo del objeto Movie
        findViewById<MaterialTextView>(R.id.title).text = movie.title

        // Se muestra el valor estreno dentro del widget.
        findViewById<MaterialTextView>(R.id.release_date).text = getString(R.string.release_date_label, movie.releaseDate)

        // Se prepara el texto que sera mostrado dentro del widget de clasificacion.
        val classification = if (movie.isAdult) getString(R.string.solo_adultos_classification_label) else getString(
            R.string.todo_publico_classification_label
        )
        findViewById<MaterialTextView>(R.id.classification).text = getString(R.string.classificacion_label, classification)

        // Se muestra el texto descripcion del objeto Movie
        findViewById<MaterialTextView>(R.id.description).text = movie.description

        // Se inicializa el widget que contendra el texto del genero del objeto Movie.
        val textViewGenres = findViewById<MaterialTextView>(R.id.genres)
        // Se obtiene el listado de Genre de acuerdo al listado de IDs que contiene el objeto Movie.
        viewModel.fetchGenresByMovie(movie)

        // Como es un proceso asincrono, observamos cuando la variable "genresList" ya tenga valores para tomar solo el nombre
        // de cada objeto Genre retornado y concatenarlo en un solo string para mostrarlo.
        viewModel.genresList.observe(this, Observer { genres ->
            textViewGenres.text = genres.map { it.name }.joinToString(",")
        })

        // Evento click asignado al boton "Volver" que cerrara este activity y regresara al anterior.
        findViewById<MaterialButton>(R.id.back_to_catalog_page).setOnClickListener {
            finish()
        }
    }
}
