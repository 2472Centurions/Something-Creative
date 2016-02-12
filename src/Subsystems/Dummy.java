package Subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class Dummy {
	
	DoubleSolenoid dummy;
	
	public Dummy(int dS1, int dS2){
		
		dummy = new DoubleSolenoid(dS1,dS2);
		
	}
	
	public void shoot(){
		
		dummy.set(Value.kForward);
		
	}
	
	public void reload(){
		
		dummy.set(Value.kReverse);
		
	}
	
	public void off(){
		
		dummy.set(Value.kOff);
		
	}

}
