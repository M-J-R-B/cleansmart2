package com.example.cleansmart.utils

object PasswordValidator {
    private const val MIN_PASSWORD_LENGTH = 8
    private val PASSWORD_PATTERN = Regex(
        "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{$MIN_PASSWORD_LENGTH,}$"
    )

    fun isValid(password: String): Boolean {
        return password.matches(PASSWORD_PATTERN)
    }

    fun calculateStrength(password: String): Int {
        if (password.isEmpty()) return 0
        
        var strength = 0
        
        // Length contribution (up to 25 points)
        strength += (password.length * 2).coerceAtMost(25)
        
        // Character variety contribution
        if (password.any { it.isDigit() }) strength += 15
        if (password.any { it.isLowerCase() }) strength += 15
        if (password.any { it.isUpperCase() }) strength += 15
        if (password.any { it in "@#$%^&+=_" }) strength += 15
        
        // Bonus for combination of requirements
        if (strength > 50) strength += 15
        
        return strength.coerceIn(0, 100)
    }

    fun getStrengthMessage(strength: Int): String {
        return when {
            strength < 30 -> "Weak Password"
            strength < 60 -> "Moderate Password"
            strength < 80 -> "Strong Password"
            else -> "Very Strong Password"
        }
    }

    fun getImprovementSuggestions(password: String): List<String> {
        val suggestions = mutableListOf<String>()
        
        if (password.length < MIN_PASSWORD_LENGTH) {
            suggestions.add("Use at least $MIN_PASSWORD_LENGTH characters")
        }
        if (!password.any { it.isDigit() }) {
            suggestions.add("Add numbers")
        }
        if (!password.any { it.isLowerCase() }) {
            suggestions.add("Add lowercase letters")
        }
        if (!password.any { it.isUpperCase() }) {
            suggestions.add("Add uppercase letters")
        }
        if (!password.any { it in "@#$%^&+=_" }) {
            suggestions.add("Add special characters")
        }
        
        return suggestions
    }

    fun passwordsMatch(password: String, confirmPassword: String): Boolean {
        return password == confirmPassword
    }

    fun meetsMinimumRequirements(password: String): Boolean {
        return password.length >= MIN_PASSWORD_LENGTH &&
                password.any { it.isDigit() } &&
                password.any { it.isLowerCase() } &&
                password.any { it.isUpperCase() } &&
                password.any { it in "@#$%^&+=_" }
    }
} 