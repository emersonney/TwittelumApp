package br.com.caelum.twittelumapp.activity

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProviders
import br.com.caelum.twittelumapp.R
import br.com.caelum.twittelumapp.bancodedados.TwittelumDatabase
import br.com.caelum.twittelumapp.modelo.Tweet
import br.com.caelum.twittelumapp.viewmodel.TweetViewModel
import br.com.caelum.twittelumapp.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.activity_tweet.*
import java.io.File

class TweetActivity : AppCompatActivity() {

    private val viewModel: TweetViewModel by lazy{
        ViewModelProviders.of(this,ViewModelFactory).get(TweetViewModel::class.java)
    }

    val CODIGO_CAMERA: Int = 123
    var caminhoDaFoto: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tweet)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

/*        val botao = findViewById<Button>(R.id.criar_tweet)
        botao.setOnClickListener(View.OnClickListener {
            publicaTweet()
            finish()
        })*/

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            CODIGO_CAMERA -> {
                if (resultCode == Activity.RESULT_OK) {
                    carregaImagem()
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.tweet_menu, menu)
        return true // deve mostrar ou não ?

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId) {
            R.id.tweet_menu_cadastrar -> {
                publicaTweet()
                finish()
            }

            R.id.tweet_menu_camera ->{
                tiraFoto()
            }

            android.R.id.home -> finish()

        }


        return false

    }

    private fun tiraFoto(){

        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        caminhoDaFoto = "${getExternalFilesDir(Environment.DIRECTORY_PICTURES)}/${System.currentTimeMillis()}.jpg"

        Log.i("caminho", caminhoDaFoto)

        val arquivo = File(caminhoDaFoto)

        val uri = FileProvider.getUriForFile(this,"br.com.caelum.twittelum.fileprovider", arquivo)

        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)

        startActivityForResult(intent,CODIGO_CAMERA)

    }

    private fun carregaImagem(){

        val bitmap = BitmapFactory.decodeFile(caminhoDaFoto)

        val scaledBitmap = Bitmap.createScaledBitmap(bitmap,300,300, true)

        tweet_foto.setImageBitmap(scaledBitmap)

        tweet_foto.scaleType = ImageView.ScaleType.FIT_XY

    }

    private fun publicaTweet(){

        val campoDeMensagemDoTweet = findViewById<EditText>(R.id.tweet_mensagem)

        val mensagemDoTweet : String = campoDeMensagemDoTweet.text.toString()

        val tweet = Tweet(mensagemDoTweet)

        viewModel.salva(tweet)

/*        val tweetDao = TwittelumDatabase.getInstance(this).getTweetDao()
        tweetDao.salva(tweet)*/

        Toast.makeText(this, "$tweet foi salvo com sucesso :D", Toast.LENGTH_LONG).show()

    }

}
