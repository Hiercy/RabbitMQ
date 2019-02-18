package com.mike.receiver;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class Receiver {

	/**
	 * Try to make connection
	 * @return channel
	 * @throws Exception
	 */
	public void receive(String queue, String exchange) throws Exception {
		// Connect to the server
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost("localhost");

		Connection connection = connectionFactory.newConnection();
		Channel channel = connection.createChannel();

		init(channel, connection, queue, exchange);
	}

	/**
	 * Initialize queue and exchange
	 * @param channel
	 * @param connection
	 * @throws Exception 
	 * @throws Exception
	 */
	private void init(Channel channel, Connection connection, String queue, String exchange) throws Exception {
		// Declare exchange 
		channel.exchangeDeclare(exchange, "fanout");

		// Declare queue
		AMQP.Queue.DeclareOk aqd  = channel.queueDeclare(queue, false, false, false, null);

		// Bind a queue to an exchange
		channel.queueBind(queue, exchange, "");

		System.out.println("Reading messages from a queue...");

		// Callback interface to be notified when a message is delivered
		DeliverCallback callback = (consumerTag, delivery) -> {
			String message = new String(delivery.getBody());
			System.out.println("================================");
			System.out.println(output(aqd, message));
			System.out.println("================================");
		};
		// Start a non-nonlocal, non-exclusive consumers, with a server-generated consumerTag
		channel.basicConsume(queue, true, callback, consumerTag -> { });
	}
	
	private String output(AMQP.Queue.DeclareOk aqd, String message) {
		String queueName = "Queue name - " + aqd.getQueue();
		String messageCount = "Messages count in " + aqd.getQueue() + " - " + aqd.getMessageCount();
		String consumerCount = "Consumer count = " + aqd.getConsumerCount();
		String text = "Message - " + message;
		
		return queueName + "\n" 
		+ messageCount   + "\n" 
		+ consumerCount  + "\n" 
		+ text;
	}
}
