package Subsystems;

import edu.wpi.first.wpilibj.CANTalon;

public class Screw {
	
	CANTalon screw;
	
	public Screw(int sM){
		
		screw = new CANTalon(sM);
		
		screw.ConfigFwdLimitSwitchNormallyOpen(true);
		
		screw.enableLimitSwitch(true, true);
		screw.enableBrakeMode(true);
		
	}
	
	public void extend(){
		
		screw.set(1);
	
	}
	
	public void pullin(){
		
		screw.set(-1);
	
	}
	
	public void stopScrew(){
		
		screw.set(0);
		
	}
}
