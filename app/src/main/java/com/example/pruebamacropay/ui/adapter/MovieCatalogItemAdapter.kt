package com.example.pruebamacropay.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.RatingBar
import com.bumptech.glide.Glide
import com.example.pruebamacropay.R
import com.example.pruebamacropay.model.Movie
import com.google.android.material.textview.MaterialTextView

/**
 * Adaptador que manejara los items del ListView usando dentro de MovieCatalogActivity.
 * Este adaptador extiende ArrayAdapter y se encargara de crear y visualizar objetos Movie dentro del listview.
 *
 * @param context Contexto en el que se esta usando el ListView, en este caso, el activity que lo contiene.
 * @param movies Listado de objetos Movie que se va a mostrar dentro del listview.
 */
class MovieCatalogItemAdapter(private val context: Context, private val movies: MutableList<Movie>)
    : ArrayAdapter<Movie>(context, 0, movies) {

    /**
     * Funcion para regresar la cantidad de items del listview.
     */
    override fun getCount(): Int = movies.size

    /**
     * Funcion que devuelve el objeto Movie de acuerdo a la posicion del listView item que fue presionado.
     */
    override fun getItem(position: Int): Movie? = movies[position]

    /**
     * Funcion que devuelve la posicion dentro en el listview del item que fue presionado.
     */
    override fun getItemId(position: Int): Long = position.toLong()

    /**
     * Funcion que crea y devuelve un View object que representa el Movie item que sera mostrado dentro del listview.
     * @param position Entero que indica la posicion del item dentro de listview.
     * @param convertView Vista reutilizable que es null si no hay una.
     * @param parent El ViewGroup que se anadira a la vista final.
     * @return eEl objeto View que representa el listview a mostrar.
     */
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // Si el view a usar no esta definido, se crea una nuevo a partir de un XML layout.
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_movie_catalog, parent, false)
        // Se obtiene el objeto Movie que representara este item.
        val movie = getItem(position) as Movie

        // Se inicializan los widgets que mostraran las propiedades del objeto Movie.
        val titleTextView = view.findViewById<MaterialTextView>(R.id.movie_title)
        val languageTextView = view.findViewById<MaterialTextView>(R.id.movie_language)
        val imageView = view.findViewById<ImageView>(R.id.movie_image)
        val ratingBarMovie = view.findViewById<RatingBar>(R.id.ratingbar_movie)

        // Se muestran en pantalla el valor de cada widget.
        titleTextView.text = movie.title
        languageTextView.text = movie.language
        ratingBarMovie.rating = (movie.voteAverage/2).toFloat()

        // Usando la libreria Glide se carga la imagen a partir de una URL que debe ser descargada de internet.
        // Para mas informacion, consultar la documentacion:
        // https://developer.themoviedb.org/docs/image-basics
        // Se usan imagenes por defecto mientras se carga la imagen de internet.
        Glide.with(context)
            .load("https://image.tmdb.org/t/p/w300${movie.posterPath}")
            .placeholder(R.drawable.loading_item_movie_catalog)
            .error(R.drawable.loading_item_movie_catalog)
            .into(imageView)


        return view
    }

    /**
     * Funcion que reemplaza el listado actual de objetos Movie con una nueva lista y notifica al adaptador para actualizar el ListView.
     * @param newMovies La nueva lista de objetos Movie que reemplazará la lista actual.
     */
    fun updateMovies(newMovies: List<Movie>) {
        clear()
        addAll(newMovies)
        notifyDataSetChanged()
    }

    /**
     * Funcion que añade más objetos Movie a la lista existente y notifica al adaptador para actualizar el listview.
     * @param newMovies La lista de nuevas películas que se añadirá a la lista actual.
     */
    fun addMovies(newMovies: List<Movie>) {
        addAll(newMovies)
        notifyDataSetChanged()
    }
}

