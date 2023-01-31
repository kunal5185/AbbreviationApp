package com.example.abbreviationapp.utils

/**
 * This is Util class which validates if abbreviation provided by user is valid or not.
 */
object ValidationUtil {
    private const val SUCCESS_MESSAGE = "Success"
    private const val EMPTY_SF_MESSAGE = "Please provide valid abbreviation."
    private const val SINGLE_CHAR_SF_MESSAGE = "Abbreviation can't be single character."
    private const val NON_ALPHABET_SF_MESSAGE = "Abbreviation can contain only alphabets."
    const val RESPONSE_ERROR_MESSAGE = "Response is null or empty."
    fun isValid(abbreviation: String): Pair<Boolean, String> {
        return if (abbreviation.isEmpty())
            Pair(false, EMPTY_SF_MESSAGE)
        else if (abbreviation.length == 1)
            Pair(false, SINGLE_CHAR_SF_MESSAGE)
        else if (!(abbreviation.matches("^[a-zA-Z]*$".toRegex())))
            Pair(false, NON_ALPHABET_SF_MESSAGE)
        else
            Pair(true, SUCCESS_MESSAGE)
    }
}