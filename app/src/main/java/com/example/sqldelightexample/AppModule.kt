package com.example.sqldelightexample

import android.app.Application
import com.example.sqldelightexample.data.PersonDataSource
import com.example.sqldelightexample.data.PersonDataSourceImpl
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSqlDriver(app: Application): SqlDriver {
        return AndroidSqliteDriver(
            schema = PersonDatabase.Schema,
            context = app,
            name = "person.db"
        )
    }

    @Provides
    @Singleton
    fun providePersonDataSoruce(driver: SqlDriver): PersonDataSource {
        return PersonDataSourceImpl(PersonDatabase(driver))
    }
}
