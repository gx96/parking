package control;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import data.Login;

public class HandleLogin extends HttpServlet{

	private static final long serialVersionUID = 221518740442503852L;
	//初始化函数，第一次调用servlet文件时调用
	public void init(ServletConfig config) throws ServletException{
		super.init(config);
		try{
			Class.forName("com.mysql.jdbc.Driver");
		}
		catch(Exception e){}
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
	public void doPost(HttpServletRequest request,HttpServletResponse response)
							throws ServletException,IOException{
		String user=request.getParameter("user").trim();
		String password=request.getParameter("password").trim();
		user =handleString(user);
		password=handleString(password);
		try {
			HttpSession session = request.getSession(true);
			Login loginBean=new Login();
			session.setAttribute("loginBean", loginBean);
			loginBean=(Login)session.getAttribute("loginBean");
			
			Connection con =DriverManager.getConnection("jdbc:mysql://127.0.0.1/parking?"+"user=root&password=admin&characterEncoding=UTF-8");
			Statement sql = con.createStatement();
			if(user.length()>0){
				if (password.length()>0) {
					ResultSet rs=sql.executeQuery("select user,password from admin where user= '" +user + "'and password ='" +password +"'");
					if(rs.next()){
						if(rs.getString(1).equals("admin") && rs.getString(2).equals("admin")){
							RequestDispatcher dispatcher=request.getRequestDispatcher("indexSystem.jsp");
							dispatcher.forward(request, response);				
							con.close();
						}
						else{
							RequestDispatcher dispatcher=request.getRequestDispatcher("indexVehicle.jsp");
							dispatcher.forward(request, response);				
							con.close();
						}
						//调用登录成功的方法
						
						return;
					}
					else {				
						String backNews="用户名或密码错误";
						loginBean.setBackNewsp(backNews);
					}			
				} 
				else {
					String backNews="请输入密码";
					loginBean.setBackNewsp(backNews);
				}			
			}
			else{
				String backNews="请输入用户名";
				loginBean.setBackNewsu(backNews);
			}
			RequestDispatcher dispatcher=request.getRequestDispatcher("login.jsp");
			dispatcher.forward(request, response);
			con.close();
			return;
		} catch (SQLException exp) {
			exp.printStackTrace();
		}
	}
	public void doGet(HttpServletRequest request,HttpServletResponse response)
			throws ServletException,IOException{
		doPost(request, response);
	}
}
