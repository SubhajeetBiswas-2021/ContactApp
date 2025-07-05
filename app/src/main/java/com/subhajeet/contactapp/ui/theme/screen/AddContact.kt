package com.subhajeet.contactapp.ui.theme.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.subhajeet.contactapp.viewModel.MyViewModel


@Composable
fun AddContact(viewModel: MyViewModel?= hiltViewModel(), navController: NavController?) {

    val name = remember{ mutableStateOf("") }
    val phoneNumber = remember{ mutableStateOf("") }
    val email = remember{ mutableStateOf("") }

    Column(modifier=Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally){

        TextField(
            value = name.value,
            onValueChange = {
                name.value=it
            },
            label = { Text("Name") }
        )

        TextField(
            value = phoneNumber.value,
            onValueChange = {
                phoneNumber.value=it
            },
            label = { Text("Number") }
        )

        TextField(
            value = email.value,
            onValueChange = {
                email.value=it
            },
            label = { Text("email") }
        )

        ElevatedButton(
            onClick = {
                viewModel?.addContacts(
                    name = name.value,
                    phoneNumber = phoneNumber.value,
                    email=email.value
                )
            }
        ) {
            Text(text="Add Contacts")
        }
    }
}

@Preview(showBackground = true, showSystemUi=true)  //required only for seeing the preview
@Composable
fun AddContactPreview() {
    AddContact(viewModel = null, navController = null)
}