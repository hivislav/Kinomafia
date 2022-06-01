package ru.kinomafia.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.kinomafia.R
import ru.kinomafia.model.FilmInfo

class MainFragmentAdapterHits(private var onItemViewClickListener: OnItemViewClickListener?) :
                                RecyclerView.Adapter<MainFragmentAdapterHits.MainFragmentFilmHolder>(){

    private var filmData: List<FilmInfo> = listOf()

    interface OnItemViewClickListener{
        fun onItemViewClick(filmInfo: FilmInfo)
    }

    fun setFilmInfo(data: List<FilmInfo>) {
        filmData = data
        notifyDataSetChanged()
    }

    fun removeListener() {
        onItemViewClickListener = null
    }

    inner class MainFragmentFilmHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(filmInfo: FilmInfo) {
            itemView.findViewById<ImageView>(R.id.poster_recycler_film_holder).setImageResource(
                                                                                filmInfo.film.poster)
            itemView.findViewById<TextView>(R.id.name_recycler_film_holder).text = filmInfo.film.name
            itemView.findViewById<TextView>(R.id.genre_recycler_film_holder).text = filmInfo.film.genre
            itemView.findViewById<TextView>(R.id.year_recycler_film_holder).text = filmInfo.film.year.toString()

            itemView.setOnClickListener { onItemViewClickListener?.onItemViewClick(filmInfo) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainFragmentFilmHolder {
        val context: Context = parent.context
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.recycler_film_holder, parent, false)
        return MainFragmentFilmHolder(view)
    }

    override fun onBindViewHolder(holder: MainFragmentFilmHolder, position: Int) {
        holder.bind(filmData[position])
    }

    override fun getItemCount(): Int {
        return filmData.size
    }
}