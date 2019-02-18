package com.mike.rabbitlib.publisher;

import com.mike.rabbitlib.connection.ConnectImpl;

public class Publisher {

	private ConnectImpl connectImpl;

	public Publisher(ConnectImpl connectImpl) {
		this.connectImpl = connectImpl;
	}

	/**
	 * Publish message 
	 * input host, message and routingKey
	 * @param host
	 * @param message
	 * @param routingKey
	 * @throws Exception
	 */
	public void publish(String host, String message, String routingKey) throws Exception {
		connectImpl.connectForSender(host, message, routingKey);
//		sended(message);
	}

	/**
	 * Publish message 
	 * input host, message, routingKey and exchangeName
	 * @param host
	 * @param message
	 * @param routingKey
	 * @param exchangeName
	 * @throws Exception
	 */
	public void publish(String host, String message, String routingKey, String exchangeName) throws Exception {
		connectImpl.connectForSender(host, message, routingKey, exchangeName);
	}

	/**
	 * Publish message 
	 * input message and routingKey with default host name
	 * @param message
	 * @param routingKey
	 * @throws Exception
	 */
	public void publishWithDefaultHost(String message, String routingKey) throws Exception {
		connectImpl.connectForSenderWithoutHost(message, routingKey);
	}

	/**
	 * Publish message 
	 * input message, routingKey and exchangeName with default host name
	 * @param message
	 * @param routingKey
	 * @param exchangeName
	 * @throws Exception
	 */
	public void publishWithDefaultHost(String message, String routingKey, String exchangeName) throws Exception {
		connectImpl.connectForSenderWithoutHost(message, routingKey, exchangeName);
	}

	/**
	 * Use if you want to see what actually sent
	 * @param message
	 */
	private void sended(String message) {
		if (message.length() < 1) {
			System.out.println("You sent nothing");
		} else {
			System.out.println("You sent - " + message);
		}
	}
}
