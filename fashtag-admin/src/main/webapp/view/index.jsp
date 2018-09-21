<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html dir="ltr" lang="en"><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="UTF-8">
<title>Admin -Login</title>
<link rel="stylesheet" type="text/css" href="css/stylesheet.css">
</head>
<body>
<div id="container">
    <div id="header">
  <div class="div1">
<div class="div2">Fashtag LOGO</div>
      </div>
  </div>
<div id="content">

  <div class="box" style="width: 400px; min-height: 300px; margin-top: 40px; margin-left: auto; margin-right: auto;">
    <div class="heading">
      <h1><img src="image/lockscreen.png" alt=""> Administrator Login</h1>
    </div>
    <div class="content" style="min-height: 150px; overflow: hidden;">
    <c:if test="${not empty error_message}">
    <div class="warning">${error_message}</div>
    </c:if>
    
                  <form action="login.htm" method="post" id="form">
        <table style="width: 100%;">
          <tbody><tr>
            <td style="text-align: center;" rowspan="4"><img src="image/login.png" alt="Please enter your login details."></td>
          </tr>
          <tr>
            <td>Username:<br>
              <input type="text" name="username" value="" style="margin-top: 4px;">
              <br>
              <br>
              Password:<br>
              <input type="password" name="password" value="" style="margin-top: 4px;">
              
                            </td>
          </tr>
          <tr>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td style="text-align: right;"><input value="Login" type="submit" class="button" /></td>
          </tr>
        </tbody></table>
        </form>
    </div>
  </div>
</div>
</div>
<div id="footer"><a href="#">Fashtag</a> &copy; 2016 </div>
</body></html>