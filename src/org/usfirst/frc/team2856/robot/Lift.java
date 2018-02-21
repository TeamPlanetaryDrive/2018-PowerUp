
package org.usfirst.frc.team2856.robot;

import edu.wpi.first.wpilibj.*;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Lift {
	private SpeedController motor;
	
	public Lift(){
		motor = Constants.lift;
	}
	
	public void liftUp(double speed){
		motor.set(speed);
	} 
		
	public void liftDown(double speed){
		motor.set(-speed);
	}
	
	public void liftStop(){
		motor.stopMotor();
	}
	
	//method for teleop
	public void updateTele(){
		double liftSpeed = 0.50;
		//Should make it so that when button 4 (right) is pressed, lift goes up
		if(Constants.button2_right.get()) {
			liftDown(liftSpeed);
		}
		//Should make it so if neither buttons are pressed, lift stays still
		else if(!Constants.button2_right.get() && !Constants.button3_right.get()) {
			liftStop();
		}
		//Should make it so that when button 5 (right) is pressed, lift goes down
		else if(Constants.button3_right.get()) {
			liftUp(liftSpeed);
		}
	}
	
}