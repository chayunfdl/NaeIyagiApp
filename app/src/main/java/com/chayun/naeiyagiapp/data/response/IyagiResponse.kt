package com.chayun.naeiyagiapp.data.response

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity(tableName = "listStory")
@Parcelize
data class IyagiResponse(
    @PrimaryKey
    @field:SerializedName("listStory")
    val listIyagi: List<ListIyagiItem>
) : Parcelable

@Entity(tableName = "listStory")
@Parcelize
data class ListIyagiItem(
    @PrimaryKey
    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("photoUrl")
    val photoUrl: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("description")
    val description: String,

    @field:SerializedName("lon")
    val lon: Double,

    @field:SerializedName("lat")
    val lat: Double,
) : Parcelable