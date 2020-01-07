package ga.freenas.newclient.activey;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import ga.freenas.newclient.NewsAdapter;
import ga.freenas.newclient.R;
import ga.freenas.newclient.bean.NewsBean;
import ga.freenas.newclient.util.NetworkUtil;
import ga.freenas.newclient.util.XmlUtil;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends Activity {

	private ListView mNewsLv;
	private NewsAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mNewsLv = (ListView)findViewById(R.id.lv);
		mAdapter=new NewsAdapter();
		mNewsLv.setAdapter(mAdapter);
		new Thread(){
			@Override
			public void run() {
				super.run();
				getNetworkResoutce();
			}
		}.start();
	}

	private void getNetworkResoutce() {
		String urlPath = "http://nas.freenas.ga:7070/myweb1/news.xml";
		try {
			//1. 网络获取资源
			InputStream is = NetworkUtil.getResource(urlPath);
			if (is!=null){
//				Log.v("520it","success!");
				ArrayList<NewsBean> newsBeans = XmlUtil.pareNews(is);
				mAdapter.setData(newsBeans);
				for (int i = 0; i < newsBeans.size(); i++) {
					Log.v("520it", newsBeans.get(i)+"");
				}
			}

		} catch (IOException | XmlPullParserException e) {
			e.printStackTrace();
		}
	}
}
