package romance.route;

import java.util.ArrayList;

import love.luomanshi.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

public class SelectRoute extends Activity {
    private TextView textViewStart;
    private TextView textViewEnd;
    private Button findButton;
    private Spinner spinnerStart;
    private Spinner spinnerEnd;
    private ArrayAdapter<String> adapter;
    
    private String cityStart;
    private String cityEnd;
    
    private static final String[] cities={"深圳","广州","南宁","昆明","柳州","贵阳","株洲",
        "南昌","福州","上海","武汉","成都","厦门","兰州","西安","郑州","徐州","天津",
        "大连","乌鲁木齐","呼和浩特","北京","沈阳","长春","哈尔滨","济南","太原","合肥",
        "长沙","南京","杭州","银川","拉萨","重庆","石家庄","西宁"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.select_route);
		textViewStart = (TextView)findViewById(R.id.textViewStart);
		textViewEnd = (TextView)findViewById(R.id.textViewEnd);
		findButton = (Button)findViewById(R.id.find);
		spinnerStart = (Spinner)findViewById(R.id.spinnerStart);
		spinnerEnd = (Spinner)findViewById(R.id.spinnerEnd);
		spinnerStart.setPrompt("起始城市");
		spinnerEnd.setPrompt("终点城市");
		adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,cities);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerStart.setAdapter(adapter);  
		spinnerEnd.setAdapter(adapter);
		
		spinnerStart.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				cityStart = cities[arg2];
			//	textViewStart.setText("起始城市："+cityStart);
				spinnerStart.setVisibility(View.VISIBLE);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		spinnerEnd.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				cityEnd = cities[arg2];
			//	textViewEnd.setText("起始城市："+cityEnd);
				spinnerEnd.setVisibility(View.VISIBLE);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		findButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ArrayList<String> cities = new ArrayList<String>();
				SelectCity selectCity = new SelectCity();
				cities = (ArrayList<String>) selectCity.getPassingNodes(cityStart, cityEnd).clone();
				String fitCity = selectCity.getFitCity(cityStart, cityEnd);
				
				Intent intent=new Intent();
				intent.putExtra("cityName", cities);
				intent.putExtra("fitCity",fitCity);
				intent.setClass(SelectRoute.this, CityList.class);
				startActivity(intent);
			}
		});
		
	}
	
}
