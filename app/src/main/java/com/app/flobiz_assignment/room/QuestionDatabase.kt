//package com.app.flobiz_assignment.room
//
//import android.content.Context
//import androidx.room.*
//
//@Database(entities = [QuestionEntity::class], exportSchema = false, version = 1)
//@TypeConverters(DataConverter::class)
// abstract class QuestionDatabase : RoomDatabase() {
//    companion object {
//        private val DB_NAME = "cached_files_db"
//        @Volatile
//        private var instance : QuestionDatabase ? = null
//
//        @Synchronized
//        fun getInstance(context: Context): QuestionDatabase? {
//            if (instance == null) {
//                instance = Room.databaseBuilder(
//                    context.applicationContext,
//                    QuestionDatabase::class.java,
//                    DB_NAME
//                ).fallbackToDestructiveMigration().build()
//            }
//            return instance }
//    }
//
//    abstract fun questionDao(): QuestionDao
//}