package com.example.morsecodeconverter.favorites

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.morsecodeconverter.BottomNavigationBarUI
import com.example.morsecodeconverter.FavoritesViewModel
import com.example.morsecodeconverter.MorseCodePlayer
import com.example.morsecodeconverter.NavigationViewModel
import com.example.morsecodeconverter.R


@Composable
fun FavoriteWordCard(
    inputWord: String,
    outputWord: String,
    onUnfavorite: () -> Unit
) {
    // For clipboard
    val clipboardManager: ClipboardManager = LocalClipboardManager.current
    val context : Context = LocalContext.current
    val morseCodePlayer = remember { MorseCodePlayer(context) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(29, 28, 33)
        ),
        shape = RoundedCornerShape(15.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
        ) {
            Text(
                text = "Text",
                fontSize = 20.sp,
                color = Color.White,
                fontFamily = FontFamily(Font(R.font.regular_sf))
            )
            Text(
                text = inputWord,
                fontSize = 40.sp,
                color = Color.White,
                fontFamily = FontFamily(Font(R.font.semibold_sf))
            )
            Divider(color = Color.Gray, thickness = 1.dp)
            Text(
                text = "Morse code",
                fontSize = 20.sp,
                color = Color(74, 191, 234),
                fontFamily = FontFamily(Font(R.font.regular_sf))
            )
            Text(
                text = outputWord,
                fontSize = 40.sp,
                color = Color(74, 191, 234),
                fontFamily = FontFamily(Font(R.font.semibold_sf))
            )
            Row {
                IconButton(
                    onClick = onUnfavorite
                ) {
                    Icon(
                        painter = painterResource(R.drawable.heart),
                        contentDescription = "Favorite words icon",
                        tint = Color(74, 191, 234),
                        modifier = Modifier
                            .size(30.dp)
                    )
                }
                IconButton(
                    onClick = {
                        clipboardManager.setText(AnnotatedString(outputWord))
                        Toast.makeText(context, "Text copied to clipboard", Toast.LENGTH_SHORT).show()
                    }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.copy),
                        contentDescription = "Copy to clipboard icon",
                        tint = Color(74, 191, 234),
                        modifier = Modifier
                            .size(30.dp)
                    )
                }
                IconButton(
                    onClick = {
                        morseCodePlayer.playMorseCode(outputWord)
                    }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.volume),
                        contentDescription = "Volume icon",
                        tint = Color(74, 191, 234),
                        modifier = Modifier
                            .size(30.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun FavoriteWordsUI(
    favoritesViewModel: FavoritesViewModel,
    navigationViewModel: NavigationViewModel,
    navController: NavHostController
) {
    val favoriteWords = favoritesViewModel.favoriteWords
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(1, 1, 1))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                modifier = Modifier.padding(top = 30.dp),
                text = "Favourites",
                fontSize = 40.sp,
                color = Color.White,
                fontFamily = FontFamily(Font(R.font.semibold_sf))
            )
            Text(
                text = "Text - Morse code",
                fontSize = 25.sp,
                color = Color.White,
                fontFamily = FontFamily(Font(R.font.semibold_sf))
            )
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                items(favoriteWords) { word ->
                    FavoriteWordCard(
                        word.inputWord,
                        word.outputWord,
                        onUnfavorite = {
                            favoritesViewModel.removeFavorite(word)
                        }
                    )
                }
            }
            BottomNavigationBarUI(navController, navigationViewModel)
        }
    }
}

@Preview (showBackground = true)
@Composable
fun FavoriteWordsUIPreview() {
    val favoritesViewModel: FavoritesViewModel = viewModel()
    val navigationViewModel: NavigationViewModel = viewModel()
    val navController = rememberNavController()
    FavoriteWordsUI(favoritesViewModel, navigationViewModel, navController)
}