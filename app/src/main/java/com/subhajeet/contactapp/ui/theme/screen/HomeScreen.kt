package com.subhajeet.contactapp.ui.theme.screen

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.subhajeet.contactapp.R
import com.subhajeet.contactapp.model.database.Contact
import com.subhajeet.contactapp.ui.theme.screen.nav.Routes
import com.subhajeet.contactapp.viewModel.MyViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: MyViewModel = hiltViewModel(), navController: NavController) {

    val contactState = viewModel.getContacts.collectAsState()
    val originalContacts = contactState.value

    var query by rememberSaveable { mutableStateOf("") }

    var active by remember { mutableStateOf(false) } // Active state for SearchBar

    var searching by rememberSaveable { mutableStateOf(false) }  //very imaportant here for controlling the backbutton

    // Handle back button when search bar is active
    BackHandler(enabled = active || searching) {
        active = false
        query = ""
        searching = false
    }


    val filteredContacts = remember(originalContacts, query){ originalContacts.filter {
        it.name.contains(query, ignoreCase = true) ||
                it.phoneNumber.contains(query)
    }
}

    LaunchedEffect(Unit) {
        viewModel.getAllContacts()
    }

    Scaffold(
        floatingActionButton = {
            IconButton(
                onClick = {
                    navController.navigate(Routes.AddContact(
                        name = "",
                        phoneNumber = "",
                        email = "",
                        id=null
                    ))
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
            verticalArrangement = Arrangement.Top
        ) {

            if(filteredContacts.isEmpty()){
                Text(text="No Contacts Available", modifier = Modifier.padding(16.dp))
            }else{

                SearchBar(modifier = Modifier.fillMaxWidth().padding(0.dp,25.dp,0.dp,0.dp),
                    query = query,
                    onQueryChange = {
                        query = it
                    },
                    onSearch = {
                     //   active = false
                    },
                    active = active,
                    onActiveChange = {
                        active = it
                        searching = it || query.isNotEmpty()
                    },
                    placeholder = {
                        Text(text = "Search contacts")
                    },
                    trailingIcon = {
                        if (active) {
                            Icon(
                                modifier = Modifier.clickable {
                                    if (query.isNotEmpty()) {
                                        query = ""
                                    } else {
                                        active = false
                                        searching = false
                                    }
                                },
                                imageVector = Icons.Default.Close,
                                contentDescription = "Close icon"
                            )
                        }
                    }) {
                /*    // Add any content inside to prevent collapsing
                    if (filteredContacts.isEmpty()) {
                        Text(
                            text = if (query.isEmpty()) "No contacts available." else "No contact found by this name.",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(top = 40.dp)
                        )
                    } else {
                        LazyColumn {
                            items(filteredContacts) {
                                Text(
                                    text = it.name,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            query = it.name
                                            active = false
                                        }
                                        .padding(16.dp)
                                )
                            }
                        }
                    } */
                    // optional suggestion list (when active)
                    if (active && filteredContacts.isNotEmpty()) {
                        LazyColumn {
                            items(filteredContacts) { contact ->
                                Text(
                                    text = contact.name,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            query = contact.name
                                            active = false
                                            searching = true  // <-- add this line
                                        }
                                        .padding(16.dp)
                                )
                            }
                        }

                    } else if (active && filteredContacts.isEmpty()) {
                        Text(
                            text = "No contact found by this name.",
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                /*LazyColumn {

                //    items(contactState.value){

                    items(filteredContacts ){
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
                }*/

                // 📋 Show filtered contacts or empty state
                if (filteredContacts.isEmpty() && query.isNotEmpty()) {
                    Text(
                        text = "No contact found by this name.",
                        modifier = Modifier.padding(16.dp)
                    )
                } else if (filteredContacts.isEmpty()) {
                    Text(
                        text = "No contacts available.",
                        modifier = Modifier.padding(16.dp)
                    )
                } else {
                    LazyColumn {
                        items(filteredContacts) { contact ->
                            eachCard(
                                contact = contact,
                                onDelete = { viewModel.deleteContact(contact) },
                                onClick = {
                                    navController.navigate(
                                        Routes.AddContact(
                                            name = contact.name,
                                            phoneNumber = contact.phoneNumber,
                                            email = contact.email,
                                            id = contact.id
                                        )
                                    )
                                }
                            )
                        }
                    }
                }
            }


        }
    }
}

@Composable
fun eachCard(contact: Contact,onDelete:() -> Unit, onClick:()-> Unit) {
    Card(modifier = Modifier.fillMaxWidth().padding(8.dp).clickable {
        onClick()
    }) {

        Box(modifier = Modifier.fillMaxWidth()) {


            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(8.dp)) {

                val imageModel = if (contact.image != null) {
                    // Optional: write ByteArray to file or handle via ViewModel/Repo
                    contact.image
                } else {

                    R.drawable.person
                }
                AsyncImage(
                    model = imageModel,
                    contentDescription = "Contact Image",
                    modifier = Modifier.size(100.dp).clip(CircleShape)
                        .border(2.dp, Color.Gray, CircleShape)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Column(
                    modifier = Modifier

                        .padding(16.dp),
                ) {


                    Text(text = contact.name, fontSize = 20.sp, fontStyle = FontStyle.Italic, fontWeight = FontWeight.Bold )
                    Text(text = contact.phoneNumber)
                    Text(text = contact.email)

                    Button(
                        onClick = onDelete
                    ) {
                        Text(text = "Delete Contact")
                    }
                }




            }

            // 📞 Phone Icon at Top Right
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                val context = LocalContext.current
                IconButton(
                    onClick = { /* handle call logic here */
                        val intent = Intent(Intent.ACTION_DIAL).apply {
                            data = Uri.parse("tel:${contact.phoneNumber}")
                        }
                        context.startActivity(intent)},
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp).clip(CircleShape).border(2.dp, Color.Gray, CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.Call,
                        contentDescription = "Phone Call",
                        tint= Color.Green
                    )
                }
            }


        }
    }
}


