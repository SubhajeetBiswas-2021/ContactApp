package com.subhajeet.contactapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.subhajeet.contactapp.model.database.Contact
import com.subhajeet.contactapp.model.database.ContactDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
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

    fun addContacts(name:String,phoneNumber:String,email:String,image:ByteArray?= null,id:Int?= null){

        /*val contact = Contact(
            name=name,
            phoneNumber = phoneNumber,
            email = email,
            image = image,
            id= id ?: 0
        )*/
       /* val finalImage = if ((image == null) && (id != null && id != 0)) {
            // Fetch existing image from DB if updating and image is not selected
            database.getDao().getContactById(id)?.image
        } else image

        val contact = if (id != null && id != 0) {
            Contact(id = id, name = name, phoneNumber = phoneNumber, email = email, image = finalImage)
        } else {
            Contact(name = name, phoneNumber = phoneNumber, email = email, image = finalImage)
        }

        viewModelScope.launch(Dispatchers.IO){
            database.getDao().upsetContact(contact)
        }*/
        viewModelScope.launch(Dispatchers.IO) {
            val finalImage = if (image == null && id != null && id != 0) {
                database.getDao().getContactById(id)?.image
            } else {
                image
            }

            val contact = if (id != null && id != 0) {
                Contact(id = id, name = name, phoneNumber = phoneNumber, email = email, image = finalImage)
            } else {
                Contact(name = name, phoneNumber = phoneNumber, email = email, image = finalImage)
            }

            database.getDao().upsetContact(contact)
        }
    }

    fun deleteContact(contact: Contact){
        viewModelScope.launch(Dispatchers.IO){
            database.getDao().deleteContact(contact)
        }
    }


}