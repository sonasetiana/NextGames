package com.sonasetiana.nextgames.presentation.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sonasetiana.nextgames.databinding.ActivitySplashBinding
import com.sonasetiana.nextgames.presentation.main.MainActivity
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit

class SplashActivity: AppCompatActivity() {

    private lateinit var binding : ActivitySplashBinding

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        compositeDisposable.add(Observable.timer(2, TimeUnit.SECONDS)
            .subscribe {
                openMainPage()
            })
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    private fun openMainPage() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}