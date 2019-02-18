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
	 * Connect for sender with host, message, routingKey and exchangeName
	 * If this is direct exchange type we don't need queueName because this will send with routingKey
	 * @param host
	 * @param message
	 * @param routingKey
	 * @param exchangeName
	 * @throws Exception
	 */
	public void connectForDirect(String host, String message, String routingKey, String exchangeName) throws Exception;

	/**
	 * Connect for sender with host, message, exchangeName and queueName
	 * If this is fanout exchange type we don't need routingKey because this will send with queueName
	 * @param host
	 * @param message
	 * @param exchangeName
	 * @param queueName
	 * @throws Exception
	 */
	public void connectForFanout(String host, String message, String exchangeName, String queueName) throws Exception;

	/**
	 * Connect with default host (localhost)
	 * @param message
	 * @param exchangeName
	 * @param queueName
	 * @throws Exception
	 */
	public void connectForFanoutWithDefaultHost(String message, String exchangeName, String queueName) throws Exception;

	/**
	 * Connect with default host (localhost)
	 * If this is direct exchange type we don't need queueName because this will send with routingKey
	 * @param message
	 * @param routingKey
	 * @param exchangeName
	 * @throws Exception
	 */
	public void connectForDirectWithDefaultHost(String message, String routingKey, String exchangeName) throws Exception;

	/**
	 * Connect for unknown type
	 * @param host
	 * @param message
	 * @param exchangeName
	 * @param exchangeType
	 * @param queueName
	 * @param routingKey
	 * @throws Exception
	 */
	public void connetcUnknownType(String host, String message, String exchangeName, String exchangeType, String queueName, String routingKey) throws Exception;

	/**
	 * Connect for unknown type
	 * @param message
	 * @param exchangeName
	 * @param exchangeType
	 * @param queueName
	 * @param routingKey
	 * @throws Exception
	 */
	public void connetcUnknownTypeWithDefaultHost(String message, String exchangeName, String exchangeType, String queueName, String routingKey) throws Exception;
}
