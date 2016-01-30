package org.usfirst.frc.team3641.robot;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDP
{
	private static UDP instance;

	static int port;
	static InetAddress address;
	static DatagramSocket socket;
	static DatagramPacket packet;
	static byte[] buf;

	public UDP()	//Sets the vars and opens a connection to the pi
	{
		try 
		{
			port = Constants.PI_UDP_PORT;
			address = InetAddress.getByName(Constants.PI_IP_ADDR);
			socket = new DatagramSocket();
			buf = new byte[256];
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static UDP getInstance() 
	{
		if(instance == null)
		{
			instance = new UDP();
		}
		return instance;
	}
	
	public static void sendData(String data) //Sends the request to the pi
	{
		buf = data.getBytes();	//Converts the String to a byte array
		packet = new DatagramPacket(buf, buf.length, address, port); //Makes a packet from the byte array, address, and port
		try
		{
			socket.send(packet);	//Send the packet :D
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	public static String getData()
	{
		try
		{
			byte[] buf = new byte[256];	//Creates the byte array for the response
			packet = new DatagramPacket(buf, buf.length); //Prepares to receive the packet
			socket.receive(packet);	//Receives the packet from the pi
			String response = new String(buf, 0, packet.getLength()); //Converts the byte array to a string
			return response;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}	
	}

}
