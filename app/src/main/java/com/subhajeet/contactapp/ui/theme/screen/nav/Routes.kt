package com.subhajeet.contactapp.ui.theme.screen.nav

import coil3.Image
import kotlinx.serialization.Serializable

sealed class Routes {


    @Serializable
    object Home

    @Serializable
    data class AddContact(   //it is formed data class because this AddContact file is not only for adding also for updating so if we want to pass data from homescreen to add contact as the particular card is clicked
        val name:String? =null,
        val phoneNumber: String? =null,
        val email: String? =null,
        val id: Int?= null        //very much required is id for each card shown
    )
}