package com.abhay.shaadi.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.abhay.shaadi.domain.model.DomainUserResult
import com.abhay.shaadi.domain.usecase.DeleteFavoriteUserUseCase
import com.abhay.shaadi.domain.usecase.GetFavoriteUserByIdUseCase
import com.abhay.shaadi.domain.usecase.GetFavoriteUsersUseCase
import com.abhay.shaadi.domain.usecase.SaveFavoriteUserUseCase
import com.abhay.library.base.presentation.extensions.LiveResult

open class FavoriteUsersViewModel(
    private val getFavoriteUserByIdUseCase: GetFavoriteUserByIdUseCase,
    private val getFavoriteUsersUseCase: GetFavoriteUsersUseCase,
    private val saveFavoriteUserUseCase: SaveFavoriteUserUseCase,
    private val deleteFavoriteUserUseCase: DeleteFavoriteUserUseCase
) : ViewModel() {

    companion object {
        const val DEFAULT_EMPTY = ""
    }

    val getFavoriteUserByIdLiveResult = LiveResult<DomainUserResult>()
    val getFavoriteUsersLiveResult = LiveResult<List<DomainUserResult>>()
    val saveFavoriteUserLiveResult = LiveResult<DomainUserResult>()
    val deleteFavoriteUserLiveResult = LiveResult<DomainUserResult>()

    fun getFavoriteUserById(userId: String) {
        getFavoriteUserByIdUseCase.execute(
            liveData = getFavoriteUserByIdLiveResult,
            params = userId
        )
    }

    fun getFavoriteUsers() {
        getFavoriteUsersUseCase.execute(
            liveData = getFavoriteUsersLiveResult,
            params = DEFAULT_EMPTY
        )
    }

    fun saveFavoriteUser(user: DomainUserResult) {
        saveFavoriteUserUseCase.execute(liveData = saveFavoriteUserLiveResult, params = user)
    }


    fun deleteFavoriteUser(user: DomainUserResult) {
        deleteFavoriteUserUseCase.execute(liveData = deleteFavoriteUserLiveResult, params = user)
    }
}