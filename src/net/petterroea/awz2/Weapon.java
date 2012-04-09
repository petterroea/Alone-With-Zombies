package net.petterroea.awz2;

import java.util.Random;

public class Weapon {
	String name = "Unnamed";
	long firetime, reloadtime;
	int maxammo, maxclip;
	int ammo, clip;
	long lastFire;
	long startReload;
	public Weapon(String name, long firetime, long reloadtime, int maxammo, int maxclip ,int ammo)
	{
		this.name = name;
		this.firetime = firetime;
		this.reloadtime = reloadtime;
		this.maxammo = maxammo;
		this.maxclip = maxclip;
		this.ammo = ammo - maxclip;
		clip = cap(ammo, 0, maxclip);
		if(this.ammo < 0)
		{
			this.ammo = 0;
		}
	}
	public boolean fire(Map map, int x, int y, double angle, EntityPlayer owner)
	{
		if(startReload == 0 || System.currentTimeMillis() - startReload > reloadtime)
		{
			startReload = 0;
			if(lastFire == 0 || System.currentTimeMillis() - lastFire > firetime)
			{
				if(clip == 0)
				{
					reload();
				}
				else
				{
					lastFire = System.currentTimeMillis();
					for(int i = 0; i < getTimes(); i++)
					{
						map.entities.add(new EntityBullet(x, y, getAim(angle), owner, getDamageFromName(), getSpecial()));
					}
					clip = clip - 1;
					return true;
				}
			}
		}
		return false;
		
	}
	/*
	 * Weapon properties getter
	 */
	private int getTimes()
	{
		if(name.equalsIgnoreCase("Shotgun"))
		{
			return 28;
		}
		if(name.equalsIgnoreCase("Flamethrower"))
		{
			return 8;
		}
		return 1;
	}
	private String getSpecial()
	{
		if(name.equalsIgnoreCase("Flamethrower"))
		{
			return "flames";
		}
		if(name.equalsIgnoreCase("Raygun"))
		{
			return "ray";
		}
		return "";
	}
	private double getAim(double reference)
	{
		double deg = Math.toDegrees(reference);
		Random rand = new Random();
		if(name.equalsIgnoreCase("gun"))
		{
			return Math.toRadians((rand.nextInt(20) - 10) + deg);
		}
		else if(name.equalsIgnoreCase("Machinegun"))
		{
			return Math.toRadians((rand.nextInt(40) - 20) + deg);
		}
		else if(name.equalsIgnoreCase("Shotgun"))
		{
			return Math.toRadians((rand.nextInt(50) - 25) + deg);
		}
		else if(name.equalsIgnoreCase("Flamethrower"))
		{
			return Math.toRadians((rand.nextInt(40) - 20) + deg);
		}
		else if(name.equalsIgnoreCase("Raygun"))
		{
			return Math.toRadians((rand.nextInt(10) - 5) + deg);
		}
		
		return reference;
	}
	private double getDamageFromName()
	{
		if(name.equalsIgnoreCase("Machinegun"))
		{
			return 0.5;
		}
		else if(name.equalsIgnoreCase("Shotgun"))
		{
			return 0.3;
		}
		else if(name.equalsIgnoreCase("Flamethrower"))
		{
			return 0.1;
		}
		else if(name.equalsIgnoreCase("Raygun"))
		{
			return 10.0;
		}
		
		return 1.0;
	}
	/*
	 * End weapon properties getter
	 */
	public void tick()
	{
		if(startReload != 0 && System.currentTimeMillis() - startReload > reloadtime)
		{
			startReload = 0;
			int addToClip = maxclip - clip;
			clip = clip + cap(ammo, 0, addToClip);
			ammo = ammo - cap(ammo, 0, addToClip);
		}
	}
	public void reload()
	{
		if(clip < maxclip)
		{
			startReload = System.currentTimeMillis();
		}
	}
	public boolean isReloading()
	{
		return (System.currentTimeMillis() - startReload < reloadtime);
	}
	public int cap(int num, int limitDown, int limitUp)
	{
		int temp = num;
		if(temp < limitDown)
		{
			temp = limitDown;
		}
		if(temp > limitUp)
		{
			temp = limitUp;
		}
		return temp;
	}
	public static Weapon getWeapon(String string) {
		if(string.equalsIgnoreCase("gun"))
		{
			return new Weapon("Gun", 500, 2000, 60, 10, 70);
		}
		if(string.equalsIgnoreCase("mg"))
		{
			return new Weapon("Machinegun", 50, 4000, 512, 64, 512);
		}
		if(string.equalsIgnoreCase("sg"))
		{
			return new Weapon("Shotgun", 650, 4000, 40, 6, 46);
		}
		if(string.equalsIgnoreCase("flame"))
		{
			return new Weapon("Flamethrower", 50, 3000, 1024, 128, 1024+128);
		}
		if(string.equalsIgnoreCase("ray"))
		{
			return new Weapon("Raygun", 300, 1000, 60, 5, 65);
		}
		System.out.println("I am about to crash the game(" + string + ")");
		return null;
	}
	public String getStatus() {
		if(isReloading())
		{
			return "Reloading";
		}
		return "";
	}
	public String getName() {
		if(name.equalsIgnoreCase("gun"))
		{
			return "gun";
		}
		else if(name.equalsIgnoreCase("Machinegun"))
		{
			return "mg";
		}
		else if(name.equalsIgnoreCase("Shotgun"))
		{
			return "sg";
		}
		else if(name.equalsIgnoreCase("Flamethrower"))
		{
			return "flame";
		}
		else if(name.equalsIgnoreCase("Raygun"))
		{
			return "ray";
		}
		System.out.println("I am about to crash the game");
		return "LAL!";
	}
}
