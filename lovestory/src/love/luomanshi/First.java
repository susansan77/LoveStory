package love.luomanshi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
//����ļ����ø��ģ��Ǽ��س���ĵ�һ��ͼƬʱʹ�õģ�û�����⣬��һ��activity��mainscreen
public class First extends Activity{

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.first);
		Handler handler=new Handler();
		handler.postDelayed(new hrun(), 2000);
	}
	private class hrun implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			Intent intent=new Intent();
			intent.setClass(First.this, Mainscreen.class);
			First.this.startActivity(intent);
		}
		
	}
	
}
