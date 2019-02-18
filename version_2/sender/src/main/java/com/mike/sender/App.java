package com.mike.sender;

public class App {

	private static final String QUEUE_NAME = "new_queue";
	private static final String EXCHANGE_NAME = "new_exchange";

	public static void main(String[] args) throws Exception {
		Sender write = new Sender();
		write.send(QUEUE_NAME, EXCHANGE_NAME, "Hello");
	}
}
