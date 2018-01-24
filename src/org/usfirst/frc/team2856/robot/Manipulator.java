package org.usfirst.frc.team2856.robot;
/*
package org.usfirst.frc.team2856.robot;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Intake {
	//PMVs
	private final double intakeSpeed=0.21358982985792347;
	
	private boolean intakeOn;
	boolean prevPressed;
	boolean currPressed;
	SpeedController motor;
	
	
	public Intake(){
		prevPressed=false;
		intakeOn=true;
		motor = Constants.intake;
	}
	
	public void updateTele(){
		if(Constants.leftJoystick.getRawButton(4))
			currPressed = Constants.leftJoystick.getRawButton(4);
		else{
			if(Constants.leftJoystick.getRawButton(5))
				currPressed = Constants.leftJoystick.getRawButton(5);
			else
				currPressed=false;
		}
		
		if(Constants.leftJoystick.getRawButton(4))
			SmartDashboard.putString("DB/String 0", "Button 4 Pressed- positive intake");
		if(Constants.leftJoystick.getRawButton(5))
			SmartDashboard.putString("DB/String 0", "Button 5 Pressed- negative intake");
		
		if(currPressed && !prevPressed){
			intakeOn = !intakeOn;
		}
		
		prevPressed = currPressed;
		
		
		if(intakeOn){
			System.out.println("Hi!");
			if(Constants.leftJoystick.getRawButton(4))
				motor.set(0.9);	//whatever the intake speed is
			else
				motor.set(-0.9);
		}else{
			
			System.out.println("Bye!");
			motor.set(0);
		}
		
		
	}
	
	//accessor
	public double getIntakeSpeed(){return intakeSpeed;}
	
}
*/