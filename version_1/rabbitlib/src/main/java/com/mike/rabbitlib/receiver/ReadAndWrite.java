package com.mike.rabbitlib.receiver;

import com.mike.rabbitlib.connection.ConnectImpl;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

public class ReadAndWrite {

	private ConnectImpl connect;

	public ReadAndWrite(ConnectImpl connect) {
		this.connect = connect;
	}

	public void readAndWrite(String queue, String exchange, String routingKey) throws Exception {
		Channel channel = connect.connect();

		callBackWithDifQueue(channel, queue, exchange, routingKey);
	}

	/**
	 * Use user queue Name and exchange
	 * @param channel
	 * @param queue
	 * @param exchange
	 * @param routingKey
	 * @throws Exception
	 */
	private void callBackWithDifQueue(Channel channel, String queue, String exchange, String routingKey) throws Exception {
		// Declare queue
		AMQP.Queue.DeclareOk aqd  = channel.queueDeclare(queue, false, false, false, null);
		// Bind a queue to an exchange
		channel.queueBind(queue, exchange, routingKey);

		if (queue.length() < 1) {
			System.out.println("Ready for read and print message(s) from the " + aqd.getQueue() + "...");
		} else {
			System.out.println("Ready for read and print message(s) from the " + queue + "...");
		}

		// Callback interface to be notified when a message is delivered
		DeliverCallback callback = (consumerTag, delivery) -> {
			String message = new String(delivery.getBody());
			if (delivery.getEnvelope().getRoutingKey().equals(routingKey)) {
				System.out.println("===========================================");
				System.out.println(output(aqd, message));
				System.out.println("===========================================");
			}
		};
		// Start a non-nonlocal, non-exclusive consumers, with a server-generated consumerTag
		channel.basicConsume(queue, true, callback, consumerTag -> { });
	}

	/**
	 * Print
	 * @param aqd
	 * @param message
	 * @return report
	 */
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
