package com.kuxhausen.colorcompete;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

/**
 * (c) 2012 Eric Kuxhausen
 * <p>
 * Results page shown after a level completes
 * 
 * @author Eric Kuxhausen
 */
public class ResultsPage extends Activity implements OnClickListener {

	int level;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// disable title bar, ask for fullscreen mode
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		// have the system blur any windows behind this one
		// getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
		// WindowManager.LayoutParams.FLAG_BLUR_BEHIND);

		setContentView(R.layout.results);

		TextView ResultsText = (TextView) this.findViewById(R.id.resultsText);
		if (this.getIntent().getExtras().getBoolean("PLAYER_WON"))
			ResultsText.setText("You Won");
		else
			ResultsText.setText("You Lost!");

		level = this.getIntent().getExtras().getInt("LEVEL");

		TextView ScoreText = (TextView) this.findViewById(R.id.scoreText);
		ScoreText.setText("Score: " + this.getIntent().getExtras().getInt("SCORE"));

		Button NextButton = (Button) this.findViewById(R.id.nextButton);
		NextButton.setOnClickListener(this);

		Button MainButton = (Button) this.findViewById(R.id.mainButton);
		MainButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.nextButton:
			Intent iNextGame = new Intent(this, GameActivity.class);
			iNextGame.putExtra("level", level + 1);
			startActivity(iNextGame);
			break;
		case R.id.mainButton:
			Intent iMenu = new Intent(this, LaunchScreen.class);
			startActivity(iMenu);
			break;
		}
	}
}