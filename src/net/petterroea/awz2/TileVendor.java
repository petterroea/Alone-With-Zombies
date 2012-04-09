package net.petterroea.awz2;

import java.awt.image.BufferedImage;

public class TileVendor extends Tile{
	static Long lastBuy = System.currentTimeMillis();
	public TileVendor(int gridx, int gridy) {
		super(gridx, gridy);
		// TODO Auto-generated constructor stub
	}
	@Override
	public String getUseText(EntityPlayer entityPlayer)
	{
		return "Do nothing";
	}
	public void add(Map map, EntityPlayer ent, Weapon wp)
	{
		if(map.currentWeapon == 0)
		{
			if(map.weapons[1] == null)
			{
				map.weapons[1] = wp;
			}
			else
			{
				map.weapons[0] = wp;
			}
		}
		else
		{
			if(map.weapons[0] == null)
			{
				map.weapons[0] = wp;
			}
			else
			{
				map.weapons[1] = wp;
			}
		}
	}
	public void buy(Map map, EntityPlayer ent)
	{
		
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
		return MediaManager.tiles.getBufferedImage(3, 4);
	}
	@Override
	public boolean collidesWith(Entity ent)
	{
		return true;
	}

}
