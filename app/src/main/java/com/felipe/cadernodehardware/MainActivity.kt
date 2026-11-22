package com.felipe.cadernodehardware

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.felipe.cadernodehardware.ui.AtalhosScreen
import com.felipe.cadernodehardware.ui.Tela
import com.felipe.cadernodehardware.ui.TerminalLinuxScreen
import com.felipe.cadernodehardware.ui.TerminalScreen
import com.felipe.cadernodehardware.ui.WinRunScreen
import com.felipe.cadernodehardware.ui.theme.CadernoDeHardwareTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CadernoDeHardwareTheme {
                var telaAtual by remember { mutableStateOf(Tela.TERMINAL) }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val mod = Modifier
                        .padding(innerPadding)
                        .imePadding()
                    val voltar = { telaAtual = Tela.TERMINAL }

                    when (telaAtual) {
                        Tela.TERMINAL -> TerminalScreen(
                            onNavegar = { telaAtual = it },
                            onSair = { finish() },
                            modifier = mod
                        )
                        Tela.ATALHOS -> AtalhosScreen(onVoltar = voltar, modifier = mod)
                        Tela.WIN_RUN -> WinRunScreen(
                            onVoltar = voltar,
                            onNavegar = { telaAtual = it },
                            modifier = mod
                        )
                        Tela.TERMINAL_LINUX -> TerminalLinuxScreen(onVoltar = voltar, modifier = mod)
                    }
                }
            }
        }
    }
}