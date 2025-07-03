package com.subhajeet.contactapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.subhajeet.contactapp.model.database.Contact
import com.subhajeet.contactapp.model.database.ContactDatabase
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MyViewModel  @Inject constructor(private val database: ContactDatabase): ViewModel() {

    private val _getContacts = MutableStateFlow<List<Contact>>(emptyList())  //made a state

    val getContacts = _getContacts.asStateFlow()

    fun getAllContacts(){
        viewModelScope.launch(Dispatchers.IO) {

            database.getDao().getAllContacts().collect{
                _getContacts.value=it
                
            }
        }
    }

    fun addContacts(name:String,phoneNumber:String,email:String,image:ByteArray?= null){

        val contact = Contact(
            name=name,
            phoneNumber = phoneNumber,
            email = email,
            image = image
        )
        viewModelScope.launch(Dispatchers.IO){
            database.getDao().upsetContact(contact)
        }
    }

    fun deleteContact(contact: Contact){
        viewModelScope.launch(Dispatchers.IO){
            database.getDao().deleteContact(contact)
        }
    }


}