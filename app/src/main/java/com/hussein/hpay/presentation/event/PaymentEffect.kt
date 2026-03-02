package com.hussein.hpay.presentation.event

sealed class PaymentEffect {
    data class ShowToast(val message: String) : PaymentEffect()
    data object NavigateToSuccess : PaymentEffect()
}