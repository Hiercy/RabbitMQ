package com.mike.rabbitlib.publisher;

import com.mike.rabbitlib.connection.ConnectImpl;

public class Publisher {

	private ConnectImpl connectImpl;

	public Publisher(ConnectImpl connectImpl) {
		this.connectImpl = connectImpl;
	}

	/**
	 * Publish message for unknown type
	 * input host, message, exchangeName, exchangeType, queueName, routingKey 
	 * @param host
	 * @param message
	 * @param exchangeName
	 * @param exchangeType
	 * @param queueName
	 * @param routingKey
	 * @throws Exception
	 */
	public void publish(String host, String message, String exchangeName, String exchangeType, String queueName, String routingKey) throws Exception {
		connectImpl.connetcUnknownType(host, message, exchangeName, exchangeType, queueName, routingKey);
	}

	/**
	 * Publish message for unknown exchange type 
	 * input message, exchangeName, exchangeType, queueName and routingKey 
	 * @param message
	 * @param exchangeName
	 * @param exchangeType
	 * @param queueName
	 * @param routingKey
	 * @throws Exception
	 */
	public void publishWithDefaultHost(String message, String exchangeName, String exchangeType, String queueName, String routingKey) throws Exception {
		connectImpl.connetcUnknownTypeWithDefaultHost(message, exchangeName, exchangeType, queueName, routingKey);
	}

	/**
	 * Publish message for fanout
	 * input host, message, exchangeName, queueName and routingKey  because type fanout no need routingKey
	 * @param host
	 * @param message
	 * @param exchangeName
	 * @param exchangeType
	 * @param queueName
	 * @throws Exception
	 */
	public void publishForFanout(String host, String message, String exchangeName, String queueName) throws Exception {
		connectImpl.connectForFanout(host, message, exchangeName, queueName);

		// If Sender want to see what was send
		//sended(message);
	}

	/**
	 * Publish message for fanout
	 * input message, exchangeName, exchangeType, queueName and routingKey 
	 * @param message
	 * @param exchangeName
	 * @param queueName
	 * @throws Exception
	 */
	public void publishForFanoutWithDefaultHost(String message, String exchangeName, String queueName) throws Exception {
		connectImpl.connectForFanoutWithDefaultHost(message, exchangeName, queueName);
	}

	/**
	 * Publish message
	 * input host, message, routingKey, exchangeName and exchangeType because type direct no need queue name
	 * @param host
	 * @param message
	 * @param routingKey
	 * @param exchangeName
	 * @param exchangeType
	 * @throws Exception
	 */
	public void publishForDirect(String host, String message, String routingKey, String exchangeName) throws Exception {
		connectImpl.connectForDirect(host, message, routingKey, exchangeName);
	}

	/**
	 * 
	 * @param message
	 * @param routingKey
	 * @param exchangeName
	 * @throws Exception
	 */
	public void publishForDirectWithDefaultHost(String message, String routingKey, String exchangeName) throws Exception {
		connectImpl.connectForDirectWithDefaultHost(message, routingKey, exchangeName);
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
