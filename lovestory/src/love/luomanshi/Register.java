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
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Register extends Activity{

	private Spinner spinner;
	private ArrayAdapter<String> adapter;
	private static final String[] m={"男","女","其它"};
	private String spinnersele="男";

	private HttpResponse httpresponse=null;
	private HttpEntity httpentity=null;
	private String baseurl="http://10.0.2.2/lovestory/index.php";
	private String recontent=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		
		final EditText e1=(EditText)findViewById(R.id.editText1);
		final EditText e2=(EditText)findViewById(R.id.editText2);
		final EditText e3=(EditText)findViewById(R.id.editText3);
		
		final EditText e22=(EditText)findViewById(R.id.EditText02);
		final EditText e33=(EditText)findViewById(R.id.EditText03);
		final EditText e44=(EditText)findViewById(R.id.EditText04);
		final EditText e55=(EditText)findViewById(R.id.EditText05);
		
		spinner = (Spinner) findViewById(R.id.spinner1);
		adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,m);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);  
		spinner.setOnItemSelectedListener(new SpinnerSelectedListener());
		spinner.setVisibility(View.VISIBLE);
		
		Button b1=(Button)findViewById(R.id.button1);
		b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!e2.getText().toString().equals(e3.getText().toString()))
				{
					Toast.makeText(getApplicationContext(), "两次输入的密码不一致", Toast.LENGTH_LONG
							).show(); 
				}
				else if(spinnersele.equals("其它"))
				{
					Toast.makeText(getApplicationContext(), "对不起，您的性别有问题，不能注册！", Toast.LENGTH_LONG
							).show(); 
				}else if(e1.getText().toString().length()<4)
				{
					Toast.makeText(getApplicationContext(), "对不起，邮箱名太短", Toast.LENGTH_LONG
							).show(); 
				}
				else if(e2.getText().toString().equals("")||e3.getText().toString().equals("")||e22.getText().toString().equals("")||e33.getText().toString().equals("")||e44.getText().toString().equals("")||e55.getText().toString().equals(""))
				{
					Toast.makeText(getApplicationContext(), "对不起，注册信息填写不完全", Toast.LENGTH_LONG
							).show();
				}
				else
				{
					String tmp=null;
					String finalurl=baseurl+"?code=4&email="+e1.getText().toString()+"&pw="+e2.getText().toString()+"&pwq="+e33.getText().toString()+"&pwa="+e44.getText().toString()+"&location="+e55.getText().toString()+"&sex="+(spinnersele.equals("男")?1:2)+"&name="+e22.getText().toString();
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
						
						if(recontent.equals("right"))
						{
							Toast.makeText(getApplicationContext(), "恭喜，注册成功！", Toast.LENGTH_LONG
									).show(); 
							Intent intent=new Intent();
							intent.setClass(Register.this, TabTab.class);
							intent.putExtra("user", e1.getText().toString());
							startActivity(intent);
						}
						else if(recontent.equals("wrong"))
						{
							Toast.makeText(getApplicationContext(), "此邮箱已注册！", Toast.LENGTH_LONG
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
	}
	//使用数组形式操作
		class SpinnerSelectedListener implements OnItemSelectedListener{

			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				spinnersele=m[arg2];
			}

			public void onNothingSelected(AdapterView<?> arg0) {
			}
		}
}
