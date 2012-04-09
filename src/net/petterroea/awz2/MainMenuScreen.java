package net.petterroea.awz2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

public class MainMenuScreen extends Screen {
	public MainMenuScreen(Game thegame)
	{
		super(thegame);
	}
	@Override
	public void tick(int delta, Graphics g)
	{
		Rectangle mouse = new Rectangle(Input.mousex, Input.mousey, 1, 1);
		g.setColor(Color.black);
		g.fillRect(0, 0, game.WIDTH, game.HEIGHT);
		g.drawImage(MediaManager.logo.getBufferedImage(), (game.WIDTH / 2) - (MediaManager.logo.getBufferedImage().getWidth() / 2), (game.HEIGHT / 2) - 100 - (MediaManager.logo.getBufferedImage().getHeight() / 2), null);
		g.drawImage(MediaManager.button.getBufferedImage(), (game.WIDTH / 2) - (MediaManager.button.getBufferedImage().getWidth() / 2), (game.HEIGHT / 2) + 200 - (MediaManager.button.getBufferedImage().getHeight()), null);
		Rectangle stringCol = new Rectangle((game.WIDTH / 2) - (MediaManager.button.getBufferedImage().getWidth() / 2) + 100, (game.HEIGHT / 2) - (MediaManager.button.getBufferedImage().getHeight() / 2) + 185, MediaManager.font.getCharWidth()*5, MediaManager.font.getCharHeight());
		FontShader shader = null;
		if(mouse.intersects(stringCol))
		{
			shader = new FontShader(){
				@Override
				public int doPixel(int x, int y, int src, int screenx, int screeny)
				{
					int r = 0;
					int g = 0;
					int b = 0;
					Color col = new Color(r, g, b);
					//Override this
					return col.getRGB();
				}
			};
		}
		MediaManager.font.draw(g, (game.WIDTH / 2) - (MediaManager.button.getBufferedImage().getWidth() / 2) + 100, (game.HEIGHT / 2) - (MediaManager.button.getBufferedImage().getHeight() / 2) + 185, "Alone", shader);
		g.drawImage(MediaManager.button.getBufferedImage(), (game.WIDTH / 2) - (MediaManager.button.getBufferedImage().getWidth() / 2), (game.HEIGHT / 2) + 330 - (MediaManager.button.getBufferedImage().getHeight()), null);
		stringCol = new Rectangle((game.WIDTH / 2) - (MediaManager.button.getBufferedImage().getWidth() / 2) + 75, (game.HEIGHT / 2) - (MediaManager.button.getBufferedImage().getHeight() / 2) + 315, MediaManager.font.getCharWidth()*8, MediaManager.font.getCharHeight());
		shader = null;
		if(mouse.intersects(stringCol))
		{
			shader = new FontShader(){
				@Override
				public int doPixel(int x, int y, int src, int screenx, int screeny)
				{
					int r = 0;
					int g = 0;
					int b = 0;
					Color col = new Color(r, g, b);
					//Override this
					return col.getRGB();
				}
			};
		}
		MediaManager.font.draw(g, (game.WIDTH / 2) - (MediaManager.button.getBufferedImage().getWidth() / 2) + 75, (game.HEIGHT / 2) - (MediaManager.button.getBufferedImage().getHeight() / 2) + 315, "Together", shader);
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		Rectangle mouse = new Rectangle(Input.mousex, Input.mousey, 1, 1);
		Rectangle stringCol = new Rectangle((game.WIDTH / 2) - (MediaManager.button.getBufferedImage().getWidth() / 2) + 100, (game.HEIGHT / 2) - (MediaManager.button.getBufferedImage().getHeight() / 2) + 185, MediaManager.font.getCharWidth()*5, MediaManager.font.getCharHeight());
		if(mouse.intersects(stringCol))
		{
			game.screen = new MapSelectorScreen(game);
		}
		stringCol = new Rectangle((game.WIDTH / 2) - (MediaManager.button.getBufferedImage().getWidth() / 2) + 75, (game.HEIGHT / 2) - (MediaManager.button.getBufferedImage().getHeight() / 2) + 315, MediaManager.font.getCharWidth()*8, MediaManager.font.getCharHeight());
		if(mouse.intersects(stringCol))
		{
			game.screen = new MPlayLobbyScreen(game);
		}
	}
}
