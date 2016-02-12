package Subsystems;

import edu.wpi.first.wpilibj.CANTalon;

public class Screw {
	
	CANTalon screw;
	
	public Screw(int sM){
		
		screw = new CANTalon(sM);
		
	}
	
	public void extend(){
		
		screw.set(1);
	
	}
	
	public void pullin(){
		
		screw.set(-1);
	
	}
	
}
