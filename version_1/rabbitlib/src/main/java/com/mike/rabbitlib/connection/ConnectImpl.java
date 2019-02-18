package com.mike.rabbitlib.connection;

import com.mike.rabbitlib.utils.Settings;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class ConnectImpl implements Connect {

	public AMQP.Queue.DeclareOk aqd;
	public AMQP.Queue.DeclareOk aqd1;

	private Channel channel;
	private Connection connection;

	public ConnectImpl() {}

	private void makeConnection() throws Exception {
		// Connect to the server
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost("localhost");

		// New connection and create new channel
		connection = connectionFactory.newConnection();
		channel = connection.createChannel();
	}

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

	@Override
	public Channel connect() throws Exception {
		// Connect to the server
		makeConnection();

		// Declare queue, exchange and bind them
		initConnect(channel);

		return channel;
	}

	@Override
	public void connectForSender(String host, String message, String routingKey) throws Exception {
		// Connect to the server
		makeConnection(host);

		// Declare queue, exchange and bind them
		initConnect(channel);

		// Send message
		sendMessage(channel, message, routingKey);

		// Close connection
		closeConnection(channel, connection);
	}

	@Override
	public void connectForSender(String host, String message, String routingKey, String exchangeName) throws Exception {
		// Connect to the server
		makeConnection(host);

		// Declare queue, exchange and bind them
		initConnect(channel);
		//		initConnect(channel, exchangeName);

		// Send message
		sendMessage(channel, message, routingKey, exchangeName);

		// Close connection
		closeConnection(channel, connection);
	}

	@Override
	public void connectForDirect(String host, String message, String routingKey, String exchangeName, String exchangeType) throws Exception {
		// Connect to the server
		makeConnection(host);

		// Declare exchange
		if (exchangeType.equals("direct")) {
			initConnect(channel, exchangeName, exchangeType);
		} else {
			System.out.println("This method used for direct exchange type only");
		}
		// Send message
		sendMessage(channel, message, routingKey, exchangeName);

		// Close connection
		closeConnection(channel, connection);
	}
	

	@Override
	public void connectForFanout(String host, String message, String exchangeName, String exchangeType, String queueName) throws Exception {
		// Connect to the server
		makeConnection(host);

		// Declare queue, exchange and bind them
		if (exchangeType.equals("fanout")) {
			initConnect(channel, exchangeName, exchangeType, queueName);
		} else {
			System.out.println("This method used for fanout exchange type only");
		}
		// Send message
		sendMessage(channel, message, "", exchangeName);

		// Close connection
		closeConnection(channel, connection);
		
	}

	@Override
	public void connectForSenderWithoutHost(String message, String routingKey) throws Exception {
		// Connect to the server
		makeConnection();

		// Declare queue, exchange and bind them
		initConnect(channel);

		// Send message
		sendMessage(channel, message, routingKey);

		// Close connection
		closeConnection(channel, connection);
	}

	@Override
	public void connectForSenderWithoutHost(String message, String routingKey, String exchangeName) throws Exception {
		// Connect to the server
		makeConnection();

		// Declare queue, exchange and bind them
		initConnect(channel);

		// Send message
		sendMessage(channel, message, routingKey, exchangeName);

		// Close connection
		closeConnection(channel, connection);
	}

	/**
	 * Can add routingKey selection
	 * @param channel
	 * @param message
	 * @param type
	 * @throws Exception
	 */
	private void sendMessage(Channel channel, String message, String routingKey) throws Exception {
		// Send message
		if (message.length() < 1) {
			message = "Nothing was input";
		}

		if (routingKey != null) {
			channel.basicPublish(Settings.EXCHANGE_NAME, routingKey, null, message.getBytes());
		} else {
			System.out.println("Wrong key");
		}
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

		if (routingKey != null) {
			channel.basicPublish(exchangeName, routingKey, null, message.getBytes());
		} else {
			System.out.println("Wrong key or exchange name");
		}
	}

	/**
	 * DEFAULT
	 * Initialize queue and exchange and bind them
	 * @param channel
	 * @param connection
	 * @throws Exception
	 */
	private void initConnect(Channel channel) {
		try {
			// Declare exchange 
			channel.exchangeDeclare(Settings.EXCHANGE_NAME, "direct");

			// Declare queue
			aqd  = channel.queueDeclare(Settings.QUEUE_NAME,   false, false, false, null);
			aqd1 = channel.queueDeclare(Settings.QUEUE_NAME_2, false, false, false, null);

			// Bind a queue to an exchange
			channel.queueBind(Settings.QUEUE_NAME,   Settings.EXCHANGE_NAME, "super");
			channel.queueBind(Settings.QUEUE_NAME_2, Settings.EXCHANGE_NAME, "basic");

		} catch (Exception e) {
			System.out.print("Cannot init queue and exchange ");
			e.printStackTrace();
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
		// Declare exchange 
		channel.exchangeDeclare(exchange, type);
		// Declare queue
		channel.queueDeclare(queueName, false, false, false, null);
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
