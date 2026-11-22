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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.felipe.cadernodehardware.ui.dados.explicarComandoLinux
import com.felipe.cadernodehardware.ui.dados.precisaDeRoot

// Cores no estilo do terminal do Ubuntu
private val LinuxFundo = Color(0xFF300A24)
private val LinuxBarra = Color(0xFF2C2C2C)
private val LinuxTexto = Color(0xFFEEEEEE)
private val LinuxVerde = Color(0xFF8AE234)
private val LinuxAzul = Color(0xFF729FCF)
private val LinuxFechar = Color(0xFFE95420)

private const val MAQUINA = "linux-desktop"
private const val USUARIO_NORMAL = "felipe"
private const val PASTA = "~"

@Composable
fun TerminalLinuxScreen(
    onVoltar: () -> Unit,
    onNavegar: (Tela) -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler { onVoltar() }

    val linhas = remember { mutableStateListOf<AnnotatedString>() }
    var comandoAtual by remember { mutableStateOf("") }

    // true = modo administrador (prompt com #)
    var modoRoot by remember { mutableStateOf(false) }

    val estadoLista = rememberLazyListState()

    LaunchedEffect(linhas.size) {
        estadoLista.animateScrollToItem(linhas.size)
    }

    val estiloTexto = TextStyle(
        color = LinuxTexto,
        fontFamily = FontFamily.Monospace,
        fontSize = 14.sp
    )

    // Monta o prompt colorido. Normal: felipe@linux-desktop:~$   Root: root@linux-desktop:~#
    fun promptColorido(comando: String, root: Boolean) = buildAnnotatedString {
        val usuario = if (root) "root" else USUARIO_NORMAL
        val simbolo = if (root) "#" else "$"
        withStyle(SpanStyle(color = LinuxVerde, fontWeight = FontWeight.Bold)) {
            append("$usuario@$MAQUINA")
        }
        withStyle(SpanStyle(color = LinuxTexto)) { append(":") }
        withStyle(SpanStyle(color = LinuxAzul, fontWeight = FontWeight.Bold)) {
            append(PASTA)
        }
        withStyle(SpanStyle(color = LinuxTexto)) { append("$simbolo $comando") }
    }

    fun texto(t: String) = buildAnnotatedString { append(t) }

    // O que acontece ao apertar Enter
    fun executar() {
        val digitado = comandoAtual.trim()
        val limpo = digitado.lowercase().replace(Regex("\\s+"), " ")

        // Linha do comando usa o prompt de ANTES da execução
        val linhaDoComando = promptColorido(comandoAtual, modoRoot)

        if (limpo == "clear") {
            linhas.clear()
        } else if (digitado.isEmpty()) {
            linhas.add(linhaDoComando)
        } else if (modoRoot && (limpo == "exit" || limpo == "logout")) {
            // Sai do modo administrador
            linhas.add(linhaDoComando)
            linhas.add(texto("exit"))
            modoRoot = false
        } else if (limpo in listOf("su", "su -", "su root", "sudo su", "sudo su -", "sudo -i", "sudo -s", "sudo bash")) {
            // Entra no modo administrador
            linhas.add(linhaDoComando)
            if (!modoRoot) {
                if (limpo.startsWith("sudo")) linhas.add(texto("[sudo] senha para $USUARIO_NORMAL: "))
                else linhas.add(texto("Senha: "))
                linhas.add(texto("Agora você é root: o prompt mostra # no lugar de \$."))
            } else {
                linhas.add(texto("Você já é root."))
            }
            modoRoot = true
        } else if (limpo == "sudo") {
            linhas.add(linhaDoComando)
            linhas.add(texto(explicarComandoLinux("sudo") ?: ""))
        } else if (limpo.startsWith("sudo ")) {
            // sudo + outro comando: roda como administrador, mas o prompt continua normal
            val resto = digitado.substringAfter(" ").trim()
            linhas.add(linhaDoComando)
            if (!modoRoot) linhas.add(texto("[sudo] senha para $USUARIO_NORMAL: "))
            val explicacao = explicarComandoLinux(resto)
            if (explicacao != null) {
                linhas.add(texto("(executado como administrador) $explicacao"))
            } else {
                linhas.add(texto("sudo: ${resto.substringBefore(" ")}: comando não encontrado"))
            }
        } else if (!modoRoot && precisaDeRoot(digitado)) {
            // Comando que exige privilégios, sem sudo
            linhas.add(linhaDoComando)
            linhas.add(texto("Permissão negada. Este comando precisa de privilégios: tente com sudo."))
        } else {
            linhas.add(linhaDoComando)
            val explicacao = explicarComandoLinux(digitado)
            if (explicacao != null) {
                linhas.add(texto(explicacao))
            } else {
                linhas.add(texto("${digitado.substringBefore(" ")}: comando não encontrado"))
            }
        }
        comandoAtual = ""
    }

    val usuarioAtual = if (modoRoot) "root" else USUARIO_NORMAL

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
                text = "$usuarioAtual@$MAQUINA: $PASTA",
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
                    Text(text = promptColorido("", modoRoot), style = estiloTexto)
                    Spacer(modifier = Modifier.width(6.dp))
                    BasicTextField(
                        value = comandoAtual,
                        onValueChange = { comandoAtual = it },
                        textStyle = estiloTexto,
                        cursorBrush = SolidColor(LinuxTexto),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                        keyboardActions = KeyboardActions(onSend = { executar() }),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        BarraNavegacao(telaAtual = Tela.TERMINAL_LINUX, onNavegar = onNavegar)
    }
}