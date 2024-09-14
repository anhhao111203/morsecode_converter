package com.example.morsecodeconverter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

@Composable
fun NavigationItem(
    icon: Int,
    label: String,
    isSelect: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(
            onClick = onClick
        ) {
            Icon(
                painter = painterResource(icon),
                contentDescription = "$label icon",
                tint = if (isSelect) Color(74, 191, 234) else Color(118,118,118),
                modifier = Modifier.size(30.dp)
            )
        }
        Text(
            text = label,
            fontSize = 15.sp,
            fontFamily = FontFamily(Font(R.font.regular_sf)),
            textAlign = TextAlign.Center,
            color = if (isSelect) Color(74, 191, 234) else Color(118,118,118),
        )
    }
}
@Composable
fun BottomNavigationBarUI(
    navController: NavHostController,
    navigationViewModel: NavigationViewModel = viewModel()
) {
    val selectedItem by navigationViewModel.selectedItem
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        NavigationItem(
            icon = R.drawable.translation,
            label = "Translation",
            isSelect = selectedItem == "Translation",
            onClick = {
                navigationViewModel.selectItem("Translation")
                navController.navigate("translation")
            }
        )
        NavigationItem(
            icon = R.drawable.heart,
            label = "Favorite",
            isSelect = selectedItem == "Favorite",
            onClick = {
                navigationViewModel.selectItem("Favorite")
                navController.navigate("favorites")
            }
        )
        NavigationItem(
            icon = R.drawable.morse_code,
            label = "Alphabet",
            isSelect = selectedItem == "Alphabet",
            onClick = {
                navigationViewModel.selectItem("Alphabet")
                navController.navigate("morse_code_alphabet")
            }
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    painter = painterResource(R.drawable.mechanical_gears),
                    contentDescription = "Setting icon",
                    tint = Color(118,118,118),
                    modifier = Modifier.size(30.dp)
                )
            }
            Text(
                text = "Setting",
                fontSize = 15.sp,
                fontFamily = FontFamily(Font(R.font.regular_sf)),
                textAlign = TextAlign.Center,
                color = Color(118,118,118)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavigationBarUIPreview() {
    val navController = rememberNavController()
    BottomNavigationBarUI(navController)
}