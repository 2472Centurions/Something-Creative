package Actions;

import org.usfirst.frc.team2472.robot.Robot;

import Objects.Action;

public class shootBall extends Action {
	
	private long runValue;
	
	public shootBall(long runTime){
	runValue = runTime;	
	}
	public void startAction(){
		super.startAction();
		setTimeOut(runValue);
		Robot.intake.outtakeBall();
	}
	public void periodic(){
		
		
		
	}
	public boolean isFinished(){
		if (timeout()){
			Robot.intake.stopIntake();
			return true;
		
		}
		else{
			return false;
		}
	}
	
}
