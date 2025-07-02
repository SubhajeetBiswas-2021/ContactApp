package com.subhajeet.contactapp.model.database

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "contact_table")          //This entity anotation will create a table through sql lite
data class Contact (
    @PrimaryKey var id : Int=0,
    var name : String,
    var phoneNumber : String,
    var email : String,
    var image : ByteArray?=null
    )