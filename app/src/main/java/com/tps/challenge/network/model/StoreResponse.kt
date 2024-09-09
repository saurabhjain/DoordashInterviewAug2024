package com.tps.challenge.network.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.tps.challenge.database.TABLE_STORES_LIST

/**
 * Store remote data model.
 */
@Entity(tableName = TABLE_STORES_LIST)
data class StoreResponse(
    @PrimaryKey
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("cover_img_url")
    val coverImgUrl: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("delivery_fee")
    val deliveryFeeCents: String
)