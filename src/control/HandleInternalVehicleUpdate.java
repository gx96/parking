package control;
import java.io.IOException;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import data.Vehicle;

public class HandleInternalVehicleUpdate extends HttpServlet{
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
		Vehicle vehicleBean=new Vehicle();
		request.setAttribute("vehicleBean", vehicleBean);
		
		//引号里的userBean是jsp页面的<jsp:getProperty name="userBean" property="phone"/>
		//取得jsp页面发过来的request里的信息
		String staffName=request.getParameter("staffName").trim();
		String plateNumber=request.getParameter("plateNumber").trim();
		
		//调用防乱码方法

		staffName=handleString(staffName);
		plateNumber=handleString(plateNumber);
		
		//定义变量
		String backNews = "";
		String uri="jdbc:mysql://127.0.0.1/parking?"+
				"user=root&password=admin&characterEncoding=utf-8";
		int result;
		try {
			Connection con=DriverManager.getConnection(uri);//获得数据库连接
			PreparedStatement sql=con.prepareStatement("UPDATE internal_vehicle SET staff_name=?,plate_number=? WHERE staff_name =?");//预处理语句
			//判断输入数据是否完整
			sql.setString(1,staffName);//1对应上面的第一个？
			sql.setString(2,plateNumber);//2对应上面的第二个？
			sql.setString(3,staffName);//3对应上面的第三个？
			result=sql.executeUpdate();//执行sql语句
			if(result == 1){
				backNews="修改成功";
				vehicleBean.setBackNews(backNews);
				vehicleBean.setStaffName(staffName);
				vehicleBean.setPlateNumber(plateNumber);
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		RequestDispatcher dispatcher=
				request.getRequestDispatcher("internalVehicleUpdate.jsp");
		dispatcher.forward(request, response);
	}
	
	//接受jsp文件form表单的post发送方式
	public void doGet(HttpServletRequest request,HttpServletResponse response)
			throws ServletException,IOException{
		doPost(request, response);
	}
}
