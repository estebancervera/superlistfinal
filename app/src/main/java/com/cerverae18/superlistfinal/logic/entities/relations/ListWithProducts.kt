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