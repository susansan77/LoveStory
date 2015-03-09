package love.luomanshi;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Toast;

public class Gallerymy extends Activity
{
	private HttpResponse httpresponse=null;
	private HttpEntity httpentity=null;
	//private String baseurl="http://123ttt.sinaapp.com";
	private String baseurl="http://10.0.2.2/lovestory/index.php";
	private String recontent="";
	private String username="";
	
  private Gallery myGallery01;
  private AnimationSet manimationSet;     
  
  ArrayList<ImageView> iii = new ArrayList<ImageView>(); 
  
  /* ��ַ���ַ��� */
  private String[] myImageURL = new String[]
  {
		  
       };
  private ImageView[] myimage = new ImageView[]
		  {
		  
		  };
  

  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    Intent intent=getIntent();
	username=intent.getStringExtra("user");
    final myInternetGalleryAdapter a=new myInternetGalleryAdapter(this);
    
    Handler h=new Handler();
    Runnable r=new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			String tmp=null;
    		String finalurl=baseurl+"?code=14&user="+username;
    		//ͨ����ѯ���ݿ�img���ű��path������username,���з���������img��¼��Ȼ����\\\\������ʽ�ֿ���Ȼ������������ʽ��������ַ��һ��һ���ļ�����Ƭ
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
    			Log.w("gallery", "recontent-->" + recontent);
    			Pattern p = Pattern.compile("[^\\\\]+");
    			Matcher m = p.matcher(recontent);
    		
    			ArrayList<String> list = new ArrayList<String>();  
    			myInternetGalleryAdapter mia=new myInternetGalleryAdapter(getApplicationContext());
    			
    			while(m.find())
    			{
    				Log.w("gallery", "m.group-->" + m.group());
    				list.add(m.group());
    				//iii.add((ImageView) mia.getImageFromNetwork(m.group()));
    			}
    			System.out.println(recontent);
    			myImageURL =  list.toArray(new String[1]);
    			myimage=iii.toArray(new ImageView[1]);
    			
    			
    			
    			
    			
    		} catch (ClientProtocolException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
			
			myGallery01 = (Gallery) findViewById(R.id.myGallery01);
		    myGallery01.setAdapter(a);
		    myGallery01.setOnItemClickListener(listener);
		}
	};
	h.postDelayed(r, 2000);
	Toast.makeText(getApplicationContext(), "��������ͼƬ�������ĵȴ�������", Toast.LENGTH_LONG
			).show(); 
  }
  
  private OnItemClickListener listener = new  OnItemClickListener(){     
      @Override     
      public void onItemClick(AdapterView<?> parent, View view, int position,     
              long id) 
      {     
            
              AnimationSet animationSet = new AnimationSet(true);     
              if(manimationSet!=null&&manimationSet!=animationSet){     
                   ScaleAnimation scaleAnimation = new ScaleAnimation(2,0.5f,2,0.5f,     
                           Animation.RELATIVE_TO_SELF,0.5f,   //ʹ�ö�������ͼƬ     
                            Animation.RELATIVE_TO_SELF,0.5f);     
                          scaleAnimation.setDuration(1000);     
                          manimationSet.addAnimation(scaleAnimation);     
                          manimationSet.setFillAfter(true); //���䱣�ֶ�������ʱ��״̬��     
                         view.startAnimation(manimationSet);     
              }     
                   ScaleAnimation scaleAnimation = new ScaleAnimation(1,2f,1,2f,     
                           Animation.RELATIVE_TO_SELF,0.5f,      
                           Animation.RELATIVE_TO_SELF,0.5f);     
                         scaleAnimation.setDuration(1000);     
                         animationSet.addAnimation(scaleAnimation);     
                         animationSet.setFillAfter(true);      
                         view.startAnimation(animationSet);     
                         manimationSet = animationSet;     
                 
              
      }

	  
  };     

  /* ʵ��BaseAdapter */
  public class myInternetGalleryAdapter extends BaseAdapter
  {
    /* ���Ա myContextΪContext���� */
    private Context myContext;
    private int mGalleryItemBackground;


    /* ������ֻ��һ����������Ҫ�����Context */
    public myInternetGalleryAdapter(Context c)
    {
      this.myContext = c;
      TypedArray a = myContext
          .obtainStyledAttributes(R.styleable.Gallery);

      /* ȡ��Gallery���Ե�Index id */
      mGalleryItemBackground = a.getResourceId(
          R.styleable.Gallery_android_galleryItemBackground, 0);

      /* �ö����styleable�����ܹ�����ʹ�� */
      a.recycle();
    }

    /* �ش������Ѷ����ͼƬ������ */
    public int getCount()
    {
      return myImageURL.length;
    }

    /* ����getItem������ȡ��Ŀǰ������Ӱ�������ID */
    public Object getItem(int position)
    {
      return position;
    }

    public long getItemId(int position)
    {
      return position;
    }

    /* ���ݾ��������λ���� ����getScale�ش�views�Ĵ�С(0.0f to 1.0f) */
    public float getScale(boolean focused, int offset)
    {
      /* Formula: 1 / (2 ^ offset) */
      return Math.max(0, 1.0f / (float) Math.pow(2, Math
          .abs(offset)));
    }

    public View getimage(String urll)
    {
  	  ImageView imageView = new ImageView(this.myContext);
        try
        {
          /* new URL�������ַ���� */
          URL aryURI = new URL("http://t1.baidu.com/it/u=2992540522,2586643032&fm=21&gp=0.jpg");
          /* ȡ������ */
          URLConnection conn = aryURI.openConnection();
          conn.connect();
          /* ȡ�ûش���InputStream */
          InputStream is = conn.getInputStream();
          /* ��InputStream���Bitmap */
          Bitmap bm = BitmapFactory.decodeStream(is);
          /* �ر�InputStream */
          is.close();
          /* �趨Bitmap��ImageView�� */
          imageView.setImageBitmap(bm);
        } catch (IOException e)
        {
          e.printStackTrace();
        }

        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        /* �趨���ImageView����Ŀ�ߣ���λΪdip */
        imageView.setLayoutParams(new Gallery.LayoutParams(200, 150));
        /* �趨Gallery����ͼ */
        imageView.setBackgroundResource(mGalleryItemBackground);
        return imageView;
  	  
    }
    
    private Bitmap getBitmapFromUrl(String imgUrl) {
        URL url;
        Bitmap bitmap = null;
        try {
                url = new URL(imgUrl);
                InputStream is = url.openConnection().getInputStream();
                BufferedInputStream bis = new BufferedInputStream(is);
                bitmap = BitmapFactory.decodeStream(bis);
                bis.close();
        } catch (MalformedURLException e) {
                e.printStackTrace();
        } catch (IOException e) {
                e.printStackTrace();
        }
        return bitmap;
}
    
    public View getImageFromNetwork(String url){
    	
    	ImageView imageView = new ImageView(getApplicationContext());
    	Bitmap bitmap = getBitmapFromUrl(url);
        imageView.setImageBitmap(bitmap);
//        imageView.invalidate();
    	
    	return imageView;
    }
    
    
    /* ȡ��Ŀǰ����ʾ��Ӱ��View����������IDֵʹ֮��ȡ����� */
    @Override
    public View getView(int position, View convertView,
        ViewGroup parent)
    {
      // TODO Auto-generated method stub
      /* ����һ��ImageView���� */
      
      return getImageFromNetwork("http://t1.baidu.com/it/u=2992540522,2586643032&fm=21&gp=0.jpg");
    }
  }
}

