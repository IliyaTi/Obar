package com.example.obar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.obar.ui.theme.ObarTheme
import com.example.obar.viewmodel.GetDataScreenViewModel
import com.example.obar.viewmodel.ListDataScreenViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            ObarTheme {

                val navController = rememberNavController()

                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                    NavHost(navController = navController, startDestination = "StartScreen") {
                        composable(route = "StartScreen") {
                            StartScreen(navController = navController)
                        }
                        composable(route = "GetDataScreen") {
                            GetDataScreen(
                                viewModel = viewModel(GetDataScreenViewModel::class),
                                navController = navController
                            )
                        }
                        composable(route = "ListDataScreen") {
                            ListDataScreen(viewModel = viewModel<ListDataScreenViewModel>())
                        }
                    }
                }


            }
        }
    }
}


@Composable
fun StartScreen(navController: NavController) {
    Column (
        modifier = Modifier.fillMaxSize().safeContentPadding(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { navController.navigate("GetDataScreen") }) {
            Text(text = "شخص جدید")
        }
        Button(onClick = { navController.navigate("ListDataScreen") }) {
            Text(text = "لیست اشخاص")
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ObarTheme {

    }
}