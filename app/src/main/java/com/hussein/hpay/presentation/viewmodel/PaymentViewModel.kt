package com.hussein.hpay.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hussein.hpay.domain.mockCards
import com.hussein.hpay.presentation.event.PaymentEffect
import com.hussein.hpay.presentation.event.PaymentIntent
import com.hussein.hpay.presentation.state.PaymentState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(PaymentState())
    val state = _state.asStateFlow()

    private val _effect = Channel<PaymentEffect>()
    val effect = _effect.receiveAsFlow()

    init {
        handleIntent(PaymentIntent.LoadCards)
    }

    fun handleIntent(intent: PaymentIntent) {
        when (intent) {
            is PaymentIntent.LoadCards -> loadCards()
            is PaymentIntent.SelectCard -> {
                _state.update { it.copy(selectedCardId = intent.cardId) }
            }
            is PaymentIntent.ProcessPayment -> processPayment()
        }
    }

    private fun loadCards() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            delay(1000) // Simulate network delay
            _state.update {
                it.copy(
                    isLoading = false,
                    cards = mockCards,
                    selectedCardId = mockCards.firstOrNull()?.id
                )
            }
        }
    }

    private fun processPayment() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            delay(2000) // Simulate processing
            _state.update { it.copy(isLoading = false) }
            _effect.send(PaymentEffect.ShowToast("Payment Successful via ${_state.value.selectedCardId}"))
            _effect.send(PaymentEffect.NavigateToSuccess)
        }
    }
}