package net.petterroea.awz2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

public class MPlayLobbyScreen extends Screen {

	public MPlayLobbyScreen(Game thegame) {
		super(thegame);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void tick(int delta, Graphics g)
	{
		Rectangle mouse = new Rectangle(Input.mousex, Input.mousey, 1, 1);
		g.setColor(Color.black);
		g.fillRect(0, 0, game.WIDTH, game.HEIGHT);
		g.drawImage(MediaManager.title.getBufferedImage(), (game.WIDTH / 2) - (MediaManager.title.getBufferedImage().getWidth() / 2), 0, null);
		MediaManager.font.draw(g, (game.WIDTH / 2) - ((MediaManager.font.getCharWidth()*5) / 2), 5, "Lobby", null);
		//Join game
		Rectangle col = new Rectangle((game.WIDTH/2) - ((MediaManager.font.getCharWidth()*9)/2), 200, (MediaManager.font.getCharWidth()*9), MediaManager.font.getCharHeight());
		FontShader highlight = new FontShader(){
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
		if(mouse.intersects(col))
		{
			MediaManager.font.draw(g, (game.WIDTH/2) - ((MediaManager.font.getCharWidth()*9)/2), 200, "Join game", highlight);
		}
		else
		{
			MediaManager.font.draw(g, (game.WIDTH/2) - ((MediaManager.font.getCharWidth()*9)/2), 200, "Join game", null);
		}
		//Host game
		col = new Rectangle((game.WIDTH/2) - ((MediaManager.font.getCharWidth()*9)/2), 300, (MediaManager.font.getCharWidth()*9), MediaManager.font.getCharHeight());
		if(mouse.intersects(col))
		{
			MediaManager.font.draw(g, (game.WIDTH/2) - ((MediaManager.font.getCharWidth()*9)/2), 300, "Host game", highlight);
		}
		else
		{
			MediaManager.font.draw(g, (game.WIDTH/2) - ((MediaManager.font.getCharWidth()*9)/2), 300, "Host game", null);
		} 
		//Back
		col = new Rectangle((game.WIDTH/2) - ((MediaManager.font.getCharWidth()*4)/2), 400, (MediaManager.font.getCharWidth()*4), MediaManager.font.getCharHeight());
		if(mouse.intersects(col))
		{
			MediaManager.font.draw(g, (game.WIDTH/2) - ((MediaManager.font.getCharWidth()*4)/2), 400, "Back", highlight);
		}
		else
		{
			MediaManager.font.draw(g, (game.WIDTH/2) - ((MediaManager.font.getCharWidth()*4)/2), 400, "Back", null);
		} 
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		Rectangle mouse = new Rectangle(Input.mousex, Input.mousey, 1, 1);
		Rectangle stringCol = new Rectangle((game.WIDTH/2) - ((MediaManager.font.getCharWidth()*9)/2), 200, (MediaManager.font.getCharWidth()*9), MediaManager.font.getCharHeight());
		if(mouse.intersects(stringCol))
		{
			game.screen = new JoinGameScreen(game);
		}
		stringCol = new Rectangle((game.WIDTH/2) - ((MediaManager.font.getCharWidth()*9)/2), 300, (MediaManager.font.getCharWidth()*9), MediaManager.font.getCharHeight());
		if(mouse.intersects(stringCol))
		{
			game.screen = new HostGameScreen(game);
		}
		stringCol = new Rectangle((game.WIDTH/2) - ((MediaManager.font.getCharWidth()*4)/2), 400, (MediaManager.font.getCharWidth()*4), MediaManager.font.getCharHeight());
		if(mouse.intersects(stringCol))
		{
			game.screen = new MainMenuScreen(game);
		}
	}

}
