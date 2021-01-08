package com.abhay.shaadi.domain.usecase

import com.abhay.shaadi.domain.model.DomainUserResult
import com.abhay.shaadi.domain.repository.DatabaseRepository
import com.abhay.library.base.data.coroutines.ResultUseCase
import kotlinx.coroutines.Dispatchers

open class SaveFavoriteUserUseCase(private val databaseRepository: DatabaseRepository) :
    ResultUseCase<DomainUserResult, DomainUserResult>(
        backgroundContext = Dispatchers.IO,
        foregroundContext = Dispatchers.Main
    ) {
    override suspend fun executeOnBackground(params: DomainUserResult): DomainUserResult? {
        databaseRepository.saveFavoriteUser(user = params)
        return params
    }
}