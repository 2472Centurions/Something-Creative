package Subsystems;

import edu.wpi.first.wpilibj.CANTalon;

public class Winch {
	
	CANTalon winchM;
	
	
	
	
	public Winch(int wM){
		
		winchM = new CANTalon(wM);
		
		winchM.ConfigFwdLimitSwitchNormallyOpen(true);
		
		winchM.enableLimitSwitch(true, false);
		winchM.enableBrakeMode(true);
	}

	public void Reel(){
		
		winchM.set(1.0);
		
	}
	
	public void ReelBack(){
		
		winchM.set(-1.0);
	}
	
	public void ReelStop()
	{
		winchM.set(0.0);
	}
	
}
