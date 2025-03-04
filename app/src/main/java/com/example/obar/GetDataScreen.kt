package com.example.obar

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.obar.viewmodel.GetDataScreenViewModel
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GetDataScreen(viewModel: GetDataScreenViewModel) {

//    LaunchedEffect(cameraPositionState.position.target) {
//        Log.d(
//            "camPosition",
//            "lat: ${cameraPositionState.position.target.latitude} / lon: ${cameraPositionState.position.target.longitude}"
//        )
//    }


    AnimatedVisibility(
        viewModel.step == 1,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
//                colors = TopAppBarColors(Color.Transparent, Color.Transparent, Color.Transparent, Color.Black, Color.Black),
                    title = {
                        Text(
                            "موقعیت روی نقشه",
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                )
            },
            floatingActionButtonPosition = FabPosition.Center,
            floatingActionButton = {
                FloatingActionButton(
                    containerColor = Color(0xFF13B999),
                    contentColor = Color.White,
                    onClick = { viewModel.step = 2 }
                ) {
                    Text(
                        "تایید موقعیت",
                        modifier = Modifier.padding(vertical = 10.dp, horizontal = 20.dp),
                        fontSize = 24.sp
                    )
                }
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {

                GoogleMap(
                    uiSettings = MapUiSettings(zoomControlsEnabled = false),
                    modifier = Modifier
                        .fillMaxSize(),
                    cameraPositionState = viewModel.campos,
//                onMapClick = { cameraPositionState.animate(cameraPositionState.) }
                )

                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.map_marker),
                    contentDescription = "",
                    modifier = Modifier
                        .align(Alignment.Center),
                    tint = Color(0xff2483b3)
                )

                Text(
                    text = "موقعیت مورد نظر خود را روی نقشه مشخص کنید",
                    textAlign = TextAlign.Center,
                    color = Color.DarkGray,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.TopCenter)
                        .background(
                            Color(0xAEFFFFFF),
                        )
                        .padding(vertical = 10.dp)
                )

            }

        }
    }


    BackHandler {
        viewModel.step = 1
    }

    AnimatedVisibility(
        visible = viewModel.step == 2,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            "ثبت نام",
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                )
            },
            bottomBar = {
                BottomAppBar {
                    Button(
                        colors = ButtonColors(containerColor = Color(0xFF13B999), contentColor = Color.White, disabledContainerColor = ButtonDefaults.buttonColors().disabledContainerColor, disabledContentColor = ButtonDefaults.buttonColors().disabledContentColor),
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {}
                    ) {
                        Text(text = "ثبت", fontSize = 24.sp)
                    }
                }
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
            ) {
                Text(
                    text = "لطفا اطلاعات تکمیلی را وارد کنید.",
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                OutlinedTextField(
                    label = { Text("نام") },
                    value = viewModel.firstName,
                    onValueChange = { viewModel.firstName = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    maxLines = 1
                )
                OutlinedTextField(
                    label = { Text("نام خانوادگی") },
                    value = viewModel.lastName,
                    onValueChange = { viewModel.lastName = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    maxLines = 1
                )
                OutlinedTextField(
                    label = { Text("تلفن همراه") },
                    value = viewModel.mobileNumber,
                    onValueChange = { viewModel.mobileNumber = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
                OutlinedTextField(
                    label = { Text("تلفن ثابت") },
                    value = viewModel.phoneNumber,
                    onValueChange = { viewModel.phoneNumber = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
                OutlinedTextField(
                    label = { Text("آدرس دقیق") },
                    value = viewModel.address,
                    onValueChange = { viewModel.address = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    minLines = 3
                )
                Row(
                    modifier = Modifier.padding(16.dp).fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("جنسیت")
                    Row {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RadioButton(
                                selected = viewModel.gender == 1,
                                onClick = { viewModel.gender = 1 }
                            )
                            Text("آقا")
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RadioButton(
                                selected = viewModel.gender == 0,
                                onClick = { viewModel.gender = 0 },
                            )
                            Text("خانم")
                        }
                    }
                }
            }
        }


    }


}


//@Preview(showBackground = true)
//@Composable
//fun getDataScreenPreview() {
//    ObarTheme {
//        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
//            GetDataScreen()
//        }
//    }
//}