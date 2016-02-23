package org.usfirst.frc.team3641.robot;
import java.io.*;
import java.net.*;

public class TCP
{
	private static TCP instance;
	
	static String host = Constants.PI_IP_ADDR;
	static int port = Constants.PI_PORT;
	static Socket socket;
	static PrintWriter out;
	static BufferedReader in;
	
	public static TCP getInstance() 
	{
		if(instance == null)
		{
			instance = new TCP();
		}
		return instance;
	}
	
	public static boolean connect()
	{
		try
		{
			socket = new Socket(host, port);
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			return true;
		}
		catch(IOException e)
		{
			return false;
		}
	}
	
	public static String doStuff(String request)
	{
		
		try
		{
			out.println(request);
			return in.readLine();
		}
		catch (IOException e)
		{
			return null;
		}
	}

}
