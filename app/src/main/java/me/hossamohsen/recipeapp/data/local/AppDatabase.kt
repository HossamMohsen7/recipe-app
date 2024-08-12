package me.hossamohsen.recipeapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import me.hossamohsen.recipeapp.data.dao.UserDao
import me.hossamohsen.recipeapp.data.models.User

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        private const val DATABASE_NAME = "app_database"

        @Volatile
        private lateinit var instance: AppDatabase

        fun getInstance(): AppDatabase = instance

        fun init(context: Context) {
            instance = buildDatabase(context)
        }

        private fun buildDatabase(context: Context): AppDatabase =
            Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                DATABASE_NAME
            ).fallbackToDestructiveMigration().build()
    }
}