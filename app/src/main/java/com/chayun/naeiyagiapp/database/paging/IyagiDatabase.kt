package com.chayun.naeiyagiapp.database.paging

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.chayun.naeiyagiapp.data.response.ListIyagiItem

@Database(
    entities = [ListIyagiItem::class],
    version = 1,
    exportSchema = false
)
abstract class IyagiDatabase : RoomDatabase() {
    companion object {
        @Volatile
        private var INSTANCE: IyagiDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): IyagiDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    IyagiDatabase::class.java, "iyagi_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}