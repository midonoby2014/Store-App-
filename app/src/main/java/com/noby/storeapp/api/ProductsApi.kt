package com.noby.storeapp.api

import com.noby.storeapp.data.models.ProductRes
import com.noby.storeapp.data.models.ProductsRes
import com.noby.storeapp.data.models.ProductsResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Response
interface ProductsApi {

    @GET("products")
    suspend fun getProducts(): Response<List<ProductsResponse>>
}