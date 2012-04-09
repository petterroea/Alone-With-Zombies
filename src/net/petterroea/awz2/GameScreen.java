package net.petterroea.awz2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class GameScreen extends Screen {
	Map map;
	static int fovx = 0;
	static int fovy = 0;
	boolean pause = false;
	public GameScreen(Game thegame, Map map) {
		super(thegame);
		this.map = map;
		// TODO Auto-generated constructor stub
	}
	public void tick(int delta, Graphics g)
	{
		if(!pause)
		{
			map.updateEntities(delta);
		}
		map.draw(g);
		//Damage blood
		if(map.playerhealth < 80)
		{
			g.drawImage(MediaManager.blood.getBufferedImage(3 - (map.playerhealth/20), 0), fovx, fovy, null);
		}
		//Fov
		g.setColor(Color.black);
		g.fillRect(fovx, 0, MediaManager.fov.getBufferedImage().getWidth(), fovy);
		g.fillRect(fovx, fovy + MediaManager.fov.getBufferedImage().getHeight(), MediaManager.fov.getBufferedImage().getWidth(), game.HEIGHT);
		g.fillRect(0, 0, fovx, game.HEIGHT);
		g.fillRect(fovx + MediaManager.fov.getBufferedImage().getWidth(), 0, game.WIDTH - (fovx + MediaManager.fov.getBufferedImage().getWidth()), game.HEIGHT);
		g.drawImage(MediaManager.fov.getBufferedImage(), fovx, fovy, null);
		map.drawGui(g);
		if(map.playerhealth == 0)
		{
			game.screen = new ScreenGameOver(game, System.currentTimeMillis() - map.gamestart);
		}
	}
	@Override
	public void keyDown(int code) {
		if(code == KeyEvent.VK_ESCAPE)
		{
			try {
				MediaManager.resetMaps();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			game.screen = new MainMenuScreen(game);
		}
	}

}
