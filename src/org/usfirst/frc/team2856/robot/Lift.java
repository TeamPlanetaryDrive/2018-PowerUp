
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
		motor.set(speed);
	}
	
	public void liftStop(){
		motor.stopMotor();
	}
	
	//method for teleop
	public void updateTele(){
		//Should make it so that when button 4 (right) is pressed, lift goes up
		if(Constants.button4_right.get()) {
			liftDown(-1);
		}
		//Should make it so if neither buttons are pressed, lift stays still
		else if(!Constants.button4_right.get() && !Constants.button5_right.get()) {
			liftStop();
		}
		//Should make it so that when button 5 (right) is pressed, lift goes down
		else if(Constants.button5_right.get()) {
			liftUp(1);
		}
	}
	
}