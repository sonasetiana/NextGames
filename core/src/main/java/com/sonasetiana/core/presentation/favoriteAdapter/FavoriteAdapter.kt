package com.sonasetiana.core.presentation.favoriteAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sonasetiana.core.databinding.ItemFavoriteBinding
import com.sonasetiana.core.domain.data.Favorite

class FavoriteAdapter(
    private val callback: FavoriteAdapterCallback? = null,
) : RecyclerView.Adapter<FavoriteAdapter.Holder>(){

    private var items : ArrayList<Favorite> = ArrayList()

    fun set(newItems : List<Favorite>) {
        items = ArrayList(newItems)
        notifyDataSetChanged()
    }

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
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}