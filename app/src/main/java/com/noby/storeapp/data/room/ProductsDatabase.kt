package com.noby.storeapp.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.noby.storeapp.data.room.entity.ProductsDb

@Database(entities = [ProductsDb::class] , version = 1)
abstract class ProductsDatabase : RoomDatabase() {
    abstract fun productsDao(): ProductsDao
}