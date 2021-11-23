package com.cerverae18.superlistfinal.logic.entities


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
 data class Category(
    @ColumnInfo(name = "name") val name: String
    ): Serializable {
<<<<<<< HEAD
    @PrimaryKey(autoGenerate = true) var categoryId : Int = 0
=======
    @PrimaryKey(autoGenerate = true) val categoryId : Int = 0
>>>>>>> master
    }

