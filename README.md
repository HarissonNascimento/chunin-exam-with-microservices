<img src="https://img.shields.io/github/workflow/status/HarissonNascimento/chunin-exam-with-microservices/chunin-exam%20CI?style=plastic" alt="Project Badge"/>

## 💬O que há neste documento
* [Proposta inicial](https://github.com/HarissonNascimento/chunin-exam-with-microservices#proposta-inicial)
* [Arquitetura](https://github.com/HarissonNascimento/chunin-exam-with-microservices#arquitetura)
* [Requisitos](https://github.com/HarissonNascimento/chunin-exam-with-microservices#requisitos)
* [Executando o projeto](https://github.com/HarissonNascimento/chunin-exam-with-microservices#executando-o-projeto)
* [Monitoramento](https://github.com/HarissonNascimento/chunin-exam-with-microservices#monitoramento)
* [Documentação](https://github.com/HarissonNascimento/chunin-exam-with-microservices#documentação)

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
- Gerenciador de banco de dados(HeidiSQL, Workench, etc.)

## 🚀Executando o projeto
Os passos a seguir levam em consideração que todos os itens dos [requisitos](https://github.com/HarissonNascimento/chunin-exam-with-microservices#requisitos)
já foram instalados.

1.**Executando docker-compose up**

Após a instalação do docker, execute-o e espere ele iniciar, após sua inicialização no terminal, navegue até '.../chunin-exam-with-microservices/**spring-backend**>' e execute

```sh
docker-compose up
```

2.**Criando usuário admin para aplicação**

O usuário admin deve ser inserido manualmente, para isso, no seu gerenciador de banco de dados, na porta 3306, usuário: root e senha: root, selecione o schema chunin_exam e execute:

```sh
INSERT INTO chunin_exam.application_user (password, role, username) VALUES ('$2a$10$L8LU9vI.48.kxxmbKRYAMeK/iITE3jRAxOJlf63Uwv7QeQSPkICya', 'ADMIN', 'root');
```

>Este comando criará um usuário cujo username é 'root' e a senha é 'harisson'

|           id           	|                    password                   	|            role           	|     username    	|
|:----------------------:	|:---------------------------------------------:	|:-------------------------:	|:---------------:	|
| Gerado automaticamente 	| O password deve estar criptografado em bcrypt 	| As roles são ADMIN e USER 	| Nome de usuário 	|

3.**Desfrutando da aplicação**

Feito todos os passos, podemos acessar a aplicação em: http://localhost:8080/front

## 📊Monitoramento
Para monitorar as métricas da utilização de recursos das API's deste projeto foram utilizados os frameworks prometheus e grafana.

Após a execução do 1º passo de [Executando o projeto](https://github.com/HarissonNascimento/chunin-exam-with-microservices#executando-o-projeto), as métricas poderão ser analisadas em:

- Prometheus: http://localhost:9090
- Grafana: http://localhost:3000

## 📝Documentação
Para documentação das API's deste projeto foi utilizado o framework Swagger.

Com os serviços em execução, a documentação das API's estará disponível em:

http://localhost:8082/gateway/<serviço>/swagger-ui.html

>\* Substitua "\<serviço\>" pelo nome do serviço que deseja obter a documentação. 
>
>Exemplo: http://localhost:8082/gateway/spring-backend/swagger-ui.html

