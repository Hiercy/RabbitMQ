package com.mike.receiver;

public class App {
	
	private static final String QUEUE_NAME = "new_queue";
	private static final String EXCHANGE_NAME = "new_exchange";
	
	public static void main(String[] args) throws Exception {
		Receiver read = new Receiver();
		read.receive(QUEUE_NAME, EXCHANGE_NAME);
	}
}
