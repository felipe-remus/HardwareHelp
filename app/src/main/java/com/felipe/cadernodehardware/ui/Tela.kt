package com.felipe.cadernodehardware.ui

enum class Tela(val comandos: List<String>) {
    TERMINAL(listOf("cmd")),
    ATALHOS(listOf("atalhos")),
    WIN_RUN(listOf("win run", "winrun")),
    TERMINAL_LINUX(listOf("terminal linux", "linux terminal", "linux"));

    companion object {
        fun deComando(texto: String): Tela? {
            val limpo = texto.trim().lowercase().replace(Regex("\\s+"), " ")
            return entries.firstOrNull { limpo in it.comandos }
        }
    }
}