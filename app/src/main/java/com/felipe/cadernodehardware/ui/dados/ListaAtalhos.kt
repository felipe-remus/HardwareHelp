package com.felipe.cadernodehardware.ui.dados

// Um atalho: nome do comando + teclas que devem ser apertadas
data class Atalho(val comando: String, val teclas: List<String>)

// ============================================================
// LISTA DE ATALHOS
// Formato de cada linha:  Atalho("Nome do comando", listOf("Tecla1", "Tecla2")),
// ============================================================
private val todosAtalhos: List<Atalho> = listOf(
    // Edição
    Atalho("Copiar", listOf("Ctrl", "C")),
    Atalho("Colar", listOf("Ctrl", "V")),
    Atalho("Recortar", listOf("Ctrl", "X")),
    Atalho("Desfazer", listOf("Ctrl", "Z")),
    Atalho("Refazer", listOf("Ctrl", "Y")),
    Atalho("Selecionar tudo", listOf("Ctrl", "A")),
    Atalho("Salvar", listOf("Ctrl", "S")),
    Atalho("Localizar", listOf("Ctrl", "F")),

    // Windows
    Atalho("Abrir o Executar", listOf("Win", "R")),
    Atalho("Abrir o Explorador de Arquivos", listOf("Win", "E")),
    Atalho("Abrir Configurações", listOf("Win", "I")),
    Atalho("Bloquear o computador", listOf("Win", "L")),
    Atalho("Mostrar a área de trabalho", listOf("Win", "D")),
    Atalho("Captura de tela", listOf("Win", "Shift", "S")),
    Atalho("Gerenciador de Tarefas", listOf("Ctrl", "Shift", "Esc")),
    Atalho("Alternar entre janelas", listOf("Alt", "Tab")),
    Atalho("Fechar janela", listOf("Alt", "F4")),

    // Editor de código (VS Code)
    Atalho("Add Cursor Above", listOf("Ctrl", "Alt", "UpArrow")),
    Atalho("Add Cursor Below", listOf("Ctrl", "Alt", "DownArrow")),
    Atalho("Add Selection to Next Find Match", listOf("Ctrl", "D"))
)

// Procura atalhos pelo nome do comando ou pelas teclas.
// Texto vazio devolve a lista inteira.
// Aceita "ctrl c", "ctrl+c" ou só "copiar".
fun buscarAtalhos(texto: String): List<Atalho> {
    val termo = texto.trim().lowercase().replace(Regex("\\s+"), " ")
    if (termo.isEmpty()) return todosAtalhos

    return todosAtalhos.filter {
        val teclasComMais = it.teclas.joinToString("+").lowercase()
        val teclasComEspaco = it.teclas.joinToString(" ").lowercase()
        it.comando.lowercase().contains(termo) ||
                teclasComMais.contains(termo) ||
                teclasComEspaco.contains(termo)
    }
}