package com.kevus.necessary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kevus.necessary.navigation.AppNavigation
import com.kevus.necessary.repositories.DataStorePreferenceRepository
import com.kevus.necessary.screens.OverviewScreen
import com.kevus.necessary.ui.theme.NecessaryTheme
import com.kevus.necessary.viemodels.DataStoreViewModel
import com.kevus.necessary.viemodels.DataStoreViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
            val dataStoreViewModel: DataStoreViewModel = viewModel(
                factory = DataStoreViewModelFactory(DataStorePreferenceRepository(LocalContext.current))
            )
            NecessaryTheme(darkTheme = true, dataStoreViewModel = dataStoreViewModel) {
                // A surface container using the 'background' color from the theme
                Surface(
//                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    AppNavigation(dataStoreViewModel = dataStoreViewModel)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    NecessaryTheme {
//        Greeting("Android")
//    }
//}