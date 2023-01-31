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
        assertEquals(true, ValidationUtil.isValid(abbreviation).first)
    }

    @Test
    fun validateEmptyAbbreviationTest() {
        val abbreviation = ""
        assertEquals(false, ValidationUtil.isValid(abbreviation).first)
    }

    @Test
    fun validateSingleCharAbbreviationTest() {
        val abbreviation = "a"
        assertEquals(false, ValidationUtil.isValid(abbreviation).first)
    }

    @Test
    fun validateSpecialCharAbbreviationTest() {
        val abbreviation = "a@a"
        assertEquals(false, ValidationUtil.isValid(abbreviation).first)
    }
}