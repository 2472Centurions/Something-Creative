package Subsystems;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Joystick;

public class Hammer {
	
	CANTalon hammer;
	
	public Hammer(int hM){
		
		hammer = new CANTalon(hM);
		
		hammer.ConfigFwdLimitSwitchNormallyOpen(false);
		hammer.ConfigRevLimitSwitchNormallyOpen(false);
		hammer.enableLimitSwitch(true, true);
		hammer.enableBrakeMode(true);
		
	}
	
	public void hammerspin(Joystick j){
	
		hammer.set(j.getRawAxis(1));
	
	}

}
