package com.sonasetiana.favorite.presentation

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.sonasetiana.core.di.DynamicFeatureDependencies
import com.sonasetiana.core.domain.usecase.GameInteractor
import com.sonasetiana.core.presentation.favoriteAdapter.FavoriteAdapter
import com.sonasetiana.core.presentation.favoriteAdapter.FavoriteAdapterCallback
import com.sonasetiana.core.utils.gone
import com.sonasetiana.core.utils.toast
import com.sonasetiana.core.utils.visible
import com.sonasetiana.favorite.R
import com.sonasetiana.favorite.databinding.ActivityFavoriteBinding
import com.sonasetiana.favorite.di.DaggerDfmDaggerComponent
import com.sonasetiana.favorite.factory.ViewModelFactory
import com.sonasetiana.nextgames.presentation.detail.DetailActivity
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding : ActivityFavoriteBinding

    @Inject
    lateinit var interactor: GameInteractor

    private lateinit var viewModel: FavoriteViewModel

    private lateinit var favoriteAdapter : FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        // Since Hilt doesn't work with dynamic feature modules, use plain Dagger instead.
        // cf. https://developer.android.com/training/dependency-injection/hilt-multi-module#dfm
        DaggerDfmDaggerComponent.factory().create(
            EntryPointAccessors.fromApplication(this, DynamicFeatureDependencies::class.java)
        ).inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Game Favorit"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        viewModel = ViewModelProvider(this, ViewModelFactory(interactor))[FavoriteViewModel::class.java]
        with(binding) {
            setupView()
            observeViewModel()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getFavorites()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home){
            onBackPressed()
        }
        return true
    }

    private fun ActivityFavoriteBinding.setupView() {
        favoriteAdapter = FavoriteAdapter(object : FavoriteAdapterCallback {
            override fun onDeleteFavorite(id: Int) {
                viewModel.deleteFromFavorite(id)
            }

            override fun onOpenDetail(id: Int) {
                DetailActivity.open(this@FavoriteActivity, id)
            }

        })
        with(rvFavorite) {
            setHasFixedSize(true)
            adapter = favoriteAdapter
        }
    }

    private fun ActivityFavoriteBinding.setErrorView(message: String) {
        with(viewError) {
            root.visible()
            txtMessage.text = message
        }
    }

    private fun ActivityFavoriteBinding.observeViewModel() {
        with(viewModel) {
            getIsLoading().observe(this@FavoriteActivity) {
                if (it) {
                    rvFavorite.gone()
                    viewError.root.gone()
                    loading.root.visible()
                }else {
                    loading.root.gone()
                }
            }

            successGetFavorites().observe(this@FavoriteActivity) {
                if (it.isEmpty()) {
                    setErrorView(getString(R.string.empty_favorite))
                }else {
                    Log.d("TAG_FAVORITE", "getFavorites: ${it.size}")
                    favoriteAdapter.set(it)
                    rvFavorite.visible()
                }
            }

            errorGetFavorites().observe(this@FavoriteActivity) {
                setErrorView(it)
            }

            resultDeleteFavorite().observe(this@FavoriteActivity) {
                toast(it)
                getFavorites()
            }
        }
    }
}