package love.luomanshi;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Tab2 extends Activity{

	private NotificationManager mNotificationManager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab2);
		Intent intent=getIntent();
		final String username=intent.getStringExtra("user");
		final String loglog=intent.getStringExtra("log");
		Button b3=(Button)findViewById(R.id.button3);
		Button b6=(Button)findViewById(R.id.button6);
		//设置button背景透明度
		b6.getBackground().setAlpha(80);
		b3.getBackground().setAlpha(80);
b6.setOnClickListener(new View.OnClickListener() {//上传图片
			
			@Override
			public void onClick(View v) {
				if(loglog.equals("0"))
				{
					Toast.makeText(getApplicationContext(), "您还没有情侣。", Toast.LENGTH_LONG
							).show(); 
				}
				else{
				Intent intent = new Intent(); 
		        intent.setAction("android.intent.action.VIEW"); 
		        Uri content_url = Uri.parse("http://10.0.2.2/lovestory/upload.php?user="+username); 
		        intent.setData(content_url); 
		        intent.setClassName("com.android.browser","com.android.browser.BrowserActivity"); 
		        startActivity(intent);
				}
			}
		});
b3.setOnClickListener(new View.OnClickListener() {//看图片的
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(loglog.equals("0"))
				{
					Toast.makeText(getApplicationContext(), "您还没有情侣。", Toast.LENGTH_LONG
							).show(); 
				}
				else{
				Intent intent=new Intent();
				intent.setClass(Tab2.this, Gallerymy.class);
				intent.putExtra("user", username);
				startActivity(intent);
				}
			}
		});
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		PackageManager pm = getPackageManager();
	    ResolveInfo homeInfo = pm.resolveActivity(new Intent(Intent.ACTION_MAIN)
	.addCategory(Intent.CATEGORY_HOME), 0);
		if (keyCode == KeyEvent.KEYCODE_BACK) {
            ActivityInfo ai = homeInfo.activityInfo;
            Intent startIntent = new Intent(Intent.ACTION_MAIN);
            startIntent.addCategory(Intent.CATEGORY_LAUNCHER);
            startIntent.setComponent(new ComponentName(ai.packageName,
                            ai.name));
            startActivitySafely(startIntent);
          //消息通知栏

           //定义NotificationManager

      String ns = Context.NOTIFICATION_SERVICE;

  mNotificationManager = (NotificationManager) getSystemService(ns);

  //定义通知栏展现的内容信息

         int icon = R.drawable.icon;

         CharSequence tickerText = "love story 正在运行";

        long when = System.currentTimeMillis();

         Notification notification = new Notification(icon, tickerText, when);
         notification.flags |= Notification.FLAG_AUTO_CANCEL;
          

 //定义下拉通知栏时要展现的内容信息

          Context context = getApplicationContext();

          CharSequence contentTitle = "love story";

         CharSequence contentText = "程序后台运行";

          //Intent notificationIntent = new Intent(this, Justlogin.class);
          Intent notificationIntent = new Intent(Intent.ACTION_MAIN);  
          notificationIntent.addCategory(Intent.CATEGORY_LAUNCHER);  
          notificationIntent.setComponent(new ComponentName(this.getPackageName(), this.getPackageName() + "." + this.getLocalClassName()));  
          notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
          
          
          PendingIntent contentIntent = PendingIntent.getActivity(this, 0,notificationIntent, 0);

           notification.setLatestEventInfo(context, contentTitle, contentText,contentIntent); 

   //用mNotificationManager的notify方法通知用户生成标题栏消息通知

          mNotificationManager.notify(1, notification);
            
            
            return true;
    } else
		
		
		return super.onKeyDown(keyCode, event);
	}
	void startActivitySafely(Intent intent) {
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "屏蔽back",
                    Toast.LENGTH_SHORT).show();
        } catch (SecurityException e) {
            Toast.makeText(this, "屏蔽back屏蔽",
                    Toast.LENGTH_SHORT).show();
           
        }
    }
}