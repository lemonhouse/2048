package com.Lemon.game2048;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.PrivateCredentialPermission;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.provider.ContactsContract;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;
import android.widget.Toast;

/**
 *类说明  游戏的界面
 *@author lemonhome
 *version V1.0
 */
public class GameView extends GridLayout {

	private Card[][] cardsmap = new Card[4][4];//初始化一个卡片数组，方便操作
	private List<Point> emptyPoints=new ArrayList<Point>();//定义空点的集合
	private boolean morge=false;//定义判断有动作，默认为false
	
	public GameView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		initGameView();//初始化类的方法
	}

	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		 //TODO Auto-generated constructor stub
		initGameView();
	}

	public GameView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initGameView();
	}
	
	//初始化类方法
	private void initGameView(){
		setColumnCount(4);//设定GridLayout是4列的
		setBackgroundColor(0xffbbada0);//设定背景颜色
		
		//新建触摸事件
		setOnTouchListener(new View.OnTouchListener() {
			private float startX,startY,offsetX,offsetY;//定义手指刚触碰和离开的坐标
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				//监听手指的意图
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN://手指按下时
					startX = event.getX();
					startY = event.getY();
					break;
				case MotionEvent.ACTION_UP://手指离开时
					offsetX = event.getX()-startX;//得到的坐标要减去按下时的坐标
					offsetY = event.getY()-startY;
					
					//判断斜方向上的手势
					//x的绝对值要是比y的绝对值大的话，说明是在水平方向上
					if (Math.abs(offsetX)>Math.abs(offsetY)) {
						//如果offset是负数说明是向左滑动，有误差，设置为-5
						if (offsetX<-5) {
							swipeLeft();
						}else if(offsetX>5){
							swipeRight();
						}
					}
						else {
							if (offsetY<-5) {
								swipeUp();
							}
							else if (offsetY>5) {
								swipeDown();
							}
						}
					
					break;
				}
				return true;//如果是false的话，无法触发onTouchDown等方法
			}
		});
	}
	
	//动态改变卡片的宽高，适应所有屏幕
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// TODO Auto-generated method stub
		super.onSizeChanged(w, h, oldw, oldh);
		
		//因为游戏的界面是正方形，选取屏幕最小的长或宽作为正方形的边长
		//空隙是10，除以4是每一个卡片
		int cardWidth = (Math.min(w, h)-10)/4;
		addCard(cardWidth,cardWidth);
		startGame();
	}
	
	//添加卡片
	private void addCard(int cardWidth,int cardHeight){
		Card c;
		for (int y = 0; y <4; y++) {
			for (int x = 0; x < 4; x++) {
				c=new Card(getContext());
				c.setNum(0);//初始化添加0
				addView(c,cardHeight,cardWidth);
				cardsmap[x][y]=c;//将遍历的数组赋值
			}
		}
	}
	
	//开始游戏
	private void startGame(){
		MainActivity.getMainActivity().clearScore();
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				cardsmap[x][y].setNum(0);
			}
		}
		addRandomNum();
		addRandomNum();
	}
	
	//添加随机数方法
	private void addRandomNum(){
		
		emptyPoints.clear();//先将集合清空
		//先对所有值进行遍历
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				//如果卡片的值是空值，才能赋值
				if (cardsmap[x][y].getNum()<=0) {
					emptyPoints.add(new Point(x,y));
				}
			}
		}
		//选择随机移除点
		Point p=emptyPoints.remove((int)(Math.random()*emptyPoints.size()));
		//符合的赋值为2否则为4，两者的概论为9：1
		cardsmap[p.x][p.y].setNum(Math.random()>0?2:4);
	}
	
	
	//定义向左滑动的方法
		private void swipeLeft(){
			for (int y = 0; y < 4; y++) {
				for (int x = 0; x < 4; x++) {
					//从右向左遍历
					for (int x1 = x+1; x1 < 4; x1++) {
						//当遍历的卡片不为空时
						if (cardsmap[x1][y].getNum()>0) {
							//当左边的卡片为0时要向左边移动,然后将右边的值赋给左边，右边的值清零
							if (cardsmap[x][y].getNum()<=0) {
								cardsmap[x][y].setNum(cardsmap[x1][y].getNum());
								cardsmap[x1][y].setNum(0);
								x--;//遍历所有的情况，继续移动，使所有的能移动的都移动
								morge=true;//有动作
								
							}//如果两个卡片的值相等，则左边的值*2，然后右边的值赋零
							else if (cardsmap[x1][y].equals(cardsmap[x][y])) {
								cardsmap[x][y].setNum(cardsmap[x][y].getNum()*2);
								cardsmap[x1][y].setNum(0);
								MainActivity.getMainActivity().addScore(cardsmap[x][y].getNum());
								morge=true;
							}
							break;//循环遍历之后要跳出
						}
				
					}
				}
			}
			//如果有动作的话，则新增一个卡片
			if (morge) {
				addRandomNum();
				chackComplete();
			}
		}
	//定义向右滑动的方法
	private void swipeRight(){
		for (int y = 0; y < 4; y++) {
			for (int x = 3; x >=0; x--) {
				for (int x1 = x-1; x1 >=0; x1--) {
					if (cardsmap[x1][y].getNum()>0) {
						if (cardsmap[x][y].getNum()<=0) {
							cardsmap[x][y].setNum(cardsmap[x1][y].getNum());
							cardsmap[x1][y].setNum(0);
							x++;
							morge=true;
						}
						else if (cardsmap[x1][y].equals(cardsmap[x][y])) {
							cardsmap[x][y].setNum(cardsmap[x][y].getNum()*2);
							cardsmap[x1][y].setNum(0);
							MainActivity.getMainActivity().addScore(cardsmap[x][y].getNum());
							morge=true;
						}
						break;
					}
					
				}
			}
		}
		if (morge) {
			addRandomNum();
			chackComplete();
		}
	}
	
	
	
	//定义向上滑动的方法
	private void swipeUp(){
		for (int x = 0; x < 4; x++) {
			for (int y = 0; y < 4; y++) {
				for (int y1 = y+1; y1 < 4; y1++) {
					if (cardsmap[x][y1].getNum()>0) {
						if (cardsmap[x][y].getNum()<=0) {
							cardsmap[x][y].setNum(cardsmap[x][y1].getNum());
							cardsmap[x][y1].setNum(0);
							y--;
							morge=true;
						}
						else if (cardsmap[x][y1].equals(cardsmap[x][y])) {
							cardsmap[x][y].setNum(cardsmap[x][y].getNum()*2);
							cardsmap[x][y1].setNum(0);
							MainActivity.getMainActivity().addScore(cardsmap[x][y].getNum());
							morge=true;
						}
						break;
					}
					
				}
			}
		}
		if (morge) {
			addRandomNum();
			chackComplete();
		}
	}
	
	//定义向下滑动的方法
	private void swipeDown(){
		for (int x = 0; x < 4; x++) {
			for (int y = 3; y >=0; y--) {
				for (int y1 = y-1; y1 >=0; y1--) {
					if (cardsmap[x][y1].getNum()>0) {
						if (cardsmap[x][y].getNum()<=0) {
							cardsmap[x][y].setNum(cardsmap[x][y1].getNum());
							cardsmap[x][y1].setNum(0);
							y++;
							morge=true;
						}
						else if (cardsmap[x][y1].equals(cardsmap[x][y])) {
							cardsmap[x][y].setNum(cardsmap[x][y].getNum()*2);
							cardsmap[x][y1].setNum(0);
							MainActivity.getMainActivity().addScore(cardsmap[x][y].getNum());
							morge=true;
						}
						break;
					}
					
				}
			}
		}
		if (morge) {
			addRandomNum();
			chackComplete();
		}
	}
	
	//判断是否游戏结束
	private void chackComplete(){
		
		boolean complete=true;
		All:
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				if (cardsmap[x][y].getNum()==0||
						(x>0)&&(cardsmap[x][y].equals(cardsmap[x-1][y]))||
						(x<3)&&(cardsmap[x][y].equals(cardsmap[x+1][y]))||
						(y>0)&&(cardsmap[x][y].equals(cardsmap[x][y-1]))||
						(y<3)&&(cardsmap[x][y].equals(cardsmap[x][y+1]))){
					complete=false;
					break All;
				}
			}
		}
		if (complete) {
			new AlertDialog.Builder(getContext()).setTitle("您好").setMessage("游戏结束").
		setPositiveButton("重新开始游戏", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					startGame();
				}
				
		}).show();
			
		}
	}
	

}
