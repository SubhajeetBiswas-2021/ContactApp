package com.subhajeet.contactapp.model.database

import androidx.room.Database

@Database(entities = [Contact::class], version = 1, exportSchema = true)
abstract class ContactDatabase {         //since abstract room will make the body of this we will not give
    abstract fun getDao():Dao
}
