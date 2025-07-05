package com.subhajeet.contactapp.di

import android.app.Application
import androidx.room.Room
import com.subhajeet.contactapp.model.database.Contact
import com.subhajeet.contactapp.model.database.ContactDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DIModules {


    @Provides
    @Singleton
    fun provideDatabase(context :Application):ContactDatabase{             //returning the database
        return Room.databaseBuilder(
            context.baseContext,
            ContactDatabase::class.java,
            "contact_database"
        ).build()
    }
}