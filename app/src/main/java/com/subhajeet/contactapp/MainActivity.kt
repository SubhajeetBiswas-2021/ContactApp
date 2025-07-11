package com.subhajeet.contactapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import com.subhajeet.contactapp.ui.theme.ContactAppTheme
import com.subhajeet.contactapp.ui.theme.screen.AddContact
import com.subhajeet.contactapp.ui.theme.screen.HomeScreen
import com.subhajeet.contactapp.ui.theme.screen.nav.NavApp
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ContactAppTheme {
                var scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
                Scaffold(modifier = Modifier.fillMaxSize().nestedScroll(scrollBehavior.nestedScrollConnection),
                    topBar = {
                        TopAppBar(
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                titleContentColor = MaterialTheme.colorScheme.primary,
                            ),
                            title = {
                                Text("Contact App")
                            },
                            scrollBehavior = scrollBehavior
                        )

                    }) { innerPadding ->
                 NavApp()
                }
            }
        }
    }
}

