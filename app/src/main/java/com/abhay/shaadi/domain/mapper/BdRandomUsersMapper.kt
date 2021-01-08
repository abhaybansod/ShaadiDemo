package com.abhay.shaadi.domain.mapper

import com.abhay.shaadi.domain.model.DomainUserResult
import com.abhay.shaadi.domain.model.database.EntityUser

class BdRandomUsersMapper {

    fun EntityUser.fromDatabaseToDomain() = DomainUserResult(
        userId = userId,
        fullName = fullName,
        gender = gender,
        address = address,
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
        isFavorite = isFavorite,
        isAccept= isAccept,
        dateFavoriteAdded = dateFavoriteAdded
    )

    fun DomainUserResult.fromDomainToDatabase() = EntityUser(
        userId = userId,
        fullName = fullName,
        gender = gender,
        address = address,
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
        isFavorite = isFavorite,
        isAccept= isAccept,
        dateFavoriteAdded = dateFavoriteAdded
    )
}