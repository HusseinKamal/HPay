package com.hussein.hpay

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.hussein.hpay.presentation.screen.PaymentRoute
import com.hussein.hpay.ui.theme.HPayTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HPayTheme {
                PaymentRoute()
            }
        }
    }
    @Preview(showBackground = true)
    @Composable
    fun PaymentPreview() {
        HPayTheme {
            PaymentRoute()
        }
    }
}