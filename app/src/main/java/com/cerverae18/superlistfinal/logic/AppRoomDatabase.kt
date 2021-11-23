package com.cerverae18.superlistfinal.logic

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.cerverae18.superlistfinal.logic.entities.Category
import com.cerverae18.superlistfinal.logic.entities.List
import com.cerverae18.superlistfinal.logic.entities.Product
import com.cerverae18.superlistfinal.logic.entities.ProductListCrossRef
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Product::class, List::class, ProductListCrossRef::class, Category::class], version = 6)
abstract class AppRoomDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao
    abstract fun categoryDao(): CategoryDao
    abstract fun listDao(): ListDao
    abstract fun productListDao(): ProductListDao

    // Singleton
    companion object {
        @Volatile
        private var INSTANCE: AppRoomDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): AppRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppRoomDatabase::class.java, "app_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(AppDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
        // ADD Callback in case we need to populate database on creation

        private class AppDatabaseCallback(private val scope: CoroutineScope) : RoomDatabase.Callback() {

            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    scope.launch {
                        populateDatabase(database.categoryDao())
                    }
                }
            }

            override fun onDestructiveMigration(db: SupportSQLiteDatabase) {
                super.onDestructiveMigration(db)
                INSTANCE?.let { database ->
                    scope.launch {
                        populateDatabase(database.categoryDao())
                    }
                }
            }

            suspend fun populateDatabase(categoryDao: CategoryDao) {
                categoryDao.deleteAll()
                categoryDao.insert(Category("Diary"))
                categoryDao.insert(Category("Cereals"))
                categoryDao.insert(Category("Beverages"))
                categoryDao.insert(Category("Proteins"))
            }
        }
    }

}