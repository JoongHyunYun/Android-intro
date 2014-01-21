package com.project.make;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.project.make.format.FormatActivity;
import com.project.make.http.Client_Jsp_mail;
import com.project.make.http.Client_Jsp_update;

import com.project.make.R;
import com.project.make.R.drawable;

import android.util.Log;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import android.widget.Toast;

@SuppressLint("NewApi")
public class Intro extends Activity {

	ImageView intro;
	String text,check;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.intro);

		String t = readFile();
		
		check=UpdateCheck();
		
		if("Access".equals(t)){
					new Thread(new Runnable()
					{
						public void run()
						{
							try{
								
									intro = (ImageView) findViewById(R.id.logo);
								    intro.setImageBitmap(BitmapFactory.decodeFile(getResources().getDrawable(drawable.intro).toString()));
									Animation alphaAnim = AnimationUtils.loadAnimation(Intro.this, R.anim.alpha);
									intro.startAnimation(alphaAnim);
									
									Thread.sleep(3500);
								    isMenu();
								    
									//intro.setBackground(null);
							}catch(Exception e)
							{}
						}
					}).start();
			}else{
				new Thread(new Runnable()
				{
					public void run()
					{
						try{
								intro = (ImageView) findViewById(R.id.logo);
								intro.setImageBitmap(BitmapFactory.decodeFile(getResources().getDrawable(drawable.intro).toString()));
								Animation alphaAnim = AnimationUtils.loadAnimation(Intro.this, R.anim.alpha);
								intro.startAnimation(alphaAnim);
								Thread.sleep(3500);
							    isIntro();
							    
							   // intro.setBackground(null);
						}catch(Exception e)
						{}
					}
				}).start();
			}
		
	}

	private void isIntro(){
		
		Intent intent = new Intent(this, LoginActivity.class); //intro 다음 진행될 activity
		//intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
		intent.putExtra("check", check);
		startActivity(intent);
		
		Drawable d = intro.getDrawable();
		if (d instanceof BitmapDrawable) {
		((BitmapDrawable)d).getBitmap().recycle();
		}
		
		finish();
	}
	private void isMenu(){
		Intent intent = new Intent(this, FormatActivity.class); //intro 다음 진행될 activity
		//intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
		intent.putExtra("check", check);
		intent.putExtra("Name", text);
		startActivity(intent);
		
		Drawable d = intro.getDrawable();
		if (d instanceof BitmapDrawable) {
		((BitmapDrawable)d).getBitmap().recycle();
		}
		
		finish();
	}
	
	/*writeFile 로 생성된 파일을 감지하여 값이 있다면 loginActivity를 건너뛰고 바로 FormatActivity로 intent 시킨다.*/
	private String readFile(){
		 String Path = "/data/data/eni.project.ebookmaker/files/cash.properties";	
		 File path = new File(Path);
		 String m_result="";
		try{
			if(path.exists()){
			//InputStream is = getAssets().open("cash.properties");
			FileInputStream is=new FileInputStream(path);
			int size = is.available();
			
			byte[] buffer = new byte[size];
			is.read(buffer);
			is.close();
			
			text = new String(buffer);
			//Toast.makeText(getApplicationContext(), text+"님 접속", Toast.LENGTH_SHORT).show();
			   m_result="Access";
			}else{
			   m_result="Refuse";
			}
		}catch(IOException e){
			Log.e("AssetDemo","read file from Asset",e);
		}
		return m_result;
	}

	public String UpdateCheck(){
		String result="";
		String str="",check="update";
		try {
			Client_Jsp_update load = new Client_Jsp_update();
			
			load.Setting("http://localhost:8080/web/application/Mobile/enroll_insert.jsp",mail,number); //test myPC
			load.start();
			load.join();
			str = load.getResult();
			str = str.trim();
			
			result=str;
			
		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
		}
		
		return result;
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			System.exit(0);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
