package com.felipe.cadernodehardware.ui

import androidx.activity.compose.BackHandler
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Cores no estilo do terminal do Ubuntu
private val LinuxFundo = Color(0xFF300A24)
private val LinuxBarra = Color(0xFF2C2C2C)
private val LinuxTexto = Color(0xFFEEEEEE)
private val LinuxVerde = Color(0xFF8AE234)
private val LinuxAzul = Color(0xFF729FCF)
private val LinuxFechar = Color(0xFFE95420)

private const val USUARIO = "felipe@linux-desktop"
private const val PASTA = "~"

@Composable
fun TerminalLinuxScreen(
    onVoltar: () -> Unit,
    onNavegar: (Tela) -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler { onVoltar() }

    // Cada linha já guarda o texto pronto (com cores)
    val linhas = remember {
        mutableStateListOf<androidx.compose.ui.text.AnnotatedString>()
    }

    var comandoAtual by remember { mutableStateOf("") }
    val estadoLista = rememberLazyListState()

    LaunchedEffect(linhas.size) {
        estadoLista.animateScrollToItem(linhas.size)
    }

    val estiloTexto = TextStyle(
        color = LinuxTexto,
        fontFamily = FontFamily.Monospace,
        fontSize = 14.sp
    )

    // Monta o prompt colorido: usuario@maquina:~$
    fun promptColorido(comando: String) = buildAnnotatedString {
        withStyle(SpanStyle(color = LinuxVerde, fontWeight = FontWeight.Bold)) {
            append(USUARIO)
        }
        withStyle(SpanStyle(color = LinuxTexto)) { append(":") }
        withStyle(SpanStyle(color = LinuxAzul, fontWeight = FontWeight.Bold)) {
            append(PASTA)
        }
        withStyle(SpanStyle(color = LinuxTexto)) { append("$ $comando") }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(LinuxFundo)
    ) {
        // Barra de título
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(36.dp)
                .background(LinuxBarra),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "$USUARIO: $PASTA",
                style = estiloTexto.copy(fontFamily = FontFamily.SansSerif, fontSize = 13.sp)
            )
            Spacer(modifier = Modifier.weight(1f))
            Box(
                modifier = Modifier
                    .padding(end = 10.dp)
                    .size(22.dp)
                    .background(LinuxFechar, CircleShape)
                    .clickable { onVoltar() },
                contentAlignment = Alignment.Center
            ) {
                Text("✕", color = Color.White, fontSize = 12.sp)
            }
        }

        // Barra de menu (só enfeite)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(28.dp)
                .background(Color(0xFF3A3A3A))
                .padding(horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            listOf("File", "Edit", "View", "Search", "Terminal", "Help").forEach {
                Text(
                    text = it,
                    style = estiloTexto.copy(fontFamily = FontFamily.SansSerif, fontSize = 12.sp)
                )
            }
        }

        // Histórico + linha de digitação
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

            item {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = promptColorido(""),
                        style = estiloTexto
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    BasicTextField(
                        value = comandoAtual,
                        onValueChange = { comandoAtual = it },
                        textStyle = estiloTexto,
                        cursorBrush = SolidColor(LinuxTexto),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                        keyboardActions = KeyboardActions(
                            onSend = {
                                val tela = Tela.deComando(comandoAtual)
                                if (tela == Tela.TERMINAL_LINUX) {
                                    // Já está nesta tela: só limpa
                                    comandoAtual = ""
                                } else if (tela != null) {
                                    comandoAtual = ""
                                    onNavegar(tela)
                                } else if (comandoAtual.trim() == "clear") {
                                    linhas.clear()
                                    comandoAtual = ""
                                } else {
                                    linhas.add(promptColorido(comandoAtual))
                                    linhas.add(
                                        buildAnnotatedString { append("(resposta em breve)") }
                                    )
                                    comandoAtual = ""
                                }
                            }
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}