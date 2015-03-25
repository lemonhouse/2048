package com.Lemon.game2048;

import android.content.Context;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 *��˵��  ��Ϸ�еĿ�Ƭ��
 *@author lemonhome
 *version V1.0
 */
public class Card extends FrameLayout {

	public int num = 0;
	private TextView label;

	
	public Card(Context context) {
		super(context);
		
		label = new TextView(getContext());//��ʼ��
		label.setTextSize(32);//�����ַ���С
		label.setGravity(Gravity.CENTER);
		label.setBackgroundColor(0x33ffffff);
		
		//���岼�ֲ���������-1��-1��ʾ������������
		LayoutParams pl = new LayoutParams(-1,-1);
		pl.setMargins(10, 10, 0, 0);//����ÿ����Ƭ�ı�Ե
		
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
		
		
	}
	
	//�жϿ�Ƭ�Ƿ���ͬ
	public boolean equals(Card o) {
		// TODO Auto-generated method stub
		return getNum()==o.getNum();
	}

}
