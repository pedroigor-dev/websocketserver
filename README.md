# WebSocket Server - Chat em Tempo Real

## Visão Geral

Este projeto é um servidor de WebSocket desenvolvido em Java com Spring Boot, seguindo princípios de Clean Architecture e SOLID. Ele permite comunicação em tempo real entre múltiplos clientes, sendo ideal para aplicações como chats, colaboração simultânea, monitoramento ao vivo, entre outros cenários que exigem atualização instantânea.

## O que é WebSocket?

WebSocket é um protocolo de comunicação bidirecional e full-duplex, que permite manter uma conexão aberta entre cliente e servidor. Diferente do HTTP tradicional, onde cada requisição precisa de uma resposta, o WebSocket permite que ambos os lados enviem mensagens a qualquer momento, sem overhead de múltiplas conexões.

## Tecnologias Utilizadas

- Java 21+
- Spring Boot 4+
- Spring WebSocket (STOMP)
- SockJS e STOMP.js (frontend)

## Arquitetura do Projeto

O projeto está organizado em camadas, seguindo Clean Architecture:

- **Domain**: Contém as entidades de negócio e contratos (interfaces), como o modelo `Message` e a interface `MessageRepository`.
- **Application**: Serviços de aplicação, como o `ChatService`, que orquestra as regras de negócio.
- **Infrastructure**: Implementações concretas dos contratos, como o repositório em memória (`InMemoryMessageRepository`).
- **Presentation**: Camada de apresentação, responsável pelos endpoints WebSocket (`ChatWebSocketEndpoint`).
- **Config**: Configuração do WebSocket para o Spring Boot.

## Fluxo de Funcionamento

1. O cliente conecta ao endpoint WebSocket `/ws-chat` usando SockJS/STOMP.
2. O cliente envia mensagens para o destino `/app/chat`.
3. O endpoint `ChatWebSocketEndpoint` recebe a mensagem, adiciona timestamp, salva via `ChatService` e retorna a mensagem para o tópico `/topic/messages`.
4. Todos os clientes conectados e inscritos em `/topic/messages` recebem a mensagem instantaneamente.

## Principais Componentes

### Modelo de Mensagem

```java
public class Message {
    private String sender;
    private String content;
    private LocalDateTime timestamp;
}
```

### Interface de Repositório

```java
public interface MessageRepository {
    void saveMessage(Message message);
    List<Message> findAll();
}
```

### Serviço de Chat

```java
@Service
public class ChatService {
    private final MessageRepository messageRepository;
}
```

### Endpoint WebSocket

```java
@Controller
public class ChatWebSocketEndpoint {
    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public Message handleMessage(Message message) {
    }
}
```

### Configuração WebSocket

```java
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
    }
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws-chat").setAllowedOriginPatterns("*").withSockJS();
    }
}
```

## Frontend de Teste

O projeto inclui um arquivo `index.html` simples em `src/main/resources/static/`, que pode ser acessado em `http://localhost:8080/index.html` após iniciar o servidor. Ele permite testar o chat em tempo real entre múltiplos navegadores.

## Como Executar

1. Certifique-se de ter o JDK 21+ instalado e configurado.
2. Execute o comando:
   ```
   ./mvnw clean spring-boot:run
   ```
3. Acesse `http://localhost:8080/index.html` em dois ou mais navegadores para testar a comunicação em tempo real.

## Possíveis Evoluções

- Persistência em banco de dados relacional ou NoSQL
- Suporte a múltiplas salas/canais
- Autenticação e autorização de usuários
- Integração com frontend moderno (React, Angular, etc.)
- Deploy em nuvem

## Licença

Este projeto é open source e está sob a licença MIT.
