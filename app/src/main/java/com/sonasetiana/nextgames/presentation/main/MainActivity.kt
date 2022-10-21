package com.sonasetiana.nextgames.presentation.main

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.paging.LoadState
import com.jakewharton.rxbinding3.appcompat.queryTextChanges
import com.sonasetiana.core.data.remote.models.toApiError
import com.sonasetiana.core.presentation.gameAdapter.GameAdapter
import com.sonasetiana.core.presentation.loadingAdapter.LoadingMoreAdapter
import com.sonasetiana.core.utils.gone
import com.sonasetiana.core.utils.visible
import com.sonasetiana.nextgames.R
import com.sonasetiana.nextgames.databinding.ActivityMainBinding
import com.sonasetiana.nextgames.presentation.detail.DetailActivity
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    private val viewModel : MainViewModel by viewModels()

    private lateinit var gameAdapter: GameAdapter

    private val compositeDisposable = CompositeDisposable()

    private var keyword: String = ""

    private var isRequestSearching: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        with(binding) {
            setupView()
            getGames()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val menuItem = menu?.findItem(R.id.search)
        val searchView = menuItem?.actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search)

        val disposable = searchView.queryTextChanges()
            .debounce(500, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .filter { it.toString().isNotEmpty() }
            .map {
                it.toString().lowercase()
            }
            .subscribe {
                keyword = it
                searchGame()
                searchView.clearFocus()
            }
        compositeDisposable.add(disposable)

        menuItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(p0: MenuItem?): Boolean {
                isRequestSearching = true
                return true
            }

            override fun onMenuItemActionCollapse(p0: MenuItem?): Boolean {
                isRequestSearching = false
                getGames()
                return true
            }

        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.favorite) {
            val uri = Uri.parse("nextgames://favorite")
            startActivity(Intent(Intent.ACTION_VIEW, uri))
        }
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    private fun ActivityMainBinding.setupView() {
        gameAdapter = GameAdapter{
            DetailActivity.open(this@MainActivity, it)
        }

        gameAdapter.addLoadStateListener {
            when (it.source.refresh) {
                is LoadState.Loading -> {
                    viewError.root.gone()
                    rvGames.gone()
                    loading.root.visible()
                }
                is LoadState.NotLoading -> {
                    loading.root.gone()
                    rvGames.visible()
                }
                is LoadState.Error -> {
                    loading.root.gone()
                    val err = it.source.refresh as? LoadState.Error
                    err?.let {  t ->
                        setErrorView(t.error.toApiError().message.orEmpty())
                    }
                }
            }
        }

        with(rvGames) {
            adapter = gameAdapter.withLoadStateFooter(footer = LoadingMoreAdapter())
            setHasFixedSize(true)
        }
        swipeRefresh.setOnRefreshListener {
            swipeRefresh.isRefreshing = false
            refresh()
        }
        viewError.btnTry.setOnClickListener {
            refresh()
        }
    }

    private fun refresh() {
        if (isRequestSearching) {
            searchGame()
        } else {
            getGames()
        }
    }

    private fun getGames() {
        viewModel.getGames().observe(this@MainActivity) {
            gameAdapter.submitData(lifecycle, it)
        }
    }

    private fun searchGame() {
        viewModel.searchGame(keyword).observe(this@MainActivity) {
            gameAdapter.submitData(lifecycle, it)
        }
    }

    private fun ActivityMainBinding.setErrorView(message: String) {
        with(viewError) {
            root.visible()
            txtMessage.text = message
        }
    }

}