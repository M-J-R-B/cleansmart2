package com.example.cleansmart.utils

object EmailValidator {
    private val EMAIL_PATTERN = Regex(
        "[a-zA-Z0-9+._%\\-]{1,256}" +
        "@" +
        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
        "(" +
        "\\." +
        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
        ")+"
    )

    fun isValid(email: String): Boolean {
        return email.matches(EMAIL_PATTERN)
    }
} 