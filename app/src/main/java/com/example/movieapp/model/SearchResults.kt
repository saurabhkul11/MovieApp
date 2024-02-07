package com.example.movieapp.model

import com.google.gson.annotations.SerializedName

data class SearchResults(
    @SerializedName("Search")
    var search: ArrayList<SearchItem>,
    @SerializedName("totalResults" ) var totalResults : String?           = null,
    @SerializedName("Response"     ) var Response     : String?           = null
)

data class SearchItem(
    @SerializedName("Title"  ) var Title  : String? = null,
    @SerializedName("Year"   ) var Year   : String? = null,
    @SerializedName("imdbID" ) var imdbID : String? = null,
    @SerializedName("Type"   ) var Type   : String? = null,
    @SerializedName("Poster" ) var Poster : String? = null
)
