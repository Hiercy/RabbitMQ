package com.mike.rabbitlib.connection;

import com.rabbitmq.client.Channel;

public interface Connect {

	/**
	 * Try to make long connection
	 * @return channel
	 * @throws Exception
	 */
	public Channel connect() throws Exception;

	/**
	 * Connect for sender with host, message and routing key
	 * @param host
	 * @param message
	 * @param routingKey
	 * @throws Exception
	 */
	public void connectForSender(String host, String message, String routingKey) throws Exception;

	/**
	 * Connect for sender with host, message and exchange name
	 * @param message
	 * @param type
	 * @param routingKey
	 * @param exchangeName
	 * @throws Exception
	 */
	public void connectForSender(String host, String message, String routingKey, String exchangeName) throws Exception;
	
	/**
	 * Connect for sender with host, message, exchange name
	 * if its direct exchange type we don't need queueName because this will send with routingKey
	 * @param host
	 * @param message
	 * @param routingKey
	 * @param exchangeName
	 * @param queueName
	 * @throws Exception
	 */
	public void connectForDirect(String host, String message, String routingKey, String exchangeName, String exchangeType) throws Exception;
	
	/**
	 * 
	 * @param host
	 * @param message
	 * @param routingKey
	 * @param exchangeName
	 * @param exchangeType
	 * @param queueName
	 * @throws Exception
	 */
	public void connectForFanout(String host, String message, String exchangeName, String exchangeType, String queueName) throws Exception;

	/**
	 * Connect with default host (localhost)
	 * @param message
	 * @param routingkey
	 * @throws Exception
	 */
	public void connectForSenderWithoutHost(String message, String routingKey) throws Exception;

	/**
	 * Connect with default host (localhost)
	 * @param message
	 * @param routingkey
	 * @param exchangeName
	 * @throws Exception
	 */
	public void connectForSenderWithoutHost(String message, String routingKey, String exchangeName) throws Exception;
}
