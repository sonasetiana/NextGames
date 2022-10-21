package com.sonasetiana.core.presentation.gameAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sonasetiana.core.databinding.ItemGameBinding
import com.sonasetiana.core.domain.data.Game

class GameAdapter(
    private val callback: ((Int) -> Unit)? = null
) : PagingDataAdapter<Game, GameAdapter.Holder>(DIFF_CALLBACK){

    inner class Holder(
        private val binding: ItemGameBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Game) = with(binding) {
            txtRating.text = item.rating.toString()
            txtTitle.text = item.name
            Glide.with(image)
                .load(item.image)
                .into(image)
            itemView.setOnClickListener {
                callback?.invoke(item.id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        return Holder(ItemGameBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }


    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Game>() {
            override fun areItemsTheSame(oldItem: Game, newItem: Game): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Game, newItem: Game): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}