package com.abhay.shaadi.data.datasource.remote.model.result

import com.abhay.shaadi.data.datasource.remote.Constants.AGE
import com.abhay.shaadi.data.datasource.remote.Constants.DATE
import com.google.gson.annotations.SerializedName

data class RemoteDob(
	@field:SerializedName(DATE) val date: String?,
	@field:SerializedName(AGE) val age: Int?
)