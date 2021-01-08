package com.abhay.shaadi.data.datasource.remote.model.result

import com.abhay.shaadi.data.datasource.remote.Constants.NAME
import com.abhay.shaadi.data.datasource.remote.Constants.VALUE
import com.google.gson.annotations.SerializedName

data class RemoteId(
	@field:SerializedName(NAME) val name: String?,
	@field:SerializedName(VALUE) val value: String?
)