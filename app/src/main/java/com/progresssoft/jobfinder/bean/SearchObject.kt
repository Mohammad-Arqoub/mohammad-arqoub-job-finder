package com.progresssoft.jobfinder.bean

import com.google.gson.annotations.SerializedName

/**
 * Created by Mohammad.Arqoub on 28-Mar-19.
 */
data class SearchObject (
    var type: String? = null,
    @SerializedName("url")
    var jobUrl: String? = null,
    @SerializedName(value = "postDate", alternate = ["created_at", "start_date"])
    var postDate: String? = null,
    @SerializedName(value = "companyName", alternate = ["company", "organization_name"])
    var companyName: String? = null,
    var company_url: String? = null,
    @SerializedName("location")
    var location: String? = null,
    @SerializedName(value = "jobPosition", alternate = ["title", "position_title"])
    var jobPosition: String? = null,
    var description: String? = null,
    var how_to_apply: String? = null,
    @SerializedName("company_logo")
    var logo: String? = null
)
