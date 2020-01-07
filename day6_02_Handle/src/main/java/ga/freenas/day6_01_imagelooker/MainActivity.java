package ga.freenas.day6_01_imagelooker;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class MainActivity extends Activity {
	private ImageView mImageView;
	private ArrayList<String> mImageUrlPaths=new ArrayList<String>();
	private int mCurrentIndex;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mImageView =(ImageView) findViewById(R.id.center_iv);

//		android.os.NetworkOnMainThreadException
		new Thread(){
			public void run() {
				//开发步骤
				//1.从网络上读取photo.txt 并封装成为ArrayList<String>
				loadImageUrls();
				//2.创建一个索引变量 用来控制当前的显示的图片索引
				//3.根据索引 从ArrayList取出某一个字符串(图片网址) 根据网址获取图片 显示到控件上来
				loadImageByUrl(mCurrentIndex);
			}
		}.start();

	}

	/**
	 * 根据索引显示图片
	 * @param currIndex
	 */
	private void loadImageByUrl(int currIndex) {
		//如果当前的图片被缓存了就不需要执行网络请求了.

		try {
			//1.创建一个Url对象
			String imageUrl = mImageUrlPaths.get(currIndex);
			//1. 拿到的图片Url地址
			File file = new File(getFilesDir(), getImageName(imageUrl));
			//2. 拿到图片名称
			//3. /拿到图片存储时的全部路径
			if (file.exists()&&file.length()>0){
				//5. 文件存在了,还要从文件里面读取图片.
				final Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						mImageView.setImageBitmap(bitmap);
					}
				});
				return;
			}
			Log.v("520it", "loadImageByUrl currIndex="+currIndex);
			URL url=new URL(imageUrl);
			//2.打开一个链接
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			//3.判断是否能够获取资源 通过状态码来判断
			int responseCode = conn.getResponseCode();
			if (responseCode==200) {
				//4.只能返回一个流数据 输入流
				InputStream is = conn.getInputStream();

				//5.将流数据设置图片控件里面
				//Bitmap 位图 它是一张图片(图片经过一些处理)
				//1.创建一张空的图片 图片什么都没有需要你自己绘制  Bitmap.createBitmap(width, height, config)
				//2.从网络 文件里面去获取一张位图
				final Bitmap bitmap = BitmapFactory.decodeStream(is);
				//将bitmap写到文件里面去,files
				FileOutputStream fos = openFileOutput(getImageName(imageUrl),MODE_PRIVATE);
				bitmap.compress(Bitmap.CompressFormat.PNG,100,fos);

				//android.view.ViewRootImpl$CalledFromWrongThreadException:
				//Only the original thread that created a view hierarchy can touch its views.
				//original thread==Main Thread==UI Thread
				//主线程 主要是用来显示控件 处理跟用户之间的交互
				//runOnUIThread() 让代码跑回到主线程中
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						mImageView.setImageBitmap(bitmap);
					}
				});

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 加载图片的URL地址
	 */
	private void loadImageUrls() {
		try {
			//1.创建一个Url对象
			URL url=new URL(getImageUrls());
			//2.打开一个链接
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			//3.判断是否能够获取资源 通过状态码来判断
			int responseCode = conn.getResponseCode();
			Log.v("text111", String.valueOf(responseCode));
			if (responseCode==200) {
				//4.只能返回一个流数据 输入流
				InputStream is = conn.getInputStream();
				//5.因为要读取字符串 所以要使用字符流
				BufferedReader reader=new BufferedReader(new InputStreamReader(is));
				String line=null;
				while ((line=reader.readLine())!=null) {
					//读取数据
					mImageUrlPaths.add(line);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	public void preClick(View v){
		mCurrentIndex--;
		if (mCurrentIndex<0) {
			mCurrentIndex=mImageUrlPaths.size()-1;
		}
		new Thread(){
			public void run() {
				loadImageByUrl(mCurrentIndex);
			}
		}.start();
	}

	public void nextClick(View v){
		mCurrentIndex++;
		if (mCurrentIndex>mImageUrlPaths.size()-1) {
			mCurrentIndex=0;
		}
		new Thread(){
			public void run() {
				loadImageByUrl(mCurrentIndex);
			}
		}.start();
	}

	/**
	 * 获取网络地址
	 */
	private String getImageUrls(){
		return "http://nas.freenas.ga:7070/myweb1/imageAndroid.txt";
	}

	public String getImageName(String urlPath){
		//拿到最后的位置
		int lastIndexOf = urlPath.lastIndexOf("/");
		//2. 返回截取到/后面的文件名
		return urlPath.substring(lastIndexOf+1);
	}

}
