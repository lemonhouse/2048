package com.Lemon.game2048;

import android.support.v7.app.ActionBarActivity;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {
	
	//为锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷MainActivity锟斤拷实锟斤拷
	public MainActivity(){
		//一锟斤拷锟斤拷锟斤拷锟酵革拷MainActivity锟侥撅拷态锟斤拷锟斤拷锟斤拷值锟斤拷锟斤拷锟斤拷锟酵匡拷锟皆凤拷锟斤拷
		mainActivity=this;
	}

	private TextView tvScore;
	private TextView tvBestScore;
	private static MainActivity mainActivity=null;
	private int score=0;
	private GameView gameView;
	private Button btnNewGame;
	public static final String SP_KEY_BEST_SCORE = "bestScore";
	
	public static MainActivity getMainActivity() {
		return mainActivity;
	}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvScore=(TextView)findViewById(R.id.tvScore);
        tvBestScore=(TextView)findViewById(R.id.tvBestScore);
        gameView=(GameView)findViewById(R.id.gameView);
        btnNewGame=(Button)findViewById(R.id.btnNewGame);
        btnNewGame.setOnClickListener(new View.OnClickListener() {
        //animLayout=(AnimLayout) findViewById(R.id.animLayer);
			public void onClick(View v) {
				// TODO Auto-generated method stub
				gameView.startGame();
			}
		});
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    //锟斤拷占品锟�
    public void clearScore(){
    	score=0;
    	showScore(); 
    }
    //锟较计凤拷锟斤拷
    public void addScore(int s) {
		score+=s;
		showScore();
	}
    //锟斤拷示锟斤拷锟斤拷
    public void showScore() {
		tvScore.setText(score+"");
		int maxScore = Math.max(score, getBestScore());
		saveBestScore(maxScore);
		showBestScore(maxScore);
	}
	private void showBestScore(int s) {
		tvBestScore.setText("最高分"+s+"");
		
	}
	private void saveBestScore(int s) {
		Editor e = getPreferences(MODE_PRIVATE).edit();
		e.putInt(SP_KEY_BEST_SCORE, s);
		e.commit();		
	}
	private int getBestScore() {
		return getPreferences(MODE_PRIVATE).getInt(SP_KEY_BEST_SCORE, 0);
		
	}
    
}
