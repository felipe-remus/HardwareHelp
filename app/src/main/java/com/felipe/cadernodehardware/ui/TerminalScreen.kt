package com.felipe.cadernodehardware.ui

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.felipe.cadernodehardware.ui.dados.explicarComandoCmd

// Cores no estilo do Windows Terminal
private val CmdFundo = Color(0xFF0C0C0C)
private val CmdTexto = Color(0xFFCCCCCC)
private val CmdBarra = Color(0xFF202020)
private val CmdAba = Color(0xFF0C0C0C)
private val CmdFechar = Color(0xFFC42B1C)
private const val PROMPT = "C:\\Users\\Felipe Remus>"

@Composable
fun TerminalScreen(
    onNavegar: (Tela) -> Unit,
    onSair: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Linhas mostradas na tela
    val linhas = remember {
        mutableStateListOf(
            "Microsoft Windows [versão 10.0.26300.9974]",
            "(c) Microsoft Corporation. Todos os direitos reservados.",
            ""
        )
    }

    // Texto que está sendo digitado
    var comandoAtual by remember { mutableStateOf("") }

    val estadoLista = rememberLazyListState()

    // Rola para o fim quando entra linha nova
    LaunchedEffect(linhas.size) {
        estadoLista.animateScrollToItem(linhas.size)
    }

    val estiloTexto = TextStyle(
        color = CmdTexto,
        fontFamily = FontFamily.Monospace,
        fontSize = 14.sp
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(CmdFundo)
    ) {
        // Barra de título estilo Windows Terminal
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .background(CmdBarra),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Aba "Prompt de Comando"
            Row(
                modifier = Modifier
                    .padding(start = 8.dp, top = 6.dp)
                    .height(34.dp)
                    .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                    .background(CmdAba)
                    .padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "C:\\_",
                    style = estiloTexto.copy(fontSize = 11.sp, fontWeight = FontWeight.Bold)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Prompt de Comando",
                    style = estiloTexto.copy(
                        fontFamily = FontFamily.SansSerif,
                        color = Color.White,
                        fontSize = 13.sp
                    )
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // Minimizar (só enfeite)
            Box(
                modifier = Modifier
                    .width(44.dp)
                    .height(40.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("—", style = estiloTexto.copy(color = Color.White))
            }

            // Maximizar (só enfeite)
            Box(
                modifier = Modifier
                    .width(44.dp)
                    .height(40.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("□", style = estiloTexto.copy(color = Color.White))
            }

            // Fechar (funciona)
            Box(
                modifier = Modifier
                    .width(48.dp)
                    .height(40.dp)
                    .background(CmdFechar)
                    .clickable { onSair() },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "X",
                    style = estiloTexto.copy(
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }

        // Área com o histórico e a linha de digitação
        LazyColumn(
            state = estadoLista,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            items(linhas) { linha ->
                Text(text = linha, style = estiloTexto)
            }

            // Linha de digitação (fica no final da lista)
            item {
                Row {
                    Text(text = PROMPT, style = estiloTexto)
                    BasicTextField(
                        value = comandoAtual,
                        onValueChange = { comandoAtual = it },
                        textStyle = estiloTexto,
                        cursorBrush = SolidColor(CmdTexto),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                        keyboardActions = KeyboardActions(
                            onSend = {
                                val digitado = comandoAtual.trim()

                                if (digitado.lowercase() == "cls") {
                                    // cls limpa a tela, como no CMD de verdade
                                    linhas.clear()
                                } else {
                                    // Mostra o que foi digitado
                                    linhas.add("$PROMPT$comandoAtual")

                                    if (digitado.isNotEmpty()) {
                                        val explicacao = explicarComandoCmd(digitado)
                                        if (explicacao != null) {
                                            linhas.add(explicacao)
                                        } else {
                                            val nome = digitado.substringBefore(" ")
                                            linhas.add("'$nome' não é reconhecido como um comando interno ou externo, um programa operável ou um arquivo em lotes.")
                                        }
                                        linhas.add("")
                                    }
                                }
                                comandoAtual = ""
                            }
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
        BarraNavegacao(telaAtual = Tela.TERMINAL, onNavegar = onNavegar)
    }
}