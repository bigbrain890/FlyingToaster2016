package org.usfirst.frc.team3641.robot;

public class Constants
{
	// Drive Controllers
	public static final int PS4_CONTROLLER = 0;
	public static final int ATTACK3 = 1;
	
	// Drive Motors
	public static final int LEFT_MOTOR_1 = 1;
	public static final int LEFT_MOTOR_2 = 2;
	public static final int RIGHT_MOTOR_1 = 3;
	public static final int RIGHT_MOTOR_2 = 4;

	
	// Encoders
	public static final int DRIVE_ENC_PORT_1 = 0;
	public static final int DRIVE_ENC_PORT_2 = 1;
	public static final int ANALOG_ENC_PORT_0 = 0;
	
	//Pi Port
	public static final int PI_COM_PIN = 2;
	public static final int PI_UDP_PORT = 3641;
	public static final String PI_IP_ADDR = "10.36.41.41";
	
	// Various Sensors 
	public static final int ULTRASONIC = 0;
	public static final double ULTRASONIC_CONVERSION_FACTOR = 0.009766;
	public static final int SHOOTER_POT = 0;
	
	// Auton References
	public static final int REALLY_COOL_AUTON = 42;
	public static final int NOT_SO_COOL_AUTON = 1;
	
	// PID Contants
	public static final double DRIVE_KP = .01;
	public static final double DRIVE_KI = .001;
	public static final double DRIVE_KD = 0;

	// PI serial transfer rate
	public static final int TRANSFER_RATE = 9600;
	
	// Camera center
	public static final int CAMERA_LINE_UP = 160;
	
	// Shooter Motors
	public static final int INTAKE_MOTOR = 7;
	public static final int FLY_WHEEL_1 = 8;
	public static final int FLY_WHEEL_2 = 9;
	
	// Punmatic Ports
	public static final int PUNCHER_FORWARD = 0;
	public static final int PUNCHER_REVERSE = 1;
	
	//Misc
	public static final double DRIVE_ENCODER_MULTIPLIER = 0.02454369; // Assuming 1:1 ratio between encoder and wheel.
	
	//Teleop State Variables
	public static final int DRIVE_NORMAL = 1;
	public static final int DRIVE_REVERSE = 0;
}
