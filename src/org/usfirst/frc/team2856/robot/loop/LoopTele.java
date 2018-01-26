package org.usfirst.frc.team2856.robot.loop;

import org.usfirst.frc.team2856.robot.Constants;
import org.usfirst.frc.team2856.robot.Robot;

public class LoopTele extends Loop{
	
	//DriveTrain driveTrain
	
	public LoopTele(Robot rob){super(rob);}
	
	
	//XXX Disable autonomous dependencies
	public void init() {
		robot.driveTrain.initTele();
	}

	
	
	
	
	public void loop() {
		
		
		if (Constants.leftJoystick.getTrigger()){
			robot.driveTrain.tankDrive(Constants.leftJoystick.getY()/3, Constants.rightJoystick.getY()/3);
		}else{
			robot.driveTrain.tankDrive(Constants.leftJoystick, Constants.rightJoystick);
		}
		//robot.shooter.updateTele();
		//robot.climber.updateTele();
		//robot.intake.updateTele();
		
	}

}
