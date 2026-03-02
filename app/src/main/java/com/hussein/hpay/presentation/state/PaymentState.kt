package com.hussein.hpay.presentation.state

import com.hussein.hpay.domain.CreditCard

// 1. STATE: What the UI looks like
data class PaymentState(
    val isLoading: Boolean = false,
    val cards: List<CreditCard> = emptyList(),
    val selectedCardId: String? = null,
    val totalAmount: Double = 149.99 // Example transaction amount
)