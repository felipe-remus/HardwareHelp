package com.felipe.cadernodehardware.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BarraNavegacao(
    telaAtual: Tela,
    onNavegar: (Tela) -> Unit,
    modifier: Modifier = Modifier
) {
    val botoes = listOf(
        "Início" to Tela.INICIO,
        "CMD" to Tela.TERMINAL,
        "Win Run" to Tela.WIN_RUN,
        "Linux" to Tela.TERMINAL_LINUX,
        "Atalhos" to Tela.ATALHOS
    ).filter { it.second != telaAtual }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xFF181818))
            .padding(horizontal = 12.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        botoes.forEach { (nome, tela) ->
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(40.dp)
                    .background(Color(0xFF2D2D2D), RoundedCornerShape(4.dp))
                    .border(1.dp, Color(0xFF555555), RoundedCornerShape(4.dp))
                    .clickable { onNavegar(tela) },
                contentAlignment = Alignment.Center
            ) {
                Text(nome, color = Color.White, fontSize = 14.sp)
            }
        }
    }
}