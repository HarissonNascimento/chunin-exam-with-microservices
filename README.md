## 💬O que há neste documento
* [Proposta inicial](https://github.com/HarissonNascimento/chunin-exam-with-microservices#proposta-inicial)
* [Arquitetura](https://github.com/HarissonNascimento/chunin-exam-with-microservices#arquitetura)
* [Requisitos](https://github.com/HarissonNascimento/chunin-exam-with-microservices#requisitos)
* [Executando o projeto](https://github.com/HarissonNascimento/chunin-exam-with-microservices#executando-o-projeto)

## 📖Proposta inicial
A proposta inicial deste projeto é refatorar o projeto [exame-chunin](https://github.com/HarissonNascimento/exame-chunin)
 e separar o front-end do back-end utilizando as tecnologias de modularização e microsserviços.
 
## 📋Arquitetura
Clique [aqui](https://youtu.be/O7oIAppBWsM) e assista um vídeo explicando como foi organizada a arquitetura do projeto.

|        discovery        	|      auth      	|          spring-backend         	|             gateway             	|                        jsf-frontend                       	|
|:-----------------------:	|:--------------:	|:-------------------------------:	|:-------------------------------:	|:---------------------------------------------------------:	|
| Registrar microsserviços 	| Endpoints login 	| Endpoints veículos e compradores 	| Ponto de entrada e saída da API 	| Envia as requisições para o gateway e recebe as respostas 	|

Abaixo um diagrama da arquitetura do projeto

<img src="https://user-images.githubusercontent.com/61818941/95804088-21889300-0cd8-11eb-845a-95b247f6e8fb.jpg" height="300" width="350" alt="Project architecture image">

## 📑Requisitos
Para execução deste projeto é necessário ter pré-instalado e configurado:
- [Docker](https://docs.docker.com/get-docker/)
- [Java 11](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
- [Tomcat 9.0.37](https://archive.apache.org/dist/tomcat/tomcat-9/v9.0.37/bin/)
- [Maven 3.6.3](https://archive.apache.org/dist/maven/maven-3/3.6.3/binaries/)
- Gerenciador de banco de dados(HeidiSQL, Workench, etc.)

## 🚀Executando o projeto
Os passos a seguir levam em consideração que todos os itens dos [requisitos](https://github.com/HarissonNascimento/chunin-exam-with-microservices#requisitos)
já foram instalados.

1.**Executando docker-compose up**

Após a instalação do docker, execute-o e espere ele iniciar, após sua inicialização no terminal, navegue até '.../chunin-exam-with-microservices/**spring-backend**>' e execute

```sh
docker-compose up
```

2.**Criando banco de dados**

No seu gerenciador de banco dados, na porta 3306, usuário: root e senha: root, execute: 

```sh
create schema chunin_exam;
```

para criar o banco de dados.

3.**Executando os microsserviços**

Antes de executar os microsserviços vamos criar os jars dos módulos no nosso repositório local do maven.
Para isso, no terminal, navegue até '.../**chunin-exam-with-microservices**>' e execute
```sh
mvn clean install -DskipTests
```
Feito isso, já podemos executar nossos microsserviços. Como descrito na [arquitetura do projeto](https://github.com/HarissonNascimento/chunin-exam-with-microservices#arquitetura), todos os microsserviços precisam se registrar no service discovery, portanto
o primeiro microsserviço que deve ser executado é o 'discovery', para isso, no terminal, navegue até  '.../chunin-exam-with-microservices/**discovery**>' e execute
```sh
mvn spring-boot:run
```
Após sua execução, para executar o 'gateway', abra outra janela do terminal, navegue até '.../chunin-exam-with-microservices/**gateway**>' e execute
```sh
mvn spring-boot:run
```
Para executar o 'auth', abra mais uma janela do terminal, navegue até '.../chunin-exam-with-microservices/**auth**>' e execute
```sh
mvn spring-boot:run
```
E por fim, para executar o 'spring-backend', abra outra janela do terminal, navegue até '.../chunin-exam-with-microservices/**spring-backend**>' e execute
```sh
mvn spring-boot:run
```

Pronto, agora que temos todos os microsserviços sendo executados, já podemos passar para o próximo passo


4.**Criando usuário admin para aplicação**

Após a execução dos microsserviços, se tudo correu bem as tabelas application_user, buyer e vehicle devem ter sido criadas.

O usuário admin deve ser inserido manualmente, para isso, no seu gerenciador de banco de dados execute:

```sh
INSERT INTO chunin_exam.application_user (password, role, username) VALUES ('$2a$10$L8LU9vI.48.kxxmbKRYAMeK/iITE3jRAxOJlf63Uwv7QeQSPkICya', 'ADMIN', 'root')
```

>Este comando criará um usuário cujo username é 'root' e a senha é 'harisson'

|           id           	|                    password                   	|            role           	|     username    	|
|:----------------------:	|:---------------------------------------------:	|:-------------------------:	|:---------------:	|
| Gerado automaticamente 	| O password deve estar criptografado em bcrypt 	| As roles são ADMIN e USER 	| Nome de usuário 	|

5.**Executando front-end**

Para executarmos o front-end precisamos primeiro inicializarmos o servidor do Tomcat 9.0.37.

Por padrão, quando se faz o download do servidor, não vem configurado nem um usuário administrador, então, o primeiro 
passo é, configurarmos um usuário que nos possibilite ter acesso as opções de gerenciamento do mesmo.

Para isso, abra a pasta onde você instalou o Tomcat 9.0.37>conf:


<img src="https://user-images.githubusercontent.com/61818941/96311846-c361f580-0fe0-11eb-9133-61350067f001.png" height="300" width="500" alt="Conf folder image">  

Dentro da pasta conf, procure pelo arquivo **tomcat-users.xml**

<img src="https://user-images.githubusercontent.com/61818941/96312548-3455dd00-0fe2-11eb-8751-b6740cb57aa9.png" height="300" width="500" alt="Tomcat-user archive image">

Abra-o com um editor de textos qualquer e adicione o seguinte código uma linha acima de *</tomcat-users>*:

```
<role rolename="manager-gui"/>
<user username="root" password="root" roles="tomcat,role1,admin,manager,manager-gui"/>
```

>Este comando irá criar um usuário cujo username é 'root' e a senha é 'root'

Seu arquivo tomcat-users.xml deve se parecer com isso:

<img src="https://user-images.githubusercontent.com/61818941/96322614-2eff8f00-0ff0-11eb-940d-cbc471f0ae8b.png" height="300" width="500" alt="Tomcat-user code image">

Salve as edições feitas e pode fechar, já temos um usuário cadastrado em nosso servidor. 

Agora vamos iniciar nosso servidor, para isso, abra outra janela do terminal e navegue até ...apache-tomcat-9.0.37/**bin** e execute:

Para sistemas Windows:
```sh
catalina.bat run
```

Para sistemas Linux:
```sh
./catalina.sh run
```

>'apache-tomcat-9.0.37' é o nome da pasta do tomcat, caso você tenha renomeado, basta substituir.

Após o servidor ter iniciado, no seu navegador, acesse: **http://localhost:8080/** e clique em _**Manager App**_

<img src="https://user-images.githubusercontent.com/61818941/96320569-1cce2280-0fe9-11eb-829a-80151e14987c.png" height="300" width="500" alt="Manager App image">

Preencha 'usuario' e 'senha' com os que configuramos no arquivo tomcat-user.xml.

Na página que abriu, procure pela aba **_Deploy_** e preencha:

* Context Path: **/jsf_frontend_war_exploded**
 
Ao executarmos o comando _mvn clean install -DskipTests_ no passo 3, em nosso diretório "jsf-frontend" foi gerada a pasta out e é com ela que faremos o deploy de nossa aplicação, para isso preencha o campo **WAR or Directory path** com o seguinte caminho de diretório:

**.../chunin-exam-with-microservices/jsf-frontend/out/artifacts/jsf_frontend_war_exploded**

>Os campos 'Version (for parallel deployment)' e 'XML Configuration file path' devem ficar em branco


<img src="https://user-images.githubusercontent.com/61818941/97794825-88b1ad00-1bdd-11eb-84f2-8c2b0f76bdd3.png" height="300" width="500" alt="Manager App image">

Com os campos preenchidos clique em **Deploy** e pronto, temos nosso front-end em: http://localhost:8080/jsf_frontend_war_exploded/

_NOTA: Para o correto funcionamento do front-end, todos os microsserviços devem estar em execução._


