package Actions;

import org.usfirst.frc.team2472.robot.Robot;

import Objects.Action;

public class shootIntake extends Action {
	
	boolean done = false;
	
	public shootIntake(double time){
		
	timeout = time;	
	
	}
	public void startAction(){
		super.startAction();

		done = false;
	}
	public void periodic(){
		
		Robot.intakePnue.out();
		
		done = true;
		
	}
	public boolean isFinished(){
		
		if (isTimedOut() || done){
			
			return true;
		
		}
		
		else{
			
			return false;
			
		}
	}
	
}