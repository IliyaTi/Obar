package com.example.obar

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.obar.model.UserLocal
import com.example.obar.viewmodel.ListDataScreenViewModel

@Composable
fun ListDataScreen(viewModel: ListDataScreenViewModel) {

    val context = LocalContext.current

    LaunchedEffect(viewModel.error) {
        if (viewModel.error == "") return@LaunchedEffect
        Toast.makeText(context, viewModel.error, Toast.LENGTH_SHORT).show()
        viewModel.error = ""
    }

    LaunchedEffect(true) {
        viewModel.loadData()
    }

    // Loading
    if (viewModel.loading)
        Box(
            modifier = Modifier.fillMaxSize().background(Color(0xD2FFFFFF)).clickable {  },
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(modifier = Modifier.size(24.dp))
        }



    LazyColumn(modifier = Modifier.fillMaxSize().safeContentPadding()) {
        for (item in viewModel.items) {
            item { ListItem(item) }
        }
    }

}


@Composable
fun ListItem(item: UserLocal) {
    Card(
        onClick = {},
        modifier = Modifier.fillMaxWidth().padding(12.dp)
    ) {
        Text(modifier = Modifier.padding(8.dp), text = item.address)
        Row(
            modifier = Modifier.padding(horizontal = 12.dp).padding(bottom = 12.dp),
            ) {
            Row {
                Text(item.first_name)
                Text(item.last_name)
            }
            Spacer(modifier = Modifier.width(6.dp))
            Text(item.coordinate_mobile)
        }
    }
}

@Preview
@Composable
fun ListItemPreview() {
    val item = UserLocal(address = "addreeess", first_name = "علی", last_name = "فلاحتی", coordinate_mobile = "13423481092")
    ListItem(item)
}