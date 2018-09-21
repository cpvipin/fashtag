<html>
<head>
	<title>Sub-merchant checkout page</title>
	<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
</head>
<body>
	<form id="nonseamless" method="post" name="redirect" action="${pgUrl}"/> 
		<input type="hidden" id="encRequest" name="encRequest" value="${encRequest}">
		<input type="hidden" name="access_code" id="access_code" value="${accessCode}">
		<script language='javascript'>document.redirect.submit();</script>
	</form>
	
 </body> 
</html>