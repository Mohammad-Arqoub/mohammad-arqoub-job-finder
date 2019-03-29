package com.qstss.connection

import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

/**
 * Created by Mohammad.Arqoub on 27-Mar-19.
 */
interface ApiInterface {

    @GET("{url}")
    fun getData(@Path("url") url: String,@QueryMap list: Map<String, String>): Observable<Response<ResponseBody>>

    @GET("{url}")
    fun getData(@Path("url") url: String): Observable<Response<ResponseBody>>
}