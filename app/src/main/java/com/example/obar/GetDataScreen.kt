package com.example.obar

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.obar.viewmodel.GetDataScreenViewModel
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GetDataScreen(
    viewModel: GetDataScreenViewModel,
    navController: NavController
) {

//    LaunchedEffect(cameraPositionState.position.target) {
//        Log.d(
//            "camPosition",
//            "lat: ${cameraPositionState.position.target.latitude} / lon: ${cameraPositionState.position.target.longitude}"
//        )
//    }

    val context = LocalContext.current

    LaunchedEffect(viewModel.error) {
        if (viewModel.error == "") return@LaunchedEffect
        Toast.makeText(context, viewModel.error, Toast.LENGTH_SHORT).show()
        viewModel.error = ""
    }

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


    BackHandler(viewModel.step != 1) {
        viewModel.step -= 1
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
                        onClick = { viewModel.saveToRemote {
                            Toast.makeText(context, "عملیات با موفقیت انجام شد", Toast.LENGTH_SHORT).show()
                            navController.popBackStack()
                        } }
                    ) {
                        Text(text = "ثبت", fontSize = 24.sp)
                    }
                }
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
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
                    maxLines = 1,
                    isError = !viewModel.firstNameValid.first,
                    supportingText = {
                        if (!viewModel.firstNameValid.first) {
                            Text(viewModel.firstNameValid.second, color = MaterialTheme.colorScheme.error)
                        }
                    }
                )
                OutlinedTextField(
                    label = { Text("نام خانوادگی") },
                    value = viewModel.lastName,
                    onValueChange = { viewModel.lastName = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    maxLines = 1,
                    isError = !viewModel.lastNameValid.first,
                    supportingText = {
                        if (!viewModel.lastNameValid.first) {
                            Text(viewModel.lastNameValid.second, color = MaterialTheme.colorScheme.error)
                        }
                    }
                )
                OutlinedTextField(
                    label = { Text("تلفن همراه") },
                    value = viewModel.mobileNumber,
                    onValueChange = { viewModel.mobileNumber = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    isError = !viewModel.mobileNumberValid.first,
                    supportingText = {
                        if (!viewModel.mobileNumberValid.first) {
                            Text(viewModel.mobileNumberValid.second, color = MaterialTheme.colorScheme.error)
                        }
                    }
                )
                OutlinedTextField(
                    label = { Text("تلفن ثابت") },
                    value = viewModel.phoneNumber,
                    onValueChange = { viewModel.phoneNumber = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    isError = !viewModel.phoneNumberValid.first,
                    supportingText = {
                        if (!viewModel.phoneNumberValid.first) {
                            Text(viewModel.phoneNumberValid.second, color = MaterialTheme.colorScheme.error)
                        }
                    }
                )
                OutlinedTextField(
                    label = { Text("آدرس دقیق") },
                    value = viewModel.address,
                    onValueChange = { viewModel.address = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    minLines = 3,
                    isError = !viewModel.addressValid.first,
                    supportingText = {
                        if (!viewModel.addressValid.first) {
                            Text(viewModel.addressValid.second, color = MaterialTheme.colorScheme.error)
                        }
                    }
                )
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("جنسیت")

                    SingleChoiceSegmentedButtonRow {
                        viewModel.genderOptions.forEachIndexed { index, pair ->
                            SegmentedButton(
                                shape = SegmentedButtonDefaults.itemShape(
                                    index = index,
                                    count = viewModel.genderOptions.size
                                ),
                                onClick = { viewModel.gender = pair },
                                selected = pair == viewModel.gender
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Image(
                                        imageVector = ImageVector.vectorResource(if(pair.first == "Male") R.drawable.male else R.drawable.female),
                                        contentDescription = ""
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(pair.second)
                                }
                            }
                        }
                    }
                }
            }
        }


    }

    AnimatedVisibility(
        visible = viewModel.loading,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xB2FFFFFF))
                .clickable {  },
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(modifier = Modifier.size(26.dp))
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