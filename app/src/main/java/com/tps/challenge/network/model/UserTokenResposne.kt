package com.tps.challenge.network.model

import com.google.gson.annotations.SerializedName

/**
 * User token data model.
 */
data class UserTokenResponse(
    @SerializedName("token")
    val token: String
)
