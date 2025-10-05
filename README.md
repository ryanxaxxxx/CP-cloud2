🚀 Guia de Configuração e Execução da CP 2 Cloud

Este guia descreve o how to  necessário para realizar a implantação do projeto em Nuvem.

🧩 1. Executar o Script SQL Server no PowerShell

Abra o PowerShell na pasta onde está o arquivo create-sql-server.

Execute o comando abaixo para rodar o script no SQL Server:

sqlcmd -S <NOME_DO_SERVIDO> -U <USUARIO> -P <SENHA> -i .\script.sqlserver



💡 Substitua servidor: sqlserver-rm555924, usuario: admsql e senha: Fiap@2tdsvms .

\\\

🐧 2. Alterar para Bash e Executar o arquiv "deploy-movtodimdim.sh"

Após a execução do SQL Server, altere o terminal para Bash.

Execute o arquivo "deploy-movtodimdim.sh" responsável pelas configurações adicionais:

bash deploy-movtodimdim.sh


Certifique-se de que o arquivo .sh tem permissão de execução. Caso não tenha, rode:

chmod +x deploy-movtodimdim.sh

\\\

🧠 3. Criar Variáveis de Ambiente no GitHub

Vá até o repositório no GitHub.

Clique em Settings → Secrets and variables → Actions → New repository secret.

Crie as seguintes variáveis de ambiente (secrets):

Nome do Secret	Descrição
SPRING_DATASOURCE_URL = jdbc:sqlserver://sqlserver-rm555924.database.windows.net:1433;database=dimdimdb;user=admsql@sqlserver-rm555924;password={your_password_here};encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;

SPRING_DATASOURCE_USERNAME	Usuário do banco de dados "admsql"

SPRING_DATASOURCE_PASSWORD	Senha do banco de dados "Fiap@2tdsvms"

\\\

⚙️ 4. Configurar o Arquivo .yml do GitHub Actions

No arquivo de workflow (.github/workflows/main_movtodimdim-rm555924.yml), adicione o bloco de variáveis de ambiente logo abaixo do comando maven clean install.

Exemplo:

- name: Build com Maven
  run: mvn clean install

  env:
    SPRING_DATASOURCE_URL: ${{ secrets.SPRING_DATASOURCE_URL }}
    SPRING_DATASOURCE_USERNAME: ${{ secrets.SPRING_DATASOURCE_USERNAME }}
    SPRING_DATASOURCE_PASSWORD: ${{ secrets.SPRING_DATASOURCE_PASSWORD }}
  
\\\
  

✅ 5. Finalizando

Após seguir todos os passos:

Confirme que o pipeline executa corretamente no GitHub Actions.

Verifique se a aplicação Java está conseguindo se conectar ao banco de dados via as variáveis de ambiente configuradas.

Em caso de erro, revise os nomes das variáveis e o arquivo .yml.
