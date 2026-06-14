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

fun discountForDishes(dishes: Int): Int = when {
    dishes <= 0  -> 0
    dishes <= 2  -> 3
    dishes <= 5  -> 5
    dishes <= 10 -> 7
    else         -> 10
}

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

        var tipPercent by remember { mutableFloatStateOf(0f) }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Чаевые:")
        Slider(
            value = tipPercent,
            onValueChange = { tipPercent = it },
            valueRange = 0f..25f,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "0")
            Text(text = tipPercent.toInt().toString() + "%")
            Text(text = "25")
        }

        val dishCount = dishCountText.toIntOrNull() ?: 0
        val discount  = discountForDishes(dishCount)
        val discountOptions = listOf(3, 5, 7, 10)

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Скидка:")
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            discountOptions.forEach { option ->
                RadioButton(
                    selected = (discount == option),
                    onClick = null
                )
                Text(
                    text = "$option%",
                    modifier = Modifier.padding(end = 12.dp)
                )
            }
        }

        val orderAmount    = orderAmountText.toDoubleOrNull() ?: 0.0
        val tipAmount      = orderAmount * tipPercent / 100.0
        val discountAmount = orderAmount * discount / 100.0
        val total          = orderAmount + tipAmount - discountAmount

        Spacer(modifier = Modifier.height(24.dp))
        HorizontalDivider()
        Spacer(modifier = Modifier.height(12.dp))

        ResultRow(label = "Чаевые:",  value = "%.2f".format(tipAmount))
        ResultRow(label = "Скидка:",  value = "-%.2f".format(discountAmount))
        ResultRow(label = "Итого:",   value = "%.2f".format(total))
    }
}

@Composable
fun ResultRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, style = MaterialTheme.typography.bodyLarge)
        Text(text = value, style = MaterialTheme.typography.bodyLarge)
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