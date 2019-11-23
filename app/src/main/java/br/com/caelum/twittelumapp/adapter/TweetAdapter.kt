package br.com.caelum.twittelumapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import br.com.caelum.twittelumapp.R
import br.com.caelum.twittelumapp.extensions.Decodificador
import br.com.caelum.twittelumapp.modelo.Tweet
import kotlinx.android.synthetic.main.item_tweet.*
import kotlinx.android.synthetic.main.item_tweet.view.*

class TweetAdapter( private val tweets : List<Tweet> ) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val tweet = tweets[position]

        val inflater = LayoutInflater.from(parent?.context)

        val view = inflater.inflate(R.layout.item_tweet,parent,false)

        view.item_tweet_texto.text = tweet.mensagem


        tweet.foto?.let {

            view.item_tweet_foto.visibility = View.VISIBLE
            view.item_tweet_foto.setImageBitmap( Decodificador.decodificaPraBitmap(it) )

        }

        return view

    }

    override fun getItem(position: Int): Any {

        return tweets[position]

    }

    override fun getItemId(position: Int): Long {

        return tweets[position].id.toLong()

    }

    override fun getCount(): Int {

        return tweets.size

    }


}