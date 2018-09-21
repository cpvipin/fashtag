<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html dir="ltr" lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="UTF-8">
<title>Dashboard</title>
<link rel="stylesheet" type="text/css" href="css/stylesheet.css">
<script type="text/javascript" src="js/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.8.16.custom.min.js"></script>
<link type="text/css" href="css/jquery-ui-1.8.16.custom.css"
	rel="stylesheet">
<script type="text/javascript" src="js/tabs.js"></script>
<script type="text/javascript" src="js/superfish.js"></script>
<script type="text/javascript" src="js/common.js"></script>
	<script type="text/javascript" src="js/cal/calendar.js"></script>
	<script type="text/javascript" src="js/cal/lang/calendar-en.js"></script>
	<script type="text/javascript" src="js/cal/calendar-setup.js"></script>
	<link href="js/cal/css/calendar-blue2.css" rel="stylesheet" type="text/css">
<script src="js/utils/CommonUtils.js"></script>

</head>
<body>
	<div id="container">
		<div id="header" >
			<div class="div1">
				<div class="div2">
				Dashboard
				</div>
				<div class="div3">
					<img src="image/lock.png" alt="" style="position: relative; top: 3px;"> 
						You are logged in <span></span>
				</div>
			</div>
			<div id="menu">
				<ul class="left sf-js-enabled" style="display: block;">
					<li id="dashboard" class="selected"><a
						href="dashboard.htm" class="top">Dashboard</a></li>
					<li id="dashboard"><a href="listProduct.htm" class="top">Products</a></li>
					
					
					<li id="extension"><a class="top">Sales</a>
						<ul style="display: none; visibility: hidden;">
							<li><a href="listOrders.htm">Orders</a></li>
							<li><a href="listReturns.htm">Returns</a></li>
						</ul>
					</li>
					
				</ul>
				<ul class="right sf-js-enabled" style="display: block;">
					
					<li><a class="top"
						href="logout.htm">Logout</a></li>
				</ul>
			</div>
		</div>
		<div id="content">
			
			<c:if test="${not empty info_message}"><div class="success">${info_message}</div> </c:if>
			<c:if test="${not empty error_message}"><div class="warning">${error_message}</div> </c:if>
			