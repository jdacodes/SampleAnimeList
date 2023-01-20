package com.jdacodes.sampleanimelist.ui.animelist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jdacodes.sampleanimelist.database.Anime
import com.jdacodes.sampleanimelist.databinding.ListItemAnimeBinding

class AnimeAdapter(val clickListener: AnimeItemListener) :
    ListAdapter<Anime, RecyclerView.ViewHolder>(AnimeDiffCallback()) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val anime = getItem(position)
        (holder as AnimeViewHolder).bind(anime!!, clickListener)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return AnimeViewHolder(
            ListItemAnimeBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    class AnimeViewHolder(
        private val binding: ListItemAnimeBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Anime, clickListener: AnimeItemListener) {

            binding.anime = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

    }

}

private class AnimeDiffCallback : DiffUtil.ItemCallback<Anime>() {

    override fun areItemsTheSame(oldItem: Anime, newItem: Anime): Boolean {
        return oldItem.animeId == newItem.animeId
    }

    override fun areContentsTheSame(oldItem: Anime, newItem: Anime): Boolean {
        return oldItem == newItem
    }
}

class AnimeItemListener(val clickListener: (animeId: Int,) -> Unit) {
    fun onClick(anime: Anime) = clickListener(anime.animeId!!)
}