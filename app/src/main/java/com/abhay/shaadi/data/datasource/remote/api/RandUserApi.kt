package com.abhay.shaadi.data.datasource.remote.api

import com.abhay.shaadi.data.datasource.remote.model.RemoteUsers
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url


interface RandUserApi {

    @GET
    fun getUsersByPage(@Url nextUrl: String): Call<RemoteUsers>

}