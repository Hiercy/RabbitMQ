package com.mike.rabbitlib.receiver;

import com.mike.rabbitlib.connection.ConnectImpl;
import com.mike.rabbitlib.utils.Settings;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

public class ReadAndWrite {

	private ConnectImpl connect;

	public ReadAndWrite(ConnectImpl connect) {
		this.connect = connect;
	}

	/**
	 * This method reads and writes from queue
	 * @throws Exception
	 */
	public void readAndWrite() throws Exception {
		Channel channel = connect.connect();

		System.out.println("Ready for read and print message(s) from the queue...");

		callBack(channel);
	}

	public void readAndWrite(String queue, String exchange, String routingKey) throws Exception {
		Channel channel = connect.connect();

		System.out.println("Ready for read and print message(s) from the " + queue + "...");

		callBackWithDifQueue(channel, queue, exchange, routingKey);
	}

	/**
	 * @param channel
	 * @return
	 * @throws Exception
	 */
	private void callBack(Channel channel) throws Exception {
		// Callback interface to be notified when a message is delivered
		DeliverCallback callback = (consumerTag, delivery) -> {
			String message = new String(delivery.getBody());
			if (delivery.getEnvelope().getRoutingKey().equals(Settings.KEY_QUEUE_SUPER)) {
				System.out.println("===========================================");
				System.out.println(output(connect.aqd, message));
				System.out.println("===========================================");
			} else if (delivery.getEnvelope().getRoutingKey().equals(Settings.KEY_QUEUE_BASIC)) {
				System.out.println("===========================================");
				System.out.println(output(connect.aqd1, message));
				System.out.println("===========================================");
			}
		};
		// Start a non-nonlocal, non-exclusive consumers, with a server-generated consumerTag
		channel.basicConsume(Settings.QUEUE_NAME, true, callback, consumerTag -> { });
		channel.basicConsume(Settings.QUEUE_NAME_2, true, callback, consumerTag -> { });
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
		AMQP.Queue.DeclareOk aqd  = channel.queueDeclare(queue,   false, false, false, null);
		// Bind a queue to an exchange
		channel.queueBind(queue, exchange, routingKey);

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
