package com.nsz.Nadfinlo.util;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;

public class XMLParase {

  /*  public static ArrayList<CommentInfor> paraseCommentInfors (InputStream inputStream)
    {
        ArrayList<CommentInfor> list = new ArrayList<CommentInfor>();
        XmlPullParser parser = Xml.newPullParser();

        try
        {
            parser.setInput (inputStream, "UTF-8");
            int eventType = parser.getEventType();
            CommentInfor info = new CommentInfor();

            while (eventType != XmlPullParser.END_DOCUMENT)
            {
                switch (eventType)
                {
                    case XmlPullParser.START_DOCUMENT:// 文档开始事件,可以进行数据初始化处理
                        break;
                    case XmlPullParser.START_TAG:// 开始元素事件
                        String name = parser.getName();
                        if (name.equalsIgnoreCase ("Review") )
                        {
                            info = new CommentInfor();
                        }
                        else if (name.equalsIgnoreCase ("userID") )
                        {
                            eventType = parser.next();
                            info.setUserID (parser.getText() );
                        }
                        else if (name.equalsIgnoreCase ("userName") )
                        {
                            eventType = parser.next();
                            info.setUserName (parser.getText() );
                        }
                        else if (name.equalsIgnoreCase ("reviewInfo") )
                        {
                            eventType = parser.next();
                            info.setReviewInfo (parser.getText() );
                        }
                        else if (name.equalsIgnoreCase ("reviewDate") )
                        {
                            eventType = parser.next();
                            info.setReviewDate (parser.getText() );
                        }
                        break;
                    case XmlPullParser.END_TAG:// 结束元素事件
                        if (parser.getName().equalsIgnoreCase ("Review") )
                        {
                            list.add (info);
                            info = null;
                        }
                        break;
                }
                eventType = parser.next();
            }
            inputStream.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return list;
    }
}*/

}
