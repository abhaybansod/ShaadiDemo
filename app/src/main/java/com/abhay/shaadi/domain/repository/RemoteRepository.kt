package com.abhay.shaadi.domain.repository

import com.abhay.shaadi.domain.model.DomainUserRequest
import com.abhay.shaadi.domain.model.DomainUserResult


interface RemoteRepository {

    suspend fun getRandomUsers(params: DomainUserRequest): List<DomainUserResult>

}