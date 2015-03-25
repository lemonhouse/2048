package com.Lemon.game2048;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {
	
	//为了在外界访问MainActivity的实例
	public MainActivity(){
		//一旦构建就给MainActivity的静态变量赋值，从外界就可以访问
		mainActivity=this;
	}

	private TextView tvScore;
	private static MainActivity mainActivity=null;
	private int score=0;
	
	public static MainActivity getMainActivity() {
		return mainActivity;
	}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvScore=(TextView)findViewById(R.id.tvScore);
        
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
    
    //清空计分
    public void clearScore(){
    	score=0;
    	showScore(); 
    }
    //合计分数
    public void addScore(int s) {
		score+=s;
		showScore();
	}
    //显示分数
    public void showScore() {
		tvScore.setText(score+"");
	}
    
}
