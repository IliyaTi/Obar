package com.example.obar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.obar.ui.theme.ObarTheme
import com.example.obar.viewmodel.GetDataScreenViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            ObarTheme {

                val navController = rememberNavController()

                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                    NavHost(navController = navController, startDestination = "GetDataScreen") {
                        composable(route = "GetDataScreen") { GetDataScreen(viewModel = viewModel(GetDataScreenViewModel::class)) }
                        composable(route = "ListDataScreen") { ListDataScreen(Modifier.padding()) }
                    }
                }


            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ObarTheme {

    }
}