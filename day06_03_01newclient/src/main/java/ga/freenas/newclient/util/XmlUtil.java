package ga.freenas.newclient.util;

import android.util.Xml;
import ga.freenas.newclient.bean.NewsBean;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class XmlUtil {
	public static ArrayList<NewsBean> pareNews(InputStream is) throws XmlPullParserException, IOException {
		ArrayList<NewsBean> result = new ArrayList<NewsBean>();
		XmlPullParser pullParser = Xml.newPullParser();
		pullParser.setInput(is,"utf-8");
		//1. 开始解析XML
		//1.1 获取事件类型
		int eventType = pullParser.getEventType();
		NewsBean newsBean=null;
		while (eventType!=XmlPullParser.END_DOCUMENT){
			if (eventType == pullParser.START_TAG){
				if (pullParser.getName().equals("item")){
					newsBean = new NewsBean();
				}else if(pullParser.getName().equals("title")){
					newsBean.setTitle(pullParser.nextText());
				}else if(pullParser.getName().equals("description")){
					newsBean.setDescription(pullParser.nextText());
				}else if(pullParser.getName().equals("image")){
					newsBean.setImage(pullParser.nextText());
				}else if(pullParser.getName().equals("type")){
					newsBean.setType(Integer.parseInt(pullParser.nextText()));
				}else if(pullParser.getName().equals("comment")){
					newsBean.setComment(Integer.parseInt(pullParser.nextText()));
				}
			}else if (eventType==XmlPullParser.END_TAG){
				if (pullParser.getName().equals("item")){
					result.add(newsBean);
				}
			}

			eventType=pullParser.next();
		}

		return result;
	}
}
