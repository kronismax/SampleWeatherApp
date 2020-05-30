package com.sample.sampleweatherapp.model.city

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CityDAO {

    @Query("select * from city")
    fun getAll(): LiveData<List<City>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(list: List<City>)

}