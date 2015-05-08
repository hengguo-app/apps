package demo.widgetsdemo;

import android.util.Log;

/**
 *
 *
 * Created by wanghengguo on 2015/5/8.
 */
public class Utils {

    public static  long getPrime(long l){
        long count = 0;
        for (int i = 2; i<l; i++){
            for(int j =2; j<=(int)Math.sqrt(i); j++){
                if(i % j == 0)
                    break;;
                if(j >= (int)Math.sqrt(i)) {
                    count ++;
                    System.out.println(i + " is a prime");
                }
            }
        }
        return  count;
    }
    public  static void main(String []args){
        Log.i("Utils",getPrime(100000)+"");
    }
}
