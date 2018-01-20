package org.usfirst.frc.team2856.robot.loop;

import org.usfirst.frc.team2856.robot.Robot;

public abstract class Loop {
	
	
	//Useless Constructor
	protected Robot robot;
	public Loop(){}
	public Loop(Robot rob){robot = rob;}
	
	public abstract void init();
	public abstract void loop();
	
}
