package com.mike.receiver;

import com.mike.rabbitlib.connection.ConnectImpl;
import com.mike.rabbitlib.receiver.ReadAndWrite;

public class Receiver {

	public static void main(String[] args) throws Exception {
		ConnectImpl connect = new ConnectImpl();
		ReadAndWrite readAndWrite = new ReadAndWrite(connect);
		
		// read own_exchange, basic_queue and super_queue
//		readAndWrite.readAndWrite();
		
		// read new_exchange, new queue without routingKey because this is fanout type
//		readAndWrite.readAndWrite("new_queue", "new_exchange", "");
		
		// read basic_queue, own_exchange with basic routingKey because this is direct type
		readAndWrite.readAndWrite("", "own_exchange", "basic");
	}
}
