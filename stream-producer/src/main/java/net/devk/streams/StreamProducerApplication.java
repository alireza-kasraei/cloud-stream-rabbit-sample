package net.devk.streams;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableBinding(ProducerChannels.class)
@RestController
@SpringBootApplication
public class StreamProducerApplication {

	private MessageChannel channel;

	public StreamProducerApplication(ProducerChannels producerChannels) {
		this.channel = producerChannels.consumer();
	}

	@PostMapping("/greet/{name}")
	public void publish(@PathVariable String name) {
		String content = "Hello " + name + " !";
		Message<String> message = MessageBuilder.withPayload(content).build();
		channel.send(message);
	}

	public static void main(String[] args) {
		SpringApplication.run(StreamProducerApplication.class, args);
	}

}

interface ProducerChannels {

	@Output
	MessageChannel consumer();

}
