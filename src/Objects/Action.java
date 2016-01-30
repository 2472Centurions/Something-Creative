package Objects;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Action{
	private long startTime,endTime;
	
	//public boolean endFactor;
	
	//public boolean done = false;
	
	public Action(){
		
		
		
	}
	
	
	public void startAction(){
		
		setTimeOut(5000);
		
		startTime = System.currentTimeMillis();
		
	}
	
	private void endAction(){
		
		
		
	}
	
	public boolean isFinished(){
		
		if(timeout() || actionDone()){
			
			endAction();
			
			return true;
			
			

		}else{
			
			return false;
			
			
		}
		
	}
	
	protected boolean timeout(){
		
		return endTime <= System.currentTimeMillis();
		
	}
	
	protected boolean actionDone(){
		
		return false;
		
	}
	
	
	
	//public void overRideFailSafe(){
		
	//	long timeAdded = 1000000000;
		
	//	endTime = endTime + timeAdded;
		
	//}

	public void periodic(){
		
		
		
	}
	
	protected void setTimeOut(long timeoutvalue){
		
		//startTime = System.currentTimeMillis();
		
		endTime = startTime + timeoutvalue;
		
		SmartDashboard.putNumber("Action Timeout Time", endTime);
		
	}

}
