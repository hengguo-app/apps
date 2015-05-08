package demo.noter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class Look extends Activity {
	private EditText etName,etMain,etTime;
	private ActivityManager am;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.look);
		
		am=ActivityManager.getInstance();
		am.addActivity(this);
		
		Intent intent=getIntent();
		String noteId=intent.getStringExtra("noteId");
		
		etName=(EditText) findViewById(R.id.noteName2);
		etMain=(EditText) findViewById(R.id.noteMain2);
		etTime=(EditText) findViewById(R.id.noteTime2);
		SqliteDBConnect sd = new SqliteDBConnect(Look.this);
		SQLiteDatabase sdb = sd.getReadableDatabase();
		Cursor c = sdb.query("note", new String[] {"noteId", "noteName", "noteContent","noteTime" },
				"noteId=?", new String[]{noteId}, null, null,null);

		while(c.moveToNext()){
			etName.setText(c.getString(c.getColumnIndex("noteName")));
			etMain.setText(c.getString(c.getColumnIndex("noteContent")));
			etTime.setText(c.getString(c.getColumnIndex("noteTime")));
		}
//		etName.setEnabled(false);
//		etMain.setEnabled(false);
	}
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	menu.add(0,1,1,"关于");
    	menu.add(0,2,2,"退出");
    	return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
    	switch (item.getItemId()) {
		case 1:
			Builder adb=new Builder(Look.this);
			adb.setTitle("关于我们");
			adb.setMessage("石家庄学院\n\t 软件工程专业");
			adb.setPositiveButton("确定",new DialogInterface.OnClickListener() {				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Toast.makeText(Look.this, "谢谢", Toast.LENGTH_SHORT).show();
				}
			});
			adb.show();
			break;
		case 2:
			Builder adb2=new Builder(Look.this);
			adb2.setTitle("消息");
			adb2.setMessage("真的要退出吗？");
			adb2.setPositiveButton("确定",new DialogInterface.OnClickListener() {				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					am.exitAllProgress();
				}
			});
			adb2.setNegativeButton("取消",null);
			adb2.show();
			break;
		default:
			break;
		}
    	return super.onMenuItemSelected(featureId, item);
    }
}
