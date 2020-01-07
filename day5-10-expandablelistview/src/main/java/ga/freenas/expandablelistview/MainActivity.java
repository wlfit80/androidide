package ga.freenas.expandablelistview;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends Activity {
	private ExpandableListView mExpLv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mExpLv = findViewById(R.id.explv);
		//1.组的数据
		ArrayList<String> groupNames = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			groupNames.add("组"+i);
		}

		//2. 成员的数据
		HashMap<String, ArrayList<String>> childNames = new HashMap<>();
		for (int i = 0; i < groupNames.size(); i++) {
			ArrayList<String> childRen = new ArrayList<>();
			for (int j = 0; j < 5; j++) {
				childRen.add("成员"+j);
			}
			//遍历下组名key就是组名值就时一个成员队列
			childNames.put(groupNames.get(i),childRen);
		}
		MyAdapter adapter = new MyAdapter(groupNames,childNames);
		mExpLv.setAdapter(adapter);

		mExpLv.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

			@Override
			public void onGroupExpand(int groupPosition) {
				Toast.makeText(MainActivity.this, groupPosition+" 被展开了", Toast.LENGTH_LONG).show();
			}
		});
	}
}
