package Subsystems;

import edu.wpi.first.wpilibj.Solenoid;

public class Sizzors {
	Solenoid d;
	
	public Sizzors()
	{
	d = new Solenoid(3);
	}
	public void goIn() {
		d.set(true);
	}

	public void out() {
		d.set(false);
	}
}
