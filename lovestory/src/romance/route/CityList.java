package romance.route;

import java.util.ArrayList;

import love.luomanshi.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class CityList extends Activity {

	private ListView cityList;
	private TextView fitCity;
	private ArrayAdapter<String> adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.city_list);
		
		cityList = (ListView)findViewById(R.id.cityList);
		fitCity = (TextView)findViewById(R.id.fitCity);
		ArrayList<String> temp = new ArrayList<String>();
		
		temp = getIntent().getStringArrayListExtra("cityName");
		String theFitCity = getIntent().getStringExtra("fitCity");
		//fitCity.setText(theFitCity);
		
	    String[] cities =  new String[temp.size()];
		for(int i=0;i<temp.size();i++)
		{
			cities[i]=temp.get(i);
		}
		
		adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,cities);
		cityList.setAdapter(adapter);
		final String[] cityDetail = cities;//final±‰¡ø
		cityList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				String citySelected =cityDetail[arg2] ;
				intent.putExtra("citySelected", citySelected);
				intent.setClass(CityList.this, CityDetail.class);
				startActivity(intent);
			}
			
		});
	}

	

}
