package com.felipe.cadernodehardware.ui.dados

// ============================================================
// LISTA DE COMANDOS DO CMD
// Formato de cada linha:  "comando" to "explicação",
// ============================================================
private val explicacoes: Map<String, String> = mapOf(
    "cd" to "Muda a pasta atual. Exemplo: cd Documentos entra na pasta Documentos",
    "cd .." to "Volta uma pasta.",
    "dir" to "Lista os arquivos e pastas que existem na pasta atual.",
    "cls" to "Limpa a tela do terminal.",
    "ipconfig" to "Mostra as configurações de rede do computador, como o endereço IP e o gateway.",
    "ipconfig /all" to "Mostra todos os detalhes da rede: IP, máscara, DNS, endereço MAC e mais.",
    "ping" to "Testa se o computador consegue se comunicar com outro endereço pela rede. Exemplo: ping google.com.",
    "tracert" to "Mostra o caminho (os saltos) que os dados percorrem até chegar a um endereço.",
    "netstat" to "Mostra as conexões de rede abertas e as portas em uso.",
    "systeminfo" to "Mostra informações do computador: versão do Windows, memória, processador e mais.",
    "tasklist" to "Lista todos os programas e processos que estão rodando no momento.",
    "taskkill" to "Encerra um processo que está rodando. Exemplo: taskkill /im notepad.exe.",
    "mkdir" to "Cria uma pasta nova. Exemplo: mkdir MinhaPasta.",
    "rmdir" to "Apaga uma pasta vazia. Com /s apaga a pasta e tudo que está dentro.",
    "del" to "Apaga um ou mais arquivos. Exemplo: del arquivo.txt.",
    "copy" to "Copia um arquivo para outro lugar. Exemplo: copy a.txt D:\\.",
    "move" to "Move um arquivo ou pasta para outro lugar.",
    "ren" to "Renomeia um arquivo ou pasta. Exemplo: ren antigo.txt novo.txt.",
    "type" to "Mostra o conteúdo de um arquivo de texto direto na tela.",
    "echo" to "Mostra na tela o texto que você digitar depois dele.",
    "chkdsk" to "Verifica o disco em busca de erros e tenta corrigir problemas.",
    "sfc /scannow" to "Verifica os arquivos do Windows e repara os que estiverem danificados.",
    "shutdown" to "Desliga, reinicia ou faz logoff do computador. Exemplo: shutdown /s /t 0 desliga na hora.",
    "hostname" to "Mostra o nome do seu computador na rede.",
    "whoami" to "Mostra o nome do usuário que está logado.",
    "ver" to "Mostra a versão do Windows.",
    "exit" to "Fecha o terminal."
)

// Procura a explicação de um texto digitado.
// Devolve null se o comando não estiver na lista.
fun explicarComandoCmd(texto: String): String? {
    val limpo = texto.trim().lowercase().replace(Regex("\\s+"), " ")

    // 1º tenta o texto inteiro (ex.: "ipconfig /all")
    explicacoes[limpo]?.let { return it }

    // 2º tenta só a primeira palavra (ex.: "ping google.com" vira "ping")
    val primeiraPalavra = limpo.substringBefore(" ")
    return explicacoes[primeiraPalavra]
}