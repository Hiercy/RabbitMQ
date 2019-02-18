package com.mike.sender;

import com.mike.rabbitlib.connection.ConnectImpl;
import com.mike.rabbitlib.publisher.Publisher;
import com.mike.rabbitlib.utils.Settings;

public class Sender {

	public static void main(String[] args) throws Exception {
		ConnectImpl connectImpl = new ConnectImpl();
		Publisher publisher = new Publisher(connectImpl);

		// publish with host
		publisher.publishForDirect("127.0.0.1", "Hello With HOST AND DIRECT BBAASSIICC", Settings.KEY_NAME_BASIC, Settings.EXCHANGE_NAME_DIRECT);
		publisher.publishForDirect("127.0.0.1", "Hello With HOST AND DIRECT SSUUPPEERR", Settings.KEY_NAME_SUPER, Settings.EXCHANGE_NAME_DIRECT);

		publisher.publishForFanout("127.0.0.1", "Hello With HOST AND FANOUT NNEEWW", Settings.EXCHANGE_NAME_FANOUT, Settings.QUEUE_NAME_NEW);
		publisher.publishForFanout("localhost", "Hello With HOST AND FANOUT NNEEWW", Settings.EXCHANGE_NAME_FANOUT, Settings.QUEUE_NAME_NEW);

		// publish without host
		publisher.publishForDirectWithDefaultHost("Without host DIRECT BBAASSIICC", Settings.KEY_NAME_BASIC, Settings.EXCHANGE_NAME_DIRECT);
		publisher.publishForDirectWithDefaultHost("Without host DIRECT SSUUPPEERR", Settings.KEY_NAME_SUPER, Settings.EXCHANGE_NAME_DIRECT);

		publisher.publishForFanoutWithDefaultHost("Without host FANOUT NNEEWW", Settings.EXCHANGE_NAME_FANOUT, Settings.QUEUE_NAME_NEW);
		publisher.publishForFanoutWithDefaultHost("Without host FANOUT NNEEWW", Settings.EXCHANGE_NAME_FANOUT, Settings.QUEUE_NAME_NEW);

		// unknown type
		// need to create new queue with random name
		publisher.publish("localhost", "WITH HOST DIRECT UNKNOWN TYPE BASIC", Settings.EXCHANGE_NAME_DIRECT, "direct", "", Settings.KEY_NAME_BASIC);
		publisher.publishWithDefaultHost("WITHOUT HOST DIRECT UNKNOWN TYPE SUPER", Settings.EXCHANGE_NAME_DIRECT, "direct", "", Settings.KEY_NAME_SUPER);

		publisher.publish("127.0.0.1", "FANOUT UNKNOWN TYPE", Settings.EXCHANGE_NAME_FANOUT, "fanout", Settings.QUEUE_NAME_NEW, "");
		publisher.publishWithDefaultHost("", Settings.EXCHANGE_NAME_FANOUT, "fanout", Settings.QUEUE_NAME_NEW, "");
	}
}
