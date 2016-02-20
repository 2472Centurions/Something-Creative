package Subsystems;

import edu.wpi.first.wpilibj.Solenoid;

public class IntakePnue {
	
	Solenoid iP;
	
	public IntakePnue(int sS){
		
		iP = new Solenoid(sS);
		
	}
	
	public void goIn() {
		
		iP.set(false);
		
	}

	public void out() {
		
		iP.set(true);
		
	}
}
