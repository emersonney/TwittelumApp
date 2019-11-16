package br.com.caelum.twittelumapp.bancodedados

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.caelum.twittelumapp.modelo.Tweet


@Database(entities = [Tweet::class], version = 1)
abstract class TwittelumDatabase: RoomDatabase() {

    abstract fun getTweetDao(): TweetDao

    companion object{

        private var database: TwittelumDatabase? = null

        private val DATABASE = "TwittelumDB"

        fun getInstance(context: Context): TwittelumDatabase {

            //            if (banco == null) {
            //                banco = criaBanco(contexto)
            //            }
            //            return banco

            return this.database ?: criaBanco(context).also{
                this.database = it
            }
        }

        private fun criaBanco(context: Context): TwittelumDatabase {
            val database: TwittelumDatabase = Room.databaseBuilder(context, TwittelumDatabase::class.java, DATABASE)
                .allowMainThreadQueries()
                .build()
            return database
        }
    }

}