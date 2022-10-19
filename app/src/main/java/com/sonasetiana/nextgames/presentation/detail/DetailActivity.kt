package com.sonasetiana.nextgames.presentation.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.sonasetiana.core.domain.data.DataMapper
import com.sonasetiana.core.domain.data.GameDetail
import com.sonasetiana.core.utils.gone
import com.sonasetiana.core.utils.toast
import com.sonasetiana.core.utils.visible
import com.sonasetiana.nextgames.databinding.ActivityDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDetailBinding

    private val viewModel : DetailViewModel by viewModels()

    private var isFavorite : Boolean = false

    private var gameDetail : GameDetail? = null

    private val detailId : Int by lazy {
        intent.getIntExtra(EXTRA_DATA, 0)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Detail Game"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        with(binding) {
            observeViewModel()
            getDetail()
            fab.setOnClickListener {
                gameDetail?.let { game ->
                    with(viewModel) {
                        if (isFavorite) {
                            deleteFromFavorite(game.id)
                        } else {
                            saveToFavorite(DataMapper.mappingGameDetailToFavorite(game))
                        }
                        checkIsFavorite(game.id)
                    }

                }
            }
            viewError.btnTry.setOnClickListener {
                getDetail()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home){
            onBackPressed()
        }
        return true
    }

    private fun getDetail() {
        viewModel.getDetail(detailId)
    }

    private fun ActivityDetailBinding.observeViewModel() {
        with(viewModel) {
            getIsLoading().observe(this@DetailActivity) {
                if (it) {
                    loading.root.visible()
                    viewError.root.gone()
                    containerContent.gone()
                }else {
                    loading.root.gone()
                }
            }

            successGetDetail().observe(this@DetailActivity) {
                gameDetail = it
                updateUi()
                containerContent.visible()
                checkIsFavorite(it.id)
            }

            errorGetDetail().observe(this@DetailActivity) {
                setErrorView(it)
            }

            successCheckFavorite().observe(this@DetailActivity) {
                setStatusFavorite(it.isNotEmpty())
            }

            errorCheckFavorite().observe(this@DetailActivity) {
                toast(it)
            }

            resultSaveFavorite().observe(this@DetailActivity) {
                toast(it)
            }

            resultDeleteFavorite().observe(this@DetailActivity) {
                toast(it)
            }
        }
    }

    private fun ActivityDetailBinding.updateUi() {
        gameDetail?.let {
            Glide.with(this@DetailActivity)
                .load(it.image)
                .into(ivDetailImage)

            txtDate.text = it.released
            txtTitle.text = it.nameOriginal
            txtGenres.text = it.genres.joinToString()
            txtRating.text = it.rating.toString()
            txtDescription.text = it.descriptionRaw
        }
    }

    private fun ActivityDetailBinding.setStatusFavorite(statusFavorite: Boolean) {
        isFavorite = statusFavorite
        if (isFavorite) {
            fab.setImageDrawable(ContextCompat.getDrawable(this@DetailActivity, com.sonasetiana.core.R.drawable.ic_favorite_active))
        } else {
            fab.setImageDrawable(ContextCompat.getDrawable(this@DetailActivity, com.sonasetiana.core.R.drawable.ic_favorite_inactive))
        }
    }

    private fun ActivityDetailBinding.setErrorView(message: String) {
        with(viewError) {
            root.visible()
            txtMessage.text = message
            btnTry.visible()
        }
    }

    companion object {
        private const val EXTRA_DATA = "EXTRA_DATA"
        fun open(context: Context, id: Int) {
            Intent(context, DetailActivity::class.java)
                .apply {
                    putExtra(EXTRA_DATA, id)
                }
                .run {
                    ContextCompat.startActivity(context, this, null)
                }
        }
    }
}