package ru.kinomafia.view.favourite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.favourite_recycler_film_item.view.*
import kotlinx.android.synthetic.main.recycler_film_holder.view.*
import ru.kinomafia.R
import ru.kinomafia.model.entities.FilmInfo

class FavouriteFragmentAdapter: RecyclerView.Adapter<FavouriteFragmentAdapter.FavouriteViewHolder>() {

    private var favouriteFilmList: List<FilmInfo> = listOf()

    fun setFavouriteFilmList(data: List<FilmInfo>) {
        this.favouriteFilmList = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteViewHolder {
        return FavouriteViewHolder(LayoutInflater.from(parent.context).inflate
            (R.layout.favourite_recycler_film_item, parent, false))
    }

    override fun onBindViewHolder(holder: FavouriteViewHolder, position: Int) {
        holder.bind(this.favouriteFilmList[position])
    }

    override fun getItemCount(): Int {
        return favouriteFilmList.size
    }

    inner class FavouriteViewHolder(view: View): RecyclerView.ViewHolder(view) {
        fun bind(filmInfo: FilmInfo) {
            itemView.apply {
                findViewById<TextView>(R.id.favourite_name).text = filmInfo.title
                findViewById<TextView>(R.id.favourite_year).text = filmInfo.year
                findViewById<TextView>(R.id.favourite_genre).text = filmInfo.genres
                findViewById<TextView>(R.id.favourite_note).text = filmInfo.note

                Picasso
                    .get().load(filmInfo.image)
                    .resize(880, 1320)
                    .onlyScaleDown()
                    .placeholder(R.drawable.poster_no)
                    .into(itemView.favourite_poster)
            }
        }
    }
}