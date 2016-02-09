package Subsystems;


import edu.wpi.first.wpilibj.DoubleSolenoid;

public class Sizzors {
	public Sizzors s;
	DoubleSolenoid d = new DoubleSolenoid(2,3);
    
	public void goIn() {
		d.set(DoubleSolenoid.Value.kForward);
	}

	public void out() {
		d.set(DoubleSolenoid.Value.kReverse);
	}

	public void off() {
		d.set(DoubleSolenoid.Value.kOff);
	}
}
