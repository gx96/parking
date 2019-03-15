package control;
import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;
import data.Out;

public class HandleCost extends HttpServlet{
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

		Out outBean= new Out();
		request.setAttribute("outBean", outBean);
		outBean.setBackNews("外部车辆出场成功");
		RequestDispatcher dispatcher=
				request.getRequestDispatcher("out.jsp");
		dispatcher.forward(request, response);
		return;
	}
	
	//接受jsp文件form表单的post发送方式
	public void doGet(HttpServletRequest request,HttpServletResponse response)
			throws ServletException,IOException{
		doPost(request, response);
	}
}
