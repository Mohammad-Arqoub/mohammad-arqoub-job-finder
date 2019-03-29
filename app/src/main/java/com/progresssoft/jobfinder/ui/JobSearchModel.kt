package com.progresssoft.jobfinder.ui

import android.util.Log
import com.google.gson.Gson
import com.progresssoft.jobfinder.bean.SearchObject
import com.progresssoft.jobfinder.interfaces.JobSearchContract
import com.progresssoft.jobfinder.utill.GITHUB
import com.progresssoft.jobfinder.utill.GOV
import com.qstss.connection.ServiceGenerator
import okhttp3.ResponseBody
import org.json.JSONArray
import retrofit2.Response
import kotlin.collections.ArrayList

/**
 * Created by Mohammad.Arqoub on 26-Mar-19.
 */

class JobSearchModel: ServiceGenerator.ResponseCallBack{
    lateinit var listener: JobSearchContract.jobSearchModel
    var url: String? = null

    override fun success(result: Response<ResponseBody>) {

        val res: String = result.body()!!.string()

        val gson = Gson()
        Log.e("STRING", res)
        val obj = JSONArray(res)
        val packagesArray: ArrayList<SearchObject> = gson.fromJson(obj.toString() , Array<SearchObject>::class.java).toCollection(
            ArrayList())
        listener.onResultSuccess(packagesArray, url)
    }

    override fun error(message: String?) {
        Log.e("ERROR", "null exception")
    }


    fun loadData(url: String,listener: JobSearchContract.jobSearchModel, map: HashMap<String, String>) {
        this.url = url
        this.listener = listener
        if (url == GITHUB){
            ServiceGenerator.requestGitHub(url, map, this)
        }else if (url == GOV) {
            ServiceGenerator.requestGov(url, map, this)
        }

    }


}