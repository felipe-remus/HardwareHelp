package com.felipe.cadernodehardware.ui.dados

// ============================================================
// LISTA DE COMANDOS DO TERMINAL LINUX
// Formato de cada linha:  "comando" to "explicação",
// ============================================================
private val explicacoesLinux: Map<String, String> = mapOf(
    "ls" to "Lista os arquivos e pastas da pasta atual.",
    "ls -l" to "Lista os arquivos com detalhes: permissões, dono, tamanho e data.",
    "ls -la" to "Lista todos os arquivos, inclusive os ocultos, com detalhes.",
    "cd" to "Muda de pasta. Exemplo: cd Documentos entra em Documentos; cd .. volta uma pasta.",
    "pwd" to "Mostra o caminho completo da pasta onde você está.",
    "mkdir" to "Cria uma pasta nova. Exemplo: mkdir MinhaPasta.",
    "rmdir" to "Apaga uma pasta vazia.",
    "rm" to "Apaga arquivos. Com -r apaga pastas inteiras. Cuidado: não vai para a lixeira.",
    "cp" to "Copia arquivos ou pastas. Exemplo: cp a.txt b.txt.",
    "mv" to "Move ou renomeia arquivos e pastas.",
    "touch" to "Cria um arquivo vazio ou atualiza a data de um arquivo existente.",
    "cat" to "Mostra o conteúdo de um arquivo na tela.",
    "nano" to "Abre um editor de texto simples dentro do terminal.",
    "echo" to "Mostra na tela o texto que você digitar depois dele.",
    "clear" to "Limpa a tela do terminal.",
    "man" to "Abre o manual de um comando. Exemplo: man ls.",
    "whoami" to "Mostra o nome do usuário atual.",
    "hostname" to "Mostra o nome do computador.",
    "uname" to "Mostra informações do sistema. Com -a mostra tudo (kernel, arquitetura...).",
    "date" to "Mostra a data e a hora atuais.",
    "df" to "Mostra o espaço usado e livre em cada disco. Com -h fica em formato legível.",
    "du" to "Mostra quanto espaço cada pasta ou arquivo ocupa.",
    "free" to "Mostra o uso da memória RAM. Com -h fica em formato legível.",
    "top" to "Mostra em tempo real os processos que mais usam CPU e memória.",
    "htop" to "Versão mais visual e interativa do top (pode precisar ser instalada).",
    "ps" to "Lista os processos em execução. Com aux mostra todos.",
    "kill" to "Encerra um processo pelo número dele (PID). Exemplo: kill 1234.",
    "chmod" to "Muda as permissões de um arquivo. Exemplo: chmod +x script.sh deixa executável.",
    "chown" to "Muda o dono de um arquivo ou pasta.",
    "grep" to "Procura um texto dentro de arquivos. Exemplo: grep erro log.txt.",
    "find" to "Procura arquivos e pastas pelo nome ou outras características.",
    "tree" to "Mostra a estrutura de pastas em forma de árvore.",
    "head" to "Mostra as primeiras linhas de um arquivo.",
    "tail" to "Mostra as últimas linhas de um arquivo. Com -f acompanha em tempo real.",
    "ln" to "Cria um link (atalho) para um arquivo. Com -s cria um link simbólico.",
    "tar" to "Cria ou extrai arquivos compactados .tar. Exemplo: tar -xvf arquivo.tar.",
    "wget" to "Baixa um arquivo da internet pelo terminal.",
    "curl" to "Faz requisições a endereços da internet e mostra a resposta.",
    "ping" to "Testa se o computador consegue se comunicar com outro endereço. Exemplo: ping google.com.",
    "ip a" to "Mostra as interfaces de rede e os endereços IP do computador.",
    "ifconfig" to "Mostra as configurações de rede (comando antigo, substituído pelo ip a).",
    "ssh" to "Conecta a outro computador de forma segura. Exemplo: ssh usuario@192.168.0.10.",
    "lsblk" to "Lista os discos e partições do computador.",
    "lsusb" to "Lista os dispositivos USB conectados.",
    "lspci" to "Lista os dispositivos PCI, como placa de vídeo e placa de rede.",
    "history" to "Mostra os últimos comandos que você digitou.",
    "systemctl" to "Controla os serviços do sistema. Exemplo: systemctl status ssh.",
    "apt update" to "Atualiza a lista de pacotes disponíveis (não instala nada). Precisa de sudo.",
    "apt upgrade" to "Atualiza os programas instalados para a versão mais nova. Precisa de sudo.",
    "apt install" to "Instala um programa. Exemplo: sudo apt install htop. Precisa de sudo.",
    "apt remove" to "Desinstala um programa. Precisa de sudo.",
    "apt" to "Gerenciador de pacotes: instala, remove e atualiza programas. Precisa de sudo.",
    "reboot" to "Reinicia o computador. Precisa de sudo.",
    "shutdown" to "Desliga o computador. Exemplo: sudo shutdown now. Precisa de sudo.",
    "sudo" to "Executa um comando com privilégios de administrador (root). Exemplo: sudo apt update.",
    "su" to "Troca para outro usuário; sem nome, troca para o root.",
    "exit" to "Fecha o terminal (ou sai do modo root, se você estiver nele)."
)

// Comandos que precisam de privilégios de administrador
private val precisamDeRoot = setOf(
    "apt", "apt-get", "reboot", "shutdown", "poweroff", "useradd", "userdel"
)

fun precisaDeRoot(texto: String): Boolean {
    val primeira = texto.trim().lowercase().substringBefore(" ")
    return primeira in precisamDeRoot
}

// Procura a explicação de um texto digitado.
// Devolve null se o comando não estiver na lista.
fun explicarComandoLinux(texto: String): String? {
    val limpo = texto.trim().lowercase().replace(Regex("\\s+"), " ")

    // 1º tenta o texto inteiro (ex.: "ls -la")
    explicacoesLinux[limpo]?.let { return it }

    // 2º tenta as duas primeiras palavras (ex.: "apt install htop" vira "apt install")
    val duas = limpo.split(" ").take(2).joinToString(" ")
    explicacoesLinux[duas]?.let { return it }

    // 3º tenta só a primeira palavra
    return explicacoesLinux[limpo.substringBefore(" ")]
}