package com.nsz.Nadfinlo.util;


import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.os.Handler;
import android.os.Message;

/**
 * 访问WebService的工具类,
 *

 *
 */
public class WebService {
    //public static final String WEB_SERVER_URL = "http://www.webxml.com.cn/WebServices/WeatherWebService.asmx";
    public static final String WEB_SERVER_URL = "http://10.0.0.164:8080/Service1.asmx";

    // 含有3个线程的线程池
    private static final ExecutorService executorService = Executors
            .newFixedThreadPool(3);

    // 命名空间
    private static final String NAMESPACE = "http://10.0.0.164:8080/";

    /**
     *
     * @param url
     *            WebService服务器地址
     * @param methodName
     *            WebService的调用方法名
     * @param properties
     *            WebService的参数
     * @param webServiceCallBack
     *            回调接口
     *///////////////////////////////////////////////////////////////
    //通过调用里面的callWebService(String url, final String methodName,HashMap<String, String> properties,final WebServiceCallBack webServiceCallBack)
    // //就可以来获取我们想要的数据，实现思路
    //创建HttpTransportsSE对象。通过HttpTransportsSE类的构造方法可以指定WebService的WSDL文档的URL
    //创建SoapObject对象，里面的参数分别是WebService的命名空间和调用方法名

    //设置调用方法的参数值，如果没有参数，就不设置，有参数的话调用SoapObject对象的addProperty(String name, Object value)方法将参数加入到SoapObject对象中
    //实例化SoapSerializationEnvelope，传入WebService的SOAP协议的版本号，将上面的SoapObject对象通过setOutputSoapObject(Object soapObject)设置到里面，并设置是否调用的是.Net开发的WebService和是否debug等信息
    //因为涉及到网络操作，所以我们使用了线程池来异步操作调用WebService接口，我们在线程中调用HttpTransportsSE对象的call(String soapAction, SoapEnvelope envelope)方法就能实现对WebService的调用，
    // 并且通过soapEnvelope.bodyIn获取WebService返回的信息，但是返回的信息是在子线程中，
    // 我们需要利用Handler来实现子线程与主线程进行转换，然后在Handler的handleMessage(Message msg)中将结果回调到callBack的参数中，总体思路就是这个样子



    public static void callWebService(String url, final String methodName,
                                      HashMap<String, String> properties,
                                      final WebServiceCallBack webServiceCallBack) {


        // 创建HttpTransportSE对象，传递WebService服务器地址
        final HttpTransportSE httpTransportSE = new HttpTransportSE(url);


        // 创建SoapObject对象
        SoapObject soapObject = new SoapObject(NAMESPACE, methodName);


        // SoapObject添加参数
        if (properties != null) {
            for (Iterator<Map.Entry<String, String>> it = properties.entrySet()
                    .iterator(); it.hasNext();) {
                Map.Entry<String, String> entry = it.next();
                soapObject.addProperty(entry.getKey(), entry.getValue());
            }
        }

        // 实例化SoapSerializationEnvelope，传入WebService的SOAP协议的版本号
        final SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        // 设置是否调用的是.Net开发的WebService
        soapEnvelope.setOutputSoapObject(soapObject);
        soapEnvelope.dotNet = true;
        httpTransportSE.debug = true;



        // 用于子线程与主线程通信的Handler
        final Handler mHandler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                // 将返回值回调到callBack的参数中
                webServiceCallBack.callBack((SoapObject) msg.obj);
            }

        };


        // 开启线程去访问WebService
        executorService.submit(new Runnable() {

            @Override
            public void run() {
                SoapObject resultSoapObject = null;
                try {
                    httpTransportSE.call(NAMESPACE + methodName, soapEnvelope);
                    if (soapEnvelope.getResponse() != null) {
                        // 获取服务器响应返回的SoapObject
                        resultSoapObject = (SoapObject) soapEnvelope.bodyIn;
                    }
                } catch (HttpResponseException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } finally {
                    // 将获取的消息利用Handler发送到主线程
                    mHandler.sendMessage(mHandler.obtainMessage(0,
                            resultSoapObject));
                }
            }
        });
    }

    /**
     *
     *
     * @author redstar
     *
     */
    public interface WebServiceCallBack {
        public void callBack(SoapObject result);
    }

}

