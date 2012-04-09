package net.petterroea.awz2;

import java.awt.image.BufferedImage;

public class TileSgVendor extends TileVendor {

	public TileSgVendor(int gridx, int gridy) {
		super(gridx, gridy);
		// TODO Auto-generated constructor stub
	}
	@Override
	public String getUseText(EntityPlayer entityPlayer)
	{
		if(entityPlayer.points >= 1200)
		{
			return "Buy sg for 1200 points";
		}
		return "You cannot afford a sg(1200)";
	}
	@Override
	public void buy(Map map, EntityPlayer ent)
	{
		if(ent.points >= 1200 && System.currentTimeMillis() - lastBuy > 2000)
		{
			lastBuy = System.currentTimeMillis();
			ent.points = ent.points - 1200;
			add(map, ent, Weapon.getWeapon("sg"));
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
		return MediaManager.tiles.getBufferedImage(6, 1);
	}
}
