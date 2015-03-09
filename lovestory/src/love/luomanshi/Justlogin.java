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

import romance.route.SelectRoute;



import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.sqlite.SQLiteDatabase;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Justlogin extends Activity{

	private HttpResponse httpresponse=null;
	private HttpEntity httpentity=null;
	//private String baseurl="http://123ttt.sinaapp.com";
	private String baseurl="http:10.0.2.2/lovestory/index.php";
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
	private BroadcastReceiver bcrIntenal1 = new BroadcastReceiver() {  

        

        public void onReceive(Context context, Intent intent) {

        	String tmp=null;
    		String finalurl=baseurl+"?code=13&user="+username;
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
    			if(recontent.equals("yess"))
    			{
    				shownotice();
    				vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);   
    
    				vibrator.vibrate(1000);
    				            
    			}else 
    			{
    				
    			}
    		
    			
    			
    		} catch (ClientProtocolException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}

        

             

        }  

    };  
    
    static final String INTENAL_ACTION_1 = Intent.ACTION_TIME_TICK; 

    //动态注册广播消息 

    //明确指定广播接收者为bcrIntenal1,

    //而且向它广播的action信息为:"com.testBroadcastReceiver.Internal_1"

           
	private final LocationListener locationListener = new LocationListener()
	{

		@Override
		public void onLocationChanged(Location arg0)
		{
			
		}

		@Override
		public void onProviderDisabled(String arg0)
		{
			
		}

		@Override
		public void onProviderEnabled(String arg0)
		{
		}

		@Override
		public void onStatusChanged(String arg0, int arg1, Bundle arg2)
		{
		}

	};
	private void initLocation()
	{
		lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER))
		{
			ct = new Criteria();
			ct.setAccuracy(Criteria.ACCURACY_FINE);// 高精度
			ct.setAltitudeRequired(false);// 显示海拔
			ct.setBearingRequired(false);// 显示方向
			ct.setSpeedRequired(false);// 显示速度
			ct.setCostAllowed(false);// 不允许有花费
			ct.setPowerRequirement(Criteria.POWER_MEDIUM);// 低功耗
			provider = lm.getBestProvider(ct, true);
			// 位置变化监听,默认5秒一次,距离10米以上
			lm.requestLocationUpdates(provider, 5000, 10, locationListener);
			loc = lm.getLastKnownLocation(provider);
			if (loc != null)
			{
				la=(int) (loc.getLatitude() * 1E6);
				lo=(int) (loc.getLongitude() * 1E6);
				
				String finalurl=baseurl+"?code=11&la="+la+"&lo="+lo+"&user="+username;
				HttpGet httpget=new HttpGet(finalurl);
				HttpClient httpclient=new DefaultHttpClient();
				
				try {
					httpresponse=httpclient.execute(httpget);
					httpentity=httpresponse.getEntity();
					
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		} else
		{
			Toast.makeText(getApplicationContext(), "您没有打开GPS定位，如果想让您的情侣知道您的位置信息，请打开GPS功能", Toast.LENGTH_LONG
					).show(); 
		}
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		instance--;
		unregisterReceiver(bcrIntenal1);
		if(null!=vibrator){   
			vibrator.cancel();   
			}  
		super.onDestroy();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		instance++;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.justlogin);
		
		
        
		 registerReceiver(bcrIntenal1, new IntentFilter(INTENAL_ACTION_1));  
	       
		Intent intent=getIntent();
		username=intent.getStringExtra("user");
		
		
		t11=(TextView)findViewById(R.id.textView1);
		t22=(TextView)findViewById(R.id.t2);
		t22.setText("欢迎您，"+username);
		Button b1=(Button)findViewById(R.id.button1);//和我的情侣聊天
		Button b2=(Button)findViewById(R.id.button2);//搜索用户
		Button b3=(Button)findViewById(R.id.button3);//我们的图库
		Button b4=(Button)findViewById(R.id.button4);//解除绑定
		Button b5=(Button)findViewById(R.id.button5);//查看位置
		Button b6=(Button)findViewById(R.id.button6);//上传照片
		Button b7=(Button)findViewById(R.id.button7);//选择路线
		b3.setVisibility(View.INVISIBLE);
		b3.setOnClickListener(new View.OnClickListener() {//我们的图库
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.setClass(Justlogin.this, Gallerymy.class);
				intent.putExtra("user", username);
				startActivity(intent);
			}
		});
		b4.setVisibility(View.INVISIBLE);
		b5.setVisibility(View.INVISIBLE);
		b5.setOnClickListener(new View.OnClickListener() {//查看位置
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.setClass(Justlogin.this, Mapactivity.class);
				intent.putExtra("user", username);
				startActivity(intent);
			}
		});
		//上传图片
		b6.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(); 
		        intent.setAction("android.intent.action.VIEW"); 
		        //Uri content_url = Uri.parse("http://123ttt.sinaapp.com/up.php?user="+username); 
		        Uri content_url = Uri.parse("http://10.0.2.2/lovestory//up.php?user="+username);
		        intent.setData(content_url); 
		        intent.setClassName("com.android.browser","com.android.browser.BrowserActivity"); 
		        startActivity(intent);
			}
		});
		//选择路线
		b7.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.setClass(Justlogin.this, SelectRoute.class);
				startActivity(intent);
			}
		});
		b4.setOnClickListener(new View.OnClickListener() {//解除绑定
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog2();
			}
		});
		Toast.makeText(getApplicationContext(), "用户："+username+",登入成功", Toast.LENGTH_LONG).show(); 
		b2.setOnClickListener(new View.OnClickListener() {//搜索用户
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.setClass(Justlogin.this, Searchuser.class);
				intent.putExtra("user", username);
				intent.putExtra("bind", t11.getText().toString().equals("您还没有绑定情侣，快去绑定您的情侣吧。")?"0":"1");
				startActivity(intent);
			}
		});
		b1.setOnClickListener(new View.OnClickListener() {//与我的情侣聊天
			
			@Override
			public void onClick(View v){
				// TODO Auto-generated method stub
				if(t11.getText().toString().equals("您还没有绑定情侣，快去绑定您的情侣吧。"))
				{
					Toast.makeText(getApplicationContext(), "您还没有情侣。", Toast.LENGTH_LONG
							).show(); 
				}
				else
				{
				Intent intent=new Intent();
				intent.setClass(Justlogin.this, Chat.class);
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
				
				t11.setText("您还没有绑定情侣，快去绑定您的情侣吧。");
				
			}
			else
			{
				t11.setText("您已与用户 "+recontent+" 绑定情侣。");
				b4.setVisibility(View.VISIBLE);
				b5.setVisibility(View.VISIBLE);
				b3.setVisibility(View.VISIBLE);
			}
			
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		tmp=null;
		finalurl=baseurl+"?code=8&user="+username;
		httpget=new HttpGet(finalurl);
		httpclient=new DefaultHttpClient();
		inputstream=null;
		
		try {
			httpresponse=httpclient.execute(httpget);
			httpentity=httpresponse.getEntity();
			inputstream=httpentity.getContent();
			BufferedReader reader=new BufferedReader(new InputStreamReader(inputstream));
			
			tmp=reader.readLine();
			
			recontent=tmp;			
			if(recontent.equals("nononono"))
			{
				
				
				
			}
			else
			{Log.w("username", recontent);
				dialog(recontent);
				
			}
			
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Handler handler = new Handler(); 
		Runnable r=new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				initLocation();
			}
		};
		Runnable rr=new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				String tmp=null;
	    		String finalurl=baseurl+"?code=13&user="+username;
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
	    			if(recontent.equals("yess"))
	    			{
	    				shownotice();
	    				vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);   
	    
	    				vibrator.vibrate(1000);
	    				            
	    			}else 
	    			{
	    				
	    			}
	    		
	    			
	    			
	    		} catch (ClientProtocolException e) {
	    			// TODO Auto-generated catch block
	    			e.printStackTrace();
	    		} catch (IOException e) {
	    			// TODO Auto-generated catch block
	    			e.printStackTrace();
	    		}
			}
		};
		handler.postDelayed(r, 2500);
		handler.postDelayed(rr, 1500);
	}

	
	protected void dialog(String uuu) {
		  AlertDialog.Builder builder = new Builder(Justlogin.this);
		  builder.setMessage("用户"+uuu+"申请与您绑定为情侣");
		  builder.setTitle("绑定请求");
		   final String rec=uuu;
		  builder.setPositiveButton("同意", new OnClickListener() {
		   @Override
		   public void onClick(DialogInterface dialog, int which) {
		    
		    String tmp=null;
		    Log.v("from", recontent);
		    System.out.println(recontent);
			String finalurl=baseurl+"?code=9&from="+recontent+"&to="+username;
		    //String finalurl=baseurl+"?code=9&from=123@qq&to="+username;
			Log.w("from", "is"+ recontent);
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
					Toast.makeText(getApplicationContext(), "绑定成功,请重新登入", Toast.LENGTH_LONG
							).show(); 
				}
				else
				{
					Toast.makeText(getApplicationContext(), "绑定失败", Toast.LENGTH_LONG
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
		  builder.setNegativeButton("忽略", new OnClickListener() {
		   @Override
		   public void onClick(DialogInterface dialog, int which) {
		    
		    dialog.dismiss();
		   }
		  });
		  builder.create().show();
		
		 }
	
	protected void dialog2() {//这段代码已经测试通过，解绑功能实现
		  AlertDialog.Builder builder = new Builder(Justlogin.this);
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
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		menu.add(0,1,1,"注销").setIcon(android.R.drawable.ic_input_delete);
		menu.add(0,2,2,"退出程序").setIcon(android.R.drawable.ic_delete);
		
		return super.onCreateOptionsMenu(menu);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if(item.getItemId()==1)
		{
			DBHelper dbh=new DBHelper(Justlogin.this, "log", null, 1);
		    SQLiteDatabase db=dbh.getReadableDatabase();
			db.execSQL("delete from log");
			db.close();
			Intent intent=new Intent();
			intent.setClass(Justlogin.this, Mainscreen.class);
			finish();
			startActivity(intent);
			
		}
		if(item.getItemId()==2)
		{
			finish();
		}
		return super.onOptionsItemSelected(item);
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

	void shownotice()
	{
		String ns = Context.NOTIFICATION_SERVICE;

		  mNotificationManager = (NotificationManager) getSystemService(ns);

		  //定义通知栏展现的内容信息

		         int icon = R.drawable.icon;

		         CharSequence tickerText = "您的情侣发送给您新的消息";

		        long when = System.currentTimeMillis();

		         Notification notification = new Notification(icon, tickerText, when);
		         notification.flags |= Notification.FLAG_AUTO_CANCEL;
		          

		 //定义下拉通知栏时要展现的内容信息

		          Context context1 = getApplicationContext();

		          CharSequence contentTitle = "love story";

		         CharSequence contentText = "您有新的消息";

		          //Intent notificationIntent = new Intent(this, Justlogin.class);
		          Intent notificationIntent = new Intent(Intent.ACTION_MAIN);  
		          notificationIntent.setClass(Justlogin.this, Chat.class);
		          notificationIntent.putExtra("user", username);
					
		          
		          PendingIntent contentIntent = PendingIntent.getActivity(this, 0,notificationIntent, 0);

		           notification.setLatestEventInfo(context1, contentTitle, contentText,contentIntent); 

		   //用mNotificationManager的notify方法通知用户生成标题栏消息通知

		          mNotificationManager.notify(2, notification);
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