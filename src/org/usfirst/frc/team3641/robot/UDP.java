package org.usfirst.frc.team3641.robot;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class UDP
{
	private static UDP instance;

	static int port;
	static InetAddress address;
	static DatagramSocket socket;
	static DatagramPacket packet;
	static byte[] buf;

	public UDP()
	{
		port = Constants.PI_UDP_PORT;
		try 
		{
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
	
	public static int getData()
	{
		packet = new DatagramPacket(buf, buf.length);
		try
		{
			socket.receive(packet);
			String received = new String(packet.getData(), 0, packet.getLength());
			return Integer.parseInt(received);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return -1; //Negative value will indicate an error
		}	
	}
}
