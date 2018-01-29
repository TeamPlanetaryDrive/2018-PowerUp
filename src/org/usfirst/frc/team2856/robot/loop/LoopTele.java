package org.usfirst.frc.team2856.robot.loop;

import org.usfirst.frc.team2856.robot.Constants;
import org.usfirst.frc.team2856.robot.Robot;

public class LoopTele extends Loop{
	
	//DriveTrain driveTrain
	
	public LoopTele(Robot rob){super(rob);}
	
	
	private double leftY = Constants.leftJoystick.getY();
	private double rightY = Constants.rightJoystick.getY();
		
	//XXX Disable autonomous dependencies
	public void init() {
		robot.driveTrain.initTele();
	}

	
	
	
	
	public void loop() {
		if(!Constants.button3_left.get() && !Constants.button3_right.get()) {
			leftY = Constants.leftJoystick.getY();
			rightY = Constants.rightJoystick.getY();
		}
		
		if (Constants.leftJoystick.getTrigger()){
			leftY /= 3;
			rightY /= 3;
		}
		if(Constants.button3_left.get() || Constants.button3_right.get()){
			double maxY = Math.max(leftY, rightY);
			leftY = maxY;
			rightY = maxY;
		}
		robot.driveTrain.tankDrive(leftY, rightY);
		//robot.shooter.updateTele();
		//robot.climber.updateTele();
		//robot.intake.updateTele();
		
	}

}
