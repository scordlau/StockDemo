package com.example.currencydemo.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.currencydemo.R
import com.example.currencydemo.data.dao.CurrencyInfoDao
import com.example.currencydemo.domain.CurrencyInfoEntity
import kotlinx.coroutines.CoroutineScope

/**
 * Created by scordlau on 3/22/21.
 */

@Database(entities = [CurrencyInfoEntity::class], version = 1)
abstract class CurrencyInfoDatabase : RoomDatabase() {

    abstract fun currencyInfoDao(): CurrencyInfoDao

    companion object {

        const val TABLE_NAME = "currencyTable"

        @Volatile
        private var instance: CurrencyInfoDatabase? = null

        fun getInstance(context: Context,
                        coroutineScope: CoroutineScope): CurrencyInfoDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(
                        context,
                        coroutineScope
                ).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context,
                                  coroutineScope: CoroutineScope): CurrencyInfoDatabase {
            return Room.databaseBuilder(
                    context,
                    CurrencyInfoDatabase::class.java,
                    TABLE_NAME
            ).addCallback(CurrencyInfoRoomCallback(context, coroutineScope, R.raw.default_currency_info_list)
            ).build()
        }
    }

}