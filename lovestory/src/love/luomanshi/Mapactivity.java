package love.luomanshi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;


import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Toast;

/**
 * 显示地图，并且有图层，做标记等
 * @author Song Shi Chao
 *
 */
public class Mapactivity extends MapActivity { 
  
        private MapView mapView;
        // 19240000，-99120000
        private HttpResponse httpresponse=null;
    	private HttpEntity httpentity=null;
    	private String baseurl="http://123ttt.sinaapp.com";
    	private String recontent="";
    	
    	private String username;
        public static  int latitudeE6 = 0;
        public static  int longitudeE6 = 0;
        boolean con;
        float old;
        private String time;
        int temp;
        String str,tem;
        Socket socket;
        GeoPoint point;
        @Override
        public void onCreate(Bundle savedInstanceState) {
            
            super.onCreate(savedInstanceState);
            setContentView(R.layout.main2);
            Intent intent=getIntent();
    		username=intent.getStringExtra("user");
            
            String tmp="";
    		String finalurl=baseurl+"?code=12&user="+username;
    		HttpGet httpget=new HttpGet(finalurl);
    		HttpClient httpclient=new DefaultHttpClient();
    		InputStream inputstream=null;
    		
    		try {
				httpresponse=httpclient.execute(httpget);
				httpentity=httpresponse.getEntity();
				inputstream=httpentity.getContent();
				BufferedReader reader=new BufferedReader(new InputStreamReader(inputstream));
				
				tmp=reader.readLine();
				if(tmp.equals("00"))
				{
					Toast.makeText(getApplicationContext(), "您的情侣还未使用过定位功能", Toast.LENGTH_LONG
							).show(); 
				}
				else
				{
				latitudeE6=Integer.parseInt(tmp.substring(0, 8));
				longitudeE6=Integer.parseInt(tmp.substring(8,17));
				time=tmp.substring(17);
				}
				
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			recontent=tmp;
    		
			System.out.println(recontent);
			
			
            mapView = (MapView) findViewById(R.id.map_view);    
            // 设置显示用于缩放的工具条
            mapView.setBuiltInZoomControls(true);  
            
            // 得到所有的图层对象
            List<Overlay> mapOverlays = mapView.getOverlays();
            Drawable drawable = this.getResources().getDrawable(R.drawable.xin);
            // 生成自定义图层类得对象
            
            CustomItemizedOverlay itemizedOverlay = 
                 new CustomItemizedOverlay(drawable, this);
            
            // 创建一个GeoPoint对象，通过经纬度，指定地图上的一个点
            point = new GeoPoint(latitudeE6, longitudeE6); 
            OverlayItem overlayitem = 
                 new OverlayItem(point, "定位时间为", ""+time);
            
            itemizedOverlay.addOverlay(overlayitem);
            
            mapOverlays.add(itemizedOverlay);
            
            MapController mapController = mapView.getController();            
            mapController.animateTo(point);
            mapController.setZoom(18);

            
            
            
       	
   	
    }

	@Override
	protected boolean isRouteDisplayed() { 
		return false;
	}
}