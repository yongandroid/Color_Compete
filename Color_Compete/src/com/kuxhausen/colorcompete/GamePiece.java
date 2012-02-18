package com.kuxhausen.colorcompete;

import android.graphics.Canvas;

/**
 * (c) 2012 Eric Kuxhausen
 * 
 * @author Eric Kuxhausen
 */
public abstract class GamePiece {

	float xc;
	float yc;
	float health;
	GameBoard gb;

	public abstract void update();

	public abstract void draw(Canvas c);

}
