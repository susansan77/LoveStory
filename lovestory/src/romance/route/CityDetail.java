package romance.route;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import love.luomanshi.R;
import android.app.Activity;
import android.os.Bundle;
import android.text.method.MovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class CityDetail extends Activity {
	private TextView cityName;
	private TextView cityBriefInfo;
	private TextView cityDetailInfo;
	private Spinner attrSpinner;
	private ArrayAdapter<String> adapter;
	
	private HttpResponse httpresponse=null;
	private HttpEntity httpentity=null;
	private String citySelected; 
	
	private String recontent="";
	//*********************************************
	//private String baseurl="http://123ttt.sinaapp.com/cityInfo.php";
	private String baseurl="http://10.0.2.2/lovestory/cityInfo.php";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.city_details);
		citySelected = getIntent().getStringExtra("citySelected");
		
		cityName = (TextView)findViewById(R.id.cityName);
		cityBriefInfo = (TextView)findViewById(R.id.cityBriefInfo);
		cityDetailInfo = (TextView)findViewById(R.id.cityDetail);
		cityDetailInfo.setMovementMethod(ScrollingMovementMethod.getInstance());
		attrSpinner = (Spinner)findViewById(R.id.attraction);
		attrSpinner.setPrompt("æ∞µ„—°‘Ò");
		
		
		String finalurl=baseurl+"?cityName="+citySelected;
		
		HttpGet httpget=new HttpGet(finalurl);
		HttpClient httpclient=new DefaultHttpClient();
		InputStream inputstream=null;
		try {
			httpresponse=httpclient.execute(httpget);
			httpentity=httpresponse.getEntity();
			inputstream=httpentity.getContent();
			BufferedReader reader=new BufferedReader(new InputStreamReader(inputstream));
			String tmp = null;
			while((tmp=reader.readLine())!=null)
			{
				recontent+=tmp;
			}
			Pattern p = Pattern.compile("[^\\/]+");
			Matcher m = p.matcher(recontent);
			
		    ArrayList<String> attractions = new ArrayList<String>();
		    while(m.find())
			{
		    	attractions.add(m.group());
		    	Log.w("attraction",m.group());
			}
		    adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,attractions);
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			attrSpinner.setAdapter(adapter);  
		  /*  for(int j=0;j<attractions.size();j++)
		    {
		    	System.out.println(attractions.get(j));
		    }*/
			final ArrayList<String> attrName = (ArrayList<String>) attractions.clone();
			attrSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					String baseurl2 = "http://10.0.2.2/lovestory/getAttr.php";
					String finalurl=baseurl2+"?attrName="+attrName.get(arg2);
					System.out.println(baseurl2+"?attrName="+attrName.get(arg2));
					HttpGet httpget=new HttpGet(finalurl);
					HttpClient httpclient=new DefaultHttpClient();
					InputStream inputstream=null;
					try {
						httpresponse=httpclient.execute(httpget);
						System.out.println(httpresponse);
						httpentity=httpresponse.getEntity();
						inputstream=httpentity.getContent();
						BufferedReader reader=new BufferedReader(new InputStreamReader(inputstream));
						String tmp = null;
						String recontent2="";
						
						while((tmp=reader.readLine())!=null)
						{//System.out.println(tmp);
							recontent2+=tmp;
						}
						System.out.println(recontent2);
						Pattern p = Pattern.compile("[^\\/]+");
						Matcher m = p.matcher(recontent2);
						String brief = "";
						String detail = "";
						if(m.find())
						{
							brief = m.group();
							cityBriefInfo.setText(brief);						}
						else
						   cityBriefInfo.setText("It's wrong!!!");
						if(m.find())
						{
							detail = m.group();
							//System.out.println(detail);
							cityDetailInfo.setText(detail);
						}
						//else
						//cityDetailInfo.setText("It's Wrong Wrong Wrong!");
					} catch (ClientProtocolException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
					
				}
			});
			cityName.setText(citySelected);
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
