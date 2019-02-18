package com.mike.sender;

import com.mike.rabbitlib.connection.ConnectImpl;
import com.mike.rabbitlib.publisher.Publisher;

public class Sender {
	public static void main(String[] args) throws Exception {
		ConnectImpl connectImpl = new ConnectImpl();
		Publisher publisher = new Publisher(connectImpl);

		// publish with host
		publisher.publish("localhost", "Hey, What's up??? BASIC", "basic");
		publisher.publish("127.0.0.1", "Hey, What's up??? SUPER", "super");

		// publish with host
		publisher.publish("localhost", "bla-bla BASIC", "basic", "own_exchange");
		publisher.publish("127.0.0.1", "bla-bla SUPER", "super", "own_exchange");

		// publish without host
		publisher.publishWithDefaultHost("bla-bla BASIC", "basic");
		publisher.publishWithDefaultHost("bla-bla SUPER", "super");

		// publish without host
		publisher.publishWithDefaultHost("bla-bla BASIC", "basic", "own_exchange");
		publisher.publishWithDefaultHost("bla-bla SUPER", "super", "own_exchange");
	}
}
