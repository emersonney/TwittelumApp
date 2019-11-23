package br.com.caelum.twittelumapp.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import br.com.caelum.twittelumapp.R
import br.com.caelum.twittelumapp.adapter.TweetAdapter
import br.com.caelum.twittelumapp.bancodedados.TwittelumDatabase
import br.com.caelum.twittelumapp.modelo.Tweet
import br.com.caelum.twittelumapp.viewmodel.TweetViewModel
import br.com.caelum.twittelumapp.viewmodel.ViewModelFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_lista.*

class ListaActivity : AppCompatActivity() {

    private val viewModel: TweetViewModel by lazy {
        ViewModelProviders.of(this, ViewModelFactory).get(TweetViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista)

        viewModel.lista().observe(this, observer())


/*        val tweets = listOf("Meu primeiro tweet","Meu segunto tweet","Meu terceiro tweet",
            "Meu quato tweet","Meu quinto tweet")*/


/*      val adpater = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,tweets)
        lista_tweet.adapter = adpater*/


        fab_add.setOnClickListener({

            val intencao = Intent(this, TweetActivity::class.java)
            startActivity(intencao)

            Snackbar.make(it, "Fab clicado", Snackbar.LENGTH_SHORT).show()
        })


        val listener = AdapterView.OnItemLongClickListener { adapter, item, posicao, id ->
            val tweet = adapter.getItemAtPosition(posicao) as Tweet
            //deletar tweet
            perguntaSeDeletaEsseTweet(tweet)
            false
        }
        lista_tweet.onItemLongClickListener = listener

    }

    private fun perguntaSeDeletaEsseTweet(tweet: Tweet) {

        val dialog = AlertDialog.Builder(this)

        dialog.setMessage("Quer mesmo deletar?")
        dialog.setTitle("Atenção")
        dialog.setNegativeButton("NÃO", null)
        dialog.setPositiveButton("SIM") { _, _ ->
            viewModel.deleta(tweet)
        }

        dialog.show()
    }

    private fun observer(): Observer<List<Tweet>> {
        return Observer {tweets ->
            tweets?.let {
                lista_tweet.adapter = TweetAdapter(tweets)
            }
//            lista_tweet.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, it)
        }
    }

/*    override fun onResume() {
        super.onResume()

        val twittelumDB = TwittelumDatabase.getInstance(this)

        val tweetDao = twittelumDB.getTweetDao()

        val tweets: List<Tweet> = tweetDao.lista()

        val adpater = ArrayAdapter<Tweet>(this, android.R.layout.simple_list_item_1,tweets)

        lista_tweet.adapter = adpater

    }*/

}