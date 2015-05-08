package demo.gravity;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity{

    private SensorManager sensorMgr;
    private TextView show_TextView;
    Sensor sensor;
    private float x, y, z;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        show_TextView = (TextView)findViewById(R.id.text_view);
        sensorMgr = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        SensorEventListener lsn = new SensorEventListener()
        {
            @Override
            public void onSensorChanged(SensorEvent e) {
                // TODO Auto-generated method stub
                x = e.values[SensorManager.DATA_X];
                y = e.values[SensorManager.DATA_Y];
                z = e.values[SensorManager.DATA_Z];
                setTitle("x="+(int)x+","+"y="+(int)y+","+"z="+(int)z);
                show_TextView.setText("x="+(int)x+", "+"y="+(int)y+", "+"z="+(int)z);
            }

            @Override
            public void onAccuracyChanged(Sensor arg0, int arg1) {

            }
        };   //注册listener，第三个参数是检测的精确度
        sensorMgr.registerListener(lsn, sensor, SensorManager.SENSOR_DELAY_GAME);
    }

}