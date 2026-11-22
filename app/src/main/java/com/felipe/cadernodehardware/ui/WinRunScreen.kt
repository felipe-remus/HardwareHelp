package com.felipe.cadernodehardware.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.felipe.cadernodehardware.ui.dados.explicarComandoWinRun

private val WinJanela = Color(0xFFF3F3F3)
private val WinTopo = Color(0xFFFFFFFF)
private val WinTextoCinza = Color(0xFF6B6B6B)

@Composable
fun WinRunScreen(
    onVoltar: () -> Unit,
    onNavegar: (Tela) -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler { onVoltar() }

    var texto by remember { mutableStateOf("") }
    var erro by remember { mutableStateOf(false) }
    var resultado by remember { mutableStateOf<String?>(null) }

    fun executar() {
        if (texto.isBlank()) return
        val explicacao = explicarComandoWinRun(texto)
        if (explicacao != null) {
            resultado = explicacao
            erro = false
        } else {
            resultado = null
            erro = true
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(WinJanela)
    ) {
        // Barra de título
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .background(WinTopo),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(12.dp))
            Box(
                modifier = Modifier
                    .size(16.dp)
                    .background(Color(0xFF3AA0E8), RoundedCornerShape(3.dp))
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text("Executar", fontSize = 13.sp, color = WinTextoCinza)
            Spacer(modifier = Modifier.weight(1f))
            Box(
                modifier = Modifier
                    .width(52.dp)
                    .height(40.dp)
                    .clickable { onVoltar() },
                contentAlignment = Alignment.Center
            ) {
                Text("✕", fontSize = 14.sp, color = Color.Black)
            }
        }

        // Corpo
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(WinTopo)
                .padding(horizontal = 20.dp, vertical = 24.dp)
        ) {
            Row(verticalAlignment = Alignment.Top) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color(0xFF3AA0E8), RoundedCornerShape(6.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text("▶", color = Color.White, fontSize = 18.sp)
                }
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "Digite o nome de um programa, pasta, documento ou recurso da Internet e o Windows o abrirá para você.",
                    fontSize = 14.sp,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Abrir:", fontSize = 14.sp, color = Color.Black)
                Spacer(modifier = Modifier.width(12.dp))

                // Campo de digitação
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .height(40.dp)
                        .background(Color.White, RoundedCornerShape(4.dp))
                        .border(
                            1.dp,
                            if (erro) Color(0xFFC42B1C) else Color(0xFF8A8A8A),
                            RoundedCornerShape(4.dp)
                        )
                        .padding(horizontal = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BasicTextField(
                        value = texto,
                        onValueChange = {
                            texto = it
                            erro = false
                            resultado = null
                        },
                        singleLine = true,
                        textStyle = TextStyle(color = Color.Black, fontSize = 14.sp),
                        cursorBrush = SolidColor(Color.Black),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Go),
                        keyboardActions = KeyboardActions(onGo = { executar() }),
                        modifier = Modifier.weight(1f)
                    )
                    Text("⌄", fontSize = 16.sp, color = WinTextoCinza)
                }
            }

            if (erro) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "O Windows não pode encontrar '${texto.trim()}'. Verifique se você digitou o nome corretamente e tente novamente.",
                    fontSize = 12.sp,
                    color = Color(0xFFC42B1C)
                )
            }
            resultado?.let {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = it,
                    fontSize = 14.sp,
                    color = Color(0xFF0067C0)
                )
            }
        }

        // Rodapé com botões
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End)
        ) {
            BotaoWin("OK", ativo = texto.isNotBlank(), onClick = { executar() })
            BotaoWin("Cancelar", onClick = onVoltar)
            BotaoWin("Procurar...")
        }
        Spacer(modifier = Modifier.weight(1f))
        BarraNavegacao(telaAtual = Tela.WIN_RUN, onNavegar = onNavegar)
    }
}

@Composable
private fun BotaoWin(
    texto: String,
    ativo: Boolean = true,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .height(36.dp)
            .widthIn(min = 90.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(Color(0xFFFDFDFD))
            .border(1.dp, Color(0xFFD0D0D0), RoundedCornerShape(4.dp))
            .clickable(enabled = ativo) { onClick() }
            .padding(horizontal = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = texto,
            fontSize = 14.sp,
            color = if (ativo) Color.Black else Color(0xFFA0A0A0)
        )
    }
}