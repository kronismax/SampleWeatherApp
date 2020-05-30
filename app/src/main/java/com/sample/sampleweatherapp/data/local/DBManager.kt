package com.sample.sampleweatherapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.sample.sampleweatherapp.R
import com.sample.sampleweatherapp.model.city.City
import com.sample.sampleweatherapp.model.city.CityDAO
import java.util.concurrent.Executors

class DBManager(context: Context) {

    companion object {
        private const val NAME = "sampleDB"
        private const val VERSION = 1
    }

    private lateinit var db: LocalDB

    init {
        db = Room.databaseBuilder(context.applicationContext, LocalDB::class.java, NAME)
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(database: SupportSQLiteDatabase) {
                    super.onCreate(database)
                    if (::db.isInitialized) {
                        val cities = context.resources.getStringArray(R.array.cities).map { City(it) }
                        Executors.newSingleThreadExecutor().execute { db.cityDAO().insert(cities) }
                    }
                }
            }).build()
    }

    @Database(entities = [City::class], version = VERSION)
    abstract class LocalDB : RoomDatabase() {
        abstract fun cityDAO(): CityDAO
    }

    fun getCityDAO() = db.cityDAO()
}