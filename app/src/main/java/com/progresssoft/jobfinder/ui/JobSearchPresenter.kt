package com.progresssoft.jobfinder.ui

import com.progresssoft.jobfinder.bean.SearchObject
import com.progresssoft.jobfinder.interfaces.JobSearchContract
import com.progresssoft.jobfinder.utill.GITHUB
import java.util.*
import kotlin.collections.HashMap

/**
 * Created by Mohammad.Arqoub on 26-Mar-19.
 */

class JobSearchPresenter(private val views: JobSearchContract.jobSearchView, private val model: JobSearchModel): JobSearchContract.jobSearchModel {
    override fun onResultSuccess(arrUpdates: ArrayList<SearchObject>, url: String?) {

        if (url == GITHUB){
            views.addDataGithub(arrUpdates)
        }else{
            views.addDataGov(arrUpdates)
        }
        views.hideProgress()
    }

    override fun onResultFail(strError: String) {
       views.hideProgress()
        views.onResponseFailure(strError)
    }



    fun getDataGitHub(url: String, map: HashMap<String, String>){
        views.showProgress()
        model.loadData(url, this, map)
    }



    fun getDataGov(url: String, map: HashMap<String, String>){
        views.showProgress()
        model.loadData(url, this, map)
    }
}