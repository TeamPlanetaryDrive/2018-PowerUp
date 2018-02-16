package org.usfirst.frc.team2856.robot.loop;

import java.util.ArrayList;
import org.usfirst.frc.team2856.robot.Constants;
import org.usfirst.frc.team2856.robot.Robot;
import org.usfirst.frc.team2856.robot.drivetrain.DriveTrain;

public class StateIterator {
	public static ArrayList CommandList = new ArrayList();
	DriveTrain drive;
	
	
	public StateIterator(DriveTrain d) {
		d = drive;
	}
	
	public void update() {
		if(!drive.moveGetActive() ) {
			
		}
	}
}
