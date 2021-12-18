package com.ceiba.mobile.di


import android.app.Application
import androidx.room.Room
import com.ceiba.mobile.api.ApiService
import com.ceiba.mobile.api.ApiServiceGenerator
import com.ceiba.mobile.db.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return logging
    }

    @Singleton
    @Provides
    fun provideApiService(
        loggingInterceptor: HttpLoggingInterceptor
    ): ApiService {
        val interceptors: Array<Interceptor> = arrayOf(loggingInterceptor)
        return ApiServiceGenerator.generate(
            "https://jsonplaceholder.typicode.com",
            ApiService::class.java,
            interceptors
        )
    }

    @Singleton
    @Provides
    fun provideDb(app: Application): PruebaDb {
        return Room
            .databaseBuilder(app, PruebaDb::class.java, "pruebas.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideUserDao(db: PruebaDb): UserDao {
        return db.userDao()
    }
}