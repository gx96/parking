<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean id="updateBean" class="data.Admin" scope="request"/>
<!DOCTYPE html>
<html>
	<head>
		<title>修改停车场管理员</title>
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
			      <li><a href="adminAdd.jsp">添加停车场管理员</a></li>
			      <li><a href="adminDelete.jsp">删除停车场管理员</a></li>
			      <li><a href="adminUpdate.jsp">修改停车场管理员</a></li>
			      <li><a href="adminQuery.jsp">查询停车场管理员</a></li>
			      <li><a href="indexSystem.jsp">返回上一页</a></li>
			    </ul>
		    	<br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br />
		  </div>
	<div align=center>
		<form action="updateAdminServlet" method="post">
			<table border="1">
				<tr>
					<td align=right>姓名：</td>
					<td><input type="text" name="name"></td>
				</tr>
				<tr>
					<td align=right>账号：</td>
					<td><input type="text" name="user"></td>
				</tr>
				<tr>
					<td align=right>密码：</td>
					<td><input type="password" name="password"></td>
				</tr>
				<tr>
					<td align=right>工号：</td>
					<td><input type="text" name="number"></td>
				</tr>
				
				<tr>
					<td align=right>联系电话：</td>
					<td><input type="text" name="phone"></td>
				</tr>
				<tr>
					<td colspan=2 align=center><input type="submit" value="提交" name="submit"></td>
				</tr>
			</table>
		</form>
	</div>
	<div align="center">
	<hr>
	<p>修改反馈</p>
	<jsp:getProperty name="updateBean" property="backNews"/>
		<table border="1" >
			<tr>
				<td colspan=2 align="center">管理员信息</td>
			</tr>
			<tr>
				<td align=right>姓名：</td>
				<td><jsp:getProperty name="updateBean" property="name"/></td>
			</tr>
			<tr>
				<td align=right width="50%">账号：</td>
				<td width="50%"><jsp:getProperty name="updateBean" property="user"/></td>
			</tr>
			<tr>
				<td align=right>工号：</td>
				<td><jsp:getProperty name="updateBean" property="number"/></td>
			</tr>		
			<tr>
				<td align=right>联系电话：</td>
				<td><jsp:getProperty name="updateBean" property="phone"/></td>
			</tr>
		</table>
	</div>
	<div class="footer">
		 </div>
		</div>
	</body>
</html>