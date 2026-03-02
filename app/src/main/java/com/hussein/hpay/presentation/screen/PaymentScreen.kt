package com.hussein.hpay.presentation.screen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import com.hussein.hpay.presentation.component.CreditCardItem
import com.hussein.hpay.presentation.event.PaymentIntent
import com.hussein.hpay.presentation.state.PaymentState
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PaymentScreen(
    state: PaymentState,
    onIntent: (PaymentIntent) -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color(0xFFF5F6FA)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header
            Spacer(modifier = Modifier.height(40.dp))
            Text(
                text = "Checkout",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Text(
                text = "$${state.totalAmount}",
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(vertical = 16.dp)
            )

            if (state.isLoading && state.cards.isEmpty()) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                Spacer(modifier = Modifier.height(20.dp))

                // --- ANIMATED CAROUSEL ---
                val pagerState = rememberPagerState(pageCount = { state.cards.size })

                // Sync pager selection with MVI state
                LaunchedEffect(pagerState.currentPage) {
                    val card = state.cards.getOrNull(pagerState.currentPage)
                    card?.let { onIntent(PaymentIntent.SelectCard(it.id)) }
                }

                HorizontalPager(
                    state = pagerState,
                    contentPadding = PaddingValues(horizontal = 40.dp),
                    modifier = Modifier.height(250.dp)
                ) { page ->
                    val card = state.cards[page]

                    // Animation Logic
                    val pageOffset = (
                            (pagerState.currentPage - page) + pagerState
                                .currentPageOffsetFraction
                            ).absoluteValue

                    // Animate Scale and Alpha
                    val scale by animateFloatAsState(
                        targetValue = lerp(0.85f, 1f, 1f - pageOffset.coerceIn(0f, 1f)),
                        label = "scale"
                    )
                    val alpha by animateFloatAsState(
                        targetValue = lerp(0.5f, 1f, 1f - pageOffset.coerceIn(0f, 1f)),
                        label = "alpha"
                    )

                    CreditCardItem(
                        card = card,
                        modifier = Modifier
                            .graphicsLayer {
                                scaleX = scale
                                scaleY = scale
                                this.alpha = alpha
                                // Optional: Add 3D rotation
                                rotationY = lerp(0f, 10f, (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction)
                            }
                    )
                }
                // -------------------------

                Spacer(modifier = Modifier.height(30.dp))

                // Payment Summary Section
                PaymentSummary(state.selectedCardId)

                Spacer(modifier = Modifier.weight(1f))

                // Pay Button
                Button(
                    onClick = { onIntent(PaymentIntent.ProcessPayment) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                        .height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    enabled = !state.isLoading
                ) {
                    if (state.isLoading) {
                        CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                    } else {
                        Text(text = "Pay Now", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
