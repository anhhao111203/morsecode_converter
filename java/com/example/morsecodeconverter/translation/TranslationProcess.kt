package com.example.morsecodeconverter.translation

class TranslateProcess {
    // Convert from text to morse code
    fun translateToMorseCode(text: String): String {
        // This function should convert the input text to Morse code
        // This is a simple placeholder for demonstration
        val morseMap = mapOf(
            'A' to ".-", 'B' to "-...", 'C' to "-.-.", 'D' to "-..",
            'E' to ".", 'F' to "..-.", 'G' to "--.", 'H' to "....",
            'I' to "..", 'J' to ".---", 'K' to "-.-", 'L' to ".-..",
            'M' to "--", 'N' to "-.", 'O' to "---", 'P' to ".--.",
            'Q' to "--.-", 'R' to ".-.", 'S' to "...", 'T' to "-",
            'U' to "..-", 'V' to "...-", 'W' to ".--", 'X' to "-..-",
            'Y' to "-.--", 'Z' to "--..",
            '1' to ".----", '2' to "..---", '3' to "...--", '4' to "....-",
            '5' to ".....", '6' to "-....", '7' to "--...", '8' to "---..",
            '9' to "----.", '0' to "-----"
        )

        return text.uppercase().map { char ->
            morseMap[char] ?: char.toString()
        }.joinToString(" ")
    }

    // Convert from morse code to text
    fun translateFromMorseCode(morseCode: String): String {
        val morseMap = mapOf(
            ".-" to 'A', "-..." to 'B', "-.-." to 'C', "-.." to 'D',
            "." to 'E', "..-." to 'F', "--." to 'G', "...." to 'H',
            ".." to 'I', ".---" to 'J', "-.-" to 'K', ".-.." to 'L',
            "--" to 'M', "-." to 'N', "---" to 'O', ".--." to 'P',
            "--.-" to 'Q', ".-." to 'R', "..." to 'S', "-" to 'T',
            "..-" to 'U', "...-" to 'V', ".--" to 'W', "-..-" to 'X',
            "-.--" to 'Y', "--.." to 'Z',
            ".----" to '1', "..---" to '2', "...--" to '3', "....-" to '4',
            "....." to '5', "-...." to '6', "--..." to '7', "---.." to '8',
            "----." to '9', "-----" to '0'
        )

        return morseCode.trim().split("   ").joinToString(" ") { word ->
            word.split(" ").map { letter ->
                morseMap[letter] ?: ""
            }.joinToString("")
        }
    }
}
