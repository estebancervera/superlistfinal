package com.cerverae18.superlistfinal.logic.entities.relations

import androidx.room.ColumnInfo
import java.io.Serializable

/**
 *  Data class representing a relationship between a product and a category
 *
 *  @param id Integer used to identify the relationship
 *  @param name name of the product
 *  @param category name of the category
 */

data class ProductWithCategory(
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "category") val category: String,
): Serializable