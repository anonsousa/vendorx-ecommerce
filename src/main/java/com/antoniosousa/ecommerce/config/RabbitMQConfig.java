package com.antoniosousa.ecommerce.config;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.notification.exchange}")
    private String exchange;

    @Value("${rabbitmq.notification.queue}")
    private String notificationQueue;

    /*
     * Creating Rabbit Admin
     * @param connectionFactory - used to establish connections to the RabbitMQ>
     */
    @Bean
    public RabbitAdmin createRabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    /*
     * Initializes the RabbitAdmin instance after the application is fully star>
     * Ensures RabbitMQ components (queues, exchanges, bindings) are declared o>
     */
    @Bean
    public ApplicationListener<ApplicationReadyEvent> initAdmin(RabbitAdmin rabbitAdmin) {
        return event -> rabbitAdmin.initialize();
    }

    /*
     * Setting MessageConverter to Jackson2Json instead Bytes
     */
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /*
     * Creating RabbitTemplate With new Configurations
     */
    @Bean
    public RabbitTemplate createRabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }

    /*
     * Creates a durable queue for notifications
     * This queue will persist across broker restarts and hold notification mes>
     */
    @Bean
    public Queue createQueueNofitication() {
        return QueueBuilder.durable(notificationQueue)
                .build();
    }

    /*
     * Creates a Fanout Exchange for broadcasting messages
     * The fanout exchange will route messages to all bound queues
     */
    @Bean
    public FanoutExchange createNotificationExchange() {
        return ExchangeBuilder.fanoutExchange(exchange)
                .build();
    }

    /*
     * Creates a binding between the queue and the exchange
     * Binds the notification queue to the fanout exchange, ensuring messages s>
     * are routed to the queue
     */
    @Bean
    public Binding createNotificationBinding() {
        return BindingBuilder.bind(
                        createQueueNofitication())
                .to(createNotificationExchange());
    }
}

