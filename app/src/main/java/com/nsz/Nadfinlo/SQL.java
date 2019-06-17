package com.nsz.Nadfinlo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import com.nsz.Nadfinlo.util.DBUtil;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.text.SimpleDateFormat;
import java.util.Date;
import com.nsz.Nadfinlo.Main2Activity;

import static android.view.View.*;

public class SQL extends Activity {

    private View btnTest;
    private View btnClean;
    private TextView tvTestResult;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sql);

        btnTest=findViewById(R.id.btnTestSql);
        btnClean=findViewById(R.id.btnClean);

        tvTestResult = (TextView)findViewById(R.id.tvTestResult);

       // btnTest.setOnClickListener(getClickEvent());
       // btnClean.setOnClickListener(getClickEvent());

     //   phoneSecEditText = (EditText) findViewById(R.id.phone_sec);

        btnTest.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // 手机号码（段）
                String phoneSec = Main2Activity.tvResult.getText().toString();
                // 简单判断用户输入的手机号码（段）是否合法
             /*   if ("".equals(phoneSec) || phoneSec.length() < 7) {
                    // 给出错误提示
                    phoneSecEditText.setError("您输入的手机号码（段）有误！");
                    phoneSecEditText.requestFocus();
                    // 将显示查询结果的TextView清空
                    tvTestResult.setText("");
                    return;
                }*/
                // 查询手机号码（段）信息
               getRemoteInfo(phoneSec);
              //  test();
            }
        });


        btnClean.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // 手机号码（段）
                String phoneSec = Main2Activity.tvResult.getText().toString();
                // 简单判断用户输入的手机号码（段）是否合法
             /*   if ("".equals(phoneSec) || phoneSec.length() < 7) {
                    // 给出错误提示
                    phoneSecEditText.setError("您输入的手机号码（段）有误！");
                    phoneSecEditText.requestFocus();
                    // 将显示查询结果的TextView清空
                    tvTestResult.setText("");
                    return;
                }*/
                // 查询手机号码（段）信息
                getRemoteInfo00(phoneSec);
                //  test();
            }
        });


    }



    public void getRemoteInfo( String  PartNum ) {

        // 命名空间
        String nameSpace = "http://tempuri.org/";
        // 调用的方法名称
        String methodName = "insertCargoInfo";
        // EndPoint
        final String endPoint = "http://10.0.0.164:8080/Service1.asmx";
        // SOAP Action
        final String soapAction = "http://tempuri.org/insertCargoInfo";

        // 指定WebService的命名空间和调用的方法名
        final SoapObject rpc = new SoapObject(nameSpace, methodName);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");//设置日期格式
        // 设置需调用WebService接口需要传入的两个参数mobileCode、userId
        rpc.addProperty("ScanNo", Main2Activity.tvResult.getText().toString() );
        rpc.addProperty("PartNum", PartNum);
        rpc.addProperty("ScanTime", df.format(new Date()));
        rpc.addProperty("Site", Main2Activity.tv.getText().toString());

        //  rpc.addProperty("userId", "");

        // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
        final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
      //  envelope.bodyOUt = rpc;
        envelope.bodyOut = rpc;
        // 设置是否调用的是dotNet开发的WebService
        envelope.dotNet = true;

        Runnable run = new Runnable()
        {
            @Override
            public void run() {
                // 等价于envelope.bodyOut = rpc;
                envelope.setOutputSoapObject(rpc);

                HttpTransportSE transport = new HttpTransportSE(endPoint);
                try {
                    // 调用WebService        /
                transport.call(soapAction, envelope);
            } catch (Exception e) {
            e.printStackTrace();
        }
            // 获取返回的数据
                String result = "";
        //    String object = (String) envelope.bodyIn;
                if(envelope.bodyIn instanceof SoapFault) {
                  //  result=ErrorTools.GetErrorResult(errorCode.netConnectError.toString(),SysConfig.serverError);
                    result= "error";
                }
                else
                {
                    SoapObject object = (SoapObject) envelope.bodyIn;
                    result = object.getProperty("insertCargoInfoResult").toString();
                }
            // 获取返回的结果
             //  String result = object.getProperty("insertCargoInfoResult").toString();
                Message msg = new Message();
                msg.what=1002;
                Bundle data = new Bundle();
                data.putString("result00", result);
                msg.setData(data);
                mHandler00.sendMessage(msg);
      //  / 将WebService返回的结果显示在TextView中
         //       tvTestResult.setText(object);
            }};

        new Thread(run).start();

    }
    Handler mHandler00 = new Handler(){
        public void handleMessage(android.os.Message msg) {
            switch (msg.what)
            {
                case 1002:
                    String str = msg.getData().getString("result00");
                    tvTestResult.setText(str);
                    break;

                default:
                    break;
            }
        };
    };



    public void getRemoteInfo00( String  PartNum ) {

        // 命名空间
        String nameSpace = "http://tempuri.org/";
        // 调用的方法名称
        String methodName = "selectAllCargoInfor";
        // EndPoint
        final String endPoint = "http://10.0.0.164:8080/Service1.asmx";
        // SOAP Action
        final String soapAction = "http://tempuri.org/selectAllCargoInfor";

        // 指定WebService的命名空间和调用的方法名
        final SoapObject rpc = new SoapObject(nameSpace, methodName);

     //   SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");//设置日期格式
        // 设置需调用WebService接口需要传入的两个参数mobileCode、userId
      //  rpc.addProperty("ScanNo", Main2Activity.tvResult.getText().toString() );
        rpc.addProperty("PartNum", PartNum);
      //  rpc.addProperty("ScanTime", df.format(new Date()));
       // rpc.addProperty("Site", Main2Activity.tv.getText().toString());

        //  rpc.addProperty("userId", "");

        // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
        final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);

        envelope.bodyOut = rpc;
        // 设置是否调用的是dotNet开发的WebService
        envelope.dotNet = true;

        Runnable run = new Runnable()
        {
            @Override
            public void run() {
                // 等价于envelope.bodyOut = rpc;
                envelope.setOutputSoapObject(rpc);

                HttpTransportSE transport = new HttpTransportSE(endPoint);
                try {
                    // 调用WebService        /
                    transport.call(soapAction, envelope);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // 获取返回的数据
                //    String object = (String) envelope.bodyIn;
                SoapObject object = ( SoapObject) envelope.bodyIn;
                // 获取返回的结果
                String result = object.getProperty("selectAllCargoInforResult").toString();
                Message msg = new Message();
                msg.what=1003;
                Bundle data = new Bundle();
                data.putString("result01", result);
                msg.setData(data);
                mHandler01.sendMessage(msg);
                //  / 将WebService返回的结果显示在TextView中
                //       tvTestResult.setText(object);
            }};

        new Thread(run).start();

    }
    Handler mHandler01 = new Handler(){
        public void handleMessage(android.os.Message msg) {
            switch (msg.what)
            {
                case 1003:
                    String str = msg.getData().getString("result01");
                    tvTestResult.setText(str);
                    break;

                default:
                    break;
            }
        };
    };



    private OnClickListener getClickEvent(){
        return new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //tvTestResult.setText("...");
              //  if(v==btnTest){
                    test();

              //  }
            }
        };
    }
    private void test()
    {
        Runnable run = new Runnable()
        {
            @Override
            public void run()
            {
              //  String ret = (String) DBUtil.getAllInfo("A02-01-BMM-0034");
                String ret = new  DBUtil().getAllInfo("A09-07-HWT-0032");
                Message msg = new Message();
                msg.what=1001;
                Bundle data = new Bundle();
                data.putString("result", ret);
                msg.setData(data);
                mHandler.sendMessage(msg);
            }
        };
        new Thread(run).start();

    }

    Handler mHandler = new Handler(){
        public void handleMessage(android.os.Message msg) {
            switch (msg.what)
            {
                case 1001:
                    String str = msg.getData().getString("result");
                    tvTestResult.setText(str);
                    break;

                default:
                    break;
            }
        };
    };
}
