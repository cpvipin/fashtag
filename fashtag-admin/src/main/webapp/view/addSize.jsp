<%@ include file="includes/header.jsp"%>
<div class="breadcrumb">
<a href="dashboard.htm">Home</a>
:: <a href="listSize.htm">Size</a>
</div>

<div class="box">
<div class="heading">
<h1><img src="image/module.png" alt="" />Size</h1>
		<div class="buttons">
		<c:set var="formUrl" value="javaScript:addFormSubmit();" />
		
		<c:if test="${not empty size.id}">
		<c:set var="formUrl" value="javaScript:editFormSubmit();" />
		</c:if>
		
			 <a onClick='<c:out value="${formUrl}" />' class="button">Save</a> 
			 <a href="listSize.htm" class="button">Cancel</a>
			 
		</div>
	</div>
	<div class="content">

		<form name="sizeForm" id="sizeForm" method="post">
		
		<c:if test="${not empty size.id}">
		<input type="hidden" name="id" value="${size.id}" />
		<input type="hidden" name="dateAdded" value="${viewTools.formatDate(size.dateAdded)}" />
		</c:if>
		
		
		
			<table class="form">
				<tbody>
					<tr>
						<td><span class="required">*</span> Name:</td>
						<td><input type="text" name="name" id="name" size="20" value="${size.name}" ></td>
					</tr>

					
					<tr>
						<td><span class="required">*</span> Display Text:</td>
						<td><input type="text" name="displayText" size="10" id="displayText" value="${size.displayText}"></td>
					</tr>
					<tr>
						<td><span class="required">*</span>Status</td>
						<td><select name="activeStatus" id="activeStatus">
								<option value="">--select status--</option>
								<option <c:if test="${size.activeStatus == true}"> selected </c:if> value="1" >Active</option>
								<option <c:if test="${size.activeStatus == false}"> selected </c:if> value="0">InActive</option>
						</select></td>
					</tr>


				</tbody>
			</table>
		</form>

	</div>
</div>

<script language="javaScript">
var nonEmptyList = [ 
							['Size Name','name'],
							['Active Status','activeStatus']
						    ];
						    
function addFormSubmit(){
	var tForm = document.sizeForm;
	if(showRequiredAlert(nonEmptyList) ){
		return;
	}
	
    tForm.action='saveSize.htm';

	document.sizeForm.submit();
}


function editFormSubmit(){
 var tForm = document.sizeForm;
if(showRequiredAlert(nonEmptyList) ){
		return;
	}
tForm.action='updateSize.htm';

	document.sizeForm.submit();
}

</script>
<%@ include file="includes/footer.jsp"%>