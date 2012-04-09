package net.petterroea.awz2;

import java.awt.image.BufferedImage;
import java.util.Random;

public class TileRandomVendor extends TileVendor {

	public TileRandomVendor(int gridx, int gridy) {
		super(gridx, gridy);
		// TODO Auto-generated constructor stub
	}
	@Override
	public String getUseText(EntityPlayer entityPlayer)
	{
		if(entityPlayer.points >= 1500)
		{
			return "Buy random for 1500 points";
		}
		return "You cannot afford random(1500)";
	}
	@Override
	public void buy(Map map, EntityPlayer ent)
	{
		if(ent.points >= 1500 && System.currentTimeMillis() - lastBuy > 2000)
		{
			lastBuy = System.currentTimeMillis();
			ent.points = ent.points - 1500;
			Random rand = new Random();
			int choice = rand.nextInt(450);
			if(choice < 100)
			{
				add(map, ent, Weapon.getWeapon("gun"));
			}
			else if(choice < 200)
			{
				add(map, ent, Weapon.getWeapon("mg"));
			}
			else if(choice < 300)
			{
				add(map, ent, Weapon.getWeapon("sg"));
			}
			else if(choice < 400)
			{
				add(map, ent, Weapon.getWeapon("flame"));
			}
			else
			{
				add(map, ent, Weapon.getWeapon("ray"));
			}
			
		}
	}
	@Override
	public boolean use(Map map, EntityPlayer ent)
	{
		buy(map, ent);
		return true;
	}
	@Override
	public BufferedImage getTile(Map map)
	{
		return MediaManager.tiles.getBufferedImage(8, 1);
	}
}
