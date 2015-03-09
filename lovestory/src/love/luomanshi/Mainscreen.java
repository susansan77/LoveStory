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
import android.app.AlertDialog.Builder;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
//#########changed by Susan############

//#####################################
public class Mainscreen extends Activity{
	private HttpResponse httpresponse=null;
	private HttpEntity httpentity=null;
	//private String baseurl="http://123ttt.sinaapp.com";
	//private String baseurl="127.0.0.1/index.php";
	private String baseurl="http://10.0.2.2/lovestory/index.php";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainscreen);
		DBHelper dbh=new DBHelper(Mainscreen.this, "log", null, 1);
	    SQLiteDatabase db=dbh.getReadableDatabase();
		Cursor cursor=db.rawQuery("select * from log ", null);
		
		if(cursor.moveToNext())
		{
			if(cursor.getString(cursor.getColumnIndex("user")).equals("null"))
			{
			}
			else
			{
			Intent intent=new Intent();
			intent.setClass(Mainscreen.this, TabTab.class);
			intent.putExtra("user", cursor.getString(cursor.getColumnIndex("user")));
			startActivity(intent);
			}
		}
		db.close();
		final EditText e1=(EditText)findViewById(R.id.editText1);
		final EditText e2=(EditText)findViewById(R.id.editText2);
		Button b1=(Button)findViewById(R.id.button1);
		Button b2=(Button)findViewById(R.id.button2);
		Button b3=(Button)findViewById(R.id.button3);
		b3.setOnClickListener(new View.OnClickListener() {//"¹ØÓÚ"°´Å¥
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		b2.setOnClickListener(new View.OnClickListener() {//×¢²á
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.setClass(Mainscreen.this, Register.class);//Ìø×ªµ½×¢²áÒ³Ãæ
				startActivity(intent);
			}
		});
		b1.setOnClickListener(new View.OnClickListener() {//µÇÂ¼
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String username=e1.getText().toString();
				String password=e2.getText().toString();
				String finalurl=baseurl+"?code=1&user=" + username+"&pw=" +password;
				HttpGet httpget=new HttpGet(finalurl);
				HttpClient httpclient=new DefaultHttpClient();
				InputStream inputstream=null;
				try {//·¢ËÍµÇÂ¼ÇëÇó
					httpresponse=httpclient.execute(httpget);
					httpentity=httpresponse.getEntity();
					inputstream=httpentity.getContent();
					BufferedReader reader=new BufferedReader(new InputStreamReader(inputstream));
					if(reader.readLine().equals("loginerror"))
					{
						dialog();
					}
					else
					{
						DBHelper dbh=new DBHelper(Mainscreen.this, "log", null, 1);
					    SQLiteDatabase db=dbh.getReadableDatabase();
						db.execSQL("delete from log");
						ContentValues values=new ContentValues();
						values.put("user", username);
						values.put("pw", password);
						values.put("auto", 1);
						db.insert("log",null,values);
						
						Intent intent=new Intent();
						intent.setClass(Mainscreen.this, TabTab.class);
						intent.putExtra("user", username);
						startActivity(intent);
					}
				
					
					
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				
			}
		});
		
		
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}
	protected void dialog() {
		  AlertDialog.Builder builder = new Builder(Mainscreen.this);
		  builder.setMessage("µÇÈëÊ§°Ü¡£ÕËºÅ/ÃÜÂë´íÎó");
		  builder.setTitle("ÌáÊ¾");
		  builder.setPositiveButton("È·¶¨", new OnClickListener() {
		   @Override
		   public void onClick(DialogInterface dialog, int which) {
		    dialog.dismiss();
		    
		   }
		  });
		  builder.setNegativeButton("ÃÜÂëÕÒ»Ø", new OnClickListener() {
		   @Override
		   public void onClick(DialogInterface dialog, int which) {
		    dialog.dismiss();
		   }
		  });
		  builder.create().show();
		 }

}
