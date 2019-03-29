package com.progresssoft.jobfinder.interfaces

import com.progresssoft.jobfinder.bean.SearchObject
import java.util.*

/**
 * Created by Mohammad.Arqoub on 27-Mar-19.
 */
interface JobSearchContract {

    interface jobSearchView{
        fun showProgress()
        fun hideProgress()
        fun addDataGithub(arrayList: ArrayList<SearchObject>)
        fun addDataGov(arrayList: ArrayList<SearchObject>)
        fun showData()
        fun onResponseFailure(strError: String)
    }

    interface jobSearchModel{
        fun onResultSuccess(arrUpdates: ArrayList<SearchObject>, url: String?)
        fun onResultFail(strError: String)
    }
}