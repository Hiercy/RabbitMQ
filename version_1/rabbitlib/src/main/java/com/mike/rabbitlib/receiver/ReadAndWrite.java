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

	/**
	 * @param channel
	 * @return
	 * @throws Exception
	 */
	private DeliverCallback callBack(Channel channel) throws Exception {
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

		return callback;
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
