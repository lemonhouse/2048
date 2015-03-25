package com.Lemon.game2048;

import android.content.Context;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 *类说明  游戏中的卡片类
 *@author lemonhome
 *version V1.0
 */
public class Card extends FrameLayout {

	public int num = 0;
	private TextView label;

	
	public Card(Context context) {
		super(context);
		
		label = new TextView(getContext());//初始化
		label.setTextSize(32);//设置字符大小
		label.setGravity(Gravity.CENTER);
		label.setBackgroundColor(0x33ffffff);
		
		//定义布局参数，设置-1，-1表示填满父局容器
		LayoutParams pl = new LayoutParams(-1,-1);
		pl.setMargins(10, 10, 0, 0);//设置每个卡片的边缘
		
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
		
		
	}
	
	//判断卡片是否相同
	public boolean equals(Card o) {
		// TODO Auto-generated method stub
		return getNum()==o.getNum();
	}

}
