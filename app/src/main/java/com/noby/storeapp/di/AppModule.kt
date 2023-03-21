package com.noby.storeapp.di

import android.app.Application
import androidx.room.Room
import com.noby.storeapp.api.ProductsApi
import com.noby.storeapp.data.room.ProductsDao
import com.noby.storeapp.data.room.ProductsDatabase
import com.noby.storeapp.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
        return client
    }

    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    @Singleton
    @Provides
    fun provideNews (retrofit: Retrofit) : ProductsApi =
        retrofit.create(ProductsApi::class.java)

    @Provides
    @Singleton
    fun provideDatabase (
        app : Application
    ) =
        Room.databaseBuilder(app  , ProductsDatabase::class.java , "news_database")
            .fallbackToDestructiveMigration()
            // .addCallback(callback)
            .build()

    //   @Singleton
    @Provides
    fun provideUserDao(db: ProductsDatabase): ProductsDao = db.productsDao()

    @ApplicationScope
    @Provides
    @Singleton
    fun providesApplicationScope () =  CoroutineScope(SupervisorJob())
}
@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScope