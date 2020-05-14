package io.aloxy;

import java.time.Duration;
import java.util.concurrent.CompletionStage;

import javax.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import io.smallrye.mutiny.Uni;
import io.smallrye.reactive.messaging.amqp.AmqpMessage;
import org.jboss.logging.Logger;

@ApplicationScoped
public class GreetingResource {

    private final Logger _logger = Logger.getLogger(getClass().getName());

    // This one works as expected
    // @Incoming("data")
    // public CompletionStage<Void> incomingMessageCS(final AmqpMessage<String> message) {
    //     _logger.debugv("received message {0}", message.getAddress());
    //     return Uni.createFrom().completionStage(message.ack())
    //         .onItem().delayIt().by(Duration.ofSeconds(1)).subscribeAsCompletionStage();
    // }

    // This one never resolves. Handles 1 message and never completes
    @Incoming("data")
    public Uni<Void> incomingMessageUni(final AmqpMessage<String> message) {
        return Uni.createFrom().completionStage(message.ack())
            .onItem().delayIt().by(Duration.ofSeconds(1));
    }
}