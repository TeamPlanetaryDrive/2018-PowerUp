package org.usfirst.frc.team2856.robot;


import edu.wpi.first.wpilibj.SpeedController;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Manipulator {
	
	//Speed controller added
	private SpeedController motor;
	public Manipulator() {
		//Instantiate motors
		motor = Constants.manipulator;
	}
	
	public void pullIn(double speed){
		motor.set(speed);
	}
	
	public void pullOut(double speed){
		motor.set(speed);
	}
	
	public void stopPull(){
		motor.stopMotor();
	}
	
	//method for teleop
	public void updateTele() {
		double manipSpeed = 0.5;
		//Right Trigger = pull in
		if(Constants.rightJoystick.getTrigger())
			pullIn(-manipSpeed); //May be wrong direction
		//Left Trigger = pull out
		else if(Constants.leftJoystick.getTrigger())
			pullOut(manipSpeed); //May be wrong direction
		//If neither pressed, stop
		else if(!Constants.leftJoystick.getTrigger() && !Constants.rightJoystick.getTrigger())
			stopPull();
	}
}
