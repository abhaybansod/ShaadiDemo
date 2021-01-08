package com.abhay.shaadi.presentation.mapper

import androidx.databinding.ObservableBoolean
import com.abhay.shaadi.R
import com.abhay.shaadi.domain.model.DomainUserResult
import com.abhay.shaadi.presentation.model.UiUserResult
import com.abhay.library.base.presentation.extensions.getFlagUrlByBase
import java.util.*

class UiRandomUsersMapper {

    companion object {
        const val MALE = "male"
        const val FEMALE = "female"
    }

    fun DomainUserResult.fromDomainToUi() = UiUserResult(
        userId = userId,
        fullName = fullName,
        gender = gender.capitalize(),
        fullAddress = "$address\n$state\n$country",
        city = city,
        state = state,
        country = country,
        imageUrlCountry = getFlagUrlByBase(nationality),
        email = email,
        age = age,
        phone = phone,
        cell = cell,
        thumbImageUrl = thumbImageUrl,
        largeImageUrl = largeImageUrl,
        nationality = nationality,
        pageSize = pageSize,
        isAccepted = ObservableBoolean(isFavorite),
        isDeclined = ObservableBoolean(isAccept),
        dateFavoriteAdded = dateFavoriteAdded
    )

    fun UiUserResult.fromUiToDomain() = DomainUserResult(
        userId = userId,
        fullName = fullName,
        gender = gender.toLowerCase(Locale.getDefault()),
        address = fullAddress,
        city = city,
        state = state,
        country = country,
        email = email,
        age = age,
        phone = phone,
        cell = cell,
        thumbImageUrl = thumbImageUrl,
        largeImageUrl = largeImageUrl,
        nationality = nationality,
        pageSize = pageSize,
        isFavorite = isAccepted.get(),
        isAccept = isDeclined.get(),
        dateFavoriteAdded = dateFavoriteAdded
    )

}