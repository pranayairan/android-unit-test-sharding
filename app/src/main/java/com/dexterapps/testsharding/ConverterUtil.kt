package com.dexterapps.testsharding

object ConverterUtil {
    // converts to celsius
    fun convertFahrenheitToCelsius(fahrenheit: Float): Float {
        return (fahrenheit - 32) * 5 / 9
    }

    // converts to fahrenheit
    fun convertCelsiusToFahrenheit(celsius: Float): Float {
        return celsius * 9 / 5 + 32
    }
}