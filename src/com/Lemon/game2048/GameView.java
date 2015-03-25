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
 *��˵��  ��Ϸ�Ľ���
 *@author lemonhome
 *version V1.0
 */
public class GameView extends GridLayout {

	private Card[][] cardsmap = new Card[4][4];//��ʼ��һ����Ƭ���飬�������
	private List<Point> emptyPoints=new ArrayList<Point>();//����յ�ļ���
	private boolean morge=false;//�����ж��ж�����Ĭ��Ϊfalse
	
	public GameView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		initGameView();//��ʼ����ķ���
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
	
	//��ʼ���෽��
	private void initGameView(){
		setColumnCount(4);//�趨GridLayout��4�е�
		setBackgroundColor(0xffbbada0);//�趨������ɫ
		
		//�½������¼�
		setOnTouchListener(new View.OnTouchListener() {
			private float startX,startY,offsetX,offsetY;//������ָ�մ������뿪������
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				//������ָ����ͼ
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN://��ָ����ʱ
					startX = event.getX();
					startY = event.getY();
					break;
				case MotionEvent.ACTION_UP://��ָ�뿪ʱ
					offsetX = event.getX()-startX;//�õ�������Ҫ��ȥ����ʱ������
					offsetY = event.getY()-startY;
					
					//�ж�б�����ϵ�����
					//x�ľ���ֵҪ�Ǳ�y�ľ���ֵ��Ļ���˵������ˮƽ������
					if (Math.abs(offsetX)>Math.abs(offsetY)) {
						//���offset�Ǹ���˵�������󻬶�����������Ϊ-5
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
				return true;//�����false�Ļ����޷�����onTouchDown�ȷ���
			}
		});
	}
	
	//��̬�ı俨Ƭ�Ŀ�ߣ���Ӧ������Ļ
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// TODO Auto-generated method stub
		super.onSizeChanged(w, h, oldw, oldh);
		
		//��Ϊ��Ϸ�Ľ����������Σ�ѡȡ��Ļ��С�ĳ������Ϊ�����εı߳�
		//��϶��10������4��ÿһ����Ƭ
		int cardWidth = (Math.min(w, h)-10)/4;
		addCard(cardWidth,cardWidth);
		startGame();
	}
	
	//��ӿ�Ƭ
	private void addCard(int cardWidth,int cardHeight){
		Card c;
		for (int y = 0; y <4; y++) {
			for (int x = 0; x < 4; x++) {
				c=new Card(getContext());
				c.setNum(0);//��ʼ�����0
				addView(c,cardHeight,cardWidth);
				cardsmap[x][y]=c;//�����������鸳ֵ
			}
		}
	}
	
	//��ʼ��Ϸ
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
	
	//������������
	private void addRandomNum(){
		
		emptyPoints.clear();//�Ƚ��������
		//�ȶ�����ֵ���б���
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				//�����Ƭ��ֵ�ǿ�ֵ�����ܸ�ֵ
				if (cardsmap[x][y].getNum()<=0) {
					emptyPoints.add(new Point(x,y));
				}
			}
		}
		//ѡ������Ƴ���
		Point p=emptyPoints.remove((int)(Math.random()*emptyPoints.size()));
		//���ϵĸ�ֵΪ2����Ϊ4�����ߵĸ���Ϊ9��1
		cardsmap[p.x][p.y].setNum(Math.random()>0?2:4);
	}
	
	
	//�������󻬶��ķ���
		private void swipeLeft(){
			for (int y = 0; y < 4; y++) {
				for (int x = 0; x < 4; x++) {
					//�����������
					for (int x1 = x+1; x1 < 4; x1++) {
						//�������Ŀ�Ƭ��Ϊ��ʱ
						if (cardsmap[x1][y].getNum()>0) {
							//����ߵĿ�ƬΪ0ʱҪ������ƶ�,Ȼ���ұߵ�ֵ������ߣ��ұߵ�ֵ����
							if (cardsmap[x][y].getNum()<=0) {
								cardsmap[x][y].setNum(cardsmap[x1][y].getNum());
								cardsmap[x1][y].setNum(0);
								x--;//�������е�����������ƶ���ʹ���е����ƶ��Ķ��ƶ�
								morge=true;//�ж���
								
							}//���������Ƭ��ֵ��ȣ�����ߵ�ֵ*2��Ȼ���ұߵ�ֵ����
							else if (cardsmap[x1][y].equals(cardsmap[x][y])) {
								cardsmap[x][y].setNum(cardsmap[x][y].getNum()*2);
								cardsmap[x1][y].setNum(0);
								MainActivity.getMainActivity().addScore(cardsmap[x][y].getNum());
								morge=true;
							}
							break;//ѭ������֮��Ҫ����
						}
				
					}
				}
			}
			//����ж����Ļ���������һ����Ƭ
			if (morge) {
				addRandomNum();
				chackComplete();
			}
		}
	//�������һ����ķ���
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
	
	
	
	//�������ϻ����ķ���
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
	
	//�������»����ķ���
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
	
	//�ж��Ƿ���Ϸ����
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
			new AlertDialog.Builder(getContext()).setTitle("����").setMessage("��Ϸ����").
		setPositiveButton("���¿�ʼ��Ϸ", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					startGame();
				}
				
		}).show();
			
		}
	}
	

}
