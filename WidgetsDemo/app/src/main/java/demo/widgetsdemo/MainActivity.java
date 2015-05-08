package demo.widgetsdemo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;
import  android.app.Activity;

import java.util.Random;


public class MainActivity extends ActionBarActivity {

    private ListView left;
    String []strs = {"Dialogs","Menus"};
    private static final int MAX_Progress=100;
    int progress;

    View dialogs, navigation, menus;
    TableLayout right;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        left = (ListView) findViewById(R.id.left);//得到ListView对象的引用 /*为ListView设置Adapter来绑定数据*/
        left.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, strs));
        left.setOnItemClickListener(listener);
        //
        dialogs = View.inflate(MainActivity.this, R.layout.dialogs, null);
        navigation = View.inflate(MainActivity.this, R.layout.navigation, null);
        menus = View.inflate(MainActivity.this, R.layout.menus, null);
        right = (TableLayout)findViewById(R.id.tableLayout);
        //初始化 右边控件
        right.addView(dialogs, 0);
        //添加 dialogs 控件
        addDialogsButtons();
        addMenusButtons();
    }

    private void addMenusButtons() {
    }

    private Button button1, button2, button3, button4, button5;
    private EditText name, password;
    private View.OnClickListener dialogListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Dialog dialog = null;
            try {
                dialog = createDialog(v.getId());
                dialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    protected Dialog createDialog(int id) throws Exception {
        switch (id) {
            case R.id.button1:
                return new AlertDialog.Builder(MainActivity.this)
                        .setMessage("确认退出吗？")
                        .setTitle("提示")
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                MainActivity.this.finish();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create();
            case R.id.button2:
                return new AlertDialog.Builder(MainActivity.this)
                    .setTitle("提示")
                    .setMessage(R.string.alert_dialog_two_buttons2_msg)
                    .setPositiveButton(R.string.alert_dialog_ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            dialog.dismiss();
                            MainActivity.this.finish();
                        }
                    })
                    .setNeutralButton(R.string.alert_dialog_something, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            Log.e("MainActivity","setNeutralButton click!");
                        }
                    })
                    .setNegativeButton(R.string.alert_dialog_cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            dialog.dismiss();
                        }
                    })
                    .create();
            case R.id.button3:
                return new AlertDialog.Builder(MainActivity.this)
                        .setTitle("提示")
                        .setItems(R.array.select_dialog_items, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                String[] items = getResources().getStringArray(R.array.select_dialog_items);
                                new AlertDialog.Builder(MainActivity.this)
                                        .setMessage("You selected: " + which + " , " + items[which])
                                        .show();
                            }
                        }).create();
            case  R.id.button4:
                //使用下面方式  无法取得name,password控件
                /*LayoutInflater factory = LayoutInflater.from(MainActivity.this);
                View textEntryView = View.inflate(MainActivity.this,R.layout.alert_dialog_text_entry, null);
                name = (EditText)findViewById(R.id.name);
                password = (EditText)findViewById(R.id.password);*/
                LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
                View textEntryView = View.inflate(MainActivity.this,R.layout.alert_dialog_text_entry,null);
                LinearLayout layout =(LinearLayout) textEntryView.findViewById(R.id.dialog_view);
                name = (EditText)textEntryView.findViewById(R.id.name);
                password = (EditText)textEntryView.findViewById(R.id.password);
                return new AlertDialog.Builder(MainActivity.this)
                        .setTitle("提示")
                        .setView(textEntryView)
                        .setPositiveButton(R.string.alert_dialog_ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.dismiss();
                                new AlertDialog.Builder(MainActivity.this).setMessage(name.getText().toString()+"\t"+password.getText().toString()).create().show();
                            }
                        })
                        .setNegativeButton(R.string.alert_dialog_cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {

                            }
                        })
                        .create();
            case R.id.button5:
                return showProgressDialog(R.style.loading_dialog);
            default:
                throw new  Exception("invalid id");
        }
    }


    // 显示进度对话框，style表示进度对话框的风格
    private Dialog showProgressDialog(int style)
    {
        final ProgressDialog progressDialog=new ProgressDialog(this);
        //progressDialog.setIcon(R.drawable.publicloading);
        progressDialog.setTitle("正在处理数据...");
        progressDialog.setMessage("请稍后...");
        // 设置进度对话框的风格
        progressDialog.setProgressStyle(style);
        // 设置进度对话框的进度最大值
        progressDialog.setMax(MAX_Progress);
        final Handler progressHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                // if (progressDialog.getProgress() >= MAX_PROGRESS)
                if(progress>=MAX_Progress)
                {
                    // 进度达到最大值，关闭对话框
                    progress=0;
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "加载完毕", Toast.LENGTH_SHORT);
                }
                else {
                    progress++;
                    // 将进度递增1
                    progressDialog.incrementProgressBy(1);
                    // 随机设置下一次递增进度（调用handleMessage方法）的时间
                    this.sendEmptyMessageDelayed(1,50+ new Random().nextInt(500));

                }
            }
        };
        /*设置进度对话框的【暂停】按钮
        progressDialog.setButton(1, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
                // 通过删除消息代码的方式停止定时器的执行
                progressHandler.removeMessages(1);
                // 恢复进度初始值
                progress=0;
                progressDialog.setProgress(0);
            }
        });*/
        // 设置进度初始值
        progress=(progress>0)?progress:0;
        progressDialog.setProgress(progress);
        progressHandler.sendEmptyMessage(1);
        return progressDialog;
    }

    private void addDialogsButtons() {
        button1 = (Button)findViewById(R.id.button1);
        button2 = (Button)findViewById(R.id.button2);
        button3 = (Button)findViewById(R.id.button3);
        button4 = (Button)findViewById(R.id.button4);
        button5 = (Button)findViewById(R.id.button5);


        button1.setOnClickListener(dialogListener);
        button2.setOnClickListener(dialogListener);
        button3.setOnClickListener(dialogListener);
        button4.setOnClickListener(dialogListener);
        button5.setOnClickListener(dialogListener);
    }

    private AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            switch (position) {
                case 0:
                    setTitle("Dialogs Show");
                    View view0 = right.getChildAt(0);
                    if(view0 != null) {
                        right.removeViewAt(0);
                        right.addView(dialogs, 0);
                    }
                    break;
                case 1:
                    setTitle("Menus Show");
                    View view1 = right.getChildAt(0);
                    if(view1 != null) {
                        right.removeViewAt(0);
                        right.addView(menus, 0);
                    }
                    break;
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


}
