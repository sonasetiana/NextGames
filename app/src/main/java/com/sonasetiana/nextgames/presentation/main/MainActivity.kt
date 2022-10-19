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
import com.jakewharton.rxbinding3.appcompat.queryTextChanges
import com.sonasetiana.core.presentation.gameAdapter.GameAdapter
import com.sonasetiana.core.utils.EndlessScrollCallback
import com.sonasetiana.core.utils.gone
import com.sonasetiana.core.utils.onScroll
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        with(binding) {
            setupView()
            observeViewModel()
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
                viewModel.searchGame(it)
                searchView.clearFocus()
            }
        compositeDisposable.add(disposable)

        menuItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(p0: MenuItem?): Boolean = true

            override fun onMenuItemActionCollapse(p0: MenuItem?): Boolean {
                viewModel.getGames()
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
        nestedScroll.onScroll(object : EndlessScrollCallback {
            override fun onReachBottom() {
                viewModel.getMoreGames()
            }
        })
        with(rvGames) {
            adapter = gameAdapter
            setHasFixedSize(true)
        }
        swipeRefresh.setOnRefreshListener {
            swipeRefresh.isRefreshing = false
            viewModel.getGames()
        }
    }

    private fun ActivityMainBinding.setErrorView(message: String) {
        with(viewError) {
            root.visible()
            txtMessage.text = message
        }
    }

    private fun ActivityMainBinding.observeViewModel() {
        with(viewModel){
            getIsLoading().observe(this@MainActivity) {
                if (it) {
                    loading.root.visible()
                    viewError.root.gone()
                    containerList.gone()
                }else {
                    loading.root.gone()
                }
            }
            successGetGames().observe(this@MainActivity) {
                if (it.isEmpty()) {
                    setErrorView(getString(R.string.empty_games))
                } else {
                    gameAdapter.set(it)
                    containerList.visible()
                }
            }
            errorGetGames().observe(this@MainActivity) {
                setErrorView(it)
            }
            loadingSearchGame().observe(this@MainActivity) {
                if (it) {
                    loading.root.visible()
                    viewError.root.gone()
                    containerList.gone()
                }else {
                    loading.root.gone()
                }
            }
            successSearchGame().observe(this@MainActivity) {
                if (it.isEmpty()) {
                    setErrorView(getString(R.string.empty_games))
                } else {
                    gameAdapter.set(it)
                    containerList.visible()
                }
            }
            errorSearchGame().observe(this@MainActivity) {
                setErrorView(it)
            }
            loadingGetMoreGames().observe(this@MainActivity) {
                if (it) loadingMore.visible() else loadingMore.gone()
            }
            successGetMoreGames().observe(this@MainActivity) {
                gameAdapter.addAll(it)
            }
            getGames()
        }
    }


}