package control;
import java.io.IOException;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import data.Admin;

public class HandleAdminAdd extends HttpServlet{
	private static final long serialVersionUID = 6699270013664699097L;
	
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
		//创建的javaBean模型
		Admin adminBean=new Admin();
		request.setAttribute("adminBean", adminBean);
		
		//引号里的userBean是jsp页面的<jsp:getProperty name="userBean" property="phone"/>
		//取得jsp页面发过来的request里的信息
		String user=request.getParameter("user").trim();
		String password=request.getParameter("password").trim();
		String again_password=request.getParameter("again_password").trim();
		String number=request.getParameter("number").trim();
		String name=request.getParameter("name").trim();
		String phone=request.getParameter("phone").trim();
		
		//调用防乱码方法
		user=handleString(user);
		password=handleString(password);
		name=handleString(name);
		phone=handleString(phone);
		
		//判断两次密码是否相同
		if (user==null) {
			user="";
		}
		if(password==null){
			password="";
		}
		if(again_password==null){
			again_password="";
		}
		if(!password.equals(again_password)){
			adminBean.setBackNews("两次密码不同，注册失败");
			RequestDispatcher dispatcher=
					request.getRequestDispatcher("adminAdd.jsp");
			dispatcher.forward(request, response);//转发
			return;
		}
		
		//定义变量
		String backNews = "";
		String uri="jdbc:mysql://127.0.0.1/parking?"+
				"user=root&password=admin&characterEncoding=utf-8";
		int result;
		try {
			Connection con=DriverManager.getConnection(uri);//获得数据库连接
			PreparedStatement sql=con.prepareStatement("insert into admin(number,user,password,name,phone) values(?,?,?,?,?)");//预处理语句
			//判断输入数据是否完整
			
			boolean boo=user.length()>0&&password.length()>0&&name.length()>0&&phone.length()>0;
			if(boo)
			{
				sql.setString(1,number);//1对应上面的第一个？
				sql.setString(2,user);//1对应上面的第一个？
				sql.setString(3,password);//2对应上面的第二个？
				sql.setString(4,name);//3对应上面的第三个？
				sql.setString(5,phone);//4对应上面的第四个？
				result=sql.executeUpdate();//执行sql语句
				if(result == 1){
					backNews="添加成功";
					adminBean.setBackNews(backNews);
					adminBean.setUser(user);
					adminBean.setPassword(password);
					adminBean.setNumber(number);
					adminBean.setName(name);
					adminBean.setPhone(phone);
				}
			}
			else{
				backNews="信息填写不完整或名字中有非法字符";	
			}
			adminBean.setBackNews(backNews);
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		RequestDispatcher dispatcher=
				request.getRequestDispatcher("adminAdd.jsp");
		dispatcher.forward(request, response);
	}
	
	//接受jsp文件form表单的post发送方式
	public void doGet(HttpServletRequest request,HttpServletResponse response)
			throws ServletException,IOException{
		doPost(request, response);
	}
}
