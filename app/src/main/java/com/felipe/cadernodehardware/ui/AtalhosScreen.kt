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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.felipe.cadernodehardware.ui.dados.buscarAtalhos

// Cores no estilo do VS Code
private val VsFundo = Color(0xFF1F1F1F)
private val VsLinhaPar = Color(0xFF242424)
private val VsBarra = Color(0xFF181818)
private val VsAzul = Color(0xFF007FD4)
private val VsTexto = Color(0xFFCCCCCC)
private val VsTecla = Color(0xFF3C3C3C)

private data class Atalho(val comando: String, val teclas: List<String>)

@Composable
fun AtalhosScreen(
    onVoltar: () -> Unit,
    onNavegar: (Tela) -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler { onVoltar() }

    var busca by remember { mutableStateOf("") }

    val filtrados = buscarAtalhos(busca)

    val estiloTexto = TextStyle(
        color = VsTexto,
        fontFamily = FontFamily.SansSerif,
        fontSize = 13.sp
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(VsFundo)
    ) {
        // Título
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .background(VsBarra)
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("⌨", color = VsTexto, fontSize = 16.sp)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Keyboard Shortcuts", style = estiloTexto.copy(color = Color.White))
        }

        // Campo de pesquisa
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 12.dp)
                .height(38.dp)
                .background(Color(0xFF313131), RoundedCornerShape(3.dp))
                .border(1.dp, VsAzul, RoundedCornerShape(3.dp))
                .padding(horizontal = 10.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            if (busca.isEmpty()) {
                Text(
                    "Type to search in keybindings",
                    style = estiloTexto.copy(color = Color(0xFF8C8C8C))
                )
            }
            BasicTextField(
                value = busca,
                onValueChange = { busca = it },
                singleLine = true,
                textStyle = estiloTexto.copy(color = Color.White, fontSize = 14.sp),
                cursorBrush = SolidColor(Color.White),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = {}),
                modifier = Modifier.fillMaxWidth()
            )
        }

        // Cabeçalho da tabela
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(VsLinhaPar)
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            Text(
                "Command",
                style = estiloTexto.copy(color = Color.White, fontWeight = FontWeight.Bold),
                modifier = Modifier.weight(1f)
            )
            Text(
                "Keybinding",
                style = estiloTexto.copy(color = Color.White, fontWeight = FontWeight.Bold),
                modifier = Modifier.weight(1f)
            )
        }

        // Lista
        LazyColumn(modifier = Modifier.weight(1f).fillMaxWidth()) {
            items(filtrados) { atalho ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = atalho.comando,
                        style = estiloTexto,
                        modifier = Modifier.weight(1f).padding(end = 8.dp)
                    )
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        atalho.teclas.forEachIndexed { i, tecla ->
                            if (i > 0) Text("+", style = estiloTexto)
                            Tecla(tecla)
                        }
                    }
                }
            }

            if (filtrados.isEmpty()) {
                item {
                    Text(
                        "Nenhum atalho encontrado.",
                        style = estiloTexto.copy(color = Color(0xFF8C8C8C)),
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
        BarraNavegacao(telaAtual = Tela.ATALHOS, onNavegar = onNavegar)
    }
}

@Composable
private fun Tecla(texto: String) {
    Box(
        modifier = Modifier
            .background(VsTecla, RoundedCornerShape(3.dp))
            .border(1.dp, Color(0xFF4A4A4A), RoundedCornerShape(3.dp))
            .padding(horizontal = 6.dp, vertical = 2.dp)
    ) {
        Text(
            text = texto,
            color = VsTexto,
            fontSize = 11.sp,
            fontFamily = FontFamily.Monospace
        )
    }
}