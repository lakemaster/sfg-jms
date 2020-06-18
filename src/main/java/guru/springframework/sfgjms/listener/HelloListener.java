package guru.springframework.sfgjms.listener;

import guru.springframework.sfgjms.config.JmsConfig;
import guru.springframework.sfgjms.model.HelloMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class HelloListener {

    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = JmsConfig.MY_QUEUE)
    public void listen(@Payload HelloMessage helloMessage,
                       @Headers MessageHeaders headers,
                       Message message) {

        // System.out.println("I got a message: " + helloMessage);
    }


    @JmsListener(destination = JmsConfig.MY_SEND_AND_RECEIVE_QUEUE)
    public void listenAndReply(@Payload HelloMessage helloMessage,
                               @Headers MessageHeaders headers,
                               Message message) throws JMSException {

        HelloMessage replyMessage = HelloMessage.builder()
                .id(UUID.randomUUID())
                .messagge("Have a nice day")
                .build();

        jmsTemplate.convertAndSend(message.getJMSReplyTo(), replyMessage);

        System.out.println("I replied a message: " + replyMessage);
    }

}
