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
INSERT INTO chunin_exam.application_user (password, role, username) VALUES ('$2a$10$L8LU9vI.48.kxxmbKRYAMeK/iITE3jRAxOJlf63Uwv7QeQSPkICya', 'ADMIN', 'root');
```

>Este comando criará um usuário cujo username é 'root' e a senha é 'harisson'

|           id           	|                    password                   	|            role           	|     username    	|
|:----------------------:	|:---------------------------------------------:	|:-------------------------:	|:---------------:	|
| Gerado automaticamente 	| O password deve estar criptografado em bcrypt 	| As roles são ADMIN e USER 	| Nome de usuário 	|

5.**Executando front-end**

Para executarmos o front-end, abra o terminal e navegue até '.../chunin-exam-with-microservices/**jsf-frontend**>' e execute:

```sh
mvn clean package
```

Feito isso, veremos que no diretório '.../chunin-exam-with-microservices/jsf-frontend' foi criada a pasta 'target' e dentro dela, um arquivo 'front.war'.
Copie este arquivo para a pasta 'webapps' onde você instalou o tomcat em: \<tomcat\>/**webapps**

Agora, dentro da pasta \<tomcat\>/**bin** execute o arquivo 'startup.bat' ou 'startup.sh' dependendo do seu sistema operacional.

Por fim, se tudo correu bem, nossa aplicação estará rodando em http://localhost:8080/front

> \*\<tomcat\> refere-se a pasta onde você instalou o tomcat

_NOTA: Para o correto funcionamento do front-end, todos os microsserviços devem estar em execução._


