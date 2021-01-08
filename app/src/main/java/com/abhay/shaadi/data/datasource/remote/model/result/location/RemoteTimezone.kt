package com.abhay.shaadi.data.datasource.remote.model.result.location

import com.abhay.shaadi.data.datasource.remote.Constants.DESCRIPTION
import com.abhay.shaadi.data.datasource.remote.Constants.OFFSET
import com.google.gson.annotations.SerializedName

data class RemoteTimezone(
    @field:SerializedName(OFFSET) val offset: String?,
    @field:SerializedName(DESCRIPTION) val description: String?
)