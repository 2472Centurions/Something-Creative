package Subsystems;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Joystick;

public class Screw {
	
	CANTalon screw;
	
	public Screw(int sM){
		
		screw = new CANTalon(sM);
		
		screw.ConfigFwdLimitSwitchNormallyOpen(true);
		screw.ConfigRevLimitSwitchNormallyOpen(true);
		screw.enableLimitSwitch(true, true);
		screw.enableBrakeMode(true);
		
	}
	
	public void screwSpin(Joystick j){
		
		screw.set(j.getRawAxis(5));
		
	}
	
	public void spin(){
		
		screw.set(1.0);
		
	}
	
	public void spinBack(){
		
		screw.set(-1.0);
		
	}
	
	public void stop(){
		
		screw.set(0.0);
		
	}
}
