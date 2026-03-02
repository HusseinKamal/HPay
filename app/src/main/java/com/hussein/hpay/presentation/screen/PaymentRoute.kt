package com.hussein.hpay.presentation.screen

import android.widget.Toast
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.hussein.hpay.presentation.event.PaymentEffect
import com.hussein.hpay.presentation.state.PaymentState
import com.hussein.hpay.presentation.viewmodel.PaymentViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlin.math.absoluteValue

// 1. Entry Point (State Hoisting)
@Composable
fun PaymentRoute(
    viewModel: PaymentViewModel = hiltViewModel<PaymentViewModel>()
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    // Handle Side Effects
    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest { effect ->
            when(effect) {
                is PaymentEffect.ShowToast -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }
                PaymentEffect.NavigateToSuccess -> {
                    // Navigate logic here
                }
            }
        }
    }
    PaymentScreen(
        state = state,
        onIntent = viewModel::handleIntent
    )
}