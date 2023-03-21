package com.noby.storeapp.data.room

import com.noby.storeapp.data.models.ProductsResponse
import com.noby.storeapp.data.room.entity.ProductsDb

interface ProductsMapper : Mapper<ProductsDb, ProductsResponse> {

    override fun ProductsDb.toRemote(): ProductsResponse {
        return ProductsResponse(
            id = id,
            title = title,
            description = description,
            image = image,
            category = category,
            price = price,
            rating = ProductsResponse.Rating(ratingcount , ratingrate)
        )
    }
    override fun ProductsResponse.toStorage(): ProductsDb {
        return ProductsDb(
            id = id,
            title = title,
            description = description,
            image = image,
            category = category,
            price = price,
            ratingcount = rating.count,
            ratingrate = rating.rate
        )
    }
}