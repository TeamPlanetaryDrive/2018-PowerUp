package org.usfirst.frc.team2856.robot.loop;

import org.usfirst.frc.team2856.robot.Constants;
import org.usfirst.frc.team2856.robot.Robot;
import edu.wpi.first.wpilibj.GenericHID.Hand;

public class LoopTele extends Loop{
	
	//DriveTrain driveTrain
	
	public LoopTele(Robot rob){super(rob);}
	
	
	//XXX Disable autonomous dependencies
	public void init() {
		robot.driveTrain.initTele();
	}

	
	
	
	
	public void loop() {
		
		
		if(Constants.controller.getBumper(Hand.kLeft)) { //(Constants.controller.getBumperPressed("left")){
			robot.driveTrain.arcadeDrive(Constants.controller.getY(Hand.kLeft)/3, Constants.controller.getX(Hand.kLeft)/3);
		}else{
			robot.driveTrain.arcadeDrive(Constants.controller);
		}
		//robot.shooter.updateTele();
		//robot.climber.updateTele();
		//robot.intake.updateTele();
		
	}

}
