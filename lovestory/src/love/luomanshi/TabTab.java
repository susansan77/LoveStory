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
			ct.setAccuracy(Criteria.ACCURACY_FINE);// �߾���
			ct.setAltitudeRequired(false);// ��ʾ����
			ct.setBearingRequired(false);// ��ʾ����
			ct.setSpeedRequired(false);// ��ʾ�ٶ�
			ct.setCostAllowed(false);// �������л���
			ct.setPowerRequirement(Criteria.POWER_MEDIUM);// �͹���
			provider = lm.getBestProvider(ct, true);
			// λ�ñ仯����,Ĭ��5��һ��,����10������
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
			Toast.makeText(getApplicationContext(), "��û�д�GPS��λ�����������������֪������λ����Ϣ�����GPS����", Toast.LENGTH_LONG
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
		
		Toast.makeText(getApplicationContext(), "�û���"+username+",����ɹ�", Toast.LENGTH_LONG).show(); 
		
        mTabHost = getTabHost(); 
        mTabWidget=getTabWidget();
        mTabWidget.setBackgroundResource(R.drawable.qw);
        //��Ҫ��ʾ��Activity����TabHost�ؼ�  
        //Ҫ��ʾ��Activity���Լ����ɴ���  
       
        
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
	        
	        setTabIndicator("�Һ���", 1,in1);  
	        setTabIndicator("����Ӱ��", 2, in2);  
	        setTabIndicator("������", 3, in3);  
	        setTabIndicator("��λTA��λ��", 4,in4); 
    }  
      
    private void setTabIndicator(String title, int nId, Intent intent)  
    {  
        //ʹ��ָ��Tab��ʽ  
        View view = LayoutInflater.from(this.mTabHost.getContext())  
                    .inflate(R.layout.tab_style, null);  
          
        TextView text   = (TextView)view.findViewById(R.id.tab_label);  
        String strId    = String.valueOf(nId);  
          
        text.setText(title);  
          
        //����һ����Tab  
        TabHost.TabSpec localTabSpec = mTabHost.newTabSpec(strId)  
                        .setIndicator(view).setContent(intent);  
        //������Tab  
        mTabHost.addTab(localTabSpec);  
    }  
    void shownotice()
	{
		String ns = Context.NOTIFICATION_SERVICE;

		  mNotificationManager = (NotificationManager) getSystemService(ns);

		  //����֪ͨ��չ�ֵ�������Ϣ

		         int icon = R.drawable.icon;

		         CharSequence tickerText = "�������·��͸����µ���Ϣ";

		        long when = System.currentTimeMillis();

		         Notification notification = new Notification(icon, tickerText, when);
		         notification.flags |= Notification.FLAG_AUTO_CANCEL;
		          

		 //��������֪ͨ��ʱҪչ�ֵ�������Ϣ

		          Context context1 = getApplicationContext();

		          CharSequence contentTitle = "love story";

		         CharSequence contentText = "�����µ���Ϣ";

		          //Intent notificationIntent = new Intent(this, Justlogin.class);
		          Intent notificationIntent = new Intent(Intent.ACTION_MAIN);  
		          notificationIntent.setClass(TabTab.this, Chat.class);
		          notificationIntent.putExtra("user", username);
					
		          
		          PendingIntent contentIntent = PendingIntent.getActivity(this, 0,notificationIntent, 0);

		           notification.setLatestEventInfo(context1, contentTitle, contentText,contentIntent); 

		   //��mNotificationManager��notify����֪ͨ�û����ɱ�������Ϣ֪ͨ

		          mNotificationManager.notify(2, notification);
	}
    protected void dialog(String uuu) {
		  AlertDialog.Builder builder = new Builder(TabTab.this);
		  builder.setMessage("�û�"+uuu+"����������Ϊ����");
		  builder.setTitle("������");
		   
		  builder.setPositiveButton("ͬ��", new OnClickListener() {
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
					Toast.makeText(getApplicationContext(), "�󶨳ɹ�,�����µ���", Toast.LENGTH_LONG
							).show(); 
				}
				else
				{
					Toast.makeText(getApplicationContext(), "��ʧ��", Toast.LENGTH_LONG
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
		  builder.setNegativeButton("����", new OnClickListener() {
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
		menu.add(0,1,1,"ע��").setIcon(android.R.drawable.ic_input_delete);
		menu.add(0,2,2,"�˳�����").setIcon(android.R.drawable.ic_delete);
		
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
          //��Ϣ֪ͨ��

           //����NotificationManager

      String ns = Context.NOTIFICATION_SERVICE;

  mNotificationManager = (NotificationManager) getSystemService(ns);

  //����֪ͨ��չ�ֵ�������Ϣ

         int icon = R.drawable.icon;

         CharSequence tickerText = "love story ��������";

        long when = System.currentTimeMillis();

         Notification notification = new Notification(icon, tickerText, when);
         notification.flags |= Notification.FLAG_AUTO_CANCEL;
          

 //��������֪ͨ��ʱҪչ�ֵ�������Ϣ

          Context context = getApplicationContext();

          CharSequence contentTitle = "love story";

         CharSequence contentText = "�����̨����";

          //Intent notificationIntent = new Intent(this, Justlogin.class);
          Intent notificationIntent = new Intent(Intent.ACTION_MAIN);  
          notificationIntent.addCategory(Intent.CATEGORY_LAUNCHER);  
          notificationIntent.setComponent(new ComponentName(this.getPackageName(), this.getPackageName() + "." + this.getLocalClassName()));  
          notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
          
          
          PendingIntent contentIntent = PendingIntent.getActivity(this, 0,notificationIntent, 0);

           notification.setLatestEventInfo(context, contentTitle, contentText,contentIntent); 

   //��mNotificationManager��notify����֪ͨ�û����ɱ�������Ϣ֪ͨ

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
            Toast.makeText(this, "����back",
                    Toast.LENGTH_SHORT).show();
        } catch (SecurityException e) {
            Toast.makeText(this, "����back����",
                    Toast.LENGTH_SHORT).show();
           
        }
    }
	
} 