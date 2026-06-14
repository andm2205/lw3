package com.example.tipcalc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tipcalc.ui.theme.TipCalcTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TipCalcTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TipScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun TipScreen(modifier: Modifier = Modifier) {

    var orderAmountText by remember { mutableStateOf("") }
    var dishCountText   by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        Text(text = "Сумма заказа:")
        OutlinedTextField(
            value = orderAmountText,
            onValueChange = { orderAmountText = it },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            placeholder = { Text("0.00") }
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "Количество блюд:")
        OutlinedTextField(
            value = dishCountText,
            onValueChange = { dishCountText = it },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            placeholder = { Text("0") }
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TipScreenPreview() {
    TipCalcTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            TipScreen(modifier = Modifier.padding(innerPadding))
        }
    }
}