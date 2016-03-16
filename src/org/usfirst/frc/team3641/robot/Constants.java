package org.usfirst.frc.team3641.robot;

public class Constants
{
	// Drive Controllers
	public static final int PS4_CONTROLLER = 0;
	public static final int ATTACK3 = 1;
	public static final int CONTROL_BOARD = 2;
	
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
	public static final int PI_PORT = 3641;
	//Ethan's Pi: "10.36.41.41"
	//Team Pi: "10.36.41.121"
	public static final String PI_IP_ADDR = "10.36.41.121";//"10.36.41.41"; Ethan's Pi
	
	// Various Sensors 
	public static final int ULTRASONIC = 0;
	public static final double ULTRASONIC_CONVERSION_FACTOR = 0.009766;
	public static final int SHOOTER_POT = 1;
	public static final int INTAKE_POT = 3;
	
	// Auton References
	public static final int LOW_BAR = 1;
	public static final int ROCK_WALL = 2;
	public static final int SHOVE_IN_FREEZER = 3;
	public static final int SALLY_PORT = 4;
	public static final int DRAW_BRIDGE = 5;
	public static final int MOAT = 6;
	public static final int ROUGH_TERRAIN = 7;
	public static final int PORTCULLIS = 8;
	public static final int RAMPARTS = 9;
	public static final int TARGET_DEFENSE_DRIVE_DIS = 130;
	public static final int STRAIGHT = 0;
	
	// PID Contants
	public static final double DRIVE_KP = .1;
	public static final double DRIVE_KI = .003;
	public static final double SHOOTER_KP = 0.00047;
	public static final double SHOOTER_KI = 0.0000023;
	public static final double INTAKE_KP = 0.0;
	public static final double INTAKE_KI = 0.0;
	public static final double CAM_KP = 0.0;
	public static final double CAM_KI = 0.0;
	public static final double KI_UPPER_LIMIT = 233;
	public static final double KI_LOWER_LIMIT = -233;

	// PI serial transfer rate
	public static final int TRANSFER_RATE = 9600;
	
	// Camera stuff
	public static final int CAMERA_LINE_UP = 160;
	public static final double DEGREES_PER_PIXEL = 0.2140625; //Life Cam
	//public static final double DEGREES_PER_PIXEL = 0.2375;  //PS3 Eye
	public static final double MIN_ANGLE_ERROR = 1;
	public static final int SEND_REQUEST = 0;
	public static final int RESPONSE_CAPTURE = 1;
	public static final int DO_MATH = 2;
	public static final int TURN_TO_TARGET = 3;
	public static final int TRACKED = 4;

	
	// Shooter stuff
	public static final int FLY_WHEEL_1 = 5;
	public static final int FLY_WHEEL_2 = 6;
	public static final int SHOOTER = 7;
	public static final int SHOOTER_LEVER = 8;
	public static final double CAM_FIRE = 0.0; 		//Position for shoving ball into shooting wheels.
	public static final double CAM_HOLD = 0.0; 		//Position for holding ball in shooter.
	public static final double CAM_INTAKE = 0.0; 	// Position for anticipating intaked ball.
	public static final double SHOOTER_INTAKE = 0.0;//Position to put shooter arm at to intake the ball.
	public static final double SHOOTER_REVERSE = 9999; //TODO: ADD REAL VALUE HERE! Value of pot when shooter is vertical.
	public static final int INTAKE_DOWN = 0;
	public static final int FAR_SHOT = 2500;
	public static final int MEDIUM_SHOT = 2400;
	public static final int CLOSE_SHOT = 3300;
	public static final int SHOOTER_SERVO_PORT = 9;
	public static final int LEVER_LIM_SWITCH = 8;
	public static final int SHOOTER_LIM_SWITCH = 9;
	
	
	// Intake Motors
	public static final int ROLLERS = 9;
	public static final int INTAKE = 10;
	
	// Punmatic Ports
	public static final int DRIVE_TRAIN = 0;
	public static final int PTO = 1;
	
	//Misc
	public static final double DRIVE_ENCODER_MULTIPLIER = 0.02454369; // Assuming 1:1 ratio between encoder and wheel.
	public static final int LIGHTING_CONTROLLER = 0;
	public static final double LIGHTING_FADE = 1.0;
	public static final double LIGHTING_THEATER_CHASE = 2.0;
	public static final double LIGHTING_RAINBOW = 3.0;
	public static final double LIGHTING_BLINK = 4.0;
	public static final double ROTATION_STALL_INPUT = .25;
	public static final int LEVER_MAX_SWING = -1000;
	
	//Teleop State Variables
	public static final boolean DRIVE_NORMAL = false;
	public static final boolean DRIVE_REVERSE = true;
	public static final int RESTING_POSITION = 0;
	public static final int FIRE = 1;
	public static final int RESET = 2;
	
}
