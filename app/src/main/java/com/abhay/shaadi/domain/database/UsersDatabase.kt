package com.abhay.shaadi.domain.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.abhay.shaadi.domain.Constants.DATABASE_VERSION
import com.abhay.shaadi.domain.database.dao.UsersDao
import com.abhay.shaadi.domain.model.database.EntityUser

@Database(
    entities = [
        EntityUser::class
    ],
    version = DATABASE_VERSION,
    exportSchema = false
)
abstract class UsersDatabase : RoomDatabase() {
    abstract val users: UsersDao
}