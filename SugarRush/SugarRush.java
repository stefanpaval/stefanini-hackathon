package SugarRush;

import java.util.Random;

import robocode.*;
import robocode.Robot;
import static robocode.util.Utils.normalRelativeAngleDegrees;

public class SugarRush extends Robot
{
	/**
	 * run: SugarRush's default behavior
	 */
	static Random random = new Random();
	String lastMovement = "";
	int closeDistance = 270;
	int veryCloseDistance = 100;
	boolean stop = false;
	int turnDirection = 1; // Clockwise or counterclockwise
	
	boolean ramfireMode = false;
	
	public void run() 
	{
		while(true) 
		{
			if(ramfireMode == true)
			{
				ramfire_Run();
			}
			else
			{
				int n = random.nextInt(4);
		
				if (n == 0) ahead(100 + random.nextInt(100));
				if (n == 1) turnRight(random.nextInt(120));
				if (n == 2) back(random.nextInt(100) + random.nextInt(100));
				if (n == 3) turnLeft(random.nextInt(100));
				turnGunRight(360);
			}
				
		}
	}

	public void onScannedRobot(ScannedRobotEvent e) 
	{
  		// see if it's close and we have more life then him
		ramfireMode = e.getDistance() < closeDistance && getEnergy() > e.getEnergy();
		
		if(ramfireMode == true)
		{
			ramfire_OnScannedRobot(e);
		}
		else
		{
			if (e.getDistance() < veryCloseDistance)
			{
				fire(6);
			}
			if (e.getDistance() < closeDistance)
			{
				fire(3);
			}
			else
			{
				fire(1);
			}
		}
	}

	public void onHitByBullet(HitByBulletEvent e) 
	{
		if(ramfireMode == false)
		{
			turnLeft(random.nextInt(180));
			back(100);
		}
	}
	
	public void onHitWall(HitWallEvent e) 
	{
		turnRight(random.nextInt(180));
		ahead(120);
	}	
	
	public void onHitRobot(HitRobotEvent e) 
	{
		ramfire_OnHitRobot(e);
	}
	
	private void ramfire_Run()
	{
		turnRight(5 * turnDirection);
	}
	
	private void ramfire_OnScannedRobot(ScannedRobotEvent e) 
    {
		if (e.getBearing() >= 0) {
			turnDirection = 1;
		} else {
			turnDirection = -1;
		}

		turnRight(e.getBearing());
		ahead(e.getDistance() + 5);
		scan(); // Might want to move ahead again!
	}

	public void ramfire_OnHitRobot(HitRobotEvent e) {
	     double turnGunAmt = normalRelativeAngleDegrees(e.getBearing() + getHeading() - getGunHeading());

		turnGunRight(turnGunAmt);
		fire(3);
	}
	
}
