package net.petterroea.awz2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class ScreenGameOver extends Screen {
	long time;
	public ScreenGameOver(Game thegame, long time) {
		super(thegame);
		// TODO Auto-generated constructor stub
		this.time = time;
	}
	@Override
	public void tick(int delta, Graphics g)
	{
		Rectangle mouse = new Rectangle(Input.mousex, Input.mousey, 1, 1);
		g.setColor(Color.black);
		g.fillRect(0, 0, game.WIDTH, game.HEIGHT);
		g.drawImage(MediaManager.button.getBufferedImage(), (game.getWidth() / 2) - (MediaManager.blood.getBufferedImage().getWidth() / 2), (game.getHeight()/2) - (MediaManager.button.getBufferedImage().getHeight()/2), null);
		MediaManager.font.draw(g, (game.getWidth() / 2) - ((MediaManager.font.getCharWidth()*9) / 2), (game.getHeight()/2) - (MediaManager.font.getCharHeight()/2), "YOU DIED!", null);
		Rectangle stringCol = new Rectangle((game.getWidth() / 2) - ((MediaManager.font.getCharWidth()*9) / 2), (game.getHeight()/2) - (MediaManager.font.getCharHeight()/2) - 200, (MediaManager.font.getCharWidth()+1)*9, MediaManager.font.getCharHeight());
		FontShader shader = null;
		if(mouse.intersects(stringCol))
		{
			shader = new FontShader(){
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
		}
		MediaManager.font.draw(g, (game.getWidth() / 2) - ((MediaManager.font.getCharWidth()*9) / 2), (game.getHeight()/2) - (MediaManager.font.getCharHeight()/2) - 200, "Main menu", shader);
		stringCol = new Rectangle((game.getWidth() / 2) - ((MediaManager.font.getCharWidth()*7) / 2), (game.getHeight()/2) - (MediaManager.font.getCharHeight()/2) + 200, MediaManager.font.getCharWidth()*7, MediaManager.font.getCharHeight());
		shader = null;
		if(mouse.intersects(stringCol))
		{
			shader = new FontShader(){
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
		}
		MediaManager.font.draw(g, (game.getWidth() / 2) - (((MediaManager.font.getCharWidth()+1)*7) / 2), (game.getHeight()/2) - (MediaManager.font.getCharHeight()/2) + 200, "Credits", shader);
		int min = (int)(time/60000);
		int sec = (int)((time-(min*60000))/1000);
		int ms = (int)(time-(min*60000)-(sec*1000));
		MediaManager.font.draw(g, (game.getWidth() / 2) - (((MediaManager.font.getCharWidth()+1)*7) / 2), (game.getHeight()/2) - (MediaManager.font.getCharHeight()/2) + 150, "Score: " + min + ":" + sec + ":" + ms, null);
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		Rectangle mouse = new Rectangle(Input.mousex, Input.mousey, 1, 1);
		Rectangle stringCol = new Rectangle((game.getWidth() / 2) - ((MediaManager.font.getCharWidth()*9) / 2), (game.getHeight()/2) - (MediaManager.font.getCharHeight()/2) - 200, (MediaManager.font.getCharWidth()+1)*9, MediaManager.font.getCharHeight());
		if(mouse.intersects(stringCol))
		{
			game.screen = new MainMenuScreen(game);
			try {
				MediaManager.resetMaps();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		stringCol = new Rectangle((game.getWidth() / 2) - ((MediaManager.font.getCharWidth()*7) / 2), (game.getHeight()/2) - (MediaManager.font.getCharHeight()/2) + 200, MediaManager.font.getCharWidth()*7, MediaManager.font.getCharHeight());
		if(mouse.intersects(stringCol))
		{
			game.screen = new CreditsScreen(game);
			try {
				MediaManager.resetMaps();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
