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

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.TabActivity;
import android.app.AlertDialog.Builder;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.DialogInterface.OnClickListener;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

public class TabTab extends TabActivity  
{  
	private HttpResponse httpresponse=null;
	private HttpEntity httpentity=null;
	private String baseurl="http://10.0.2.2/lovestory/index.php";
	private String recontent=null;
	private String rescontent=null;
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
	
    private TabWidget mTabWidget;  
    private TabHost mTabHost;  
    private String ttt="";
    
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
    			
    			rescontent=tmp;
    			if(rescontent.equals("yess"))
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
	
    /** Called when the activity is first created. */  
    @Override  
    public void onCreate(Bundle savedInstanceState)  
    {
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.main_tab);  
        instance++;
        registerReceiver(bcrIntenal1, new IntentFilter(INTENAL_ACTION_1));  
        
        Intent intent=getIntent();
		username=intent.getStringExtra("user");
		
		Toast.makeText(getApplicationContext(), "用户："+username+",登入成功", Toast.LENGTH_LONG).show(); 
		
        mTabHost = getTabHost(); 
        mTabWidget=getTabWidget();
        mTabWidget.setBackgroundResource(R.drawable.qw);
        //将要显示的Activity载入TabHost控件  
        //要显示的Activity由自己自由创建  
       
        
        String tmp=null;
        String finalurl=baseurl+"?code=8&user="+username;
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
				
				
				
			}
			else
			{
				dialog(recontent);
				
				
				
			}
			
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		tmp=null;
		finalurl=baseurl+"?code=5&user="+username;
		httpget=new HttpGet(finalurl);
		httpclient=new DefaultHttpClient();
		inputstream=null;

		try {
			httpresponse=httpclient.execute(httpget);
			httpentity=httpresponse.getEntity();
			inputstream=httpentity.getContent();
			BufferedReader reader=new BufferedReader(new InputStreamReader(inputstream));
			
			tmp=reader.readLine();
			
			rescontent=tmp;
			
			if(rescontent.equals("nononono"))
			{
				
				ttt="0";
				
			}
			else
			{
				ttt="1";
				
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
	    			
	    			rescontent=tmp;
	    			if(rescontent.equals("yess"))
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
		  Intent in1=new Intent(this, Tab1.class);
	        Intent in2=new Intent(this, Tab2.class);
	        Intent in3=new Intent(this, Tab3.class);
	        Intent in4=new Intent(this, Tab4.class);
	        in1.putExtra("user", username);
	        in2.putExtra("user", username);
	        in3.putExtra("user", username);
	        in4.putExtra("user", username);
	        in1.putExtra("log", ttt);
	        in2.putExtra("log", ttt);
	        in3.putExtra("log", ttt);
	        in4.putExtra("log", ttt);
	        
	        setTabIndicator("我和你", 1,in1);  
	        setTabIndicator("甜蜜影像", 2, in2);  
	        setTabIndicator("爱出行", 3, in3);  
	        setTabIndicator("定位TA的位置", 4,in4); 
    }  
      
    private void setTabIndicator(String title, int nId, Intent intent)  
    {  
        //使用指定Tab样式  
        View view = LayoutInflater.from(this.mTabHost.getContext())  
                    .inflate(R.layout.tab_style, null);  
          
        TextView text   = (TextView)view.findViewById(R.id.tab_label);  
        String strId    = String.valueOf(nId);  
          
        text.setText(title);  
          
        //创建一个新Tab  
        TabHost.TabSpec localTabSpec = mTabHost.newTabSpec(strId)  
                        .setIndicator(view).setContent(intent);  
        //加载新Tab  
        mTabHost.addTab(localTabSpec);  
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
		          notificationIntent.setClass(TabTab.this, Chat.class);
		          notificationIntent.putExtra("user", username);
					
		          
		          PendingIntent contentIntent = PendingIntent.getActivity(this, 0,notificationIntent, 0);

		           notification.setLatestEventInfo(context1, contentTitle, contentText,contentIntent); 

		   //用mNotificationManager的notify方法通知用户生成标题栏消息通知

		          mNotificationManager.notify(2, notification);
	}
    protected void dialog(String uuu) {
		  AlertDialog.Builder builder = new Builder(TabTab.this);
		  builder.setMessage("用户"+uuu+"申请与您绑定为情侣");
		  builder.setTitle("绑定请求");
		   
		  builder.setPositiveButton("同意", new OnClickListener() {
		   @Override
		   public void onClick(DialogInterface dialog, int which) {
		    
		    String tmp=null;
//		    Log.w("TabTab", "recontent="+ recontent);
			String finalurl=baseurl+"?code=9&from="+recontent+"&to="+username;
			HttpGet httpget=new HttpGet(finalurl);
			HttpClient httpclient=new DefaultHttpClient();
			InputStream inputstream=null;
		    
			try {
				httpresponse=httpclient.execute(httpget);
				httpentity=httpresponse.getEntity();
				inputstream=httpentity.getContent();
				BufferedReader reader1=new BufferedReader(new InputStreamReader(inputstream));
				
				tmp=reader1.readLine();
				
				rescontent=tmp;
				if(rescontent.equals("yess"))
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
			DBHelper dbh=new DBHelper(TabTab.this, "log", null, 1);
		    SQLiteDatabase db=dbh.getReadableDatabase();
			db.execSQL("delete from log");
			db.close();
			Intent intent=new Intent();
			intent.setClass(TabTab.this, Mainscreen.class);
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