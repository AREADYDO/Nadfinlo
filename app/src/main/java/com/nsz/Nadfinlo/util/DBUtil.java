package com.nsz.Nadfinlo.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;



public class DBUtil
{
    static boolean feeflag=false;
    ArrayList<String> arrayList=new ArrayList<String>();
    ArrayList<String> brrayList=new ArrayList<String>();
    ArrayList<String> crrayList=new ArrayList<String>();
    HttpConnSoap Soaptest=new HttpConnSoap();
    public static Connection getConnection()
    {
        Connection con=null;
        try
        {
            System.out.println("111");
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            System.out.println("222");
            con=DriverManager.getConnection("jdbc:jtds:sqlserver://" + "192.168.100.250" + ":1433/" + "EPICOR10" , "szmis", "szmis");
            System.out.println("333");
        }
        catch(Exception e)
        {
            System.out.println("444");
            e.printStackTrace();
        }
        return con;
    }

    public  void delete(String sql)
    {
        arrayList.clear();
        brrayList.clear();
        crrayList.clear();
        arrayList.add("sql");
        brrayList.add(sql);
        crrayList=Soaptest.GetWebServre("delete", arrayList, brrayList);

    }
    //��¼��֤
    public  String getAllInfo( String PartNum)//selectADPwd(String mgNo)
    {
        String result=null;
        arrayList.clear();
        brrayList.clear();
        arrayList.add("PartNum");
        brrayList.add(PartNum);
        crrayList=Soaptest.GetWebServre("selectAllCargoInfor", arrayList, brrayList);
        result= crrayList.get(0);
        return result;

    }
    //Ȩ����֤
    public  int CheckPermitted(String mgNO)
    {
        int permitted=0;
        String result=null;
        arrayList.clear();
        brrayList.clear();
        crrayList.clear();
        arrayList.add("mgNO");
        brrayList.add(mgNO);
        crrayList=Soaptest.GetWebServre("CheckPermitted", arrayList, brrayList);
        result= crrayList.get(0);
        permitted=Integer.parseInt(result);

        return permitted;
    }
    //��ѯ����Ա
    public  String[] SelectAdmin(String mgNO)
    {
        String sa[]=new String[3];
        String result=null;
        arrayList.clear();
        brrayList.clear();
        crrayList.clear();
        arrayList.add("mgNO");
        brrayList.add(mgNO);
        crrayList=Soaptest.GetWebServre("SelectAdmin", arrayList, brrayList);
        sa[0]=crrayList.get(0);
        sa[1]=crrayList.get(1);
        sa[2]=crrayList.get(2);
        //System.out.println("ffff");
        //System.out.println("�˳�");
        return sa;
    }
    //��ӹ���Ա
    public  Boolean insertCargoInfo(String ScanNo, String PartNum, String ScanTime, String Site)//insertManager(String mgNO,String permitted,String password)
    {
        Boolean falg=false;
        String result=null;
        arrayList.clear();
        brrayList.clear();
        crrayList.clear();
        arrayList.add("ScanNo");
        brrayList.add(ScanNo);
        arrayList.add("PartNum");
        brrayList.add(PartNum);
        arrayList.add("ScanTime");
        brrayList.add(ScanTime);
        arrayList.add("Site");
        brrayList.add(Site);
        crrayList=Soaptest.GetWebServre("insertCargoInfo", arrayList, brrayList);
       /* result=crrayList.get(0);
        if (result.equalsIgnoreCase("true"))
        {
            falg=true;
        }*/
        falg=true;
        return falg;

    }


    //û�п�ʼ��
    //ɾ������Ա
    public  void deleteManager(String mgNO)
    {
        String result=null;
        arrayList.clear();
        brrayList.clear();
        crrayList.clear();
        arrayList.add("mgNO");
        brrayList.add(mgNO);
        Soaptest.GetWebServre("deleteManager", arrayList, brrayList);
    }
    //��ѯ����Ա����
    public  String selectAdminPassword(String mgNo)
    {
        String pwd=null;
        String result=null;
        arrayList.clear();
        brrayList.clear();
        crrayList.clear();
        arrayList.add("mgNo");
        brrayList.add(mgNo);
        //System.out.println("KKK"+mgNo);
        crrayList=Soaptest.GetWebServre("selectAdminPassword", arrayList, brrayList);
        //System.out.println("��ѯ��ffff");
        pwd=crrayList.get(0).trim();
        //System.out.println("��ѯ����");
        return pwd;

    }

    //�޸Ĺ���Ա����

    public  void updateManager(String mgNo,String password)
    {
        String result=null;
        arrayList.clear();
        brrayList.clear();
        crrayList.clear();
        arrayList.add("mgNo");
        brrayList.add(mgNo);
        arrayList.add("password");
        brrayList.add(password);
        crrayList=Soaptest.GetWebServre("updateManager", arrayList, brrayList);
    }

    //ͼ�����
    public void insertBook(String isbn,String BookNo,String BookName,String Author,String Publishment,String BuyTime,String Borrowed,String Ordered,String instroduction)
    {
        String result=null;
        arrayList.clear();
        brrayList.clear();
        crrayList.clear();
        arrayList.add("isbn");
        arrayList.add("BookNo");
        arrayList.add("BookName");
        arrayList.add("Author");
        arrayList.add("Publishment");
        arrayList.add("BuyTime");
        arrayList.add("Borrowed");
        arrayList.add("Ordered");
        arrayList.add("instroduction");

        brrayList.add(isbn);
        brrayList.add(BookNo);
        brrayList.add(BookName);
        brrayList.add(Author);
        brrayList.add(Publishment);
        brrayList.add(BuyTime);
        brrayList.add(Borrowed);
        brrayList.add(Ordered);
        brrayList.add(instroduction);
        crrayList=Soaptest.GetWebServre("insertBook", arrayList, brrayList);
    }
    //ɾ��ͼ����Ϣ
    public  void deleteBook(String bookNO)
    {
        String pwd=null;
        String result=null;
        arrayList.clear();
        brrayList.clear();
        crrayList.clear();
        arrayList.add("bookN");
        brrayList.add(bookNO);
        crrayList=Soaptest.GetWebServre("deleteBook", arrayList, brrayList);
    }
    //�޸�ͼ����Ϣ
    public  void updateBook(String BookNo,String BookName,String Author,String Publishment,String BuyTime,String Borrowed,String Ordered,String Introduction)
    {
        String result=null;
        arrayList.clear();
        brrayList.clear();
        crrayList.clear();
        arrayList.add("BookNo");
        arrayList.add("BookName");
        arrayList.add("Author");
        arrayList.add("Publishment");
        arrayList.add("BuyTime");
        arrayList.add("Borrowed");
        arrayList.add("Ordered");
        arrayList.add("Introduction");


        brrayList.add(BookNo);
        brrayList.add(BookName);
        brrayList.add(Author);
        brrayList.add(Publishment);
        brrayList.add(BuyTime);
        brrayList.add(Borrowed);
        brrayList.add(Ordered);
        brrayList.add(Introduction);
        crrayList=Soaptest.GetWebServre("updateBook", arrayList, brrayList);
    }
    //���ѧ��
    public  void addStu(String StuNO,String StuName,String StuAge,String StuSex,String Class,String Department,String Tel,String Permitted,String Password)
    {
        arrayList.clear();
        brrayList.clear();
        crrayList.clear();
        arrayList.add("StuNO");
        arrayList.add("StuName");
        arrayList.add("StuAge");
        arrayList.add("StuSex");
        arrayList.add("Class");
        arrayList.add("Department");
        arrayList.add("Tel");
        arrayList.add("Permitted");
        arrayList.add("Password");

        brrayList.add(StuNO);
        brrayList.add(StuName);
        brrayList.add(StuAge);
        brrayList.add(StuSex);
        brrayList.add(Class);
        brrayList.add(Department);
        brrayList.add(Tel);
        brrayList.add(Permitted);
        brrayList.add(Password);

        crrayList=Soaptest.GetWebServre("addStu", arrayList, brrayList);
    }
    //��ѯѧ����Ϣ
    public  String[] selectStu(String StuNO)
    {
        String ss[]=new String[8];
        String result=null;
        arrayList.clear();
        brrayList.clear();
        crrayList.clear();
        arrayList.add("StuNO");
        brrayList.add(StuNO);
        crrayList=Soaptest.GetWebServre("selectStu", arrayList, brrayList);
        ss[0]=crrayList.get(0);
        ss[1]=crrayList.get(1);
        ss[2]=crrayList.get(2);
        ss[3]=crrayList.get(3);
        ss[4]=crrayList.get(4);
        ss[5]=crrayList.get(5);
        ss[6]=crrayList.get(6);
        ss[7]=crrayList.get(7);
        return ss;

    }
    //ɾ��ѧ����Ϣ
    public  void delectStu(String Sno)
    {

        String result=null;
        arrayList.clear();
        brrayList.clear();
        crrayList.clear();
        arrayList.add("Sno");
        brrayList.add(Sno);
        crrayList=Soaptest.GetWebServre("delectStu", arrayList, brrayList);
    }
    //�޸�ѧ����Ϣ
    public  void updateStu(String StuNO,String StuName,String StuAge,String StuSex,String Class,String Department,String Tel,String Permitted,String Password)
    {
        arrayList.clear();
        brrayList.clear();
        crrayList.clear();
        arrayList.add("StuNO");
        arrayList.add("StuName");
        arrayList.add("StuAge");
        arrayList.add("StuSex");
        arrayList.add("Class");
        arrayList.add("Department");
        arrayList.add("Tel");
        arrayList.add("Permitted");
        arrayList.add("Password");

        brrayList.add(StuNO);
        brrayList.add(StuName);
        brrayList.add(StuAge);
        brrayList.add(StuSex);
        brrayList.add(Class);
        brrayList.add(Department);
        brrayList.add(Tel);
        brrayList.add(Permitted);
        brrayList.add(Password);

        crrayList=Soaptest.GetWebServre("updateStu", arrayList, brrayList);
    }
    //����Ƿ���
    public  int checktime(String sno,String bno)
    {//-1������û������  0����������   1��������������  -2��ʾ���ڽ�����
        int flag;
        String result=null;
        arrayList.clear();
        brrayList.clear();
        crrayList.clear();
        arrayList.add("sno");
        brrayList.add(sno);
        arrayList.add("bno");
        brrayList.add(bno);
        arrayList.add("feeflag");
        if  (feeflag)
        {
            brrayList.add("true");
        }
        else
        {
            brrayList.add("false");
        }
        crrayList=Soaptest.GetWebServre("checktime", arrayList, brrayList);
        result=crrayList.get(0);
        flag=Integer.parseInt(result);
        return flag;

    }
    //�鿴����������Ϣ
    public  int selectfee(String StuNO)
    {
        int day=0;
        String result=null;
        arrayList.clear();
        brrayList.clear();
        crrayList.clear();
        arrayList.add("StuNO");
        brrayList.add(StuNO);
        crrayList=Soaptest.GetWebServre("selectfee", arrayList, brrayList);
        result=crrayList.get(0);
        day=Integer.parseInt(result);
        return day;
    }

    //����
    //������Ҫ����FEEFALG
    public  void fee(String StuNo,String fee)
    {


        String result=null;
        arrayList.clear();
        brrayList.clear();
        crrayList.clear();
        arrayList.add("StuNo");
        brrayList.add(StuNo);
        arrayList.add("fee");
        brrayList.add(fee);
        arrayList.add("feeflag");
        if (feeflag)
        {
            brrayList.add("true");
        }
        else
        {
            brrayList.add("false");
        }
        crrayList=Soaptest.GetWebServre("fee", arrayList, brrayList);
        result=crrayList.get(0);
        if (result.equalsIgnoreCase("true"))
        {
            feeflag=true;
        }
        else
        {
            feeflag=false;
        }

    }
    //��ѯ���Ļ�ԤԼͼ��
    public String borrowororderbook(String bookNo)
    {
        String s=null;
        String result=null;
        arrayList.clear();
        brrayList.clear();
        crrayList.clear();
        arrayList.add("bookNo");
        brrayList.add(bookNo);
        crrayList=Soaptest.GetWebServre("borrowororderbook", arrayList, brrayList);
        s=crrayList.get(0);

        return s;
    }
    //ԤԼͼ��
    public  void orderbook(String bookNo,String StuNo)
    {
        arrayList.clear();
        brrayList.clear();
        crrayList.clear();
        arrayList.add("bookNo");
        brrayList.add(bookNo);
        arrayList.add("StuNo");
        brrayList.add(StuNo);
        System.out.println("111");
        crrayList=Soaptest.GetWebServre("orderbook", arrayList, brrayList);
        System.out.println("1113");

    }
    //����ͼ��
    public  void borrowbook(String bookNo,String StuNo)
    {
        arrayList.clear();
        brrayList.clear();
        crrayList.clear();
        arrayList.add("bookNo");
        brrayList.add(bookNo);
        arrayList.add("StuNo");
        brrayList.add(StuNo);
        crrayList=Soaptest.GetWebServre("borrowbook", arrayList, brrayList);
    }

    public  Vector<String> selectbookfromISBN(String ISBN)
    {

        Vector<String> v =new Vector<String>();
        arrayList.clear();
        brrayList.clear();
        crrayList.clear();
        arrayList.add("ISBN");
        brrayList.add(ISBN);
        crrayList=Soaptest.GetWebServre("selectbookfromISBN", arrayList, brrayList);
        for(String s:crrayList)
        {
            v.add(s);
        }

        return v;
    }

    public  Vector<String> selectfeeinformation(String StuNO)
    {
        Vector<String> v =new Vector<String>();
        arrayList.clear();
        brrayList.clear();
        crrayList.clear();
        arrayList.add("StuNO");
        brrayList.add(StuNO);
        crrayList=Soaptest.GetWebServre("selectfeeinformation", arrayList, brrayList);
        for(String s:crrayList)
        {
            v.add(s);
        }
        return v;

    }

    //�õ���ʧͼ�����Ϣ���еļ�¼������
    public  int getMaxGSBH()
    {
        int result=0;
        arrayList.clear();
        brrayList.clear();
        crrayList.clear();
        arrayList.add("VVV");
        brrayList.add("VVV");
        crrayList=Soaptest.GetWebServre("getMaxGSBH", arrayList, brrayList);
        //result=crrayList.get(0);
        result=Integer.parseInt(crrayList.get(0));
        return result;

    }
    //ִ��û�з���ֵ�Ĳ������ķ���
    public  void update(String sql)
    {
        arrayList.clear();
        brrayList.clear();
        crrayList.clear();
        arrayList.add("sql");
        brrayList.add(sql);
        crrayList=Soaptest.GetWebServre("update", arrayList, brrayList);
    }
    //��֪�������õ�����鼮�Ļ�����Ϣ
    public  Vector<String> selectAllfrombook(String BookName)
    {
        Vector<String> v =new Vector<String>();
        arrayList.clear();
        brrayList.clear();
        crrayList.clear();
        arrayList.add("BookName");
        brrayList.add(BookName);
        crrayList=Soaptest.GetWebServre("selectAllfrombook", arrayList, brrayList);
        for(String s:crrayList)
        {
            v.add(s);
        }

        return v;
    }
    //ͨ����ŵõ���Ļ�����Ϣ
    public  String[] selectbookinformationfrombookno(String bookno)
    {
        String info[]=new String[6];
        arrayList.clear();
        brrayList.clear();
        crrayList.clear();
        arrayList.add("bookno");
        brrayList.add(bookno);
        crrayList=Soaptest.GetWebServre("selectbookinformationfrombookno", arrayList, brrayList);
        info[0]=crrayList.get(0);
        info[1]=crrayList.get(1);
        info[2]=crrayList.get(2);
        info[3]=crrayList.get(3);
        info[4]=crrayList.get(4);
        info[5]=crrayList.get(5);
        return info;
    }
    //ͨ��ѧ�Ų�ѯ��������
    public  int selectcount(String StuNO)
    {
        int a=0;
        arrayList.clear();
        brrayList.clear();
        crrayList.clear();
        arrayList.add("StuNO");
        brrayList.add(StuNO);
        crrayList=Soaptest.GetWebServre("selectcount", arrayList, brrayList);
        //result=crrayList.get(0);
        a=Integer.parseInt(crrayList.get(0));

        return a;
    }

    //�õ�ͬ��ISBN���鼮������
    public  int getNumfrombdetailedInfo(String ISBN)
    {
        int num=0;
        arrayList.clear();
        brrayList.clear();
        crrayList.clear();
        arrayList.add("ISBN");
        brrayList.add(ISBN);
        crrayList=Soaptest.GetWebServre("selectcount", arrayList, brrayList);
        num=Integer.parseInt(crrayList.get(0));
        return num;

    }

    //һ��ISBN�ŵõ�ͬ�ֺ��µ���������Ļ�����Ϣ
    public  Vector<String> selectISBNALlfromdetailInfo(String ISBN)
    {
        Vector<String> v =new Vector<String>();
        arrayList.clear();
        brrayList.clear();
        crrayList.clear();
        arrayList.add("ISBN");
        brrayList.add(ISBN);
        crrayList=Soaptest.GetWebServre("selectISBNALlfromdetailInfo", arrayList, brrayList);
        for(String s:crrayList)
        {
            v.add(s);
        }
        return v;
    }


    //������ŵõ�������
    public  String getAuthor(String BookNO)
    {
        String result=null;
        arrayList.clear();
        brrayList.clear();
        crrayList.clear();
        arrayList.add("BookNO");
        brrayList.add(BookNO);
        crrayList=Soaptest.GetWebServre("getAuthor", arrayList, brrayList);
        result=crrayList.get(0);
        return result;

    }

    //����ѧ��ID�õ�ѧ���İ༶������
    public  Vector<String> getClassAndsname(String StuNO)
    {
        Vector<String> result =new Vector<String>();
        arrayList.clear();
        brrayList.clear();
        crrayList.clear();
        arrayList.add("StuNO");
        brrayList.add(StuNO);
        crrayList=Soaptest.GetWebServre("getClassAndsname", arrayList, brrayList);
        for(String s:crrayList)
        {
            result.add(s);
        }
        return result;


    }

    //ͨ������ͼ������ߵõ�ͼ��Ļ�����Ϣ
    public  Vector<String> getAuthorAllfromBook(String Author)
    {
        Vector<String> v =new Vector<String>();
        arrayList.clear();
        brrayList.clear();
        crrayList.clear();
        arrayList.add("Author");
        brrayList.add(Author);
        crrayList=Soaptest.GetWebServre("getAuthorAllfromBook", arrayList, brrayList);
        for(String s:crrayList)
        {
            v.add(s);
        }
        return v;
    }


    //ͨ��������õ�ͼ��Ļ�����Ϣ
    public  Vector<String> getPubAllfrombook(String Publishment)
    {
        Vector<String> v =new Vector<String>();
        arrayList.clear();
        brrayList.clear();
        crrayList.clear();
        arrayList.add("Publishment");
        brrayList.add(Publishment);
        crrayList=Soaptest.GetWebServre("getPubAllfrombook", arrayList, brrayList);
        for(String s:crrayList)
        {
            v.add(s);
        }
        return v;

    }


    //ͨ�����������ߵõ�ͼ��Ļ�����Ϣ
    public  Vector<String> getBnAuAllfrombook(String BookName,String Author)
    {
        Vector<String> v =new Vector<String>();
        arrayList.clear();
        brrayList.clear();
        crrayList.clear();
        arrayList.add("BookName");
        brrayList.add(BookName);
        arrayList.add("Author");
        brrayList.add(Author);
        crrayList=Soaptest.GetWebServre("getBnAuAllfrombook", arrayList, brrayList);
        for(String s:crrayList)
        {
            v.add(s);
        }
        return v;
    }

    //ͨ�������ͳ�����õ�ͼ��Ļ�����Ϣ
    public  Vector<String> getBnCbAllfrombook(String BookName,String Publishment)
    {
        Vector<String> v =new Vector<String>();
        arrayList.clear();
        brrayList.clear();
        crrayList.clear();
        arrayList.add("BookName");
        brrayList.add(BookName);
        arrayList.add("Publishment");
        brrayList.add(Publishment);
        crrayList=Soaptest.GetWebServre("getBnCbAllfrombook", arrayList, brrayList);
        for(String s:crrayList)
        {
            v.add(s);
        }
        return v;
    }

    //ͨ�����ߺͳ�����
    public  Vector<String> getAuCbAllfrombook(String Author,String Publishment)
    {
        Vector<String> v =new Vector<String>();
        arrayList.clear();
        brrayList.clear();
        crrayList.clear();
        arrayList.add("Author");
        brrayList.add(Author);
        arrayList.add("Publishment");
        brrayList.add(Publishment);
        crrayList=Soaptest.GetWebServre("getAuCbAllfrombook", arrayList, brrayList);
        for(String s:crrayList)
        {
            v.add(s);
        }
        return v;
    }

    //ͨ������ �����ͳ�������в�ѯ
    public  Vector<String> getBnAuCbAllfrombook(String BookName,String Author,String Publishment)
    {
        Vector<String> v =new Vector<String>();
        arrayList.clear();
        brrayList.clear();
        crrayList.clear();
        arrayList.add("BookName");
        brrayList.add(BookName);
        arrayList.add("Author");
        brrayList.add(Author);
        arrayList.add("Publishment");
        brrayList.add(Publishment);
        crrayList=Soaptest.GetWebServre("getBnAuCbAllfrombook", arrayList, brrayList);
        for(String s:crrayList)
        {
            v.add(s);
        }
        return v;
    }

    //ͨ����Ŷ�ISBN��ͼ����Ĳ�ѯ
    public  Vector<String> getISinfromdetails(String BookNo)
    {
        Vector<String> v =new Vector<String>();
        arrayList.clear();
        brrayList.clear();
        crrayList.clear();
        arrayList.add("BookNo");
        brrayList.add(BookNo);
        crrayList=Soaptest.GetWebServre("getISinfromdetails", arrayList, brrayList);
        for(String s:crrayList)
        {
            v.add(s);
        }
        return v;
    }

    //ͨ��ISBN��ͬһ��ISBN���µĻ�����Ϣ
    public  Vector<String> getISfrombook(String isbn)
    {
        Vector<String> v =new Vector<String>();
        arrayList.clear();
        brrayList.clear();
        crrayList.clear();
        arrayList.add("isbn");
        brrayList.add(isbn);

        crrayList=Soaptest.GetWebServre("getISfrombook", arrayList, brrayList);
        for(String s:crrayList)
        {
            v.add(s);
        }
        return v;
    }

    //����ѧ����ID�õ���ԤԼͼ��Ļ�����Ϣ
    public  Vector<String> getBNofromOrder(String stuNo)
    {
        Vector<String> v =new Vector<String>();
        arrayList.clear();
        brrayList.clear();
        crrayList.clear();
        arrayList.add("stuNo");
        brrayList.add(stuNo);
        crrayList=Soaptest.GetWebServre("getISfrombook", arrayList, brrayList);
        for(String s:crrayList)
        {
            v.add(s);
        }
        return v;
    }
    //����ԤԼͼ����Ϣ��õ�ĳͬѧ��ԤԼͼ����Ϣ
    public int getNumfromborderreport(String stuno)
    {
        int num=0;
        arrayList.clear();
        brrayList.clear();
        crrayList.clear();
        arrayList.add("stuNo");
        brrayList.add(stuno);
        crrayList=Soaptest.GetWebServre("getNumfromborderreport", arrayList, brrayList);
        num=Integer.parseInt(crrayList.get(0));
        return num;

    }

    //����ѧ����ѧ�ŵõ�ͼ���ISBN��BookNO,BookName,Author,Publishment,����ʱ�䣬�黹ʱ��
    public  Vector<String> getSomeInfo(String stuno)
    {
        Vector<String> result=new Vector<String>();
        arrayList.clear();
        brrayList.clear();
        crrayList.clear();
        arrayList.add("stuno");
        brrayList.add(stuno);
        crrayList=Soaptest.GetWebServre("getSomeInfo", arrayList, brrayList);
        for(String s:crrayList)
        {
            result.add(s);
        }
        return result;

    }



    //����ͼ�����ŵõ�ͼ��Ļ�����Ϣ
    public  Vector<String> getBNSomeInfo(String BookNO)
    {
        Vector<String> result=new Vector<String>();
        arrayList.clear();
        brrayList.clear();
        crrayList.clear();
        arrayList.add("BookNO");
        brrayList.add(BookNO);
        crrayList=Soaptest.GetWebServre("getBNSomeInfo", arrayList, brrayList);
        for(String s:crrayList)
        {
            result.add(s);
        }
        return result;

    }

    //����ԤԼͼ����ŵõ�ͼ�������Ϣ
    public  Vector<String> getBNSomeINFO(String BookNO)
    {
        Vector<String> result=new Vector<String>();
        arrayList.clear();
        brrayList.clear();
        crrayList.clear();
        arrayList.add("BookNO");
        brrayList.add(BookNO);
        crrayList=Soaptest.GetWebServre("getBNSomeINFO", arrayList, brrayList);
        for(String s:crrayList)
        {
            result.add(s);
        }
        return result;

    }

    //ͨ��ѧ����ID�õ�ѧ���İ༶��������ѧ��
    public  String[] getIDClNO(String stuno)
    {
        String[] result=new String[3];
        arrayList.clear();
        brrayList.clear();
        crrayList.clear();
        arrayList.add("stuno");
        brrayList.add(stuno);
        crrayList=Soaptest.GetWebServre("getIDClNO", arrayList, brrayList);
        result[0]=crrayList.get(0);
        result[1]=crrayList.get(1);
        result[2]=crrayList.get(2);

        return result;

    }

    //ͨ����ŵõ��黹ʱ��
    public  String gettimefromrecord(String BookNo)
    {
        String result=null;
        arrayList.clear();
        brrayList.clear();
        crrayList.clear();
        arrayList.add("BookNo");
        brrayList.add(BookNo);
        crrayList=Soaptest.GetWebServre("getIDClNO", arrayList, brrayList);
        result=crrayList.get(0);
        return result;

    }


    //ͨ������ж�ʱ�����ٽ�״̬
    public  String getifBorrow(String BookNO)
    {
        String result=null;
        arrayList.clear();
        brrayList.clear();
        crrayList.clear();
        arrayList.add("BookNO");
        brrayList.add(BookNO);
        crrayList=Soaptest.GetWebServre("getifBorrow", arrayList, brrayList);
        result=crrayList.get(0);
        return result;

    }

    //ͨ����Ų�ѯԤԼ��
    public  String getstu(String BookNO)
    {
        String stu=null;
        arrayList.clear();
        brrayList.clear();
        crrayList.clear();
        arrayList.add("BookNO");
        brrayList.add(BookNO);
        crrayList=Soaptest.GetWebServre("getstu", arrayList, brrayList);
        stu=crrayList.get(0);
        return stu;
    }
    //ͨ��isbn������
    public  String getBookNumber()
    {
        String bookno=null;
        arrayList.clear();
        brrayList.clear();
        crrayList.clear();
        arrayList.add("vvv");
        brrayList.add("vvv");
        crrayList=Soaptest.GetWebServre("getBookNumber", arrayList, brrayList);
        bookno=crrayList.get(0);
        return bookno;
    }
}

