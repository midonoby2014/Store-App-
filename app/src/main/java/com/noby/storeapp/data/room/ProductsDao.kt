package com.noby.storeapp.data.room

import androidx.room.*
import com.noby.storeapp.data.room.entity.ProductsDb
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArticles(articles: List<ProductsDb>)

    @Query("DELETE FROM productslist")
    fun clearAllArticles()

    @Transaction
    fun clearAndCacheArticles(articles: List<ProductsDb>) {
        clearAllArticles()
        insertArticles(articles)
    }

    @Query("SELECT * FROM productslist")
    fun getProductsArticles(): Flow<List<ProductsDb>>


}