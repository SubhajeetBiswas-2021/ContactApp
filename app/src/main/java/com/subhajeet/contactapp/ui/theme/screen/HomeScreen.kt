package com.subhajeet.contactapp.ui.theme.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
            modifier=Modifier.fillMaxSize().padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            if(contactState.value.isEmpty()){
                Text(text="No Contacts Available", modifier = Modifier.padding(16.dp))
            }else{

                LazyColumn {

                    items(contactState.value){


                        Column(modifier = Modifier.fillMaxSize().padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center) {
                            Text(text=it.name)
                            Text(text=it.phoneNumber)
                            Text(text= it.email)
                        }
                    }
                }
            }


        }
    }
}