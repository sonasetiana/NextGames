package com.sonasetiana.core.presentation.gameAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sonasetiana.core.databinding.ItemGameBinding
import com.sonasetiana.core.domain.data.Game

class GameAdapter(
    private val callback: ((Int) -> Unit)? = null
) : RecyclerView.Adapter<GameAdapter.Holder>(){

    private var items : ArrayList<Game> = ArrayList()

    fun set(newItems : List<Game>) {
        items = ArrayList(newItems)
        notifyDataSetChanged()
    }

    fun addAll(newItems : List<Game>) {
        items.addAll(newItems)
        notifyDataSetChanged()
    }

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
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}