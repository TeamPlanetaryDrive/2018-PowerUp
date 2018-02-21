package org.usfirst.frc.team2856.robot;

import edu.wpi.first.wpilibj.*;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Climb {
	
	private SpeedController motor;
	
	public Climb() {
		motor = Constants.climb;
	}
	
	public void extendClimb(double speed) {
		motor.set(speed);
	}
	
	public void retractClimb(double speed) {
		motor.set(speed);
	}
	
	public void climbStop() {
		motor.stopMotor();
	}
	
	//method for teleop
	public void updateTele(){
		double climbSpeedUp = 0.250;
		double climbSpeedDown = 0.50;
		//Should make it so that when button 4 (right) is pressed, lift goes up
		if(Constants.button4_left.get()) {
			retractClimb(-climbSpeedDown);
		}
		//Should make it so if neither buttons are pressed, lift stays still
		else if(!Constants.button4_left.get() && !Constants.button5_left.get()) {
			climbStop();
		}
		//Should make it so that when button 5 (right) is pressed, lift goes down
		else if(Constants.button5_left.get()) {
			extendClimb(climbSpeedUp);
		}
	}
	
}
