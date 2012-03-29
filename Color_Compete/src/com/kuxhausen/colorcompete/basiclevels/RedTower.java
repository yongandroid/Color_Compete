package com.kuxhausen.colorcompete.basiclevels;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;

import com.kuxhausen.colorcompete.GameBoard;
import com.kuxhausen.colorcompete.GameEngine;
import com.kuxhausen.colorcompete.GamePiece;
import com.kuxhausen.colorcompete.Route;

/**
 * (c) 2012 Eric Kuxhausen
 * <p>
 * Player tower that fires projectiles
 * 
 * @author Eric Kuxhausen
 */
public class RedTower extends GamePiece {

	float speed = 1f;
	private static final int COST = 500, spawnRate = 2, spawnPoolMax = 65;
	private static final float RADIUS_HEALTH_RATIO = 2, firingRadius = 200f, HEALTH_COST_RATIO = .5f;
	public static final float RED_RADIUS = 300;//cost * healthcost * radiushealth
	private int spawnPool = 200;
	public boolean selected;
	Paint selectedP;
	Path selectedPath;
	DashPathEffect[] pathEffects;
	int phase;

	public RedTower(float xCenter, float yCenter, GameEngine gEngine, Route route) {

		p = LevelLoader.redTowerP;
		radiusHealthRatio = RADIUS_HEALTH_RATIO;
		xc = xCenter;
		yc = yCenter;
		r = route;
		gEng = gEngine;
		gb = gEng.towerMap;
		gList = gEng.towers;
		gb.register(this);
		health = COST * HEALTH_COST_RATIO;
		radius = radiusHealthRatio * (float) Math.sqrt(health);
		
		//register route
		route.clear();
		gEngine.activeRoutes.add(route);
		route.mode=Route.CHASE_MODE;
		
		//build selected indicator
		selectedP = LevelLoader.selectedP;
		pathEffects = new DashPathEffect[15];
		for (int i = 0; i < pathEffects.length; i++)
			pathEffects[i] = new DashPathEffect(new float[] { 10, 5 }, i);
		
		selectedP.setPathEffect(pathEffects[0]);
		selectedPath = new Path();
		selectedPath.addCircle(0, 0, .8f*radius, Path.Direction.CW);
	}

	@Override
	/** returns false if the piece dies */
	public boolean update() {
		// update spawnPool, don't go beyond max
		spawnPool = Math.min(spawnPool + spawnRate, spawnPoolMax);

		// check if tower should fire
		GamePiece nearestEnemy = gEng.enemyMap.getNearest(xc, yc);
		if (nearestEnemy != null
				&& spawnPool >= SimpleProjectile.COST
				&& (nearestEnemy.radius + firingRadius) > GameBoard.distanceBetween(xc, yc, nearestEnemy.xc,
						nearestEnemy.yc)) {
			gEng.projectiles.add(new SimpleProjectile(xc, yc, gEng, nearestEnemy));
			spawnPool -= SimpleProjectile.COST;
		}
		
		// update location
		r.moveAlongRoute(xc, yc, speed, this);
		
		return true;
	}
	
	@Override
	public void draw(Canvas c, float xOffset) {
		super.draw(c, xOffset);
		if(selected){
			phase++;
			selectedP.setPathEffect(pathEffects[phase % pathEffects.length]);
			selectedPath.offset(xc+xOffset, yc);
			c.drawPath(selectedPath, selectedP);
			selectedPath.offset(-(xc+xOffset), -yc);
		}
	}
	@Override
	public boolean reduceHealth(float damage) {
		boolean result = super.reduceHealth(damage);
		if(result){
			selectedPath.rewind();
			selectedPath.addCircle(0, 0, .8f*radius, Path.Direction.CW);	
		}	
		return result;
	}
	
}
