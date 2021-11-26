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


/**
 * An abstract class that extends RoomDatabase
 *
 * This is class that extends from RoomDatabase and represents the data that will be saved in the database
 * including entities.
 *
 */
@Database(entities = [Product::class, List::class, ProductListCrossRef::class, Category::class], version = 8)
abstract class AppRoomDatabase : RoomDatabase() {


    /**
     * Abstract method that returns a productDao
     * @return a ProductDao
     */
    abstract fun productDao(): ProductDao
    /**
     * Abstract method that returns a CategoryDao
     * @return a CategoryDao
     */
    abstract fun categoryDao(): CategoryDao
    /**
     * Abstract method that returns a ListDao
     * @return a ListDao
     */
    abstract fun listDao(): ListDao
    /**
     * Abstract method that returns a ProductDao
     * @return a ProductListDao
     */
    abstract fun productListDao(): ProductListDao

    /**
     * Singleton instance to make sure only one instance of the database is created and accessed throughout the app
     * @property INSTANCE  represents the an instance of  AppRoomDatabase
     */
    companion object {
        @Volatile
        private var INSTANCE: AppRoomDatabase? = null
        /**
         * Getter method for the instance, that if instance is null, will create one.
         * @return the AppRoomDatabase instance
         */
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


        /**
         * Callback for the AppRoomDatabase, this gives access to a callback function to the database
         *  Here it is used to populate the database upon creation with all the categories
         *
         */
        private class AppDatabaseCallback(private val scope: CoroutineScope) : RoomDatabase.Callback() {


            /**
             * Callback to when the Database is created.
             * Here we call populateDatabase that inserts all the categories to the Database
             * @param db is a SupportSQLDatabase
             */
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    scope.launch {
                        populateDatabase(database.categoryDao())
                    }
                }
            }

            /**
             * Callback to when the Database needs a migration and destructiveMigration is `true`
             * Here we also call populateDatabase that inserts all the categories to the Database,
             * since the actual instance still exists we have to repopulate all the data.
             * @param db is a SupportSQLDatabase
             */
            override fun onDestructiveMigration(db: SupportSQLiteDatabase) {
                super.onDestructiveMigration(db)
                INSTANCE?.let { database ->
                    scope.launch {
                        populateDatabase(database.categoryDao())
                    }
                }
            }

            /**
             * Suspend Method that inserts the default categories to the database but first deletes all the categories from the database
             * @param categoryDao is the CategoryDao and is used to access the insert method of categories
             */
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