package net.devk.streams;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.handler.GenericHandler;
import org.springframework.messaging.SubscribableChannel;

@EnableBinding(ConsumerChannels.class)
@SpringBootApplication
public class StreamConsumerApplication {

	@Bean
	IntegrationFlow integrationFlow(ConsumerChannels consumerChannels) {
		return IntegrationFlows.from(consumerChannels.producer())
				.handle(String.class, (GenericHandler<String>) (payload, headers) -> {
					System.out.println(payload);
					return null;
				}).get();
	}

	public static void main(String[] args) {
		SpringApplication.run(StreamConsumerApplication.class, args);
	}

}

interface ConsumerChannels {

	@Input
	SubscribableChannel producer();

}
