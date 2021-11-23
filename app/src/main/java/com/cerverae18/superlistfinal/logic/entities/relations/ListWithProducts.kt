package com.cerverae18.superlistfinal.logic.entities.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.cerverae18.superlistfinal.logic.entities.List
import com.cerverae18.superlistfinal.logic.entities.Product
import com.cerverae18.superlistfinal.logic.entities.ProductListCrossRef
import javax.security.auth.Subject

data class ListWithProducts (
    @Embedded val list : List,
    @Relation(
        parentColumn = "listId",
        entityColumn = "productId",
        associateBy = Junction(ProductListCrossRef::class)
    )
<<<<<<< HEAD
    val products: kotlin.collections.List<Product>
=======
    val product: kotlin.collections.List<Product>
>>>>>>> master
    )