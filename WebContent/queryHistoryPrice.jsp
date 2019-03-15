<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.sun.rowset.*,java.util.Date,java.text.SimpleDateFormat,java.sql.*,data.Query" %>
<jsp:useBean id="queryBean" class="data.Query" scope="request"/>
<!DOCTYPE html>
<html>
	<head>
		<style type="text/css"> 
			<!--
			body {font: 100%/1.4 Verdana, Arial, Helvetica, sans-serif;background-color: #42413C;margin: 0;padding: 0;color: #000;}
			ul, ol, dl {padding: 0;margin: 0;}
			a:visited {color: #6E6C64;text-decoration: underline;}
			ul.nav {list-style: none;border-top: 1px solid #666;margin-bottom: 15px; }
			ul.nav li {border-bottom: 1px solid #666;}
			ul.nav a, ul.nav a:visited {padding: 5px 5px 5px 15px;display: block; width: 160px; text-decoration: none;background-color: #C6D580;}
			ul.nav a:hover, ul.nav a:active, ul.nav a:focus {background-color: #ADB96E;color: #FFF;}
			table{margin:0 auto;}
			.container {width: 960px;background-color: #FFF;margin: 0 auto; }
			.header {background-color: #ADB96E;}
			.sidebar1 {float: left;width: 180px;background-color: #EADCAE;padding-bottom: 10px;}
			.content {padding: 10px 0;width: 780px;float: left;}
			.footer {padding: 10px 0;background-color: #CCC49F;position: relative;clear: both; }
			-->
		</style> 
		<title>查看历史收费记录</title>
	</head>
	<body>
		<div class="container">
			<div class="header" ><a href="#"><img src="image/20.jpg" alt="在此处插入徽标" name="Insert_logo" width="180" height="90" id="Insert_logo" style="background-color: #C6D580; display:block;" /></a> 
		  	</div>
		  	<div class="sidebar1">
			    <ul class="nav">   
			      <li><a href="price.jsp">设置收费金额</a></li>
			      <li><a href="queryHistoryPrice.jsp">查看历史收费记录</a></li>
			      <li><a href="indexSystem.jsp">返回上一页</a></li>
			    </ul>
			    <br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br />
			</div>
			<table border="1" style="margin-top:20px">
				<tr>
					<td align=center>车牌号</td>
					<td align=center>停车费</td>
					<td align=center>入场时间</td>
					<td align=center>出场时间</td>
				</tr>
				<jsp:setProperty property="totalPages" name="queryBean" param="pageSize"/>
				<jsp:setProperty property="currentPage" name="queryBean" param="currentPage"/>
				<%!
				int totalRecord; //当前记录数
				int totalPages;//当前页数
				int index;//游标
				//改变时间对象的输出形式
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
				%>
				<%
				  try{
				   Class.forName("com.mysql.jdbc.Driver");
				   String uri="jdbc:mysql://127.0.0.1/parking?"+
							"user=root&password=admin&characterEncoding=UTF-8";
				   Connection con =DriverManager.getConnection(uri);
				   Statement sql = con.createStatement();
					ResultSet rs=sql.executeQuery("select plate_number,money,enter_date,out_date from charge_record");
					CachedRowSetImpl rowSet=new CachedRowSetImpl();
					rowSet.populate(rs);			
					rowSet.last();
					totalRecord=rowSet.getRow();//全部记录数
					
					totalPages=queryBean.getTotalPages();
					if(totalRecord%7==0)
						totalPages=totalRecord/7;
					else
						totalPages=totalRecord/7+1;
					queryBean.setTotalPages(totalPages);
					if(totalPages>=1){//总页数
						if(queryBean.getCurrentPage()<1)//当前页数
							queryBean.setCurrentPage(queryBean.getTotalPages());
						if(queryBean.getCurrentPage()>queryBean.getTotalPages())
							queryBean.setCurrentPage(1);
						index=(queryBean.getCurrentPage()-1)*7+1;
						rowSet.absolute(index);
						boolean boo =true;
						for(int i=1;i<=7&&boo;i++){
							
				%>
				<tr>
					<td align=center><%= rowSet.getObject(1) %></td>
					<td align=center><%= rowSet.getObject(2) %>元</td>
					<td align=center><%= dateChange(rowSet.getTimestamp(3)) %></td>
					<td align=center><%= dateChange(rowSet.getTimestamp(4)) %></td>
				</tr>					
				<% 
						boo = rowSet.next();
						}					
					}
					rowSet.close();
					con.close();
				    }catch(Exception e){
					    e.printStackTrace();
				    }
				%>
			</table>
			<br>
			<table>
			<tr>
				<td align="center">
				全部记录数：<%= totalRecord %>
				当前显示第<jsp:getProperty name="queryBean" property="currentPage"/>
				页，共有<jsp:getProperty name="queryBean" property="totalPages"/>页。
				</td>
			</tr>
			</table>
			  <table>
				<tr align=right>
					<td>
						<form action="" method=post>
							<input type=hidden name="currentPage" value="<%= queryBean.getCurrentPage()-1%>">
							<input type=submit name="g" value="上一页">
						</form>
					</td>
					<td>
						<form action="" method=post>
							<input type=hidden name="currentPage" value="<%= queryBean.getCurrentPage()+1%>">
							<input type=submit name="g" value="下一页">
						</form>
					</td>
					<td>
						<form action="" method=post>
							转跳：<input type=text name="currentPage" size=2>页
							<input type=submit name="g" value="确定">
						</form>
					</td>
				</tr>
			</table>
			<div class="footer">
			</div>
		</div>
	</body>
</html>