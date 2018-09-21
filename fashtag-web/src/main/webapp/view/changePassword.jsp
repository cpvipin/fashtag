<%@ include file="includes/header.jsp"%>


  <c:if test="${not empty error_message}">
    <div class="warning">${error_message}</div>
    </c:if>
    
    
<form action="updatePassword.htm" method="post" name="changePasswordFrm" id="changePasswordFrm" >
Email/Mobile<input type="text"  name="userId" id="userId" value="${USERID}" autocomplete="off" /><br/>
New password: <input type="password" name="password" id="password" autocomplete="off"/><br/>
Confirm password: <input type="password" name="confirmPassword" id="confirmPassword" autocomplete="off"/><br/>
<input type="submit" value="Change password" />

</form>


  <%@ include file="includes/footer.jsp"%>
  