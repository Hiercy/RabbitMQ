package com.mike.rabbitlib.connection;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class ConnectImpl implements Connect {

	private Channel channel;
	private Connection connection;

	public ConnectImpl() {}

	@Override
	public Channel connect() throws Exception {
		// Connect to the server
		makeConnection();

		return channel;
	}

	@Override
	public void connectForDirect(String host, String message, String routingKey, String exchangeName) throws Exception {
		// Connect to the server
		makeConnection(host);

		// Declare exchange
		initConnect(channel, exchangeName, "direct");

		// Send message
		sendMessage(channel, message, routingKey, exchangeName);

		// Close connection
		closeConnection(channel, connection);
	}

	@Override
	public void connectForDirectWithDefaultHost(String message, String routingKey, String exchangeName) throws Exception {
		// Connect to the server
		makeConnection();

		// Declare queue, exchange and bind them
		initConnect(channel, exchangeName, "direct");

		// Send message
		sendMessage(channel, message, routingKey, exchangeName);

		// Close connection
		closeConnection(channel, connection);
	}

	@Override
	public void connectForFanout(String host, String message, String exchangeName, String queueName) throws Exception {
		// Connect to the server
		makeConnection(host);

		// Declare queue, exchange and bind them
		initConnect(channel, exchangeName, "fanout", queueName);

		// Send message
		sendMessage(channel, message, "", exchangeName);

		// Close connection
		closeConnection(channel, connection);

	}

	@Override
	public void connectForFanoutWithDefaultHost(String message, String exchangeName, String queueName) throws Exception {
		// Connect to the server
		makeConnection();

		// Declare queue, exchange and bind them
		initConnect(channel, exchangeName, "fanout", queueName);

		// Send message
		sendMessage(channel, message, "", exchangeName);

		// Close connection
		closeConnection(channel, connection);
	}


	@Override
	public void connetcUnknownType(String host, String message, String exchangeName, String exchangeType, String queueName, String routingKey) throws Exception {
		// Connect to the server
		makeConnection(host);

		// Declare queue, exchange and bind them
		initConnect(channel, exchangeName, exchangeType, queueName);

		// Send message
		sendMessage(channel, message, routingKey, exchangeName);

		// Close connection
		closeConnection(channel, connection);
	}

	@Override
	public void connetcUnknownTypeWithDefaultHost(String message, String exchangeName, String exchangeType, String queueName, String routingKey) throws Exception {
		// Connect to the server
		makeConnection();

		// Declare queue, exchange and bind them
		initConnect(channel, exchangeName, exchangeType, queueName);

		// Send message
		sendMessage(channel, message, routingKey, exchangeName);

		// Close connection
		closeConnection(channel, connection);
	}

	/**
	 * 
	 * @param host
	 * @throws Exception
	 */
	private void makeConnection(String host) throws Exception {
		if (host == null) {
			throw new IllegalArgumentException("Make sure that you entered host name");
		}
		// Connect to the server
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost(host);

		// New connection and create new channel
		connection = connectionFactory.newConnection();
		channel = connection.createChannel();
	}

	/**
	 * 
	 * @throws Exception
	 */
	private void makeConnection() throws Exception {
		// Connect to the server
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost("localhost");

		// New connection and create new channel
		connection = connectionFactory.newConnection();
		channel = connection.createChannel();
	}

	/**
	 * Send message with exchangeName and routingKey
	 * @param channel
	 * @param message
	 * @param type
	 * @param routingKey
	 * @param exchangeName
	 * @throws Exception
	 */
	private void sendMessage(Channel channel, String message, String routingKey, String exchangeName) throws Exception {
		// Send message
		if (message.length() < 1) {
			message = "Nothing was input";
		}

		if (channel != null && routingKey != null && exchangeName != null) {
			channel.basicPublish(exchangeName, routingKey, null, message.getBytes());
		} else {
			System.out.println("Wrong key or exchange name");
		}
	}

	/**
	 * MORE FLEXIBLE
	 * Initialize queue and exchange
	 * @param channel
	 * @param exchange
	 * @param type
	 * @param queueName
	 * @throws Exception
	 */
	private void initConnect(Channel channel, String exchange, String type, String queueName) throws Exception {
		if (channel != null && exchange != null && type != null) {
			// Declare exchange 
			channel.exchangeDeclare(exchange, type);
			// Declare queue
			channel.queueDeclare(queueName, false, false, false, null);
		} else {
			System.out.println("Some argument is null");
		}
	}

	/**
	 * MORE FLEXIBLE
	 * Initialize exchange 
	 * @param channel
	 * @param exchange
	 * @param type
	 * @throws Exception
	 */
	private void initConnect(Channel channel, String exchange, String type) throws Exception {
		// Declare exchange 
		channel.exchangeDeclare(exchange, type);
	}

	/**
	 * Close connection
	 * @param channel
	 * @param connection
	 * @throws Exception
	 */
	private void closeConnection(Channel channel, Connection connection) throws Exception {
		channel.close();
		connection.close();
	}
}
