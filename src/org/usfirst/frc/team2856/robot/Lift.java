
package org.usfirst.frc.team2856.robot;

import edu.wpi.first.wpilibj.*;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Lift {
	//SpeedController variable
	private SpeedController motor;
	
	public Lift(){
		motor = Constants.lift;
	}
	
	public void LiftUp (double speed){
		motor.set(speed);
	} 
		
	public void LiftDown (double speed){
		motor.set(speed);
	}
	
	public void LiftStop(){
		motor.stopMotor();
	}
	
	public void updateTele(){
		if(Constants.button4_right.get()) {	 //should make it so that when button 4 (right) is pressed, lift goes up -S
			LiftDown(.5);
		}
		else if(!Constants.button4_right.get() && !Constants.button5_right.get()) {	//should make it so if neither buttons are pressed, lift stays still -S
			LiftStop();
		}
		else if(Constants.button5_right.get()) {  //should make it so that when button 5 (right) is pressed, lift goes down -S
			LiftUp(-.5);
		}
	}
	
}