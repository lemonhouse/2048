package com.Lemon.game2048;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.FrameLayout.LayoutParams;

/**
 *��˵��  ��Ϸ�еĿ�Ƭ��
 *@author lemonhome
 *version V1.0
 */
public class Card extends FrameLayout {

	public int num = 0;
	private TextView label;
	private View background;//���屳��

	
	public Card(Context context) {
		super(context);
		//���岼�ֲ���������-1��-1��ʾ������������
		LayoutParams pl = new LayoutParams(-1,-1);
		pl.setMargins(10, 10, 0, 0);//����ÿ����Ƭ�ı�Ե
		
		background = new View(getContext());//ʵ��������
		pl = new LayoutParams(-1, -1);
		pl.setMargins(10, 10, 0, 0);
		background.setBackgroundColor(0x33ffffff);
		addView(background, pl);
		
		label = new TextView(getContext());//��ʼ��
		label.setTextSize(28);//�����ַ���С
		label.setGravity(Gravity.CENTER);
		label.setBackgroundColor(0x33ffffff);
		

		
		//���벼����
		addView(label,pl);
		setNum(0);
	}

	public int getNum(){
		return num;
	}
	public void setNum(int num) {
		this.num = num;
		
		//���������0�Ļ�����Ƭ����ʾ�յġ������ֵĻ���ʾ����
		if (num<=0) {
			label.setText("");
		}else {
			//��android��ָ������Դ��id�����Ͽ��ַ�����ʹ֮����ַ���
			label.setText(num+"");
		}
		//���ÿ�Ƭ����ɫ
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
	
	//�жϿ�Ƭ�Ƿ���ͬ
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
