package demo.widgetsdemo;

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


public class MenuActivity extends ActionBarActivity {

    private Button lists;
    private ListView left;
    String []strs = {"Dialogs","Menus"};

    View dialogs, navigation, menus;
    TableLayout right;
    private View.OnClickListener dialogListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //menus = (Button)findViewById(R.id.menus);
        //dialogs = (Button)findViewById(R.id.dialogs);
       // lists = (Button)findViewById(R.id.lists);
        left = (ListView) findViewById(R.id.left);//得到ListView对象的引用 /*为ListView设置Adapter来绑定数据*/
        left.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, strs));
        left.setOnItemClickListener(listener);
        //
        dialogs = View.inflate(MenuActivity.this, R.layout.dialogs, null);
        navigation = View.inflate(MenuActivity.this, R.layout.navigation, null);
        menus = View.inflate(MenuActivity.this, R.layout.menus, null);
        right = (TableLayout)findViewById(R.id.tableLayout);
        //初始化
        right.addView(dialogs, 0);
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
                /*RelativeLayout dialogsLayout = (RelativeLayout)findViewById(R.layout.dialogs);
                TableLayout right = (TableLayout)findViewById(R.id.tableLayout);
                right.addView(dialogsLayout);
                */
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
