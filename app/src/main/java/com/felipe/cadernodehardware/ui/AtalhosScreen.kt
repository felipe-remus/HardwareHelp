package com.felipe.cadernodehardware.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AtalhosScreen(onVoltar: () -> Unit, modifier: Modifier = Modifier) {
    BackHandler { onVoltar() }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF0C0C0C))
            .padding(16.dp)
    ) {
        Text("Atalhos", color = Color.White, fontSize = 24.sp)
        Text("(conteúdo em breve)", color = Color.Gray)
        Button(onClick = onVoltar) { Text("Voltar") }
    }
}