package com.cerverae18.superlistfinal.logic.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(primaryKeys = ["productId", "listId"], tableName = "Product_list")
data class ProductListCrossRef(
    val productId: Int,
    val quantity: Int,
    val listId: Int

): Serializable

