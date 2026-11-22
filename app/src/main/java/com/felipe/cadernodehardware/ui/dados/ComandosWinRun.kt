package com.felipe.cadernodehardware.ui.dados

// ============================================================
// LISTA DE COMANDOS DO WIN + R (Executar)
// Formato de cada linha:  "comando" to "explicação",
// ============================================================
private val explicacoesWinRun: Map<String, String> = mapOf(
    "cmd" to "Abre o Prompt de Comando.",
    "powershell" to "Abre o PowerShell, um terminal mais avançado que o CMD.",
    "notepad" to "Abre o Bloco de Notas.",
    "calc" to "Abre a Calculadora.",
    "mspaint" to "Abre o Paint.",
    "explorer" to "Abre o Explorador de Arquivos.",
    "control" to "Abre o Painel de Controle.",
    "taskmgr" to "Abre o Gerenciador de Tarefas.",
    "regedit" to "Abre o Editor do Registro. Cuidado: alterar o registro errado pode danificar o Windows.",
    "msconfig" to "Abre a Configuração do Sistema, onde você controla a inicialização do Windows.",
    "services.msc" to "Abre a lista de serviços do Windows, onde você pode iniciar e parar serviços.",
    "devmgmt.msc" to "Abre o Gerenciador de Dispositivos, onde ficam os drivers e o hardware.",
    "diskmgmt.msc" to "Abre o Gerenciamento de Disco, para ver e organizar discos e partições.",
    "compmgmt.msc" to "Abre o Gerenciamento do Computador.",
    "eventvwr" to "Abre o Visualizador de Eventos, com o histórico de erros e avisos do sistema.",
    "appwiz.cpl" to "Abre a lista de programas instalados, para desinstalar ou alterar.",
    "ncpa.cpl" to "Abre as conexões de rede (adaptadores).",
    "sysdm.cpl" to "Abre as Propriedades do Sistema.",
    "desk.cpl" to "Abre as configurações de vídeo e resolução.",
    "mmsys.cpl" to "Abre as configurações de som.",
    "firewall.cpl" to "Abre o Firewall do Windows.",
    "winver" to "Mostra a versão do Windows instalada.",
    "msinfo32" to "Abre as Informações do Sistema, com detalhes de hardware e software.",
    "dxdiag" to "Abre o diagnóstico do DirectX, com informações de vídeo e som.",
    "resmon" to "Abre o Monitor de Recursos (uso de CPU, memória, disco e rede).",
    "perfmon" to "Abre o Monitor de Desempenho.",
    "cleanmgr" to "Abre a Limpeza de Disco, para apagar arquivos temporários.",
    "mstsc" to "Abre a Conexão de Área de Trabalho Remota.",
    "snippingtool" to "Abre a Ferramenta de Captura.",
    "temp" to "Abre a pasta de arquivos temporários do usuário.",
    "%temp%" to "Abre a pasta de arquivos temporários do usuário.",
    "shell:startup" to "Abre a pasta dos programas que iniciam junto com o Windows.",
    "." to "Abre a pasta do usuário atual."
)

// Procura a explicação de um texto digitado no Executar.
// Devolve null se o comando não estiver na lista.
fun explicarComandoWinRun(texto: String): String? {
    val limpo = texto.trim().lowercase().replace(Regex("\\s+"), " ")
    explicacoesWinRun[limpo]?.let { return it }
    // aceita também com .exe no final (ex.: notepad.exe)
    return explicacoesWinRun[limpo.removeSuffix(".exe")]
}