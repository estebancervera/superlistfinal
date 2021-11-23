package com.cerverae18.superlistfinal.logic.entities.relations

import androidx.room.ColumnInfo
import java.io.Serializable

data class ProductWithCategory(
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "category") val category: String,
): Serializable