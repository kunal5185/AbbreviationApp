package com.example.abbreviationapp.utils

import org.junit.Assert.*

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ValidationUtilTest {

    @Test
    fun validateAbbreviationTest() {
        val abbreviation = "HMM"
        assertEquals(true, ValidationUtil.isValid(abbreviation))
    }

    @Test
    fun validateEmptyAbbreviationTest() {
        val abbreviation = ""
        assertEquals(false, ValidationUtil.isValid(abbreviation))
    }

    @Test
    fun validateSpecialCharAbbreviationTest() {
        val abbreviation = "a@a"
        assertEquals(false, ValidationUtil.isValid(abbreviation))
    }
}