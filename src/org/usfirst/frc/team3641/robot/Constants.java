package org.usfirst.frc.team3641.robot;

public class Constants
{
	// Drive Controllers
	public static final int PS4_CONTROLLER = 0;
	public static final int ATTACK3 = 1;
	
	// Drive Motors
	public static final int LEFT_MOTOR_1 = 1;
	public static final int LEFT_MOTOR_2 = 2;
	public static final int LEFT_MOTOR_3 = 3;
	public static final int RIGHT_MOTOR_1 = 4;
	public static final int RIGHT_MOTOR_2 = 5;
	public static final int RIGHT_MOTOR_3 = 6;
	
	// Encoders
	public static final int DRIVE_ENC_PORT_1 = 0;
	public static final int DRIVE_ENC_PORT_2 = 1;
	public static final int ANALOG_ENC_PORT_0 = 0;
	
	//Pi Port
	public static final int PI_COM_PIN = 2;
	
	// Various Sensors 
	public static final int ULTRASONIC = 0;
	
	// Auton References
	public static final int REALLY_COOL_AUTON = 42;
	public static final int NOT_SO_COOL_AUTON = 1;
	
	// PID Contants
	public static final double DRIVE_KP = .005;
	public static final double DRIVE_KI = .01;
	public static final double DRIVE_KD = 0;

	// PI serial transfer rate
	public static final int TRANSFER_RATE = 9600;
	
	// Camera center
	public static final int CAMERA_LINE_UP = 160;
	
}
