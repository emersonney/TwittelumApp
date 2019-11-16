package br.com.caelum.twittelumapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.caelum.twittelumapp.TwittelumApplication
import br.com.caelum.twittelumapp.repository.TweetRepository
import br.com.caelum.twittelumapp.bancodedados.TwittelumDatabase

object ViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val twittelumBD = TwittelumDatabase.getInstance(TwittelumApplication.getInstance())
        val tweetDao = twittelumBD.getTweetDao()
        val tweetRepository = TweetRepository(tweetDao)
        val tweetViewModel = TweetViewModel(tweetRepository)

        return tweetViewModel as T

    }
}

