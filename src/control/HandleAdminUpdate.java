package control;
import java.io.IOException;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import data.Admin;

public class HandleAdminUpdate extends HttpServlet{
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
		request.setAttribute("updateBean", adminBean);
		
		//引号里的userBean是jsp页面的<jsp:getProperty name="userBean" property="phone"/>
		//取得jsp页面发过来的request里的信息
		String user=request.getParameter("user");
		String password=request.getParameter("password");
		String number=request.getParameter("number");
		String name=request.getParameter("name");
		String phone=request.getParameter("phone");
		
		//调用防乱码方法
		user=handleString(user);
		password=handleString(password);
		name=handleString(name);
		phone=handleString(phone);
		
		
		//定义变量
		String backNews = "";
		String uri="jdbc:mysql://127.0.0.1/parking?"+
				"user=root&password=admin&characterEncoding=utf-8";
		int result;
		try {
			Connection con=DriverManager.getConnection(uri);//获得数据库连接
			PreparedStatement sql1 = con.prepareStatement(
					"select user,password,phone,number from admin where name=?");
			sql1.setString(1,name);
			ResultSet rs=sql1.executeQuery();
			if(rs.next()){
			if (user==null) {
				user=rs.getString(1);
			}
			if(password==null){
				password=rs.getString(2);
			}
			if(phone==null){
				phone=rs.getString(3);		
			}
			
			if(number==null){
				number=rs.getString(4);		
			}}
			PreparedStatement sql=con.prepareStatement("UPDATE admin SET number=?,user=?,password=?,name=?,phone=? WHERE name =?");//预处理语句
			//判断输入数据是否完整	
			sql.setString(1,number);//1对应上面的第一个？
			sql.setString(2,user);//1对应上面的第一个？
			sql.setString(3,password);//2对应上面的第二个？
			sql.setString(4,name);//3对应上面的第三个？
			sql.setString(5,phone);//4对应上面的第四个？
			sql.setString(6,name);//4对应上面的第四个？
			result=sql.executeUpdate();//执行sql语句
			if(result == 1){
				backNews="修改成功";
				adminBean.setBackNews(backNews);
				adminBean.setUser(user);
				adminBean.setNumber(number);
				adminBean.setName(name);
				adminBean.setPhone(phone);
			}
			adminBean.setBackNews(backNews);
			con.close();
		} catch (SQLException e) {
			//backNews="该账号已被使用，请更换账号";
			adminBean.setBackNews(backNews);
			e.printStackTrace();
		} 
		RequestDispatcher dispatcher=
				request.getRequestDispatcher("adminUpdate.jsp");
		dispatcher.forward(request, response);
	}
	
	//接受jsp文件form表单的post发送方式
	public void doGet(HttpServletRequest request,HttpServletResponse response)
			throws ServletException,IOException{
		doPost(request, response);
	}
}
