package com.abhay.shaadi.data.datasource.remote.model.result.location

import com.abhay.shaadi.data.datasource.remote.Constants.LATITUDE
import com.abhay.shaadi.data.datasource.remote.Constants.LONGITUDE
import com.google.gson.annotations.SerializedName

data class RemoteCoordinates(
    @field:SerializedName(LATITUDE) val latitude: String?,
    @field:SerializedName(LONGITUDE) val longitude: String?
)