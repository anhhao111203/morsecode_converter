package com.example.morsecodeconverter.translation

// Import necessary libraries
import android.content.Context
import android.widget.Toast
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.morsecodeconverter.BottomNavigationBarUI
import com.example.morsecodeconverter.favorites.FavoriteWords
import com.example.morsecodeconverter.FavoritesViewModel
import com.example.morsecodeconverter.MorseCodePlayer
import com.example.morsecodeconverter.NavigationViewModel
import com.example.morsecodeconverter.R

// Translation UI:
// This function takes 3 parameters:
//       navController: for managing navigation between multiple screens
//       navigationViewModel: for managing which screen is currently active
//       favoritesViewModel: for handling adding and managing favorite words
@Composable
fun TranslationUI(
    navController: NavHostController,
    navigationViewModel: NavigationViewModel,
    favoritesViewModel: FavoritesViewModel
) {
    // For saving letter
    var letterText by remember {
        mutableStateOf("")
    }
    // For saving morse code
    var morseCode by remember {
        mutableStateOf("")
    }
    // For saving the state when the input field is focused
    // Once it's focused, some icons (sound, copy, add to favorites) will appears
    var isIconVisible by remember {
        mutableStateOf(false)
    }
    // For saving the state whether current state is text-to-morse or vice versa
    var isTextToMorseCode by remember {
        mutableStateOf(true)
    }
    // For saving the state when user adds word to favorites
    var isFavourite by remember {
        mutableStateOf(false)
    }
    // Animation variable
    // The text fields will expand when input field is focus by user
    val animatedWeight by animateFloatAsState(
        targetValue = if (isIconVisible) 0.4f else 0.3f,
        label = "Float Animation"
    )
    // For clipboard
    val clipboardManager: ClipboardManager = LocalClipboardManager.current
    // For context
    val context : Context = LocalContext.current
    // For user to hear the playback of morse code
    val morseCodePlayer = remember { MorseCodePlayer(context) }

    // UI design
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(1, 1, 1))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
                .background(color = Color(1, 1, 1)),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                modifier = Modifier.padding(top = 30.dp),
                text = "Translate",
                fontSize = 40.sp,
                color = Color.White,
                fontFamily = FontFamily(Font(R.font.semibold_sf)),
            )
            TextField(
                value = letterText,
                onValueChange = { newText ->
                    letterText = newText
                    morseCode = if (isTextToMorseCode) TranslateProcess().translateToMorseCode(newText) else TranslateProcess().translateFromMorseCode(newText)
                },
                textStyle = TextStyle(
                    fontSize = 25.sp,
                    color = Color.White,
                    fontFamily = FontFamily(Font(R.font.semibold_sf))
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged { focusState ->
                        isIconVisible = focusState.isFocused
                    }
                    .weight(animatedWeight)
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                placeholder = {
                    Text(
                        text = if (isTextToMorseCode) "Enter text to convert" else "Enter morse code to convert",
                        style = TextStyle(
                            fontSize = 25.sp,
                            color = Color(61, 60, 65),
                            fontFamily = FontFamily(Font(R.font.semibold_sf))
                        )
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(29, 28, 33),
                    unfocusedContainerColor = Color(29, 28, 33)
                )
            )
            Box(
                modifier = Modifier.weight(animatedWeight)
            ) {
                TextField(
                    value = morseCode,
                    onValueChange = {},
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(bottomEnd = 16.dp, bottomStart = 16.dp)),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color(29, 28, 33),
                        unfocusedContainerColor = Color(29, 28, 33),
                        focusedTextColor = Color(74, 191, 234),
                        unfocusedTextColor = Color(74, 191, 234)
                    ),
                    textStyle = TextStyle(
                        fontSize = 25.sp,
                        color = Color(74, 191, 234),
                        fontFamily = FontFamily(Font(R.font.semibold_sf))
                    ),
                    placeholder = {
                        Text(
                            text = if (isTextToMorseCode) "Converted morse code" else "Converted text",
                            style = TextStyle(
                                fontSize = 25.sp,
                                color = Color(35, 73, 74),
                                fontFamily = FontFamily(Font(R.font.semibold_sf))
                            )
                        )
                    }
                )
                if(isIconVisible) {
                    Row(
                        modifier = Modifier.align(Alignment.BottomStart)
                    ) {
                        IconButton(
                            onClick = {
                                favoritesViewModel.addFavorite(FavoriteWords(letterText, morseCode))
                                isFavourite = true
                                Toast.makeText(context, "Added to favourites", Toast.LENGTH_SHORT).show()
                            },
                            modifier = Modifier
                                .padding(20.dp)
                        ) {
                            Icon(
                                painter = if(isFavourite) painterResource(R.drawable.heart) else painterResource(
                                    R.drawable.heart_empty
                                ),
                                contentDescription = "Favorite words icon",
                                tint = Color(74, 191, 234),
                                modifier = Modifier
                                    .size(30.dp)
                            )
                        }
                        IconButton(
                            onClick = {
                                clipboardManager.setText(AnnotatedString(morseCode))
                                Toast.makeText(context, "Text copied to clipboard", Toast.LENGTH_SHORT).show()
                            },
                            modifier = Modifier
                                .padding(20.dp)
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
                                morseCodePlayer.playMorseCode(morseCode)
                            },
                            modifier = Modifier
                                .padding(20.dp)
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
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Button(
                    onClick = {
                        isTextToMorseCode = true
                    },
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .fillMaxWidth()
                        .size(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isTextToMorseCode) Color(74, 191, 234) else Color(29, 28, 33),
                        contentColor = if (isTextToMorseCode) Color.White else Color(61, 60, 65)
                    )
                ) {
                    Text(
                        text = "Text to Morse code",
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontFamily = FontFamily(Font(R.font.semibold_sf))
                        )
                    )
                }
                Button(
                    onClick = {
                        isTextToMorseCode = false
                    },
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .fillMaxWidth()
                        .size(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (!isTextToMorseCode) Color(74, 191, 234) else Color(29, 28, 33),
                        contentColor = if (!isTextToMorseCode) Color.White else Color(61, 60, 65)
                    )
                ) {
                    Text(
                        text = "Morse code to text",
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontFamily = FontFamily(Font(R.font.semibold_sf))
                        )
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f - animatedWeight))
            BottomNavigationBarUI(navController, navigationViewModel)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ConvertTextToMorseCodeUIPreview() {
    val navController = rememberNavController()
    val navigationViewModel: NavigationViewModel = viewModel()
    val favoritesViewModel: FavoritesViewModel = viewModel()
    TranslationUI(navController, navigationViewModel, favoritesViewModel)
}