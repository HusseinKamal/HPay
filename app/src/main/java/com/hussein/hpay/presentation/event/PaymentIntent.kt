package com.hussein.hpay.presentation.event

sealed class PaymentIntent {
    data object LoadCards : PaymentIntent()
    data class SelectCard(val cardId: String) : PaymentIntent()
    data object ProcessPayment : PaymentIntent()
}