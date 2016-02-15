package Subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class Scissors {
	
	DoubleSolenoid scissors;
	
	public Scissors(int dS1, int dS2){
		
		scissors = new DoubleSolenoid(dS1,dS2);
		
	}
	
	public void shoot(){
		
		scissors.set(Value.kForward);
		
	}
	
	public void reload(){
		
		scissors.set(Value.kReverse);
		
	}
	
	public void off(){
		
		scissors.set(Value.kOff);
		
	}

}
