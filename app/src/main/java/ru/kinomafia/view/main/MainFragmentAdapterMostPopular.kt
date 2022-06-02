package ru.kinomafia.view.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.kinomafia.R
import ru.kinomafia.model.entities.FilmItem

class MainFragmentAdapterMostPopular(private var onItemViewClickListener: MainFragment.OnItemViewClickListener?) :
                                RecyclerView.Adapter<MainFragmentAdapterMostPopular.MainFragmentFilmHolder>(){

    private var filmData: List<FilmItem> = listOf()

    fun setFilmInfo(data: List<FilmItem>) {
        filmData = data
        notifyDataSetChanged()
    }

    fun removeListener() {
        onItemViewClickListener = null
    }

    inner class MainFragmentFilmHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(filmItem: FilmItem) {
            itemView.apply {
                findViewById<ImageView>(R.id.poster_recycler_film_holder).setImageResource(R.drawable.poster_no)
                findViewById<TextView>(R.id.name_recycler_film_holder).text = filmItem.title
                findViewById<TextView>(R.id.genre_recycler_film_holder).text = filmItem.imDbRating
                findViewById<TextView>(R.id.year_recycler_film_holder).text = filmItem.year
                setOnClickListener {
                    onItemViewClickListener?.onItemViewClick(filmItem)
                }
            }
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