package control;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;

import javax.servlet.*;
import javax.servlet.http.*;
import data.Price;

public class HandlePrice extends HttpServlet{
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
		String unit=request.getParameter("unit").trim();
		double unitSum=Double.valueOf(request.getParameter("unitSum").trim());
		//调用防乱码方法	
		unit=handleString(unit);
		//定义变量
		String backNews = "";
		String uri="jdbc:mysql://127.0.0.1/parking?"+
							"user=root&password=admin&characterEncoding=utf-8";
		Connection con;
		PreparedStatement sql;	
		Price priceBean= new Price();//创建新的数据模型
		request.setAttribute("priceBean", priceBean);
		try {
			con =DriverManager.getConnection(uri);//获得数据库连接
			String Condition="UPDATE fee_scale SET unit=?,unit_sum=? WHERE unit =?";//sql语句
			sql=con.prepareStatement(Condition);//预处理语句
			
			//判断输入数据是否完整
			boolean boo=(unitSum!=0&& unit.length()>0);
			if(boo){
				sql.setString(1,unit);
				sql.setDouble(2,unitSum);
				sql.setString(3,unit);
				int result=sql.executeUpdate();//执行sql语句
				
				if(result == 1){
					backNews="设置成功";
					priceBean.setUnit(unit);
					priceBean.setUnitSum(unitSum);
				}
				else {
					backNews = "请重新输入";
				}
			}
			else{
				backNews = "请重新输入";
			}
			priceBean.setBackNews(backNews);
			con.close();
		} catch (SQLException exp) {
			backNews="请重新输入";
			priceBean.setBackNews(backNews);
		}
		RequestDispatcher dispatcher=
				request.getRequestDispatcher("price.jsp");
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
