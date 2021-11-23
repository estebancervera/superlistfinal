package com.cerverae18.superlistfinal.logic.entities.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.cerverae18.superlistfinal.logic.entities.Category
import com.cerverae18.superlistfinal.logic.entities.List
import com.cerverae18.superlistfinal.logic.entities.Product
import com.cerverae18.superlistfinal.logic.entities.ProductListCrossRef

data class CategoryWithProducts(
    @Embedded val category: Category,
    @Relation(
        parentColumn = "categoryId",
     entityColumn = "productId",
    )
    val products: kotlin.collections.List<Product>
)