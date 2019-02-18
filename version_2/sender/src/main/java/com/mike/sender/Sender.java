package com.mike.sender;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Sender {

	/**
	 * Make connection declare queue, exchange and bind them
	 * send message
	 * @param queue
	 * @param exchange
	 * @param message
	 * @throws Exception
	 */
	public void send(String queue, String exchange, String message) throws Exception {
		// Connect to the server
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");

		try (Connection connection = factory.newConnection();
				Channel channel = connection.createChannel()) {

			// Declare exchange 
			channel.exchangeDeclare(exchange, "fanout");
			// Declare queue 
			channel.queueDeclare(queue, false, false, false, null);
			// Bind them
			channel.queueBind(queue, exchange, "");

			sendMessage(channel, exchange, message);
		}
	}

	private void sendMessage(Channel channel, String exchange, String message) throws Exception {
		// Send message
		if (message.length() < 1) {
			message = "Nothing was input";
		}
		channel.basicPublish(exchange, "", null, message.getBytes());
		System.out.println("Sent " + message);
	}
}
