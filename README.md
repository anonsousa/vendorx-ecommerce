# 🛒 VendorX Project

## Descrição

Este é um projeto de e-commerce em desenvolvimento. A aplicação permitirá que os usuários naveguem por produtos, façam compras, e gerenciem seus pedidos. O backend está sendo construído com **Spring Boot** e **PostgreSQL**.

---

## 🛠️ Tecnologias Utilizadas

- **Java 21**
- **Spring Boot**
- **PostgreSQL**
- **RabbitMQ** (Mensageria, em breve)

---

## 🚀 Como Executar

### Pré-requisitos

- **Java 21**
- **Maven**
- **PostgreSQL**

### Passo a Passo

1. **Clone o repositório**:
   ```bash
   git clone https://github.com/anonsousa/vendorx-ecommerce.git
   cd vendorx-ecommerce
   
2. **Configure o banco de dados**:
    ```bash
   spring.datasource.url=jdbc:postgresql://localhost:5432/seubanco
    spring.datasource.username=usuario
    spring.datasource.password=senha
    spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
    spring.jpa.hibernate.ddl-auto=update
   
3. **Compile e execute**:
    ```bash
    ./mvnw clean install
    ./mvnw spring-boot:run
   

## 📄 Licença
**Este projeto está sob a licença MIT.**

