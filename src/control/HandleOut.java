package control;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;

import javax.servlet.*;
import javax.servlet.http.*;
import data.Out;

public class HandleOut extends HttpServlet{
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
		//调用防乱码方法
		plateNumber=handleString(plateNumber);
		//定义变量
		String backNews = "车辆出场信息";
		String uri="jdbc:mysql://127.0.0.1/parking?"+
							"user=root&password=admin&characterEncoding=utf-8";
		Connection con;
		Out outBean= new Out();//创建新的数据模型
		request.setAttribute("outBean", outBean);
		try {
			con =DriverManager.getConnection(uri);//获得数据库连接

			PreparedStatement sql_query_vehicle = con.prepareStatement(
					"select plate_number,enter_date from vehicle where plate_number=?");//查找在场车辆的全部信息
			PreparedStatement sql_query_internal_vehicle = con.prepareStatement(
					"select plate_number from internal_vehicle where plate_number=?");//查看该车辆是否内部车辆
			PreparedStatement sql_query_fee = con.prepareStatement(
					"select unit_sum from fee_scale where unit='时'");//查找单位金额
			PreparedStatement sql_insert = con.prepareStatement(
					"insert into history_vehicle(plate_number,enter_date,out_date) values(?,?,?)");//将车辆信息加入历史车辆信息
			PreparedStatement sql_delete = con.prepareStatement(
					"DELETE FROM vehicle WHERE plate_number=?;");//删除在场车辆信息
			PreparedStatement sql= con.prepareStatement(
					"insert into charge_record(plate_number,money,enter_date,out_date) values(?,?,?,?)");//保存收费记录
													
			if(plateNumber.length()>0){//判断输入数据是否为空
				Timestamp outDate=new Timestamp(System.currentTimeMillis());
					sql_query_vehicle.setString(1, plateNumber);
					ResultSet rs_vehicle=sql_query_vehicle.executeQuery();		//查找车辆的全部信息	
					if(rs_vehicle.next()){//判断是否查到在场车辆的全部信息
						plateNumber=rs_vehicle.getString(1);
						Timestamp enterDate=rs_vehicle.getTimestamp(2);
						outBean.setPlateNumber(plateNumber);
						outBean.setEnterDate(dateChange(enterDate));
						outBean.setOutDate(dateChange(outDate));
						sql_query_internal_vehicle.setString(1, plateNumber);
						ResultSet rs_internal=sql_query_internal_vehicle.executeQuery();//查看该车辆是否内部车辆
						ResultSet rs_unit=sql_query_fee.executeQuery();			//查找单位金额
						if(rs_internal.next()){//是内部车辆
							outBean.setMoney(0);
							sql_insert.setString(1, plateNumber);
							sql_insert.setTimestamp(2, enterDate);
							sql_insert.setTimestamp(3, outDate);
							int rs_history=sql_insert.executeUpdate();//将车辆信息加入历史车辆信息
							if(rs_history == 1){//判断更新历史车辆是否成功
								sql_delete.setString(1, plateNumber);
								int rd=sql_delete.executeUpdate();//删除在场车辆信息
								if(rd==1){
									backNews ="内部车辆出场成功";
								}
								else{
									backNews = "删除记录失败";
								}
							}
							else{
								backNews = "保存历史失败";
							}
						}
						else if(rs_unit.next()){//不是内部车辆，就查询单位金额
							float unit_sum=rs_unit.getFloat(1);
							float money =jisuanmoney(enterDate,outDate,unit_sum);
							outBean.setMoney(money);					
							sql_insert.setString(1, plateNumber);
							sql_insert.setTimestamp(2, enterDate);
							sql_insert.setTimestamp(3, outDate);
							int rs_history=sql_insert.executeUpdate();//将车辆信息加入历史车辆信息
							if(rs_history == 1){//判断更新历史车辆是否成功
								sql_delete.setString(1, plateNumber);
								int rd=sql_delete.executeUpdate();//删除在场车辆信息
								if(rd==1)
								{
									backNews ="外部车辆出场信息";
									sql.setString(1, plateNumber);
									sql.setDouble(2, money);
									sql.setTimestamp(3,enterDate);
									sql.setTimestamp(4,outDate);
									sql.executeUpdate();//更新收费记录
								}
								else{
									backNews = "删除记录失败";
								}
							}
							else{
								backNews = "保存历史失败";
							}
						}
						else{
							backNews = "查询失败";
						}
					}
					else {
						backNews = "请输入正确的车牌号";
					}
				}
				else{
					backNews = "请重新输入车牌号";
				}
			con.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		outBean.setBackNews(backNews);
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
	//计算停车费
	public float jisuanmoney(Timestamp enterDate,Timestamp outDate,float unit_sum){
		long nh = 1000 * 60 * 60;
		long diff = outDate.getTime() - enterDate.getTime();
		long hour = diff /  nh;
		if(hour>0)	{
			return hour*unit_sum;
		}
		else{
			return unit_sum;
		}
	}
	//改变从数据库读出日期的格式
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
