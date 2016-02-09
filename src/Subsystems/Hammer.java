package Subsystems;

import edu.wpi.first.wpilibj.CANTalon;

public class Hammer {
CANTalon Ham = new CANTalon(8);

public void hammerspin(double speeeeeeeeeeeeed){
	Ham.ConfigFwdLimitSwitchNormallyOpen(true);
	Ham.enableLimitSwitch(true, true);
	Ham.set(speeeeeeeeeeeeed);
}

}
