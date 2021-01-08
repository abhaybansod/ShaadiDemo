package com.abhay.shaadi.ui.list.interfaces

import com.abhay.shaadi.presentation.model.UiUserResult

interface UserItemListener {
    fun onFavoriteClick(item: UiUserResult)
    fun onNotFavoriteClick(item: UiUserResult)
}