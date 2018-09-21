<%@ include file="includes/header.jsp"%>
<div class="breadcrumb">
<a href="dashboard.htm">Home</a>
:: <a href="listProduct.htm">Add Design Attribute</a>
</div>

<div class="box">
<div class="heading">
<h1><img src="image/module.png" alt="" />DesignAttribute</h1>
		<div class="buttons">
		<c:set var="formUrl" value="javaScript:addFormSubmit();" />
		
		<c:if test="${not empty designAttribute.id}">
		<c:set var="formUrl" value="javaScript:editFormSubmit();" />
		</c:if>
		
			 <a onClick='<c:out value="${formUrl}" />' class="button">Save</a> 
			 <a href="listDesignAttribute.htm" class="button">Cancel</a>
			 
		</div>
	</div>
	<div class="content">

		<form name="designAttributeForm" id="designAttributeForm" method="post">
		
		<c:if test="${not empty designAttribute.id}">
		<input type="hidden" name="id" value="${designAttribute.id}" />
		<input type="hidden" name="dateAdded" value="${viewTools.formatDate(designAttribute.dateAdded)}" />
		</c:if>
		
		
		
			<table class="form">
				<tbody>
					<tr>
						<td><span class="required">*</span> Name:</td>
						<td><input type="text" name="name" id="name" size="20" value="${designAttribute.name}" ></td>
					</tr>

					
					<tr>
						<td> Description:</td>
						<td><textarea name="description" id="description">${designAttribute.description}</textarea></td>
					</tr>
					<tr>
						<td><span class="required">*</span>Status</td>
						<td><select name="activeStatus" id="activeStatus">
								<option value="">--select status--</option>
								<option <c:if test="${designAttribute.activeStatus == true}"> selected </c:if> value="1" >Active</option>
								<option <c:if test="${designAttribute.activeStatus == false}"> selected </c:if> value="0">InActive</option>
						</select></td>
					</tr>

					<tr>
						<td><span class="required">*</span> Sort Order:</td>
						<td><input type="text" name="sortOrder" id="sortOrder"  value="${designAttribute.sortOrder}" ></td>
					</tr>



					<tr>
						<td><span class="required">*</span> Display Text:</td>
						<td><input type="text" name="displayText" id="displayText" size="20" value="${designAttribute.displayText}" ></td>
					</tr>


				</tbody>
			</table>
		</form>

	</div>
</div>

<script language="javaScript">
var nonEmptyList = [ 
							['Design Attribute Name','name'],
							['Active Status','activeStatus'],
							['Sort Order','sortOrder'],
							['Display Text','displayText']
							
						    ];
						    
function addFormSubmit(){
	alert("done");
	var tForm = document.designAttributeForm;
	if(showRequiredAlert(nonEmptyList) ){
		return;
	}
	
    tForm.action='saveDesignAttribute.htm';

    document.designAttributeForm.submit();
}


function editFormSubmit(){
 var tForm = document.designAttributeForm;
if(showRequiredAlert(nonEmptyList) ){
		return;
	}
tForm.action='updateDesignAttribute.htm';

	tForm.submit();
}

</script>
<%@ include file="includes/footer.jsp"%>