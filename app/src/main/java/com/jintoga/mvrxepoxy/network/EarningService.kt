package com.jintoga.mvrxepoxy.network

import com.jintoga.mvrxepoxy.model.EarningTime
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface EarningService {

    @GET(".")
    fun loadEarnings(@Query("date") date: String): Observable<List<EarningTime>>

}