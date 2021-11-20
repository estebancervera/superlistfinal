package com.cerverae18.superlistfinal

import java.io.Serializable
import java.sql.Date

data class List(
    val name: String,
    val date: Date,
    val products: HashMap<Product, Int>

    ): Serializable
