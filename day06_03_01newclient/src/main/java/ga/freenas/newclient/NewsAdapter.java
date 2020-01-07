package ga.freenas.newclient;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import ga.freenas.newclient.bean.NewsBean;
import ga.freenas.newclient.ui.ImageViewWithUrl;

import java.util.ArrayList;

public class NewsAdapter extends BaseAdapter {
	private ArrayList<NewsBean> mData;
	public void setData(ArrayList<NewsBean> newsBeans) {
		mData=newsBeans;
	}

	@Override
	public int getCount() {
		return mData != null?mData.size():0;
	}
	class ViewHolder{
		TextView newsTitleIv;
		TextView newsDescIv;
		TextView newsTypeIv;
		ImageViewWithUrl newsIv;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView==null){
			LayoutInflater inflater = LayoutInflater.from(parent.getContext());
			convertView=inflater.inflate(R.layout.news_item_layout,null);
			holder = new ViewHolder();
			holder.newsIv =  convertView.findViewById(R.id.news_iv);
			holder.newsTitleIv = convertView.findViewById(R.id.title_tv);
			holder.newsDescIv =  convertView.findViewById(R.id.desc_tv);
			holder.newsTypeIv =  convertView.findViewById(R.id.type_tv);
			convertView.setTag(holder);
		}else{
			holder= (ViewHolder) convertView.getTag();
		}
		NewsBean newsBean = mData.get(position);
		holder.newsIv.setImageUrl(newsBean.getImage());
		holder.newsTitleIv.setText(newsBean.getTitle());
		holder.newsDescIv.setText(newsBean.getDescription());
		initTypeText(holder, newsBean);
		return convertView;
	}

	private void initTypeText(ViewHolder holder, NewsBean newsBean) {
		if (newsBean.getType()==1){
			holder.newsTypeIv.setText("评论:"+newsBean.getComment());
		}else	if (newsBean.getType()==2){
			holder.newsTypeIv.setText("专题");
		}else if (newsBean.getType()==3){
			holder.newsTypeIv.setText("科技");
		}
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}



}
