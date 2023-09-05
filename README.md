# Tutorial - Implementação CRUD com Spring Boot, JUnit e Mockito
* [Spring Boot Unit Testing CRUD REST API with JUnit and Mockito](https://www.javaguides.net/2022/03/spring-boot-unit-testing-crud-rest-api-with-junit-and-mockito.html)

No tutorial referenciado acima, é implementada um CRUD de funcionários.<br>
O objetivo do tutorial é demostrar como realizar teste unitário em uma<br>
API RESTful utilizando JUnit e Mockito.

De maneira bem objetiva, são mostradas as estapas de implementação das classes<br>
e interfaces da API, assim como os teste unitários.

### Mocks Objects
O termo **Mock Objects** é utilizado para descrever um caso especial de objetos que imitam<br>
objetos reais para teste. Esses Mock Objects atualmente podem ser criados através de<br>
frameworks que facilitam bastante a sua criação. Praticamente todas as principais<br>
linguagens possuem frameworks disponíveis para a criação de Mock Objects. Os Mock<br>
Objects são mais uma forma de objeto de teste.<br>

Mocks Objects são bastante difundidos na comunidade e na literatura de métodos ágeis<br>
**Extremme Programming (XP)**, visto que, utilizando o XP se faz uso constante de testes<br>
através da técnica **Test-Driven Development (TDD)** que prega teste antes da implementação.<br>
Com isso, devemos simular alguns objetos no intuito de conseguir testar o código.<br>
