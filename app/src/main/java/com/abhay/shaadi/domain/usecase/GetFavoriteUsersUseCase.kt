package com.abhay.shaadi.domain.usecase

import com.abhay.shaadi.domain.model.DomainUserResult
import com.abhay.shaadi.domain.repository.DatabaseRepository
import com.abhay.library.base.data.coroutines.ResultUseCase
import kotlinx.coroutines.Dispatchers

open class GetFavoriteUsersUseCase(private val databaseRepository: DatabaseRepository) :
    ResultUseCase<String, List<DomainUserResult>>(
        backgroundContext = Dispatchers.IO,
        foregroundContext = Dispatchers.Main
    ) {
    override suspend fun executeOnBackground(params: String): List<DomainUserResult>? {
        return databaseRepository.getFavoriteUsers()
    }
}