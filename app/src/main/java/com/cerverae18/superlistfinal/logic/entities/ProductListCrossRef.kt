package com.cerverae18.superlistfinal.logic.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

/**
 *  Data class representing a correlation between products and lists as a table
 *
 *  @param productId number used to identify the specified product
 *  @param quantity amount of entities desired of specific product
 *  @param listId String used to identify specified list
 *  @param checked boolean used to determine whether a product has been checked on the list or not
 *  @param id String used to represent this correlation
 */

@Entity(primaryKeys = ["productId", "listId"], tableName = "Product_List", foreignKeys = [ForeignKey(
    onDelete = CASCADE,
    entity = List::class,
    parentColumns = ["listId"],
    childColumns = ["listId"],
)] )
data class ProductListCrossRef(
    val productId: Int,
    val quantity: Int,
    val listId: String,
    val checked: Boolean = false,
    val id: String = UUID.randomUUID().toString()
): Serializable

