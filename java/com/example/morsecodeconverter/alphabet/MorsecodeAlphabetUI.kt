package com.example.morsecodeconverter.alphabet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.morsecodeconverter.BottomNavigationBarUI
import com.example.morsecodeconverter.MorseCodePlayer
import com.example.morsecodeconverter.NavigationViewModel
import com.example.morsecodeconverter.R

@Composable
fun MorseCodeAlphabetScreen(
    navigationViewModel: NavigationViewModel,
    navController: NavHostController
) {
    val morseCodeAlphabet = MorseCodeAlphabet().alphabet
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(1, 1, 1))
            .padding(20.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            modifier = Modifier.padding(top = 30.dp),
            text = "Morse Code Alphabet",
            fontSize = 40.sp,
            lineHeight = 50.sp,
            color = Color.White,
            fontFamily = FontFamily(Font(R.font.semibold_sf))
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            morseCodeAlphabet.forEach { (letter, code) ->
                item {
                    MorseCodeItem(letter, code)
                }
            }
        }
        BottomNavigationBarUI(navController, navigationViewModel)
    }
}

@Composable
fun MorseCodeItem(letter: String, code: String) {
    val context = LocalContext.current
    val morseCodePlayer = remember { MorseCodePlayer(context) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(29, 28, 33)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = letter,
                fontSize = 30.sp,
                color = Color.White,
                fontFamily = FontFamily(Font(R.font.semibold_sf)),
                modifier = Modifier.weight(0.2f),
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.weight(0.3f))
            Text(
                text = code,
                fontSize = 30.sp,
                color = Color(74, 191, 234),
                fontFamily = FontFamily(Font(R.font.semibold_sf)),
                modifier = Modifier.weight(0.5f),
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.weight(0.7f))
            IconButton(
                onClick = {
                          morseCodePlayer.playMorseCode(code)
                },
                modifier = Modifier.padding(start = 10.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.volume),
                    contentDescription = "Volume icon",
                    tint = Color(74, 191, 234),
                    modifier = Modifier.size(30.dp)
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MorseCodeAlphabetUIPreview() {
    val navigationViewModel: NavigationViewModel = viewModel()
    val navController = rememberNavController()
    MorseCodeAlphabetScreen(navigationViewModel, navController)
}
