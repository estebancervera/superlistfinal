package com.cerverae18.superlistfinal.logic.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.sql.Date
import java.util.*

/**
 *  Data class representing a List
 *
 *  @param listId String used to identify the list
 *  @param name name of the list
 *  @param date date in which the list is intended to be used
 */

@Entity
 data class List(
 @PrimaryKey(autoGenerate = false) val listId: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "date") val date: Long,
): Serializable

