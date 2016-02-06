package Subsystems;

import edu.wpi.first.wpilibj.CANTalon;

public class Winch {
	
	CANTalon winchM;
	
	
	
	public Winch(int wM){
		
		winchM = new CANTalon(wM);
		
	}
	
	

	public void Reel(){
		
		winchM.ConfigFwdLimitSwitchNormallyOpen(true);
		
		winchM.enableLimitSwitch(true, false);
		
		winchM.set(1.0);
		
	}
	
}
