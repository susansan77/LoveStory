package love.luomanshi;

import android.app.Activity; 
import android.os.Bundle; 
import android.view.View; 
import android.view.View.OnClickListener; 
import android.widget.Button; 
import android.widget.ImageSwitcher; 
import android.widget.ImageView; 
import android.widget.LinearLayout; 
import android.widget.ViewSwitcher.ViewFactory;

public class Imageswitcher extends Activity implements OnClickListener, 
ViewFactory { 
// 所有要显示的图片资源索引 
private static final Integer[] imagelist = { R.drawable.ic_launcher, 
R.drawable.icon, R.drawable.ren };

// 创建ImageSwitcher对象 
private ImageSwitcher m_Switcher; 
// 索引 
private static int index = 0;

// "下一页"按钮ID 
private static final int BUTTON_DWON_ID = 0x123456; 
// “上一页”按钮ID 
private static final int BUTTON_UP_ID = 0x123457; 
// ImageSwitcher对象的ID 
private static final int SWITCHER_ID = 0x123458;

@Override 
public void onCreate(Bundle savedInstanceState) { 
super.onCreate(savedInstanceState); 
// 创建一个线性布局LinearLayout 
LinearLayout main_view = new LinearLayout(this); 
// 创建ImageSwitcher对象 
m_Switcher = new ImageSwitcher(this); 
// 在线性布局中添加ImageSwitcher视图 
main_view.addView(m_Switcher); 
// 设置ImageSwitcher对象的ID 
m_Switcher.setId(SWITCHER_ID); 
// 设置ImageSwitcher对象的数据源 
m_Switcher.setFactory((ViewFactory) this); 
m_Switcher.setImageResource(imagelist[index]);

// 设置显示上面的线性布局 
setContentView(main_view); 
// 设置背景图片 
main_view.setBackgroundResource(R.drawable.ic_launcher);

// 创建“下一张”按钮 
Button next = new Button(this); 
next.setId(BUTTON_DWON_ID); 
next.setText("下一张"); 
next.setOnClickListener((OnClickListener) this); 
LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(100, 
100); 
main_view.addView(next, param);

// 创建“上一张”按钮 
Button pre = new Button(this); 
pre.setId(BUTTON_UP_ID); 
pre.setText("上一张"); 
pre.setOnClickListener((OnClickListener) this); 
main_view.addView(pre, param); 
}

// 事件监听、处理 
public void onClick(View v) { 
switch (v.getId()) { 
// 下一页 
case BUTTON_DWON_ID: 
index++; 
if (index >= imagelist.length) { 
index = 0; 
} 
// ImageSwitcher对象资源索引 
m_Switcher.setImageResource(imagelist[index]); 
break; 
// 上一页 
case BUTTON_UP_ID: 
index--; 
if (index < 0) { 
// 到最后一张 
index = imagelist.length - 1; 
} 
// ImageSwitcher对象资源索引 
m_Switcher.setImageResource(imagelist[index]); 
default: 
break; 
} 
}

public View makeView() { 
// 将所有图片通过ImageView来显示 
return new ImageView(this); 
} 
}