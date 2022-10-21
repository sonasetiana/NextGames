package com.sonasetiana.core.presentation.loadingAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sonasetiana.core.databinding.ItemLoadingBinding

class LoadingMoreAdapter : LoadStateAdapter<LoadingMoreAdapter.Holder>(){

    inner class Holder(
        private val binding: ItemLoadingBinding
    ) : RecyclerView.ViewHolder(binding.root){
        fun bind(state: LoadState) = with(binding) {
            progressBar.isVisible = state is LoadState.Loading
        }
    }

    override fun onBindViewHolder(holder: Holder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): Holder {
        val inflater = LayoutInflater.from(parent.context)
        return Holder(ItemLoadingBinding.inflate(inflater, parent, false))
    }
}