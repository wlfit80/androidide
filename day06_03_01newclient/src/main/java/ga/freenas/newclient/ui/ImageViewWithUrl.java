package ga.freenas.newclient.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.ImageView;
import ga.freenas.newclient.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ImageViewWithUrl extends ImageView {
	private static final int ACTION_LOAD_SUCCESS=0X0001;
	private static final int ACTION_LOAD_ERROR=0X0002;
	private Handler mHandler=new Handler(){
		public void handleMessage(Message msg){
			if (msg.what==ACTION_LOAD_SUCCESS){
				setImageBitmap((Bitmap) msg.obj);
			}else if (msg.what==ACTION_LOAD_ERROR){
				setImageResource(R.mipmap.ic_launcher);
			}

		}
	};

	/**
	 * 当把控件设置在xml文件中的时候,系统在渲染的时候就会调用以该构造器
	 * @param context
	 * @param attrs
	 */
	public ImageViewWithUrl(Context context,  AttributeSet attrs) {
		super(context, attrs);
	}


	/**
	 * 程序员在new一个控件的时候调用的.
	 * @param context
	 */
	public ImageViewWithUrl(Context context) {
		super(context);
	}

	/**
	 * 如果有些图片找不到,就只能替代静态图片
	 * @param urlPath
	 */
	public void setImageUrl(final String urlPath){
		new Thread(){
			@Override
			public void run() {
				super.run();
				//开始做一个网络请求
				try {
					URL url = new URL(urlPath);
					HttpURLConnection conn =  (HttpURLConnection) url.openConnection();
					if (conn.getResponseCode()==200){
						//如果成功就显示网络的图片
						InputStream is = conn.getInputStream();
						Bitmap bmp = BitmapFactory.decodeStream(is);
						Message msg = new Message();
						msg.what=ACTION_LOAD_SUCCESS;
						msg.obj=bmp;
						mHandler.sendMessage(msg);
					}else{
						// TODO 失败
						Message msg = new Message();
						msg.what=ACTION_LOAD_ERROR;
						mHandler.sendMessage(msg);
					}

				} catch (MalformedURLException e) {
					e.printStackTrace();
					// TODO 失败
					Message msg = new Message();
					msg.what=ACTION_LOAD_ERROR;
					mHandler.sendMessage(msg);
				} catch (IOException e) {
					e.printStackTrace();
					// TODO 失败
					Message msg = new Message();
					msg.what=ACTION_LOAD_ERROR;
					mHandler.sendMessage(msg);
				}
			}
		}.start();
	}


}
