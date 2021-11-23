package com.cerverae18.superlistfinal.logic.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.io.Serializable

<<<<<<< HEAD
@Entity(primaryKeys = ["productId", "listId"], tableName = "Product_List")
data class ProductListCrossRef(
    val productId: Int,
    val quantity: Int,
    val listId: String,
    val checked: Boolean = false
=======
@Entity(primaryKeys = ["productId", "listId"], tableName = "product_list")
data class ProductListCrossRef(
    val productId: Int,
    val quantity: Int,
    val listId: Int
>>>>>>> master
): Serializable

