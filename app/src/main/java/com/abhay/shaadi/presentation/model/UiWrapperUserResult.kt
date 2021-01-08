package com.abhay.shaadi.presentation.model

import com.abhay.shaadi.R


data class UiWrapperUserResult(
    val items: List<UiUserResult>,
    val layoutId: Int? =  R.layout.view_cell_user_item
)