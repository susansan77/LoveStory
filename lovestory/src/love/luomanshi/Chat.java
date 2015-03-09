package love.luomanshi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class Chat extends ListActivity{
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}
	private HttpResponse httpresponse=null;
	private HttpEntity httpentity=null;
	//private String baseurl="http://123ttt.sinaapp.com";
	private String baseurl="http://10.0.2.2/lovestory/index.php";
	private String recontent="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);
		Intent intent=getIntent();
		final String username=intent.getStringExtra("user");
		
		AnimationSet ani=new AnimationSet(true);
		AlphaAnimation alpha=new AlphaAnimation(0, 1);
		alpha.setDuration(1000);
		ani.addAnimation(alpha);
		ScaleAnimation scale=new ScaleAnimation(3f, 1f, 3f, 1f);
		scale.setDuration(800);
		ani.addAnimation(scale);
		LayoutAnimationController lac=new LayoutAnimationController(ani);
		lac.setOrder(LayoutAnimationController.ORDER_NORMAL);
		lac.setDelay((float) 0.15);
		getListView().setLayoutAnimation(lac);
		
		
		final EditText e1=(EditText)findViewById(R.id.editText1);
		Button b2=(Button)findViewById(R.id.button2);
		b2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.setClass(Chat.this, Chat.class);
				intent.putExtra("user", username);
				startActivity(intent);
			}
		});
		Button b1=(Button)findViewById(R.id.button1);
		b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				String content=e1.getText().toString();
				if(content.equals(""))
				{
					Toast.makeText(getApplicationContext(), "请输入信息内容", Toast.LENGTH_SHORT
							).show();
				}
				else{
				String finalurl=baseurl+"?code=3&user_send=" + username+"&content=" +java.net.URLEncoder.encode(content);
				HttpGet httpget=new HttpGet(finalurl);
				HttpClient httpclient=new DefaultHttpClient();
				//InputStream inputstream=null;
				
					try {
						httpresponse=httpclient.execute(httpget);
						httpentity=httpresponse.getEntity();
						//inputstream=httpentity.getContent();
						//BufferedReader reader=new BufferedReader(new InputStreamReader(inputstream));
						e1.setText("");
						Toast.makeText(getApplicationContext(), "已发送", Toast.LENGTH_SHORT).show(); 
					} catch (ClientProtocolException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
		}});

		
		String tmp=null;
		String finalurl=baseurl+"?code=2&user=" + username;
		HttpGet httpget=new HttpGet(finalurl);
		HttpClient httpclient=new DefaultHttpClient();
		InputStream inputstream=null;
		try {
			httpresponse=httpclient.execute(httpget);
			httpentity=httpresponse.getEntity();
			inputstream=httpentity.getContent();
			BufferedReader reader=new BufferedReader(new InputStreamReader(inputstream));
			
			while((tmp=reader.readLine())!=null)
			{
				recontent+=tmp;
			}
			
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		updatelist();
		
}
	private void updatelist()
	{
		Pattern p = Pattern.compile("[^\\/]+");
		Matcher m = p.matcher(recontent);
		
		ArrayList<HashMap<String, String>> list=new ArrayList<HashMap<String, String>>();
		while(m.find())
		{
			
			HashMap<String, String> hash=new HashMap<String, String>();
			hash.put("user_send", "发送者:"+m.group()+"  ");
			
			if(m.find())
			hash.put("user_re", "接收者:"+m.group());
			if(m.find())
			hash.put("read", m.group());
			if(m.find())
			hash.put("time", m.group());
			if(m.find())
			hash.put("con", m.group());
			
			list.add(hash);
		}
		if(list.isEmpty()==true)
		{HashMap<String, String> hash=new HashMap<String, String>();
		hash.put("content", "(无信息)");
		list.add(hash);
		}
		SpecialAdapter adapter=new SpecialAdapter(this, list, R.layout.list_list,
				new String[]{"user_send","user_re","con","time"}, new int[]{R.id.textView1
			,R.id.textView2,R.id.textView3,R.id.textView4});
		setListAdapter(adapter);
	}
	public class SpecialAdapter extends SimpleAdapter{  
	    private int[] colors = new int[]{0x31FFEBCD, 0x30FAAFFF};  
	    public SpecialAdapter(Context context, ArrayList<HashMap<String, String>> list,  
	            int resource, String[] from, int[] to) {  
	        super(context, list, resource, from, to);  
	        // TODO Auto-generated constructor stub  
	    }  
	    
	    public View getView(int position, View convertView, ViewGroup parent) {  
	        // TODO Auto-generated method stub  
	        View view = super.getView(position, convertView, parent);  
	        int colorPos = position%colors.length;  
	        view.setBackgroundColor(colors[colorPos]);  
	        return view;  
	    }  

	}
}
