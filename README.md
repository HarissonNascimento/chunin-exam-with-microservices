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
| Registra microsserviços 	| Enpoints login 	| Endpoints veículos e compradores 	| Ponto de entrada e saída da API 	| Envia as requisições para o gateway e recebe as respostas 	|

Abaixo um diagrama da arquitetura do projeto

<img src="https://user-images.githubusercontent.com/61818941/95804088-21889300-0cd8-11eb-845a-95b247f6e8fb.jpg" height="300" width="300" alt="Project architecture image">

## 📑Requisitos
Para execução deste projeto é necessário ter pré-instalado:
- [Docker](https://docs.docker.com/docker-for-windows/install/)
- [Java 11](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
- [Tomcat](http://tomcat.apache.org/index.html)
- [Maven](http://maven.apache.org/download.cgi)
- Gerenciador de banco de dados(HeidiSQL, Workench, etc.)

## 🚀Executando o projeto
Os passos a seguir levam em consideração que todos os itens dos [requisitos](https://github.com/HarissonNascimento/chunin-exam-with-microservices#requisitos)
já foram instalados.

1.**Executando docker-compose up**

No terminal

```sh
.../chunin-exam-with-microservices/spring-backend>docker-compose -f stack.yml up
```

2.**Criando banco de dados**

No seu gerenciador de banco dados, na porta 3306, usuário: root e senha: root, execute: 

```sh
create schema chunin_exam;
```

para criar o banco de dados.

3.**Executando os microsserviços**

O primeiro microsserviço a ser executado deve ser o **discovery**, a ordem de execução dos outros não importa.

4.**Criando usuário admin**

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

Para execução do front-end será necessário a configuração do Tomcat server, esse passo pode variar de IDE para IDE e caso não saiba como fazer basta uma pesquisa rápida que encontrará diversos sites explicando como configurar.

Sua pasta WEB-INF deve conter:

![web-inf](https://user-images.githubusercontent.com/61818941/95812154-20ad2c80-0ceb-11eb-83e3-59a878e931c8.png)

Sua pasta WEB-INF/classes deve conter:

![classes](https://user-images.githubusercontent.com/61818941/95812292-6ec23000-0ceb-11eb-9086-7622d2f454ae.png)

Sua pasta WEB-INF/lib deve conter:

![lib](https://user-images.githubusercontent.com/61818941/95812506-f445e000-0ceb-11eb-93e4-4bb7917c168e.png)

Após as etapas acima, basta executar 'jsf-frontend'





