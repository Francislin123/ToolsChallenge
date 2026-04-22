# Payments API - Hexagonal Architecture

### 🛠️ Tech Stack

![Java 17](https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot 3](https://img.shields.io/badge/Spring%20Boot-3-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![Maven](https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)
![Lombok](https://img.shields.io/badge/Lombok-red?style=for-the-badge)

### 🏗️ Design & Testing

![Architecture](https://img.shields.io/badge/Architecture-Hexagonal%20(Ports%20%26%20Adapters)-lightgrey?style=for-the-badge)
![JUnit 5](https://img.shields.io/badge/JUnit-5-25A162?style=for-the-badge&logo=junit5&logoColor=white)
![Mockito](https://img.shields.io/badge/Mockito-blue?style=for-the-badge)

## Endpoints

### Criar Pagamento
POST /payments

### Consultar Todos
GET /payments

### Consultar por ID
GET /payments/{id}

### Estorno
POST /payments/{id}/refund

## Executar
mvn clean install
mvn spring-boot:run

# ✅ Requisitos para Rodar o Projeto

- Para executar este projeto corretamente, é necessário que sua máquina atenda aos seguintes requisitos:
- Java JDK 17 ou superior instalado
- IntelliJ IDEA 2025 (recomendado): facilita a configuração do projeto e a integração com ferramentas do
- Ecossistema Spring

# Documentação para testar a API (Swagger)
- http://localhost:8080/api/v1/swagger-ui/index.html#/

# 📌 Exemplos de Requisição

- 🔹 1. Pagamento à vista
{
"transacao": {
"cartao": "4444********1234",
"id": "3fa85f64-5717-4562-b3fc-2c963f66afa7",
"descricao": {
"valor": 333.80,
"estabelecimento": "Petshop Vali"
},
"formaPagamento": {
"tipo": "AVISTA",
"parcelas": 1
}
}
}
- 🔹 2. Pagamento Parcelado no Emissor
{
"transacao": {
"cartao": "7777********1234",
"id": "3fa85f64-5717-4562-b3fc-2c963f66afa3",
"descricao": {
"valor": 450.90,
"estabelecimento": "Petshop Frans"
},
"formaPagamento": {
"tipo": "PARCELADO_EMISSOR",
"parcelas": 10
}
}
}
- 🔹 3. Pagamento Parcelado na Loja (valor inválido → será NEGADO)
{
"transacao": {
"cartao": "9999********1234",
"id": "3fa85f64-5717-4562-b3fc-2c963f66afa8",
"descricao": {
"valor": 0.0,
"estabelecimento": "Petshop CLEITON"
},
"formaPagamento": {
"tipo": "PARCELADO_LOJA",
"parcelas": 10
}
}
}

# ✅ Mais informacoes

- Realizei algumas alterações no JSON de entrada para simplificar os testes da API e incluí exemplos de requisições (JSONs de entrada) para facilitar a validação.

- A API está bem testada, com cobertura das principais classes, especialmente PaymentController e PaymentService.

- Utilizei o modelo de arquitetura hexagonal, aplicando boas práticas como:

- Separação clara de responsabilidades

- Uso de interfaces para desacoplamento

- Criação de exceptions específicas para tratamento adequado de erros

- Padronização das mensagens de resposta

- Organização em camadas seguindo princípios de Clean Architecture

- Procurei seguir as melhores práticas adotadas no mercado para garantir organização, legibilidade, testabilidade e manutenção do código.

- Espero que gostem!

### Arquitetura de solução baseada em AWS
![Captura de Tela 2019-05-12 às 15 18 49](https://res.cloudinary.com/duep7y7ve/image/upload/v1750900266/ih1dqh1zvohcqzeenswk.jpg)

# 👨‍💻 Autor

**Francislin**

**Engenharia de Software**

---

# ⭐ Se gostou do projeto

Deixe uma **⭐ no repositório** para apoiar o desenvolvimento.

---