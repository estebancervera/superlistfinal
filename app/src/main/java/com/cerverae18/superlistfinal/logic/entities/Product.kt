package com.cerverae18.superlistfinal.logic.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

/**
 *  Data class representing a product
 *
 *  @param name name of the product
 *  @param categoryId product's category id
 *  @property productId  number used to identify the product
 */

@Entity
 data class Product(
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "category") val categoryId: Int
    ): Serializable {
        @PrimaryKey(autoGenerate = true) var productId : Int = 0
    }



