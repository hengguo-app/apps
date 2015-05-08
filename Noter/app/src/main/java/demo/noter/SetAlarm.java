package demo.noter;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class SetAlarm extends Activity {
	private ListView listV;
	private SimpleAdapter sa;
	private static final String MUSIC_PATH = new String("/sdcard/");

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.musicmain);
		System.out.println("set Alarm");
		listV = (ListView) findViewById(R.id.list);
		musicList();

		listV.setOnItemClickListener(new OnItemClickListener() {
			@SuppressWarnings("unchecked")
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Map<String, String> map = (Map<String, String>) arg0
				.getItemAtPosition(arg2);
				final String name = map.get("musicName");
				Builder adb = new Builder(SetAlarm.this);
				adb.setTitle("提示消息");
				adb.setMessage("确定要将 "+name+" 设置为默认闹铃声吗？");
				adb.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Uri uri = Uri.parse(MUSIC_PATH + name);
						ActivityManager.setUri(uri);
						Toast.makeText(SetAlarm.this, "设置成功",Toast.LENGTH_SHORT).show();
						finish();
					}
				});
				adb.setNegativeButton("取消",null);
				adb.show();
			}
		});
	}

	public void musicList() {
		System.out.println("musiclist begin");
		File home = new File(MUSIC_PATH);
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		if (home.listFiles(new MusicFilter()).length > 0) {
			for (File file : home.listFiles(new MusicFilter())) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("musicName", file.getName());
				list.add(map);
			}
			sa = new SimpleAdapter(SetAlarm.this, list, R.layout.musicitems,
					new String[] { "musicName" }, new int[] { R.id.musicName });
			listV.setAdapter(sa);
		}
	}
}

class MusicFilter implements FilenameFilter {
	public boolean accept(File dir, String name) {
		return (name.endsWith(".mp3"));
	}
}
