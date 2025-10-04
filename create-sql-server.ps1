$RG = "rg-movtodimdim"
$LOCATION = "brazilsouth"
$SERVER_NAME = "sqlserver-rm555924"
$USERNAME = "admsql"
$PASSWORD = "Fiap@2tdsvms"
$DBNAME = "dimdimdb"

az group create --name $RG --location $LOCATION
az sql server create -l $LOCATION -g $RG -n $SERVER_NAME -u $USERNAME -p $PASSWORD --enable-public-network true
az sql db create -g $RG -s $SERVER_NAME -n $DBNAME --service-objective Basic --backup-storage-redundancy Local --zone-redundant false
az sql server firewall-rule create -g $RG -s $SERVER_NAME -n AllowAll --start-ip-address 0.0.0.0 --end-ip-address 255.255.255.255

# Cria os objetos de Banco
# Certifique-se de ter o sqlcmd instalado em seu ambiente
Invoke-Sqlcmd -ServerInstance "$SERVER_NAME.database.windows.net" `
              -Database "$DBNAME" `
              -Username "$USERNAME" `
              -Password "$PASSWORD" `
              -Query @"
CREATE TABLE [dbo].[categoria] (
    id BIGINT NOT NULL IDENTITY,
    nome VARCHAR(100) NOT NULL,
    descricao VARCHAR(255),
    PRIMARY KEY (id)
);

CREATE TABLE [dbo].[produto] (
    id BIGINT NOT NULL IDENTITY,
    nome VARCHAR(100) NOT NULL,
    preco DECIMAL(10,2) NOT NULL,
    descricao VARCHAR(255),
    categoria_id BIGINT NOT NULL,
    PRIMARY KEY (id)
);

ALTER TABLE [dbo].[produto] ADD CONSTRAINT FK_PRODUTO_CATEGORIA FOREIGN KEY (categoria_id) REFERENCES [dbo].[categoria] (id);
"@
