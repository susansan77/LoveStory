package love.luomanshi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Tab1 extends Activity{

	private HttpResponse httpresponse=null;
	private HttpEntity httpentity=null;
	//private String baseurl="http://123ttt.sinaapp.com";
	private String baseurl="http://10.0.2.2/lovestory/index.php";
	private String recontent=null;
	private String username="";
	
	private NotificationManager mNotificationManager;
	
	ProgressDialog mypDialog;
	
	private int la,lo;
	private LocationManager lm;
	private Location loc;
	private Criteria ct;
	private String provider;
	TextView t11;
	TextView t22;
	static private int instance=0;
	Vibrator vibrator; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab1);
		
		Intent intent=getIntent();
		username=intent.getStringExtra("user");
		final String loglog=intent.getStringExtra("log");
		t11=(TextView)findViewById(R.id.textView1);
		t22=(TextView)findViewById(R.id.t2);
		t11.setText("欢迎您，"+username);
		Button b1=(Button)findViewById(R.id.button1);//与情侣聊天
		Button b2=(Button)findViewById(R.id.button2);//搜索用户
		Button b4=(Button)findViewById(R.id.button4);//与情侣解除绑定
		b1.getBackground().setAlpha(80);
		b2.getBackground().setAlpha(80);
		b4.getBackground().setAlpha(80);
b4.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(loglog.equals("0"))
				{
					Toast.makeText(getApplicationContext(), "您还没有情侣。", Toast.LENGTH_LONG
							).show(); 
				}
				else
				{
				dialog2();
				}
			}
		});
b2.setOnClickListener(new View.OnClickListener() {
	
	@Override
	public void onClick(View v) {//搜索情侣的，点击跳转到searchuser，这一部分功能正常
		// TODO Auto-generated method stub
		Intent intent=new Intent();
		intent.setClass(Tab1.this, Searchuser.class);
		intent.putExtra("user", username);
		Log.w("Tab1", "substring-->"+ t22.getText().toString().subSequence(0, 7));
		intent.putExtra("bind", t22.getText().toString().subSequence(0, 8).equals("您还没有绑定情侣")?"0":"1");
		startActivity(intent);
	}
});

b1.setOnClickListener(new View.OnClickListener() {
	
	@Override
	public void onClick(View v){
		// TODO Auto-generated method stub
		if(loglog.equals("0"))
		{
			Toast.makeText(getApplicationContext(), "您还没有情侣。", Toast.LENGTH_LONG
					).show(); 
		}
		else
		{
		Intent intent=new Intent();
		intent.setClass(Tab1.this, Chat.class);
		intent.putExtra("user", username);
		startActivity(intent);
		}
		}
});

String tmp=null;
String finalurl=baseurl+"?code=5&user="+username;
HttpGet httpget=new HttpGet(finalurl);
HttpClient httpclient=new DefaultHttpClient();
InputStream inputstream=null;

try {
	httpresponse=httpclient.execute(httpget);
	httpentity=httpresponse.getEntity();
	inputstream=httpentity.getContent();
	BufferedReader reader=new BufferedReader(new InputStreamReader(inputstream));
	
	tmp=reader.readLine();
	
	recontent=tmp;
	
	if(recontent.equals("nononono"))
	{
		
		t22.setText("您还没有绑定情侣，快去绑定您的情侣吧。");
		
	}
	else
	{
		t22.setText("您已与用户 "+recontent+" 绑定情侣。");
		
	}
	
	
} catch (ClientProtocolException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
} catch (IOException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}

	}
	protected void dialog2() {
		  AlertDialog.Builder builder = new Builder(Tab1.this);
		  builder.setMessage("即使最美好的爱情，一生中也会有200次分手的念头，50次掐死对方的冲动···\n坚持，是最好的品质···\n您真的确定与您的情侣解除绑定吗？");
		  builder.setTitle("提示");
		  builder.setPositiveButton("确定", new OnClickListener() {
		   @Override
		   public void onClick(DialogInterface dialog, int which) {
			   String tmp=null;
			   System.out.println(recontent);
			   String finalurl=baseurl+"?code=10&from="+username;
				HttpGet httpget=new HttpGet(finalurl);
				HttpClient httpclient=new DefaultHttpClient();
				InputStream inputstream=null;
			    
				try {
					httpresponse=httpclient.execute(httpget);
					httpentity=httpresponse.getEntity();
					inputstream=httpentity.getContent();
					BufferedReader reader1=new BufferedReader(new InputStreamReader(inputstream));
					
					tmp=reader1.readLine();
					
					recontent=tmp;
					if(recontent.equals("yess"))
					{
						Toast.makeText(getApplicationContext(), "解除绑定成功，请重新登入", Toast.LENGTH_LONG
								).show(); 
					}
					else
					{
						Toast.makeText(getApplicationContext(), "解除绑定失败", Toast.LENGTH_LONG
								).show(); 
					}
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    dialog.dismiss();
		    
		   }
		  });
		  builder.setNegativeButton("取消", new OnClickListener() {
		   @Override
		   public void onClick(DialogInterface dialog, int which) {
		    dialog.dismiss();
		   }
		  });
		  builder.create().show();
		 }
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		PackageManager pm = getPackageManager();
	    ResolveInfo homeInfo = pm.resolveActivity(new Intent(Intent.ACTION_MAIN)
	.addCategory(Intent.CATEGORY_HOME), 0);
		if (keyCode == KeyEvent.KEYCODE_BACK&&instance<2) {
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
