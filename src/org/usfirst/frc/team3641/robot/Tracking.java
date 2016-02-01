package org.usfirst.frc.team3641.robot;

import edu.wpi.first.wpilibj.AnalogInput;
/*import java.io.*;
import java.net.Socket;*/
import java.lang.String;

public class Tracking 
{
	private static AnalogInput ultrasonic;
	private static Tracking instance;
	/*private static Socket socket;
	private static BufferedReader pipe;
	private static String read;
	private static int castedRead;*/
	
	private Tracking()
	{
		/*try
		{
			socket = new Socket("10.36.41.41", 3641);
			pipe = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		}
		catch(IOException error)
		{
			error.printStackTrace();
		}*/
		ultrasonic = new AnalogInput(Constants.ULTRASONIC);
	}

/*	public static int heading()
	{
		try
		{
			read = pipe.readLine();
			castedRead = Integer.parseInt(read);
		}
		catch(IOException io)
		{
			io.printStackTrace();
		}
		return castedRead;
	}
*/	
	public static float getRawUltrasonic()
	{
		return ultrasonic.getValue();
	}
	
	public static Tracking getInstance()
	{
		if(instance == null)
		{
			instance = new Tracking();
		}
		return instance;
	}
	
	public static void autoTarget()
	{
		UDP.sendData("Request");
		String response = UDP.getData();
		if(response != null && !response.equals("0"))
		{
			DriveBase.driveNormal(0.0, PILoop.smoothDrive(Integer.parseInt(response), Constants.CAMERA_LINE_UP, true));		
		}
		else
		{
			DriveBase.driveNormal(0.0, 0.0);
		}
	}
}
