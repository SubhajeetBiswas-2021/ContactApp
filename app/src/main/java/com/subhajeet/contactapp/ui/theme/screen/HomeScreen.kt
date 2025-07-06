package com.subhajeet.contactapp.ui.theme.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.subhajeet.contactapp.model.database.Contact
import com.subhajeet.contactapp.ui.theme.screen.nav.Routes
import com.subhajeet.contactapp.viewModel.MyViewModel

@Composable
fun HomeScreen(viewModel: MyViewModel = hiltViewModel(), navController: NavController) {

    val contactState = viewModel.getContacts.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getAllContacts()
    }

    Scaffold(
        floatingActionButton = {
            IconButton(
                onClick = {
                    navController.navigate(Routes.AddContact)
                }
            ){
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Contact",
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    ) {
        innerPadding ->

        Column(
            modifier= Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            if(contactState.value.isEmpty()){
                Text(text="No Contacts Available", modifier = Modifier.padding(16.dp))
            }else{

                LazyColumn {

                    items(contactState.value){

                       eachCard(
                           contact = it,
                           onDelete = {
                               viewModel.deleteContact(it)
                           },
                           onClick = {
                                navController.navigate(Routes.AddContact(
                                    name = it.name,
                                    phoneNumber = it.phoneNumber,
                                    email = it.email,
                                    id=it.id
                                ))
                           }
                       )



                    }
                }
            }


        }
    }
}

@Composable
fun eachCard(contact: Contact,onDelete:() -> Unit, onClick:()-> Unit) {
    Card(modifier = Modifier.clickable {
        onClick()
    }){

        AsyncImage(
            model=contact.image,
            contentDescription = "Contact Image"
        )

        Column(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
            ) {


            Text(text=contact.name)
            Text(text=contact.phoneNumber)
            Text(text= contact.email)

            Button(
                onClick=onDelete
            ) {
                Text(text="Delete Contact")
            }
        }
    }
}