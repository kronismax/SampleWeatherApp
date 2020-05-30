package com.sample.sampleweatherapp.model.city

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "city")
data class City(@PrimaryKey val name: String) : Parcelable