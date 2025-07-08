package com.subhajeet.contactapp.ui.theme.screen.nav

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.subhajeet.contactapp.ui.theme.screen.AddContact
import com.subhajeet.contactapp.ui.theme.screen.HomeScreen

@Composable
fun NavApp() {

    val navController = rememberNavController()

    NavHost(
        navController=navController,
        startDestination = Routes.Home
    ){
        composable<Routes.Home> {
            HomeScreen(navController=navController)
        }

        composable<Routes.AddContact> {
            val data = it.toRoute<Routes.AddContact>()
            AddContact(navController =navController,
                name = data.name,
                phoneNumber = data.phoneNumber,
                email = data.email,
                id = data.id
               )
        }
    }
}