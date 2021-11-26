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

@Database(entities = [Product::class, List::class, ProductListCrossRef::class, Category::class], version = 8)
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
                categoryDao.insert(Category("Baby"))
                categoryDao.insert(Category("Bakery"))
                categoryDao.insert(Category("Beverages"))
                categoryDao.insert(Category("Breads"))
                categoryDao.insert(Category("Breakfast"))
                categoryDao.insert(Category("Canned Goods"))
                categoryDao.insert(Category("Cereal"))
                categoryDao.insert(Category("Condiments/Spices"))
                categoryDao.insert(Category("Snacks"))
                categoryDao.insert(Category("Dairy, Eggs & Cheese"))
                categoryDao.insert(Category("Flowers"))
                categoryDao.insert(Category("Frozen Foods"))
                categoryDao.insert(Category("Fruits"))
                categoryDao.insert(Category("Grains & Pasta"))
                categoryDao.insert(Category("International Cuisine"))
                categoryDao.insert(Category("Meat & Seafood"))
                categoryDao.insert(Category("Miscellaneous"))
                categoryDao.insert(Category("Paper Products"))
                categoryDao.insert(Category("Cleaning Supplies"))
                categoryDao.insert(Category("Health & Beauty"))
                categoryDao.insert(Category("Pet Care"))
                categoryDao.insert(Category("Pharmacy"))
                categoryDao.insert(Category("Tobacco"))
                categoryDao.insert(Category("Vegetables"))
            }
        }
    }

}