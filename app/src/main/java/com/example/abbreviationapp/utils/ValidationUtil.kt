package com.example.abbreviationapp.utils

object ValidationUtil {
    fun isValid(abbreviation: String): Boolean {
        return abbreviation.isNotEmpty() && abbreviation.matches("^[a-zA-Z]*$".toRegex())
    }
}