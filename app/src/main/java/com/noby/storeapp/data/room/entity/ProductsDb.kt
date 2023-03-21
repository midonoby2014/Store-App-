package com.noby.storeapp.data.room.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Entity(tableName = "productslist")
@Parcelize
data  class ProductsDb(
    @PrimaryKey(autoGenerate = true) val PRid: Int = 0,
    val id: Int = 0,
    val category: String,
    val image: String,
    val description: String,
    val price: Double,
    val title: String,
    val ratingcount: Int,
    val ratingrate: Double
) : Parcelable {


}
