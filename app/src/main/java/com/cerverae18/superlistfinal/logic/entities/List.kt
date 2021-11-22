package com.cerverae18.superlistfinal.logic.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.sql.Date

@Entity
 data class List(
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "date") val date: Date,
    ): Serializable {
    @PrimaryKey(autoGenerate = true) val listId: Int = 0
    }

