package net.petterroea.awz2;

import java.awt.image.BufferedImage;

public class TileFlameVendor extends TileVendor {

	public TileFlameVendor(int gridx, int gridy) {
		super(gridx, gridy);
		// TODO Auto-generated constructor stub
	}
	@Override
	public String getUseText(EntityPlayer entityPlayer)
	{
		if(entityPlayer.points >= 2000)
		{
			return "Buy flamethrower for 2000 points";
		}
		return "You cannot afford a flamethrower(2000)";
	}
	@Override
	public void buy(Map map, EntityPlayer ent)
	{
		if(ent.points >= 2000 && System.currentTimeMillis() - lastBuy > 2000)
		{
			lastBuy = System.currentTimeMillis();
			ent.points = ent.points - 2000;
			add(map, ent, Weapon.getWeapon("flame"));
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
		return MediaManager.tiles.getBufferedImage(7, 1);
	}
}
