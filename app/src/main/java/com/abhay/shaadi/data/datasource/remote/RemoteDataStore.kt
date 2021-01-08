package com.abhay.shaadi.data.datasource.remote

import com.abhay.shaadi.data.datasource.remote.Constants.PAGE_SIZE
import com.abhay.shaadi.data.datasource.remote.Constants.URL_FORMAT
import com.abhay.shaadi.data.datasource.remote.api.RandUserApi
import com.abhay.shaadi.domain.mapper.RandomUsersMapper
import com.abhay.shaadi.domain.model.DomainUserRequest
import com.abhay.shaadi.domain.model.DomainUserResult
import com.abhay.shaadi.domain.repository.RemoteRepository
import com.abhay.library.base.presentation.extensions.await


open class RemoteDataStore(
    private val randUserApi: RandUserApi,
    private val mapper: RandomUsersMapper
) : RemoteRepository {

    override suspend fun getRandomUsers(params: DomainUserRequest): List<DomainUserResult> {
        val query = String.format(URL_FORMAT, params.page, PAGE_SIZE)
        val response = randUserApi.getUsersByPage(query).await()

        with(mapper) {
            return (response?.results?.map { item ->
                item?.fromRemoteToDomain(PAGE_SIZE)
            } ?: listOf()) as List<DomainUserResult>
        }
    }

}