package com.cerverae18.superlistfinal.logic.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity
 data class Product(
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "category") val category: Category
    ): Serializable {
        @PrimaryKey(autoGenerate = true) val productId : Int = 0
    }



