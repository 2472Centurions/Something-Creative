package Subsystems;

import edu.wpi.first.wpilibj.Solenoid;

public class Sizzors {
	
	Solenoid sizzors;
	
	public Sizzors(int sS){
		
		sizzors = new Solenoid(sS);
		
	}
	
	public void goIn() {
		
		sizzors.set(true);
		
	}

	public void out() {
		
		sizzors.set(false);
		
	}
}
