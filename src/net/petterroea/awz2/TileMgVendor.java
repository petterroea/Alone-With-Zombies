package net.petterroea.awz2;

import java.awt.image.BufferedImage;

public class TileMgVendor extends TileVendor {

	public TileMgVendor(int gridx, int gridy) {
		super(gridx, gridy);
		// TODO Auto-generated constructor stub
	}
	@Override
	public String getUseText(EntityPlayer entityPlayer)
	{
		if(entityPlayer.points >= 1500)
		{
			return "Buy mg for 1500 points";
		}
		return "You cannot afford a Mg(1500)";
	}
	@Override
	public void buy(Map map, EntityPlayer ent)
	{
		if(ent.points >= 1500 && System.currentTimeMillis() - lastBuy > 2000)
		{
			lastBuy = System.currentTimeMillis();
			ent.points = ent.points - 1500;
			add(map, ent, Weapon.getWeapon("mg"));
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
		return MediaManager.tiles.getBufferedImage(5, 1);
	}
}
