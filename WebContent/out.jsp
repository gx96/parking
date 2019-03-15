<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.Date,java.text.SimpleDateFormat" %>
<jsp:useBean id="outBean" class="data.Out" scope="request"/>
<!DOCTYPE html>
<html>
	<head>
		<title>出场登记</title>
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
	</head>
	<body>
		<div class="container">
			<div class="header" ><a href="#"><img src="image/20.jpg" alt="在此处插入徽标" name="Insert_logo" width="180" height="90" id="Insert_logo" style="background-color: #C6D580; display:block;" /></a> 
				</div>
			    <div class="sidebar1">
				    <ul class="nav">   
				      <li><a href="enter.jsp">车辆入场登记</a></li>
				      <li><a href="out.jsp">车辆出场登记</a></li>
				      <li><a href="internalVehicle.jsp">内部车辆信息管理</a></li>
				      <li><a href="query.jsp">车辆信息查看</a></li>
				      <br />
				      <br />
				      <br />
				      <br />
				      <br />
				      <li><a href="login.jsp">退出系统</a></li>
			    	</ul>  
			    	<br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br />
			</div>
			<div align=center>
				<form action="outServlet" method="post">
					<table border="1">
						<tr>
							<td align=right>车牌号：</td>
							<td><input type="text" name="plateNumber"></td>
						</tr>
						<tr>
							<td colspan=2 align=center><input type="submit" value="登记" name="submit"></td>
						</tr>
					</table>
				</form>
			</div>
			<div align="center">
				<hr>
				<form action="costServlet" method="post">
				<table border="1">
					<tr>
						<td colspan=2><jsp:getProperty name="outBean" property="backNews"/></td>
					</tr>
					<tr>
						<td>车牌号：</td>
						<td><jsp:getProperty name="outBean" property="plateNumber"/></td>
					</tr>
					<tr>
						<td>入场时间：</td>
						<td><jsp:getProperty name="outBean" property="enterDate"/></td>
					</tr>
					<tr>
						<td>出场时间：</td>
						<td><jsp:getProperty name="outBean" property="outDate"/></td>
					</tr>
					<tr>
						<td>停车费用：</td>
						<td><jsp:getProperty name="outBean" property="money"/>元</td>
					</tr>
					<tr>
						<td colspan="2" align="center"><input type="submit" name="m" value="确认收费" ></td>
					</tr>
				</table>
				</form>
			</div>
			<div class="footer">
		 	</div>
		</div>
	</body>
</html>