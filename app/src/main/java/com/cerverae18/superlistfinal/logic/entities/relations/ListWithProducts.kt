package com.cerverae18.superlistfinal.logic.entities.relations

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.cerverae18.superlistfinal.logic.entities.List
import com.cerverae18.superlistfinal.logic.entities.Product
import com.cerverae18.superlistfinal.logic.entities.ProductListCrossRef
import java.io.Serializable
import javax.security.auth.Subject

/**
 *  Data class representing a relationship between a product and a list
 *
 *  @param id Integer used to identify the relationship
 *  @param name name of the product
 *  @param quantity amount of entities desired of such product
 *  @param category name of the category
 *  @param checked boolean used to determine whether a product has been checked on the list or not
 */

data class ListWithProducts (
    @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "productName") val name: String,
    @ColumnInfo(name = "quantity") val quantity: Int,
    @ColumnInfo(name = "categoryName") val category: String,
    @ColumnInfo(name = "checked") val checked: Boolean,
    ):Serializable


//@Embedded val list : List,
//    @Relation(
//        parentColumn = "listId",
//        entityColumn = "productId",
//        associateBy = Junction(ProductListCrossRef::class)
//    )
//    val products: kotlin.collections.List<Product>