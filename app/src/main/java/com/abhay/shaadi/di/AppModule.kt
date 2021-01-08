package com.abhay.shaadi.di

import android.content.Context
import android.net.ConnectivityManager
import androidx.room.Room
import com.abhay.shaadi.BuildConfig
import com.abhay.shaadi.R
import com.abhay.shaadi.data.datasource.remote.RemoteDataStore
import com.abhay.shaadi.data.datasource.remote.api.RandUserApi
import com.abhay.shaadi.domain.Constants.DATABASE_NAME
import com.abhay.shaadi.domain.database.DatabaseDataStore
import com.abhay.shaadi.domain.database.UsersDatabase
import com.abhay.shaadi.domain.mapper.BdRandomUsersMapper
import com.abhay.shaadi.domain.mapper.RandomUsersMapper
import com.abhay.shaadi.domain.repository.DatabaseRepository
import com.abhay.shaadi.domain.repository.RemoteRepository
import com.abhay.shaadi.domain.usecase.*
import com.abhay.shaadi.presentation.mapper.UiRandomUsersMapper
import com.abhay.shaadi.presentation.model.UiUserResult
import com.abhay.shaadi.presentation.viewmodel.FavoriteUsersViewModel
import com.abhay.library.base.presentation.viewmodel.PaginationViewModel
import com.abhay.shaadi.presentation.viewmodel.RandomUsersViewModel
import com.abhay.shaadi.ui.list.adapter.UsersAdapter
import com.squareup.picasso.Picasso
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.experimental.dsl.viewModel
import org.koin.dsl.module
import org.koin.experimental.builder.factory
import org.koin.experimental.builder.factoryBy
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val appModule = module {

    // Android Services
    single {
        androidApplication().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    // Retrofit
    single {
        val httpLoggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT)
        val clientBuilder = OkHttpClient.Builder()


            if (BuildConfig.DEBUG) {
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                clientBuilder.addInterceptor(httpLoggingInterceptor)
            }

        clientBuilder.callTimeout(1, TimeUnit.MINUTES)
        clientBuilder.connectTimeout(1, TimeUnit.MINUTES)
        clientBuilder.writeTimeout(1, TimeUnit.MINUTES)
        clientBuilder.readTimeout(1, TimeUnit.MINUTES)
        clientBuilder.build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(androidApplication().getString(R.string.shaadi_challenge_endpoint))
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single {
        get<Retrofit>()
            .create(RandUserApi::class.java) as RandUserApi
    }

    // Picasso
    single {
        Picasso.get()
    }

    // Database
    single {
        Room.databaseBuilder(
            androidContext(),
            UsersDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    // ViewModels
    viewModel<RandomUsersViewModel>()
    viewModel<FavoriteUsersViewModel>()
    viewModel<PaginationViewModel<UiUserResult>>()

    // Factories
    factoryBy<RemoteRepository, RemoteDataStore>()
    factoryBy<DatabaseRepository, DatabaseDataStore>()

    // Use Cases
    factory<GetRandomUsersUseCase>()
    factory<GetFavoriteUserByIdUseCase>()
    factory<GetFavoriteUsersUseCase>()
    factory<SaveFavoriteUserUseCase>()
    factory<DeleteFavoriteUserUseCase>()

    // Mapper
    factory<RandomUsersMapper>()
    factory<UiRandomUsersMapper>()
    factory<BdRandomUsersMapper>()


    // Adapter
    factory<UsersAdapter>()

}
