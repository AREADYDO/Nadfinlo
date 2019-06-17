package com.nsz.Nadfinlo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.nsz.Nadfinlo.util.Constant;
import com.google.zxing.activity.CaptureActivity;
import com.google.zxing.util.QrCodeGenerator;
import com.nsz.Nadfinlo.util.DBUtil;
import com.nsz.Nadfinlo.util.ProgressDialogUtils;
import com.nsz.Nadfinlo.util.WebService;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.io.InputStream;
import java.io.OutputStream;


   import java.sql.PreparedStatement;

   import java.util.ArrayList;
   import java.util.List;


public class Main2Activity extends AppCompatActivity implements View.OnClickListener {


    Button btnQrCode; // 扫码

    public static TextView tvResult; // 结果

    private View btnTest; // 提交
    private TextView tvTestResult; //上传结果
    private TextView sqlResult; //sql 结果
    Button btnGenerate; // 生 成二维码
    EditText etContent; // 待生成内容
    ImageView imgQrcode; // 二维码图片
    private String str;
    private Spinner sp;
    public static TextView tv;
    public String sqlResultcx =  "";

    private List<String> provinceList = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

     //   init();

        Button bt_click = (Button) findViewById(R.id.button2);
        bt_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main2Activity.this, SQL.class);

                startActivity(intent);
            }
        });


        /////sql
        btnTest = (Button) findViewById(R.id.submit);
        tvTestResult = (TextView) findViewById(R.id.subresult);
        sqlResult=(TextView) findViewById(R.id.assume_result);
     //   btnTest.setOnClickListener(getClickEvent());
        //btnClean.setOnClickListener(getClickEvent());

        /////下拉单

       //final static int WRAP_CONTENT = -2;//表示WRAP_CONTETN    的常量
   //     int[] drawableIds = {R.drawable.football, R.drawable.basketball, R.drawable.volleyball};//所有资源文件
       /* int[] msgIds = {R.string.l01, R.string.l02,R.string.l03,R.string.l04,R.string.l05,R.string.l06,R.string.l07,R.string.l08,R.string.l09,R.string.l10
                ,R.string.l11,R.string.l12,R.string.l13,R.string.l14,R.string.l15,R.string.l16,R.string.l17,R.string.l18,R.string.l19,R.string.l20
                ,R.string.l21,R.string.l22,R.string.l23,R.string.l24,R.string.l25,R.string.l26,R.string.l27,R.string.l28,R.string.l29,R.string.l30,R.string.l31};//所有资源字符串*/
         sp = (Spinner)this.findViewById(R.id.spinner);//初始化Spinner
         tv = (TextView) findViewById(R.id.site);
        str = (String) sp.getSelectedItem();
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                //拿到被选择项的值
                str = (String) sp.getSelectedItem();
                //把该值传给 TextView
                tv.setText(str);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });





        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // String phoneSec = "A02-01-BMM-0034";
                String phoneSec =  sqlResult.getText().toString();
                getRemoteInfo(phoneSec);
                //  test();
            }
        });

    }



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
              //  String result = object.getProperty("selectAllCargoInforResult").toString();
                SoapObject result =  (SoapObject)object.getProperty("selectAllCargoInforResult");
                String title = result.getProperty("string").toString();
                Message msg = new Message();
                msg.what=1003;
                Bundle data = new Bundle();
                data.putString("result01", title);
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
                    sqlResult.setText(str);
                    break;

                default:
                    break;
            }
        };
    };




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
              //  SoapObject object = ( SoapObject) envelope.bodyIn;
                String result ="";
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
            //    String result = object.getProperty("insertCargoInfoResult").toString();
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


    public void initView() {
        btnQrCode = (Button) findViewById(R.id.btn_qrcode);
        btnQrCode.setOnClickListener(this);
        btnGenerate = (Button) findViewById(R.id.btn_generate);
        btnGenerate.setOnClickListener(this);

        tvResult = (TextView) findViewById(R.id.txt_result);
        etContent = (EditText) findViewById(R.id.et_content);
        imgQrcode = (ImageView) findViewById(R.id.img_qrcode);
    }



    private View.OnClickListener getClickEvent() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvTestResult.setText("...");

                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");//设置日期格式
                new DBUtil().insertCargoInfo(tvResult.getText().toString(),tvResult.getText().toString(),df.format(new Date()),tv.getText().toString());

            }
        };
    }


    // 开始扫码
    private void startQrCode() {
        // 申请相机权限
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // 申请权限
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission
                    .CAMERA)) {
                Toast.makeText(this, "请至权限中心打开本应用的相机访问权限", Toast.LENGTH_SHORT).show();
            }
            ActivityCompat.requestPermissions(Main2Activity.this, new String[]{Manifest.permission.CAMERA}, Constant.REQ_PERM_CAMERA);
            return;
        }
        // 申请文件读写权限（部分朋友遇到相册选图需要读写权限的情况，这里一并写一下）
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // 申请权限
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission
                    .WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(this, "请至权限中心打开本应用的文件读写权限", Toast.LENGTH_SHORT).show();
            }
            ActivityCompat.requestPermissions(Main2Activity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, Constant.REQ_PERM_EXTERNAL_STORAGE);
            return;
        }
        // 二维码扫码
        Intent intent = new Intent(Main2Activity.this, CaptureActivity.class);
        startActivityForResult(intent, Constant.REQ_QR_CODE);
    }

    /**
     * 生成二维码
     */
    private void generateQrCode() {
        if (etContent.getText().toString().equals("")) {
            Toast.makeText(this, "请输入二维码内容", Toast.LENGTH_SHORT).show();
            return;
        }
        Bitmap bitmap = QrCodeGenerator.getQrCodeImage(etContent.getText().toString(), imgQrcode.getWidth(), imgQrcode.getHeight());
        if (bitmap == null) {
            Toast.makeText(this, "生成二维码出错", Toast.LENGTH_SHORT).show();
            imgQrcode.setImageBitmap(null);
        } else {
            imgQrcode.setImageBitmap(bitmap);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_qrcode:
                startQrCode();

             //   sqlResultcx
            //    getRemoteInfo00(phoneSec00);
                break;
            case R.id.btn_generate:
                generateQrCode();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //扫描结果回调
        if (requestCode == Constant.REQ_QR_CODE && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            String scanResult = bundle.getString(Constant.INTENT_EXTRA_KEY_QR_SCAN);
            //将扫描出的信息显示出来
            tvResult.setText(scanResult);
            sqlResultcx = tvResult.getText().toString();
               getRemoteInfo00(sqlResultcx);
            tvTestResult.setText("记得提交！");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case Constant.REQ_PERM_CAMERA:
                // 摄像头权限申请
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 获得授权
                    startQrCode();
                } else {
                    // 被禁止授权
                    Toast.makeText(Main2Activity.this, "请至权限中心打开本应用的相机访问权限", Toast.LENGTH_LONG).show();
                }
                break;
            case Constant.REQ_PERM_EXTERNAL_STORAGE:
                // 文件读写权限申请
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 获得授权
                    startQrCode();
                } else {
                    // 被禁止授权
                    Toast.makeText(Main2Activity.this, "请至权限中心打开本应用的文件读写权限", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }


    /*private static Connection getSQLConnection(String ip, String user, String pwd, String db) {
        Connection con = null;
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
             con = DriverManager.getConnection("jdbc:jtds:sqlserver://" + ip + ":1433/" + db + ";charset=utf8", user, pwd);
         //   con = DriverManager.getConnection("jdbc:jtds:sqlserver://" + ip + ":1433/" + db, user, pwd);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }
*/

  /*  private void test() {
        Runnable run = new Runnable() {
            @Override
            public void run() {
                String ret = QuerySQL();
             //   InsertSQL();
             //   String reI=InsertSQL();
               //  ret =  ret +"-"+ reI ;
                ret =  ret;
                Message msg = new Message();
                msg.what = 1001;
                Bundle data = new Bundle();
                data.putString("result",ret);
                msg.setData(data);
                mHandler.sendMessage(msg);
            }
        };
        new Thread(run).start();

    }

    Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1001:
                    String str = msg.getData().getString("result");
                    tvTestResult.setText(str);
                    break;

                default:
                    break;
            }
        }

        ;
    };

*/


  /*  public static String QuerySQL() {

       // TextView tvResult; // 结果
       // tvResult = (TextView) findViewById(R.id.txt_result);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");//设置日期格式
        String result = "";
        try {
            Connection conn = getSQLConnection("192.168.100.250", "szmis", "szmis", "EPICOR10TEST");

            String sql = "select top 1 PartNum from part where partnum = '" +tvResult.getText().toString() + "' And Company = 'NSZ'";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String s1 = tvResult.getText().toString();
                String s2 = rs.getString("PartNum");
                result += s1 + "  ,  " + s2 + "," + df.format(new Date()) + "\n";
                System.out.println(s1 + "  , " + s2 + "," + df.format(new Date()));
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            result += "查询数据异常!" + e.getMessage();
        }
        return result;
    }


    public static String InsertSQL() {

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");//设置日期格式
        String result = "";
        try {
            Connection conn = getSQLConnection("192.168.100.250", "szmis", "szmis", "EPICOR10TEST");

            String sqlInset = "INSERT INTO ice.UD38(Company, Key1, Key2, Key3,, Key3) VALUES('NSZ','"+tvResult.getText().toString()+"','"+ QuerySQL()+"',  '"+df.format(new Date()) +"','"+tv.getText().toString()+"')";
            PreparedStatement stmtsert = conn.prepareStatement(sqlInset);   //会抛出异常

            int i = stmtsert.executeUpdate();            //执行插入数据操作，返回影响的行数


            if (i == 1) {
                result = "true";
            }

            stmtsert.close();
            conn.close(); //打开一个Connection连接后，最后一定要调用它的close（）方法关闭连接，以释放系统资源及数据库资源
            return result;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            e.printStackTrace();
            result += "插入数据异常!" + e.getMessage();

        } finally { //finally的用处是不管程序是否出现异常，都要执行finally语句，所以在此处关闭连接

            return result;
        }

    }*/
}



























