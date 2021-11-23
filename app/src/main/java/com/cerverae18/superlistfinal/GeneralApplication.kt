package com.cerverae18.superlistfinal

import android.app.Application
import com.cerverae18.superlistfinal.logic.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob


class GeneralApplication: Application() {

    val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy { AppRoomDatabase.getDatabase(this, applicationScope) }
    val productRepository by lazy { ProductRepository(database.productDao()) }
    val categoryRepository by lazy { CategoryRepository(database.categoryDao()) }
    val listRepository by lazy { ListRepository(database.listDao()) }
    val productListRepository by lazy { ProductListRepository(database.productListDao()) }
}