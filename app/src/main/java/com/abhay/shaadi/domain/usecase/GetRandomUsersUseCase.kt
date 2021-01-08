package com.abhay.shaadi.domain.usecase

import com.abhay.shaadi.domain.model.DomainUserRequest
import com.abhay.shaadi.domain.model.DomainUserResult
import com.abhay.shaadi.domain.repository.RemoteRepository
import com.abhay.library.base.data.coroutines.ResultUseCase
import kotlinx.coroutines.Dispatchers

open class GetRandomUsersUseCase(private val remoteRepository: RemoteRepository) :
    ResultUseCase<DomainUserRequest, List<DomainUserResult>>(
        backgroundContext = Dispatchers.IO,
        foregroundContext = Dispatchers.Main
    ) {
    override suspend fun executeOnBackground(params: DomainUserRequest): List<DomainUserResult>? {
        return remoteRepository.getRandomUsers(params = params)
    }
}