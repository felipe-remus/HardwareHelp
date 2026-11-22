package com.felipe.cadernodehardware.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun InicioScreen(
    onNavegar: (Tela) -> Unit,
    onSair: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF0C0C0C))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "C:\\_",
            color = Color(0xFFCCCCCC),
            fontSize = 40.sp,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Caderno de Hardware",
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Escolha uma ferramenta",
            color = Color(0xFF8C8C8C),
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(32.dp))

        BotaoInicio("CMD", "Prompt de Comando do Windows", Color(0xFF3A3A3A)) {
            onNavegar(Tela.TERMINAL)
        }
        Spacer(modifier = Modifier.height(12.dp))
        BotaoInicio("Win Run", "Janela Executar (Win + R)", Color(0xFF0067C0)) {
            onNavegar(Tela.WIN_RUN)
        }
        Spacer(modifier = Modifier.height(12.dp))
        BotaoInicio("Terminal Linux", "Prompt de Comando do Linux", Color(0xFF77216F)) {
            onNavegar(Tela.TERMINAL_LINUX)
        }
        Spacer(modifier = Modifier.height(12.dp))
        BotaoInicio("Atalhos", "Atalhos de teclado", Color(0xFF007FD4)) {
            onNavegar(Tela.ATALHOS)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Sair",
            color = Color(0xFFC42B1C),
            fontSize = 14.sp,
            modifier = Modifier
                .clickable { onSair() }
                .padding(8.dp)
        )
    }
}

@Composable
private fun BotaoInicio(
    titulo: String,
    descricao: String,
    cor: Color,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(cor, RoundedCornerShape(8.dp))
            .border(1.dp, Color(0x33FFFFFF), RoundedCornerShape(8.dp))
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 14.dp)
    ) {
        Text(titulo, color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Text(descricao, color = Color(0xCCFFFFFF), fontSize = 12.sp)
    }
}