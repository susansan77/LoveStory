package love.luomanshi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Searchuser extends Activity{

	private HttpResponse httpresponse=null;
	private HttpEntity httpentity=null;
	private String baseurl="http://10.0.2.2/lovestory/index.php";
	private String recontent="";
	
	private String tmpname="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.searchuser);
		
		Intent intent=getIntent();
		final String username=intent.getStringExtra("user");
		final String userbind=intent.getStringExtra("bind");
		final TextView t3=(TextView)findViewById(R.id.textView3);
		final TextView t4=(TextView)findViewById(R.id.textView4);
		final TextView t5=(TextView)findViewById(R.id.textView5);
		final TextView t6=(TextView)findViewById(R.id.textView6);
		final TextView t7=(TextView)findViewById(R.id.textView7);
		final EditText e1=(EditText)findViewById(R.id.editText1);
		Button b1=(Button)findViewById(R.id.button1);
		final Button b2=(Button)findViewById(R.id.button2);
		b2.setVisibility(View.INVISIBLE);
		b2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if(t7.getText().toString().equals("是否已绑定情侣：是"))
				{
					Toast.makeText(getApplicationContext(), "对不起，对方已有情侣！", Toast.LENGTH_SHORT
							).show();
				}
				else if(userbind.equals("1"))
				{
					Toast.makeText(getApplicationContext(), "对不起，您已有情侣！只能绑定一位情侣哦。", Toast.LENGTH_SHORT
							).show();
				}
				else
				{
					
					String finalurl=baseurl+"?code=7&from="+username+"&to="+tmpname;
					HttpGet httpget=new HttpGet(finalurl);
					HttpClient httpclient=new DefaultHttpClient();
					InputStream inputstream=null;
					try {
						httpresponse=httpclient.execute(httpget);
						httpentity=httpresponse.getEntity();
						inputstream=httpentity.getContent();
						BufferedReader reader=new BufferedReader(new InputStreamReader(inputstream));
						recontent=reader.readLine();
						if(recontent.equals("yess"))
						{
							Toast.makeText(getApplicationContext(), "申请绑定情侣消息已发送", Toast.LENGTH_SHORT
									).show();
						}
						else
						{
							Toast.makeText(getApplicationContext(), "申请失败", Toast.LENGTH_SHORT
									).show();
						}
						
						
						
					} catch (ClientProtocolException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(e1.getText().toString().equals(""))
				{
					Toast.makeText(getApplicationContext(), "输入的用户名为空", Toast.LENGTH_SHORT
							).show();
					b2.setVisibility(View.INVISIBLE);
				}
				else if(e1.getText().toString().length()<4)
				{
					Toast.makeText(getApplicationContext(), "输入的用户名太短", Toast.LENGTH_SHORT
							).show();
				}
				else
				{
					String tmp=null;
					String finalurl=baseurl+"?code=6&user="+e1.getText().toString();
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
						
						Pattern p = Pattern.compile("[^\\/]+");
						Matcher m = p.matcher(recontent);
						
						while(m.find())
						{
							if(m.group().equals("nullnono")||m.group().equals("nono"))
							{
								b2.setVisibility(View.INVISIBLE);
								t3.setText("无此用户");
								t4.setText("");
								t5.setText("");
								t6.setText("");
								t7.setText("");
								
								break;
							}
							
							b2.setVisibility(View.VISIBLE);
							if(m.group().substring(0, 4).equals("yess")||m.group().substring(0, 4).equals("null")||m.group().substring(0, 4).equals("nono"))
								t3.setText("用户账号："+(tmpname=m.group().substring(4)));
							else
								t3.setText("用户账号："+(tmpname=m.group()));
							
							if(m.find())
								t4.setText("用户昵称："+m.group());
								
							if(m.find())
								t5.setText("性别："+(m.group().equals("1")?"男":"女"));
							if(m.find())
								t6.setText("所在地："+m.group());
							if(m.find())
								t7.setText("是否已绑定情侣："+(m.group().equals("1")?"是":"否"));
							
						}
						
					} catch (ClientProtocolException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		
		
		
	}

}
