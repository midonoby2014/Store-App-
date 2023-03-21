package com.noby.storeapp.data.repository

import android.content.Context
import com.noby.storeapp.api.ProductsApi
import com.noby.storeapp.data.models.ProductRes
import com.noby.storeapp.data.models.ProductsRes
import com.noby.storeapp.data.models.ProductsResponse
import com.noby.storeapp.data.room.ProductsDao
import com.noby.storeapp.data.room.ProductsMapper
import com.noby.storeapp.data.room.entity.ProductsDb
import com.noby.storeapp.utils.Utils
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import com.noby.storeapp.utils.ViewState
import com.noby.storeapp.utils.httpError
import retrofit2.Response
import javax.inject.Inject

interface ProductsRepository {

    fun getProductsArticles(context: Context): Flow<ViewState<List<ProductsDb>>>
    suspend fun getProductsFromWebservice(): Response<List<ProductsResponse>>
}

//@Singleton
class DefaultProductsRepository @Inject constructor(
    private val ProductsDao: ProductsDao,
    private val productsService: ProductsApi

) : ProductsRepository, ProductsMapper {
    override fun getProductsArticles(context: Context): Flow<ViewState<List<ProductsDb>>> = flow {
        emit(ViewState.loading())
        var internetAvailable: Boolean = Utils.isConnectingToInternet(context)
        if (!internetAvailable) {
            emit(ViewState.error("No Network"))
            val cachedNews = ProductsDao.getProductsArticles()
           emitAll(cachedNews.map { ViewState.success(it) })
        }else {
            val freshNews = getProductsFromWebservice()
            freshNews.body()?.toStorage()?.let(ProductsDao::clearAndCacheArticles)
            val cachedNews = ProductsDao.getProductsArticles()
            emitAll(cachedNews.map { ViewState.success(it) })
        }
    }
        .flowOn(Dispatchers.IO)

    override suspend fun getProductsFromWebservice(): Response<List<ProductsResponse>> {
     return try {
               productsService.getProducts()
      } catch (e: Exception) {
            httpError(404)
     }
    }


}
@Module
@InstallIn(SingletonComponent::class)
interface ProductsRepositoryModule {
    @Binds
    fun it(it: DefaultProductsRepository): ProductsRepository
}