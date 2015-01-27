# cordova-plugins-activity
cordova调用安卓原生activity插件

###使用简介
JS端使用方式
```
//sendData表示要从js端发送到原生activity的数据，需用json对象传输
var sendData = {
  id:10001,
  name:"Simon",
  age:28
};
window.cordova.plugins.activity.start("com.zlzkj.vendor.MyActivity",sendData,function(data){
    //回调函数中的data表示从原生activity传回来的数据，已处理为json对象
    alert(data.id+"::"+data.name);
});
```
原生安卓activity接收参数和回传参数简单示例
```
public class MyActivity extends Activity {

    private Button btn;

    private int flag = 0;

    private Intent intent = null;

    private Context context = this;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.my_activity_main);

        intent = getIntent();

        String userId = intent.getStringExtra("id");
        String userName = intent.getStringExtra("name");
        String userAge = intent.getStringExtra("age");

        btn = (Button) findViewById(R.id.button_back);
        TextView tv = (TextView) findViewById(R.id.textView);
        tv.setText(userId+"::"+userName+"::"+userAge);

        btn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent mIntent = new Intent();
                mIntent.putExtra("id", "10003");
                mIntent.putExtra("name", "Jackson");
                // 设置结果，并进行传送
                setResult(RESULT_OK, mIntent);
                finish();
            }
        });

    }

}
```

