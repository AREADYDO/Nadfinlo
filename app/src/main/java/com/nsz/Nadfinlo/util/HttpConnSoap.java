package com.nsz.Nadfinlo.util;


        import java.io.IOException;
        import java.io.InputStream;
        import java.io.OutputStream;
        import java.net.HttpURLConnection;
        import java.net.URL;
        import java.util.ArrayList;
        import java.util.List;

        import org.xmlpull.v1.XmlPullParser;
        import android.util.Xml;

public class HttpConnSoap {

    /**
     * 获取返回的InputStream，为了增强通用性，在方法内不对其进行解析。
     *
     * @param methodName
     *            webservice方法名
     * @param Parameters
     *            webservice方法对应的参数名
     * @param ParValues
     *            webservice方法中参数对应的值
     * @return 未解析的InputStream
     */
    public ArrayList<String> GetWebServre(String methodName,ArrayList<String> Parameters,ArrayList<String>ParValues)
    {
        ArrayList<String> Values=new ArrayList<String>();
        String ServerUrl="http://10.0.0.164:8080/Service1.asmx";
        //String soapAction="http://tempuri.org/LongUserId1";
        String soapAction="http://tempuri.org/"+methodName;
        String data="";
        String soap = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                +"<soap:Body />";
        String tps,vps,ts;
        String mreakString="";
        mreakString="<"+methodName+" xmlns=\"http://tempuri.org/\">";
        for ( int i = 0; i < Parameters.size(); i++) {
            tps=Parameters.get(i).toString();

            vps=ParValues.get(i).toString();
            ts="<"+tps+">"+vps+"</"+tps+">";
            mreakString=mreakString+ts;
        }
        mreakString=mreakString+"</"+methodName+">";
	            /*
	            +"<HelloWorld xmlns=\"http://tempuri.org/\">"
	            +"<x>string11661</x>"
	            +"<SF1>string111</SF1>"
	            + "</HelloWorld>"
	            */
        String soap2="</soap:Envelope>";
        String requestData=soap+mreakString+soap2;
        System.out.println(requestData);

        //其上所有的数据都是在拼凑requestData，即向服务器发送的数据

        try{
            //	URL url =new URL(ServerUrl);
            //	HttpURLConnection con=(HttpURLConnection)url.openConnection();
            //	byte[] bytes=requestData.getBytes("utf-8");
            //	con.setDoInput(true);
            //	con.setDoOutput(true);
            //	con.setUseCaches(false);
            //	con.setConnectTimeout(8000);// ���ó�ʱʱ��
            //	con.setRequestMethod("POST");
            //	con.setRequestProperty("Content-Type", "text/xml;charset=utf-8");
            //	con.setRequestProperty("SOAPAction",soapAction);
            //	con.setRequestProperty("Content-Length",""+bytes.length);
            URL url = new URL (ServerUrl); //指定服务器地址
            HttpURLConnection con = (HttpURLConnection) url.openConnection();//打开链接
            System.out.println(url);
            byte[] bytes = requestData.getBytes ("utf-8"); //指定编码格式，可以解决中文乱码问题
            con.setDoInput (true); //指定该链接是否可以输入
            con.setDoOutput (true); //指定该链接是否可以输出
            con.setUseCaches (false); //指定该链接是否只用caches
            con.setConnectTimeout (6000); // 设置超时时间
            con.setRequestMethod ("POST"); //指定发送方法名，包括Post和Get。
            con.setRequestProperty ("Content-Type", "text/xml;charset=utf-8"); //设置（发送的）内容类型
            con.setRequestProperty ("SOAPAction", soapAction); //指定soapAction
            con.setRequestProperty ("Content-Length", "" + bytes.length); //指定内容长度
            System.out.println("11111111111111111111");
            //发送数据
            OutputStream outStream=con.getOutputStream();
            outStream.write(bytes);
            System.out.println(bytes);
            outStream.flush();
            outStream.close();

            //获取数据
            InputStream inStream=con.getInputStream();

            //data=parser(inStream);
            //System.out.print("11");
            Values= inputStreamtovaluelist(inStream,methodName);
            System.out.println(Values);
            //System.out.println(Values.size());
            return Values;



            //return inputStream;

            /**
             * 此类到此结束了，比原来的HttpConnSoap还短，因为这里没有对返回的数据做解析。数据完全都保存在了inputStream中。
             * 而原来的类是将数据解析成了ArrayList
             * <String>格式返回。显然，这样无法解决我们上面的需求（返回值是复杂类型的List）
             */

        }
        catch(Exception e)
        {
            System.out.print("2221");
            return null;
        }
    }
    public   ArrayList<String>   inputStreamtovaluelist  (InputStream   in,String MonthsName)   throws   IOException   {
        StringBuffer   out   =   new   StringBuffer();
        String s1="";
        byte[]   b   =   new   byte[4096];
        ArrayList<String> Values=new ArrayList<String>();
        Values.clear();
        for  (int   n;   (n   =   in.read(b))   !=   -1;)   {
            s1=new  String(b,   0,   n);
            out.append(s1);
        }
        System.out.println(out);
        String[] s13=s1.split("><");
        String ifString=MonthsName+"Result";
        String TS="";
        String vs="";

        Boolean getValueBoolean=false;
        for(int i=0;i<s13.length;i++){
            TS=s13[i];
            System.out.println(TS);
            int j,k,l;
            j=TS.indexOf(ifString);
            k=TS.lastIndexOf(ifString);

            if (j>=0)
            {
                System.out.println(j);
                if (getValueBoolean==false)
                {
                    getValueBoolean=true;
                }
                else {

                }

                if ((j>=0)&&(k>j))
                {
                    System.out.println("FFF"+TS.lastIndexOf("/"+ifString));
                    //System.out.println(TS);
                    l=ifString.length()+1;
                    vs=TS.substring(j+l,k-2);
                    //System.out.println("fff"+vs);
                    Values.add(vs);
                    System.out.println("�˳�"+vs);
                    getValueBoolean=false;
                    return   Values;
                }

            }
            if (TS.lastIndexOf("/"+ifString)>=0)
            {
                getValueBoolean=false;
                return   Values;
            }
            if ((getValueBoolean)&&(TS.lastIndexOf("/"+ifString)<0)&&(j<0))
            {
                k=TS.length();
                //System.out.println(TS);
                vs=TS.substring(7,k-8);
                //System.out.println("f"+vs);
                Values.add(vs);
            }

        }

        return   Values;
    }

}

