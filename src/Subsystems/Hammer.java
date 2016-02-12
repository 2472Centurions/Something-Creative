package Subsystems;

import edu.wpi.first.wpilibj.CANTalon;

public class Hammer {
	
	CANTalon hammer;
	
	public Hammer(int hM){
		
		hammer = new CANTalon(hM);
		
	}
	
	public void hammerspin(double speed){
	
		hammer.ConfigFwdLimitSwitchNormallyOpen(true);
	
		hammer.enableLimitSwitch(true, true);
	
		hammer.set(speed);
	
	}

}
