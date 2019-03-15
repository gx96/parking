package control;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;

import javax.servlet.*;
import javax.servlet.http.*;
import data.Enter;

public class HandleEnter extends HttpServlet{
	private static final long serialVersionUID = -7771781025484297081L;
	
	//初始化函数，第一次调用servlet文件时调用
	public void init(ServletConfig config) throws ServletException{
		super.init(config);
		try {
			Class.forName("com.mysql.jdbc.Driver");//将JDBC驱动装进JAVA虚拟机中.
		} catch (ClassNotFoundException e) {			
			e.printStackTrace();
		}
	}
	
	//防乱码
	public String handleString(String s){
		try{
			byte bb[]=s.getBytes("iso-8859-1");//返回字符串的"iso-8858-1"编码字节
			s=new String(bb);//新建字符串,将字节编码再转成字符串。防止因为编码问题导致的乱码。
		}
		catch(Exception ee){}
		return s;
	}
	
	//接受jsp文件form表单的post发送方式
	public void doPost(HttpServletRequest request,HttpServletResponse response)
							throws ServletException,IOException{
		
		//引号里的userBean是jsp页面的<jsp:getProperty name="userBean" property="plateNumber"/>
		//取得jsp页面发过来的request里的信息
		String plateNumber=request.getParameter("plateNumber").trim();
		//String enterDate=request.getParameter("enterDate").trim();
		
		//调用防乱码方法
		plateNumber=handleString(plateNumber);
		//enterDate=handleString(enterDate);
		
		//定义变量
		String backNews = "车辆入场信息";
		String uri="jdbc:mysql://127.0.0.1/parking?"+
							"user=root&password=admin&characterEncoding=utf-8";
		Connection con;
		PreparedStatement sqlInsert;	
		Enter enterBean= new Enter();//创建新的数据模型
		request.setAttribute("enterBean", enterBean);
		try {
			con =DriverManager.getConnection(uri);//获得数据库连接
			String insertCondition="insert into vehicle(plate_number,enter_date) values(?,?)";//sql语句
			sqlInsert=con.prepareStatement(insertCondition);//预处理语句
			
			//判断输入数据是否完整
			boolean boo=(plateNumber.length()>6);
			if(boo){
				sqlInsert.setString(1,plateNumber);//1对应上面的第一个？
				Timestamp enterDate=new Timestamp(System.currentTimeMillis());
				sqlInsert.setTimestamp(2,enterDate);//2对应上面的第二个？
				
				int result=sqlInsert.executeUpdate();//执行sql语句
				
				if(result == 1){
					backNews="车辆入场成功";
					enterBean.setEnterDate(dateChange(enterDate));
					enterBean.setPlateNumber(plateNumber);
					enterBean.setBackNews(backNews);
				}
				else {
					backNews = "请输入正确的车牌号";
				}
			}
			else{
				backNews = "请重新输入车牌号";
			}
			enterBean.setBackNews(backNews);
			con.close();
		} catch (SQLException exp) {
			backNews="请重新输入车牌号";
			enterBean.setBackNews(backNews);
		}
		RequestDispatcher dispatcher=
				request.getRequestDispatcher("enter.jsp");
		dispatcher.forward(request, response);
	}
	
	//接受jsp文件form表单的post发送方式
	public void doGet(HttpServletRequest request,HttpServletResponse response)
			throws ServletException,IOException{
		doPost(request, response);
	}
	public String dateChange(Timestamp timestamp){
        try {
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String ss = sdf.format(timestamp);
            return ss;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
