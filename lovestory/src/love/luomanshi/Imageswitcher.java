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
// ����Ҫ��ʾ��ͼƬ��Դ���� 
private static final Integer[] imagelist = { R.drawable.ic_launcher, 
R.drawable.icon, R.drawable.ren };

// ����ImageSwitcher���� 
private ImageSwitcher m_Switcher; 
// ���� 
private static int index = 0;

// "��һҳ"��ťID 
private static final int BUTTON_DWON_ID = 0x123456; 
// ����һҳ����ťID 
private static final int BUTTON_UP_ID = 0x123457; 
// ImageSwitcher�����ID 
private static final int SWITCHER_ID = 0x123458;

@Override 
public void onCreate(Bundle savedInstanceState) { 
super.onCreate(savedInstanceState); 
// ����һ�����Բ���LinearLayout 
LinearLayout main_view = new LinearLayout(this); 
// ����ImageSwitcher���� 
m_Switcher = new ImageSwitcher(this); 
// �����Բ��������ImageSwitcher��ͼ 
main_view.addView(m_Switcher); 
// ����ImageSwitcher�����ID 
m_Switcher.setId(SWITCHER_ID); 
// ����ImageSwitcher���������Դ 
m_Switcher.setFactory((ViewFactory) this); 
m_Switcher.setImageResource(imagelist[index]);

// ������ʾ��������Բ��� 
setContentView(main_view); 
// ���ñ���ͼƬ 
main_view.setBackgroundResource(R.drawable.ic_launcher);

// ��������һ�š���ť 
Button next = new Button(this); 
next.setId(BUTTON_DWON_ID); 
next.setText("��һ��"); 
next.setOnClickListener((OnClickListener) this); 
LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(100, 
100); 
main_view.addView(next, param);

// ��������һ�š���ť 
Button pre = new Button(this); 
pre.setId(BUTTON_UP_ID); 
pre.setText("��һ��"); 
pre.setOnClickListener((OnClickListener) this); 
main_view.addView(pre, param); 
}

// �¼����������� 
public void onClick(View v) { 
switch (v.getId()) { 
// ��һҳ 
case BUTTON_DWON_ID: 
index++; 
if (index >= imagelist.length) { 
index = 0; 
} 
// ImageSwitcher������Դ���� 
m_Switcher.setImageResource(imagelist[index]); 
break; 
// ��һҳ 
case BUTTON_UP_ID: 
index--; 
if (index < 0) { 
// �����һ�� 
index = imagelist.length - 1; 
} 
// ImageSwitcher������Դ���� 
m_Switcher.setImageResource(imagelist[index]); 
default: 
break; 
} 
}

public View makeView() { 
// ������ͼƬͨ��ImageView����ʾ 
return new ImageView(this); 
} 
}