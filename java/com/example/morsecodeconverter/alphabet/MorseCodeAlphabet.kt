package com.example.morsecodeconverter.alphabet

// Class to save the morse code alphabet list
class MorseCodeAlphabet {
    // Immutable variable named "alphabet" typed "List",
    // each item in List is a Pair(Tuple) which represent the relationship between 2 String values
    val alphabet: List<Pair<String,String>> = listOf(
        "A" to ".-",
        "B" to "-...",
        "C" to "-.-.",
        "D" to "-..",
        "E" to ".",
        "F" to "..-.",
        "G" to "--.",
        "H" to "....",
        "I" to "..",
        "J" to ".---",
        "K" to "-.-",
        "L" to ".-..",
        "M" to "--",
        "N" to "-.",
        "O" to "---",
        "P" to ".--.",
        "Q" to "--.-",
        "R" to ".-.",
        "S" to "...",
        "T" to "-",
        "U" to "..-",
        "V" to "...-",
        "W" to ".--",
        "X" to "-..-",
        "Y" to "-.--",
        "Z" to "--.."
    )
}