package demo.weightcalculator;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import android.view.View;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    private TextView tvSex, tvHeight;
    private RadioButton rbMale, rbFemale;
    RadioGroup rgSex;
    private EditText etx;
    private Button bCount,bExit;
    private String sex = "男";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createView();

        sexChoose();

        setListener();
    }
    private void createView(){
        tvSex = (TextView)findViewById(R.id.sex);
        tvHeight = (TextView)findViewById(R.id.height);
        rbMale =(RadioButton)findViewById(R.id.radioMale);
        rbFemale =(RadioButton)findViewById(R.id.radioFemale);
        rgSex = (RadioGroup)findViewById(R.id.rgSex);
        etx =(EditText)findViewById(R.id.etx);
        etx.setInputType(InputType.TYPE_CLASS_NUMBER);
        bCount = (Button)findViewById(R.id.btn);
        bExit = (Button)findViewById(R.id.ext);
    }

    private void  sexChoose(){
        if(rbMale.isChecked()){
            sex ="男";
        }else if(rbFemale.isChecked()){
            sex  ="女";
        }
    }

    private void setListener(){
        bCount.setOnClickListener(bCountListener);
        rgSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.radioMale){
                    sex = "男";
                }else if(checkedId == R.id.radioFemale){
                    sex = "女";
                }
            }
        });
        bExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.finish();
                System.exit(0);
            }
        });
    }
    private View.OnClickListener bCountListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            double height = 0;
            try {
                height = Double.parseDouble(etx.getText().toString());
            }catch(Exception e){
                e.printStackTrace();
                Toast.makeText(MainActivity.this,"您输入的身高不是数字！", Toast.LENGTH_SHORT).show();
                return;
            }
            StringBuffer sb = new StringBuffer();
            sb.append("您的性别是："+sex+",");
            sb.append("\n");
            sb.append("身高为："+height+"cm,");
            sb.append("\n");
            sb.append("对应的标准体重为：" + getWeight(height)+".");
            //利用工厂方法构造一个简单的Toast,并链式结构的直接进行提示
            Toast.makeText(MainActivity.this,
                    sb.toString(),
                    Toast.LENGTH_LONG).show();
        }
    };
    //标准体重格式化输出的函数
    private String format(double num) {
        NumberFormat formatter = new DecimalFormat("0.00");
        String str = formatter.format(num);
        return str;
    }

    //得到标准体重的函数
    private String getWeight(double height) {
        String weight = "";
        if (sex.equals("男")) {
            weight =format((height - 80) * 0.7);
        }
        else {
            weight = format((height - 70) * 0.6);
        }
        return weight;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
