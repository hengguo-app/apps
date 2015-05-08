package demo.widgetsdemo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TableLayout;

public class DialogActivity extends ActionBarActivity {

    private Button button1, button2, button3, button4;

    private View.OnClickListener dialogListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.button1){
                AlertDialog.Builder builder = new AlertDialog.Builder(DialogActivity.this);
                   builder.setMessage("确认退出吗？");
                  builder.setTitle("提示");
               builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                         @Override
                      public void onClick(DialogInterface dialog, int which) {
                           dialog.dismiss();
                         DialogActivity.this.finish();
                           }
                      });
                  builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                       @Override
                      public void onClick(DialogInterface dialog, int which) {
                           dialog.dismiss();
                           }
                     });
                 builder.create().show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        button1 = (Button)findViewById(R.id.button1);
        button2 = (Button)findViewById(R.id.button2);
        button3 = (Button)findViewById(R.id.button3);
        button4 = (Button)findViewById(R.id.button4);

        button1.setOnClickListener(dialogListener);
        button2.setOnClickListener(dialogListener);
        button3.setOnClickListener(dialogListener);
        button4.setOnClickListener(dialogListener);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

}
