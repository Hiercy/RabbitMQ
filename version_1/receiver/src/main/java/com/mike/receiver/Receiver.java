package com.mike.receiver;

import com.mike.rabbitlib.connection.ConnectImpl;
import com.mike.rabbitlib.receiver.ReadAndWrite;

public class Receiver {

	public static void main(String[] args) throws Exception {
		ConnectImpl connect = new ConnectImpl();
		ReadAndWrite readAndWrite = new ReadAndWrite(connect);
		
		/*
		 * read basic_queue, super_queue
		 * from own_exchange 
		 * and new_queue
		 * from new_exchange
		 */
		readAndWrite.readAndWrite("basic_queue", "own_exchange", "basic");
		readAndWrite.readAndWrite("super_queue", "own_exchange", "super");
		readAndWrite.readAndWrite("new_queue", "own_exchange", "");
	}
}
