<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 
<!doctype html>
<html class="no-js" lang="">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="x-ua-compatible" content="ie=edge">
        <title>Fashtag</title>
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="https://fonts.googleapis.com/css?family=Lato:300,400,700,700i,900" rel="stylesheet">
        <link rel="apple-touch-icon" href="apple-touch-icon.png">
        <!-- Place favicon.ico in the root directory -->
<c:set var="req" value="${pageContext.request}" />
<c:set var="url">${req.requestURL}</c:set>
<c:set var="base" value="${fn:substring(url, 0, fn:length(url) - fn:length(req.requestURI))}${req.contextPath}" />
        <link rel="stylesheet" href="${base}/css/bootstrap.min.css">
        <link rel="stylesheet" href="${base}/css/ionicons.min.css">
        <link rel="stylesheet" href="${base}/css/slick.css">
        <link rel="stylesheet" href="${base}/css/iCheck.css">
        <link rel="stylesheet" href="${base}/css/normalize.css">
        <link rel="stylesheet" href="${base}/css/main.css">
        <script src="${base}/js/jquery-2.1.1.js"></script>
        <script src="${base}/js/vendor/modernizr-2.8.3.min.js"></script>
        <script src="js/smoothproducts.min.js"></script>
       <link rel="stylesheet" href="${base}/css/smoothproducts.css">
    </head>
    <body <c:if test="${not empty viewTools.getLoggedInCustomerName()}">class="loggedin"</c:if>>
        <!--[if lt IE 8]>
            <p class="browserupgrade">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
        <![endif]-->
        <div class="alertBox">
        <div class="alert alert-success <c:if test="${ empty info_message}"> hidden </c:if>">
	     <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
        ${info_message}
        </div>
        <div class="alert alert-danger <c:if test="${ empty error_message}"> hidden </c:if>">
      <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
      ${error_message}
       </div>
        </div>
        <div class="container-fluid headerTop">
            <div class="container">
                <div class="row">
                    <div class="col-sm-3 col-xs-6">
                        <p><span class="ion-ios-telephone"></span>${viewTools.getSupportPhone()}</p>
                    </div>
                    <div class="col-sm-9 col-xs-6 pr0">  
                        <ul class="nav navbar-nav navbar-right">
                            <li><a href="care.htm?page=contact">Contact Us</a></li>
                            <li class="hidden-xs"><a href="care.htm?page=feedback">Feedback</a></li>
                            <li><a href="trackOrder.htm">Track Order</a></li>
                            <li class="hidden-xs"><a href="care.htm?page=contact">Complaints</a></li>
                        </ul>                       
                    </div>
                </div>
            </div>
        </div>
        <div class="container-fluid headerMain">
            <div class="container">
                <div class="row mts">
                    <div class="col-xs-4">
                        <a href="index.htm" class="brand"><img src="img/logo.png" alt="" width="156" height="36"></a>
                    </div>
                    <div class="col-xs-8">
                        <ul class="headerNav pull-right">
<input type="hidden" id="loggedInCustomer" value="${viewTools.getLoggedInCustomerName()}" />                            
                            <c:choose>
<c:when test="${not empty viewTools.getLoggedInCustomerName()}">
<li class="dropdown profileDD">
<label class="hidden-sm hidden-xs">${viewTools.getLoggedInCustomerName()}'s Profile</label>
<a href="#" class="user-profile btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
${viewTools.getSubStr(profile.name,7)}
<span class="caret"></span></a>
                            <i class="dropdown-arrow dropdown-arrow-inverse"></i>
                              <ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
                              <li><a data-id="0" href="#">Select Profile</a></li>
                        <c:forEach var="custProf" items="${viewTools.getLoggedInCustomerProfiles()}">      
                                <li><a data-id="${custProf.id}" href="#" >${custProf.name}</a></li>
                         </c:forEach>
                              </ul>
                            </li>
 
<li class="dropdown userDD">
<a href="#" class="user btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
						<span class="ion-ios-person"></span></a>
                              <ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
                                <li><a href="userPreference.htm?res=ORDERS"> Orders</a></li>
                                <li><a href="userPreference.htm?res=RETURNS"> Returns</a></li>
                                <li><a href="userPreference.htm?res=WISHLIST"> Wishlist</a></li>
                                <li><a href="userPreference.htm?res=PROFILE"> Profiles</a></li>
                                <li><a href="userPreference.htm?res=INFO">My Info</a></li>
                                <li><a href="logout.htm">Logout</a></li>
                              </ul>
                            </li>
                            
</c:when>
<c:otherwise>
<li>
<a href="#" class="btn btn-primary" data-toggle="modal" data-target="#loginModal">
<span class="hidden-xs">Login/sign up </span><span class="ion-log-in visible-xs"></span>
</a>
</li>                        
</c:otherwise>
</c:choose>
<li><a href="cart.htm" class="cart"><em class="hidden-xs">${productCount} items - Rs ${totalPrice}</em><span class="ion-android-cart"></span></a></li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>