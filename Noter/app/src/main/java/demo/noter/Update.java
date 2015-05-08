package demo.noter;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.AlertDialog.Builder;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

public class Update extends Activity {
	private Button btnCommit, btnCancel;
	private EditText etName, etMain, etTime;
	private String noteId;
	private SQLiteDatabase sdb;
	private ActivityManager am;
	private Calendar c;
	private int year, month, day, hours, minute, second;
	private PendingIntent pi;
	private AlarmManager alm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		am = ActivityManager.getInstance();
		am.addActivity(this);

		Intent intent = getIntent();
		noteId = intent.getStringExtra("noteId");
		btnCommit = (Button) findViewById(R.id.btnCommit);
		btnCancel = (Button) findViewById(R.id.btnCancel);
		etName = (EditText) findViewById(R.id.noteName);
		etMain = (EditText) findViewById(R.id.noteMain);
		etTime = (EditText) findViewById(R.id.noteTime);

		c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);
		hours = c.get(Calendar.HOUR);
		minute = c.get(Calendar.MINUTE);
		second = c.get(Calendar.SECOND);

		btnCommit.setText("修  改");

		SqliteDBConnect sd = new SqliteDBConnect(Update.this);
		sdb = sd.getReadableDatabase();
		Cursor c = sdb.query("note", new String[] { "noteId", "noteName",
				"noteContent", "noteTime" }, "noteId=?",
				new String[] { noteId }, null, null, null);

		while (c.moveToNext()) {
			etName.setText(c.getString(c.getColumnIndex("noteName")));
			etMain.setText(c.getString(c.getColumnIndex("noteContent")));
			etTime.setText(c.getString(c.getColumnIndex("noteTime")));
		}
		c.close();
		// etTime.setEnabled(false);
		etTime.setTextColor(Color.RED);
		btnCommit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				int n = testTime();
				if (n == 0) {
					return;
				}
				Builder adb = new Builder(Update.this);
				adb.setTitle("保存");
				adb.setMessage("确定要保存吗?");
				adb.setPositiveButton("保存",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								baocunNote();

							}
						});
				adb.setNegativeButton("取消",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								Toast.makeText(Update.this, "不保存！",
										Toast.LENGTH_SHORT).show();
							}
						});
				adb.show();
			}
		});
		btnCancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				System.exit(0);
			}
		});
		etTime.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				DatePickerDialog dpd = new DatePickerDialog(Update.this,
						new DatePickerDialog.OnDateSetListener() {
							@Override
							public void onDateSet(DatePicker view, int y,
									int monthOfYear, int dayOfMonth) {
								String[] time = { "",
										hours + ":" + minute + ":" + second };
								try {
									String[] time2 = etTime.getText().toString().trim()
											.split(" ");
									if(time2.length==2){
										time[1]=time2[1];
									}
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								String mo = "", da = "";
								if (monthOfYear < 10) {
									mo = "0" + (monthOfYear + 1);
								} else {
									mo = monthOfYear + "";
								}
								if (dayOfMonth < 10) {
									da = "0" + dayOfMonth;
								} else {
									da = dayOfMonth + "";
								}
								etTime.setText(y + "-" + mo + "-" + da + " "
										+ time[1]);
							}
						}, year, month, day);
				dpd.setTitle("设置日期");
				dpd.show();
			}
		});
		etTime.setOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				TimePickerDialog tpd = new TimePickerDialog(Update.this,
						new OnTimeSetListener() {
							@Override
							public void onTimeSet(TimePicker view,
									int hourOfDay, int minute) {
								String[] time = {year + "-" + month + "-" + day, "" };
								try {
									time = etTime.getText().toString().trim()
											.split(" ");
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								String ho = "", mi = "";
								if (hourOfDay < 10) {
									ho = "0" + hourOfDay;
								} else {
									ho = hourOfDay + "";
								}
								if (minute < 10) {
									mi = "0" + minute;
								} else {
									mi = minute + "";
								}
								etTime.setText(time[0] + " " + ho + ":" + mi);
							}
						}, hours, minute, true);
				tpd.setTitle("设置时间");
				tpd.show();
				return true;
			}
		});
	}

	public int testTime() {
		int flag = 0;
		try {
			String[] t = etTime.getText().toString().trim().split(" ");
			String[] t1 = t[0].split("-");
			String[] t2 = t[1].split(":");
			if (t1.length != 3)
				flag = 1;
			if (t2.length != 2 && t2.length != 3)
				flag = 1;
			int m = Integer.parseInt(t1[1]) - 1;
			int d = Integer.parseInt(t1[2]);
			int h = Integer.parseInt(t2[0]);
			int mi = Integer.parseInt(t2[1]);
			if (m < 0 || m > 11)
				flag = 1;
			if (d < 1 || d > 31)
				flag = 1;
			if (h < 0 || h > 24)
				flag = 1;
			if (mi < 0 || mi > 60)
				flag = 1;
			if (t2.length == 3) {
				int s = Integer.parseInt(t2[2]);
				if (s < 0 || s > 60)
					flag = 1;
			}
			if (flag == 1) {
				Toast.makeText(Update.this, "时间格式不正确，请单击或长按时间框设置",
						Toast.LENGTH_SHORT).show();
				return 0;
			}

			Calendar c2 = Calendar.getInstance();
			c2.set(Integer.parseInt(t1[0]), Integer.parseInt(t1[1]) - 1,
					Integer.parseInt(t1[2]), Integer.parseInt(t2[0]),
					Integer.parseInt(t2[1]));
		} catch (Exception e) {
			Toast.makeText(Update.this, "日期时间不正确，请单击或长按时间框设置",
					Toast.LENGTH_SHORT).show();
			return 0;
		}
		return 1;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 1, 1, "关于");
		menu.add(0, 2, 2, "设置闹铃声");
		menu.add(0, 3, 3, "退出");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case 1:
			Builder adb = new Builder(Update.this);
			adb.setTitle("关于我们");
			adb.setMessage("石家庄学院\n\t 软件工程专业");
			adb.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Toast.makeText(Update.this, "谢谢", Toast.LENGTH_SHORT)
							.show();
				}
			});
			adb.show();
			break;
		case 2:
			Intent intent = new Intent();
			intent.setClass(Update.this, SetAlarm.class);
			startActivity(intent);
			break;
		case 3:
			Builder adb2 = new Builder(Update.this);
			adb2.setTitle("消息");
			adb2.setMessage("真的要退出吗？");
			adb2.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					am.exitAllProgress();
				}
			});
			adb2.setNegativeButton("取消", null);
			adb2.show();
			break;
		default:
			break;
		}
		return super.onMenuItemSelected(featureId, item);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Builder adb = new Builder(Update.this);
			adb.setTitle("消息");
			adb.setMessage("是否要保存？");
			adb.setPositiveButton("保存", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					int n = testTime();
					if (n == 0) {
						return;
					}
					baocunNote();
				}
			});
			adb.setNegativeButton("不保存", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Intent intent2 = new Intent();
					intent2.setClass(Update.this, MainShow.class);
					startActivity(intent2);
				}
			});
			adb.show();
		}
		return super.onKeyDown(keyCode, event);
	}

	public void baocunNote() {
		String name = etName.getText().toString().trim();
		String content = etMain.getText().toString().trim();
		String time = etTime.getText().toString().trim();
		if ("".equals(name) || "".equals(content)) {
			Toast.makeText(this, "名称和内容都不能为空", Toast.LENGTH_SHORT)
					.show();
		} else if ("请输入记事名称".equals(name) || "请输入记事内容".equals(content)) {
			Toast.makeText(this, "请输入内容", Toast.LENGTH_SHORT).show();
		} else {
			am.saveNote(sdb, name, content, noteId, time);
			Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
			String[] t = etTime.getText().toString().trim().split(" ");
			System.out.println(t[0] + ":" + t[1]);
			String[] t1 = t[0].split("-");
			String[] t2 = t[1].split(":");
			Calendar c2 = Calendar.getInstance();
			c2.set(Integer.parseInt(t1[0]), Integer.parseInt(t1[1]) - 1,
					Integer.parseInt(t1[2]), Integer.parseInt(t2[0]),
					Integer.parseInt(t2[1]));
			c=Calendar.getInstance();
			if (c.getTimeInMillis() + 1000 * 10 <= c2.getTimeInMillis()) {
				String messageContent;
				if (content.length() > 20) {
					messageContent = content.substring(0, 18) + "…";
				} else {
					messageContent = content;
				}
				Intent intent = new Intent();
				intent.setClass(this, AlarmNote.class);
				intent.putExtra("messageTitle", etName.getText().toString());
				intent.putExtra("messageContent", messageContent);
				pi = PendingIntent.getBroadcast(this, 0, intent,
						PendingIntent.FLAG_UPDATE_CURRENT);
				alm = (AlarmManager) getSystemService(ALARM_SERVICE);
				alm.set(AlarmManager.RTC_WAKEUP, c2.getTimeInMillis(), pi);
			}
			Intent intent2 = new Intent();
			intent2.setClass(this, MainShow.class);
			startActivity(intent2);
		}
	}
}
