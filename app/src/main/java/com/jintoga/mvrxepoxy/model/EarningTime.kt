package com.jintoga.mvrxepoxy.model

import com.squareup.moshi.Json

data class EarningTime(
        @get:Json(name = "ticker") @Json(name = "ticker") val ticker: String,
        @get:Json(name = "when") @Json(name = "when") val earningTime: String
)