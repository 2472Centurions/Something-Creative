package Subsystems;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Joystick;

public class Screw {
	
	CANTalon screw;
	
	public Screw(int sM){
		
		screw = new CANTalon(sM);
		
		screw.ConfigFwdLimitSwitchNormallyOpen(false);
		screw.ConfigRevLimitSwitchNormallyOpen(false);
		screw.enableLimitSwitch(true, true);
		screw.enableBrakeMode(true);
		
	}
	
	public void screwSpin(Joystick j){
		
		screw.set(j.getRawAxis(5));
		
	}
}
