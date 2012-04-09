package net.petterroea.awz2;

import java.awt.image.BufferedImage;

public class TileGunVendor extends TileVendor {

	public TileGunVendor(int gridx, int gridy) {
		super(gridx, gridy);
		// TODO Auto-generated constructor stub
	}
	@Override
	public String getUseText(EntityPlayer entityPlayer)
	{
		if(entityPlayer.points >= 500)
		{
			return "Buy gun for 500 points";
		}
		return "You cannot afford a gun(500)";
	}
	@Override
	public void buy(Map map, EntityPlayer ent)
	{
		if(ent.points >= 500 && System.currentTimeMillis() - lastBuy > 2000)
		{
			lastBuy = System.currentTimeMillis();
			ent.points = ent.points - 500;
			add(map, ent, Weapon.getWeapon("gun"));
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
		return MediaManager.tiles.getBufferedImage(4, 1);
	}
}
