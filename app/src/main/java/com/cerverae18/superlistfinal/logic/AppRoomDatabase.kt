package com.cerverae18.superlistfinal.logic

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.cerverae18.superlistfinal.logic.entities.Product
import kotlinx.coroutines.CoroutineScope

@Database(entities = arrayOf(Product::class,), version = 1)
abstract class AppRoomDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao

    // Singleton
    companion object {
        @Volatile
        private var INSTANCE: AppRoomDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): AppRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppRoomDatabase::class.java, "app_database")
                   // .addCallback(MusicDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
        // ADD Callback in case we need to populate database on creation
    }
}