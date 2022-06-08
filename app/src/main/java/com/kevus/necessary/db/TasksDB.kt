package com.kevus.necessary.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.kevus.necessary.models.Task

@Database(
    entities = [Task::class], //add entities here
    version = 1,
    exportSchema = false
)
abstract class TasksDB: RoomDatabase(){
    abstract fun tasksDao(): TasksDao

    companion object{
        private var INSTANCE: TasksDB? = null

        fun getDataBase(context: Context): TasksDB{
            return INSTANCE ?: synchronized(this){
                INSTANCE ?: buildDatabase(context).also{
                    INSTANCE = it
                }
            }
        }

        private fun buildDatabase(context: Context): TasksDB{
            return Room
                .databaseBuilder(context, TasksDB::class.java, "task_database")
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