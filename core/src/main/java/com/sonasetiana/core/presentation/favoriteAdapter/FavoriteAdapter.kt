package com.sonasetiana.core.presentation.favoriteAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sonasetiana.core.databinding.ItemFavoriteBinding
import com.sonasetiana.core.domain.data.Favorite

class FavoriteAdapter(
    private val callback: FavoriteAdapterCallback? = null,
) : ListAdapter<Favorite, FavoriteAdapter.Holder>(DIFF_CALLBACK){

    inner class Holder(
        private val binding: ItemFavoriteBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Favorite) = with(binding) {
            txtRating.text = item.rating.toString()
            txtTitle.text = item.name
            Glide.with(image)
                .load(item.image)
                .into(image)
            btnFavorite.setOnClickListener {
                callback?.onDeleteFavorite(item.gameId)
            }
            itemView.setOnClickListener {
                callback?.onOpenDetail(item.gameId)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        return Holder(ItemFavoriteBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val favorite = getItem(position)
        holder.bind(favorite)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Favorite>() {
            override fun areItemsTheSame(oldItem: Favorite, newItem: Favorite): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Favorite, newItem: Favorite): Boolean {
                return oldItem.gameId == newItem.gameId
            }
        }
    }
}