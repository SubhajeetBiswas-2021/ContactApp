package com.subhajeet.contactapp.ui.theme.screen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage

import com.subhajeet.contactapp.viewModel.MyViewModel


@Composable
fun AddContact(viewModel: MyViewModel?= hiltViewModel(), navController: NavController?) {

    val name = remember{ mutableStateOf("") }
    val phoneNumber = remember{ mutableStateOf("") }
    val email = remember{ mutableStateOf("") }
    val image = remember { mutableStateOf<ByteArray?>(null) }//(1)

    val imageUri = remember { mutableStateOf<Uri?>(null) } //imageuri is the image path(2)
    val context = LocalContext.current


    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia() //telling to pick the image(3)
    ) {uri ->          //lambda function
        if(uri != null){
            imageUri.value = uri

            val inputStream = uri.let { context.contentResolver.openInputStream(uri) }     //6
            val byteArray = inputStream?.readBytes()   //7
            image.value=byteArray  //8
        }
    }
    Column(modifier=Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally){

        Button(              //4
            onClick = {
                pickImageLauncher.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                )
            }
        ) {
            Text(text = "Select Image")
        }

        if(imageUri != null){   //5
            Image(
                imageVector = Icons.Default.Person,
                contentDescription = "Selected Image",
                modifier = Modifier.size(100.dp)
            )
        }else{
            AsyncImage(
                model=imageUri.value,
                contentDescription = "Selected Image",
                modifier = Modifier.size(100.dp)
            )
        }





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
                    email=email.value,
                    image=image.value
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