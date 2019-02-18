package com.mike.receiver;

import com.mike.rabbitlib.connection.ConnectImpl;
import com.mike.rabbitlib.receiver.ReadAndWrite;

public class Receiver {

	public static void main(String[] args) throws Exception {
		ConnectImpl connect = new ConnectImpl();
		ReadAndWrite readAndWrite = new ReadAndWrite(connect);
		
		readAndWrite.readAndWrite();
	}
}
