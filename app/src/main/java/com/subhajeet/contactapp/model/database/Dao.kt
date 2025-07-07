package com.subhajeet.contactapp.model.database


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow


@Dao
interface Dao {
    @Upsert
    suspend fun upsetContact(contact: Contact)                    //for set and update

    @Delete
    suspend fun deleteContact(contact: Contact)

    @Query("SELECT * FROM contact_table ORDER BY name ASC")         //for Read purpose
    fun getAllContacts():Flow<List<Contact>>   //give it in list of contacts

    @Query("SELECT * FROM contact_table WHERE id = :id LIMIT 1")
    fun getContactById(id: Int): Contact?
}