package net.petterroea.awz2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

public class MapSelectorScreen extends Screen {
	public int yoffset = 0;
	public MapSelectorScreen(Game thegame) {
		super(thegame);
		// TODO Auto-generated constructor stub
	}
	public void tick(int delta, Graphics g)
	{
		g.setColor(Color.black);
		g.fillRect(0, 0, game.WIDTH, game.HEIGHT);
		g.drawImage(MediaManager.title.getBufferedImage(), (game.WIDTH / 2) - (MediaManager.title.getBufferedImage().getWidth() / 2), 0, null);
		MediaManager.font.draw(g, (game.WIDTH / 2) - ((MediaManager.font.getCharWidth()*10) / 2), 5, "Select map", null);
		Rectangle mouse = new Rectangle(Input.mousex, Input.mousey, 1, 1);
		FontShader shader = new FontShader(){
			@Override
			public int doPixel(int x, int y, int src, int screenx, int screeny)
			{
				int r = 180;
				int g = 0;
				int b = 0;
				Color col = new Color(r, g, b);
				//Override this
				return col.getRGB();
			}
		};
		for(int i = 0; i < MediaManager.maps.size(); i++)
		{
			Rectangle current = new Rectangle(0, 150 + yoffset + (i * MediaManager.font.getCharHeight() + (i*15)), game.WIDTH, MediaManager.font.getCharHeight());
			if(mouse.intersects(current))
			{
				MediaManager.font.draw(g, 50, 150 + yoffset + (i * MediaManager.font.getCharHeight() + (i*15)), MediaManager.maps.get(i).name, shader);
			}
			else
			{
				MediaManager.font.draw(g, 50, 150 + yoffset + (i * MediaManager.font.getCharHeight() + (i*15)), MediaManager.maps.get(i).name, null);
			}
		}
		g.setColor(Color.white);
		//Stats
		g.drawLine(0, 130, game.WIDTH, 130);
		MediaManager.font.draw(g, 50, 125 - (MediaManager.font.getCharHeight()), "Name", null);
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		if(arg0.getY() > 150)
		{
			Rectangle mouse = new Rectangle(arg0.getX(), arg0.getY(), 1, 1);
			for(int i = 0; i < MediaManager.maps.size(); i++)
			{
				Rectangle current = new Rectangle(0, 150 + yoffset + (i * MediaManager.font.getCharHeight() + (i*15)), game.WIDTH, MediaManager.font.getCharHeight());
				if(mouse.intersects(current))
				{
					game.screen = new GameScreen(game, MediaManager.maps.get(i));
					return;
				}
			}
		}
	}

}
