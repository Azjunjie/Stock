package com.aitewei.stock.utils;

import android.os.Handler;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Description:    WebService请求工具类
 * Author:         张俊杰
 * CreateDate:     2020/3/14 下午3:07
 * Version:        1.0
 */
public class WebServiceUtils {

    public static final String WEB_SERVER_URL = "http://221.238.140.132:5858/WcfServer.IndexService.svc?wsdl";
    // 命名空间
    private static final String NAMESPACE = "http://tempuri.org/";
    private static final String ACTION = "http://tempuri.org/IIndexService/";

    public static Thread callWebService(String methodName, String gsdm, Handler handler) {
        //开启线程去访问WebService
        Thread thread = new Thread(() -> {
            //创建HttpTransportSE对象，传递WebService服务器地址
            final HttpTransportSE httpTransportSE = new HttpTransportSE(WEB_SERVER_URL);
            //创建SoapObject对象
            SoapObject soapObject = new SoapObject(NAMESPACE, methodName);
            soapObject.addProperty("gsdm", gsdm);

            //实例化SoapSerializationEnvelope,传入WebService的SOAP协议的版本号
            final SoapSerializationEnvelope soapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapSerializationEnvelope.bodyOut = soapObject;
            soapSerializationEnvelope.dotNet = false;
            SoapObject resultSoapObject = null;

            try {
                httpTransportSE.call(ACTION + methodName, soapSerializationEnvelope);
                if (soapSerializationEnvelope.getResponse() != null) {
                    //获取服务器响应返回的SoapObject
                    resultSoapObject = (SoapObject) soapSerializationEnvelope.bodyIn;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } finally {
                //将获取的消息通过handler发到主线程
                handler.sendMessage(handler.obtainMessage(0, resultSoapObject));
            }
        });
        thread.start();
        return thread;
    }

}
