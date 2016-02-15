package Subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class ScrewPnue {
	
	DoubleSolenoid screwPnue;
	
	public ScrewPnue(int dS1, int dS2){
		
		screwPnue = new DoubleSolenoid(dS1,dS2);
		
	}
	
	public void shoot(){
		
		screwPnue.set(Value.kForward);
		
	}
	
	public void reload(){
		
		screwPnue.set(Value.kReverse);
		
	}
	
	public void off(){
		
		screwPnue.set(Value.kOff);
		
	}

}