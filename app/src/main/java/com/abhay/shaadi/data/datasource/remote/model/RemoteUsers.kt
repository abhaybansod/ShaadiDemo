package com.abhay.shaadi.data.datasource.remote.model

import com.abhay.shaadi.data.datasource.remote.Constants.INFO
import com.abhay.shaadi.data.datasource.remote.Constants.RESULTS
import com.google.gson.annotations.SerializedName

data class RemoteUsers(
    @field:SerializedName(RESULTS) val results: List<RemoteUserResult?>?,
    @field:SerializedName(INFO) val remoteInfo: RemoteInfo?
)