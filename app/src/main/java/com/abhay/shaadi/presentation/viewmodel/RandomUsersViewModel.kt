package com.abhay.shaadi.presentation.viewmodel

import com.abhay.shaadi.domain.model.DomainUserRequest
import com.abhay.shaadi.domain.model.DomainUserResult
import com.abhay.shaadi.domain.usecase.*
import com.abhay.shaadi.presentation.state.SearchState
import com.abhay.library.base.presentation.extensions.LiveResult
import com.abhay.library.base.presentation.viewmodel.BaseViewModel

open class RandomUsersViewModel(
    private val getRandomUsersUseCase: GetRandomUsersUseCase
) : BaseViewModel<SearchState>(initState = SearchState()) {

    val usersLiveResult = LiveResult<List<DomainUserResult>>()

    fun getUsers(page: Int) {
        val params = DomainUserRequest(
            page = page
        )
        getRandomUsersUseCase.execute(liveData = usersLiveResult, params = params)
    }
}