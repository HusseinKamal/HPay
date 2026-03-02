package com.hussein.hpay.domain

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

data class CreditCard(
    val id: String,
    val holderName: String,
    val balance: Double,
    val cardNumber: String, // Masked usually
    val expiryDate: String,
    val cardType: CardType,
    val backgroundBrush: Brush
)

// Mock Data for the UI
val mockCards = listOf(
    CreditCard("1", "John Doe", 5430.50, "**** **** **** 1234", "12/26", CardType.VISA,
        Brush.linearGradient(listOf(Color(0xFF2C3E50), Color(0xFF4CA1AF)))),
    CreditCard("2", "John Doe", 1200.00, "**** **** **** 5678", "09/25", CardType.MASTERCARD,
        Brush.linearGradient(listOf(Color(0xFF1A2980), Color(0xFF26D0CE)))),
    CreditCard("3", "John Doe", 850.25, "**** **** **** 9012", "01/28", CardType.VISA,
        Brush.linearGradient(listOf(Color(0xFFDD2476), Color(0xFFFF512F))))
)