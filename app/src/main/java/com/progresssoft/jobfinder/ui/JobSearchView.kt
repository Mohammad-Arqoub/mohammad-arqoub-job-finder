package com.progresssoft.jobfinder.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.Toast
import com.progresssoft.jobfinder.adapter.Adapter
import com.progresssoft.jobfinder.R
import com.progresssoft.jobfinder.bean.SearchObject
import com.progresssoft.jobfinder.interfaces.JobSearchContract
import com.progresssoft.jobfinder.interfaces.OnItemClickListener
import com.progresssoft.jobfinder.utill.GITHUB
import com.progresssoft.jobfinder.utill.GOV
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.collections.ArrayList
import android.content.Intent
import android.net.Uri
import android.widget.RadioButton
import android.widget.RadioGroup
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.places.Place
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment
import com.google.android.gms.location.places.ui.PlaceSelectionListener


/**
 * Created by Mohammad.Arqoub on 26-Mar-19.
 */
class JobSearchView : AppCompatActivity(), JobSearchContract.jobSearchView, OnItemClickListener,
    RadioGroup.OnCheckedChangeListener, PlaceSelectionListener {

    var autocompleteFragment: PlaceAutocompleteFragment? = null

    var providerString: String = "All"
    var positionString: String = ""
    var locationString: String = ""
    val govMap: HashMap<String, String> = HashMap()
    val githubMap: HashMap<String, String> = HashMap()
    var layoutManager: LinearLayoutManager = LinearLayoutManager(this)
    private lateinit var Presenter: JobSearchPresenter
    var searchArray: ArrayList<SearchObject> = ArrayList()
    var githubArray: ArrayList<SearchObject> = ArrayList()
    var govArray: ArrayList<SearchObject> = ArrayList()
    lateinit var jobAdapter: Adapter

    override fun addDataGithub(arrayList: java.util.ArrayList<SearchObject>) {


        githubArray.addAll(arrayList)
        searchArray.addAll(arrayList)
        if (providerString == "All")
            Presenter.getDataGov(GOV, govMap)
        else
            showData()
        Log.e("Size", searchArray.size.toString())
    }

    override fun addDataGov(arrayList: java.util.ArrayList<SearchObject>) {
        searchArray.addAll(arrayList)
        govArray.addAll(arrayList)
        Log.e("Size", searchArray.size.toString())
        showData()
    }

    override fun showData() {
        jobAdapter.notifyDataSetChanged()
    }

    override fun Clicked(pos: Int) {

        openNewTabWindow(searchArray[pos].jobUrl.toString())
    }

    override fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressBar.visibility = View.GONE
    }

    override fun onResponseFailure(strError: String) {
        Toast.makeText(applicationContext, strError, Toast.LENGTH_LONG).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        jobAdapter = Adapter(searchArray, applicationContext, this, 0)

        jobRecyclerView.layoutManager = layoutManager
        jobRecyclerView.adapter = jobAdapter
        Presenter = JobSearchPresenter(this, JobSearchModel())
        // GITHUB, GOV
        Presenter.getDataGitHub(GITHUB, githubMap)

        autocompleteFragment = supportFragmentManager?.findFragmentById(R.id.place_autocomplete_fragment)
                as? PlaceAutocompleteFragment

        autocompleteFragment?.setOnPlaceSelectedListener(this)
        radioGroup.setOnCheckedChangeListener(this)
        btnFitler.setOnClickListener {


            filterDataByPositionAndLocation()

        }

    }

    fun filterDataByProvider() {

        if (providerString == "All") {
            searchArray.clear()
            searchArray.addAll(githubArray)
            searchArray.addAll(govArray)

        } else if (providerString == "Github") {
            searchArray.clear()
            searchArray.addAll(githubArray)

        } else if (providerString == "Gov") {
            searchArray.clear()
            searchArray.addAll(govArray)
        }
        jobAdapter.notifyDataSetChanged()

    }

    fun filterDataByPositionAndLocation() {
        positionString = positionEdt.text.toString()

        if (positionString.isNotEmpty()) {

            searchArray.clear()
            jobAdapter.notifyDataSetChanged()

            govMap.put("query", positionString)
            githubMap.put("description", positionString)
            if (providerString == "Gov") {

                Presenter.getDataGitHub(GOV, govMap)
            } else if (providerString == "Github" || providerString == "All") {
                Presenter.getDataGitHub(GITHUB, githubMap)
            }
            return
        }

        filterDataByProvider()
    }

    override fun onPlaceSelected(p0: Place?) {

        locationString = p0.toString()
        Log.e("Place", p0?.name.toString())
    }

    override fun onError(p0: Status?) {

    }

    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {

        val rb = group!!.findViewById<RadioButton>(checkedId)
        providerString = rb.text.toString()
    }

    fun openNewTabWindow(urls: String) {
        val uris = Uri.parse(urls)
        val intents = Intent(Intent.ACTION_VIEW, uris)
        val b = Bundle()
        b.putBoolean("new_window", true)
        intents.putExtras(b)
        startActivity(intents)
    }

}
