package com.dexterapps.testsharding

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.experimental.categories.Category

class ConverterUtilTest {

    @Test
    @Category(UnitTests::class)
    fun testConvertFahrenheitToCelsius() {
        val actual = ConverterUtil.convertCelsiusToFahrenheit(100F)
        // expected value is 212
        val expected = 212f
        // use this method because float is not precise
        assertEquals("Conversion from celsius to fahrenheit failed",
            expected.toDouble(), actual.toDouble(), 0.001)
    }

    @Test
    @Category(FastTests::class)
    fun testConvertCelsiusToFahrenheit() {
        val actual = ConverterUtil.convertFahrenheitToCelsius(212F)
        // expected value is 100
        val expected = 100f
        // use this method because float is not precise
        assertEquals("Conversion from celsius to fahrenheit failed",
            expected.toDouble(), actual.toDouble(), 0.001)
    }
}