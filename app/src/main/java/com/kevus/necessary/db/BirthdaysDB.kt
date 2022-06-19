package com.kevus.necessary.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.kevus.necessary.models.Birthday
import com.kevus.necessary.models.Task

@Database(
    entities = [Birthday::class], //add entities here
    version = 1,
    exportSchema = false
)
abstract class BirthdaysDB: RoomDatabase(){
    abstract fun birthdayDao(): BirthdayDao

    companion object{
        private var INSTANCE: BirthdaysDB? = null

        fun getDataBase(context: Context): BirthdaysDB{
            return INSTANCE ?: synchronized(this){
                INSTANCE ?: buildDatabase(context).also{
                    INSTANCE = it
                }
            }
        }

        private fun buildDatabase(context: Context): BirthdaysDB{
            return Room
                .databaseBuilder(context, BirthdaysDB::class.java, "birthday_database")
                .addCallback(
                    object : RoomDatabase.Callback(){
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            //do work on DB creation
                        }

                        override fun onOpen(db: SupportSQLiteDatabase) {
                            super.onOpen(db)
                            //do work on each start
                        }
                    }
                )
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}