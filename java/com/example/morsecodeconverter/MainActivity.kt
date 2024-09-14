package com.example.morsecodeconverter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.morsecodeconverter.alphabet.MorseCodeAlphabetScreen
import com.example.morsecodeconverter.favorites.FavoriteWordsUI
import com.example.morsecodeconverter.translation.TranslationUI
import com.example.morsecodeconverter.ui.theme.MorseCodeConverterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MorseCodeConverterTheme {
                val navController = rememberNavController()
                val navigationViewModel: NavigationViewModel = viewModel()
                val favoritesViewModel: FavoritesViewModel = viewModel()
                Navigation(navController = navController, navigationViewModel = navigationViewModel, favoritesViewModel = favoritesViewModel)
            }
        }
    }
}

@Composable
fun Navigation(navController: NavHostController, navigationViewModel: NavigationViewModel, favoritesViewModel: FavoritesViewModel){
    NavHost(navController = navController, startDestination = "translation") {
        composable("translation") { TranslationUI(navController, navigationViewModel, favoritesViewModel) }
        composable("morse_code_alphabet") { MorseCodeAlphabetScreen(navigationViewModel, navController) }
        composable("favorites") { FavoriteWordsUI(favoritesViewModel, navigationViewModel, navController) }
    }

}

