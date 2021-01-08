package com.abhay.shaadi.domain.usecase

import com.abhay.shaadi.domain.model.DomainUserResult
import com.abhay.shaadi.domain.repository.DatabaseRepository
import com.abhay.library.base.data.coroutines.ResultUseCase
import kotlinx.coroutines.Dispatchers

open class GetFavoriteUserByIdUseCase(private val databaseRepository: DatabaseRepository) :
    ResultUseCase<String, DomainUserResult>(
        backgroundContext = Dispatchers.IO,
        foregroundContext = Dispatchers.Main
    ) {
    override suspend fun executeOnBackground(params: String): DomainUserResult? {
        return databaseRepository.getFavoriteUserById(userId = params)
    }
}