package com.qstss.connection

import com.progresssoft.jobfinder.utill.BASE_URL_GITHUB
import com.progresssoft.jobfinder.utill.BASE_URL_GOV
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory


/**
 * Created by Mohammad.Arqoub on 27-Mar-19.
 */

class ServiceGenerator {

    companion object {
        fun createGitHub(): ApiInterface {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(
                    RxJava2CallAdapterFactory.create()
                )
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL_GITHUB)
                .build()

            return retrofit.create(ApiInterface::class.java)
        }

        fun createGov(): ApiInterface {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(
                    RxJava2CallAdapterFactory.create()
                )
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL_GOV)
                .build()

            return retrofit.create(ApiInterface::class.java)
        }

        val apiServerGitHub by lazy {
            createGitHub()
        }
        val apiServerGov by lazy {
            createGov()
        }

        var disposable: Disposable? = null


        @JvmStatic fun requestGitHub(url: String, params: HashMap<String, String>, listener: ResponseCallBack) {
            disposable =
                apiServerGitHub.getData(url, params)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                            { result -> listener.success(result) },
                            { error -> listener.error(error.message) }
                        )
        }

        @JvmStatic fun requestGov(url: String, params: HashMap<String, String>, listener: ResponseCallBack) {
            disposable =
                apiServerGov.getData(url, params)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                            { result -> listener.success(result) },
                            { error -> listener.error(error.message) }
                        )
        }


    }

    interface ResponseCallBack {
        fun success(result: Response<ResponseBody>)
        fun error(message: String?)
    }
}