package Subsystems;

import Constants.Const;
import edu.wpi.first.wpilibj.Solenoid;

public class Suspension {
	
	Solenoid s1, s2;

	public Suspension(int ss1, int ss2){
		
		s1 = new Solenoid(ss1);
		s2 = new Solenoid(ss2);
		
	}
	
	public void shoot(){
		
		s1.set(true);
		s2.set(true);
	}
	
	public void reload(){
		
		s1.set(false);
		s2.set(false);
	}
	
}
