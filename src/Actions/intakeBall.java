package Actions;

import org.usfirst.frc.team2472.robot.Robot;

import Objects.Action;

public class intakeBall extends Action {
	
	public intakeBall(double time){
		
	timeout = time;	
	
	}
	public void startAction(){
		super.startAction();

		
	}
	public void periodic(){
		
		Robot.intake.intakeBall();
		
	}
	public boolean isFinished(){
		
		if (isTimedOut()){
			
			Robot.intake.stopIntake();
			
			return true;
		
		}
		
		else{
			
			return false;
			
		}
	}
	
}