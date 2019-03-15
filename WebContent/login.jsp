<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> 
<jsp:useBean id="loginBean" class="data.Login" scope="session"/>
<!DOCTYPE html>
<html>
<head> 
    <title>登录</title>
	<link rel="stylesheet" type="text/css" href="css/style.css" />  
</head>
<body >
<div class="main">
   	<div class="mainin">
         <div class="mainin1">
         	<form action="loginServlet" method="post">
          	<ul>
              	<li><span>用户名：</span><input name="user" type="text" id="loginName" placeholder="登录名" class="SearchKeyword"><jsp:getProperty name="loginBean" property="backNewsu"/></li>
                  <li><span>密码：</span><input type="password" name="password" id="Possword" placeholder="密码" class="SearchKeyword2"><jsp:getProperty name="loginBean" property="backNewsp"/></li>
                  <li><input type="submit" name="submit" value="登录" class="tijiao"></li>
              </ul>
             </form>
         </div>
    </div>
</div>
</body>
</html>