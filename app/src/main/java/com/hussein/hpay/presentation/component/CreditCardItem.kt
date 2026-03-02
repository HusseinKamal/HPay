package com.hussein.hpay.presentation.component


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hussein.hpay.domain.CreditCard

@Composable
fun CreditCardItem(
    card: CreditCard,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(220.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(card.backgroundBrush)
            .padding(24.dp)
    ) {
        // Card Chip
        Box(
            modifier = Modifier
                .size(width = 50.dp, height = 35.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(Color.White.copy(alpha = 0.3f))
                .align(Alignment.TopStart)
        )

        // Card Type (Visa/Mastercard)
        Text(
            text = card.cardType.name,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.align(Alignment.TopEnd)
        )

        // Card Number
        Text(
            text = card.cardNumber,
            color = Color.White,
            style = MaterialTheme.typography.headlineMedium,
            letterSpacing = 2.sp,
            modifier = Modifier.align(Alignment.CenterStart).padding(top = 16.dp)
        )

        // Bottom Info
        Row(
            modifier = Modifier.align(Alignment.BottomStart).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = "Card Holder", color = Color.White.copy(alpha = 0.7f), fontSize = 12.sp)
                Text(text = card.holderName, color = Color.White, fontWeight = FontWeight.SemiBold)
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(text = "Expires", color = Color.White.copy(alpha = 0.7f), fontSize = 12.sp)
                Text(text = card.expiryDate, color = Color.White, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}