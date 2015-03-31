package com.Lemon.game2048;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.FrameLayout.LayoutParams;

/**
 *类说明  游戏中的卡片类
 *@author lemonhome
 *version V1.0
 */
public class Card extends FrameLayout {

	public int num = 0;
	private TextView label;
	private View background;//定义背景

	
	public Card(Context context) {
		super(context);
		//定义布局参数，设置-1，-1表示填满父局容器
		LayoutParams pl = new LayoutParams(-1,-1);
		pl.setMargins(10, 10, 0, 0);//设置每个卡片的边缘
		
		background = new View(getContext());//实例化背景
		pl = new LayoutParams(-1, -1);
		pl.setMargins(10, 10, 0, 0);
		background.setBackgroundColor(0x33ffffff);
		addView(background, pl);
		
		label = new TextView(getContext());//初始化
		label.setTextSize(28);//设置字符大小
		label.setGravity(Gravity.CENTER);
		label.setBackgroundColor(0x33ffffff);
		

		
		//加入布局中
		addView(label,pl);
		setNum(0);
	}

	public int getNum(){
		return num;
	}
	public void setNum(int num) {
		this.num = num;
		
		//如果数字是0的话，卡片上显示空的。有数字的话显示出来
		if (num<=0) {
			label.setText("");
		}else {
			//在android中指的是资源的id，加上空字符串，使之变成字符串
			label.setText(num+"");
		}
		//设置卡片的颜色
		switch (num) {
		case 0:
			label.setBackgroundColor(0x00000000);
			break;
		case 2:
			label.setBackgroundColor(0xffeee4da);
			break;
		case 4:
			label.setBackgroundColor(0xffede0c8);
			break;
		case 8:
			label.setBackgroundColor(0xfff2b179);
			break;
		case 16:
			label.setBackgroundColor(0xfff59563);
			break;
		case 32:
			label.setBackgroundColor(0xfff67c5f);
			break;
		case 64:
			label.setBackgroundColor(0xfff65e3b);
			break;
		case 128:
			label.setBackgroundColor(0xffedcf72);
			break;
		case 256:
			label.setBackgroundColor(0xffedcc61);
			break;
		case 512:
			label.setBackgroundColor(0xffedc850);
			break;
		case 1024:
			label.setBackgroundColor(0xffedc53f);
			break;
		case 2048:
			label.setBackgroundColor(0xffedc22e);
			break;
		default:
			label.setBackgroundColor(0xff3c3a32);
			break;
		}
	
		
	}
	
	//判断卡片是否相同
	public boolean equals(Card o) {
		// TODO Auto-generated method stub
		return getNum()==o.getNum();
	}
	
	protected Card clone(){
		Card c= new Card(getContext());
		c.setNum(getNum());
		return c;
	}
	
	public TextView getLabel() {
		return label;
	}
	

}
