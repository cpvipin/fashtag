<%@ include file="includes/header.jsp"%>
<div class="breadcrumb">
<a href="dashboard.htm">Home</a>
:: <a href="listBodyMeasurementAttribute.htm">Body Measurement Attribute</a>
</div>

<div class="box">
<div class="heading">
<h1><img src="image/module.png" alt="" />Body Measurement Attribute</h1>
		<div class="buttons">
		<c:set var="formUrl" value="javaScript:addFormSubmit();" />
		
		<c:if test="${not empty bodyMeasurementAttribute.id}">
		<c:set var="formUrl" value="javaScript:editFormSubmit();" />
		</c:if>
		
			 <a onClick='<c:out value="${formUrl}" />' class="button">Save</a> 
			 <a href="listBodyMeasurementAttribute.htm" class="button">Cancel</a>
			 
		</div>
	</div>
	<div class="content">

		<form name="bodyMeasurementAttrForm" id="bodyMeasurementAttrForm" method="post">
		
		<c:if test="${not empty bodyMeasurementAttribute.id}">
		<input type="hidden" name="id" value="${bodyMeasurementAttribute.id}" />
		</c:if>
		
		
		
			<table class="form">
				<tbody>
					<tr>
						<td><span class="required">*</span> Name:</td>
						<td><input type="text" name="name" id="name" size="20" value="${bodyMeasurementAttribute.name}" ></td>
					</tr>

					
					<tr>
						<td><span class="required">*</span> Gender:</td>
						<td><select name="gender" id="gender">
						<option  value="0">--select gender--</option>
						<option <c:if test="${bodyMeasurementAttribute.gender== 2}"> selected </c:if> value="2">Male</option>
						<option <c:if test="${bodyMeasurementAttribute.gender == 1}"> selected </c:if> value="1">FeMale</option>
						<option <c:if test="${bodyMeasurementAttribute.gender == 3}"> selected </c:if> value="3">Not Specified</option>
						</select></td>
					</tr>
					
					<tr>
						<td><span class="required">*</span>Is Required</td>
						<td><input type="checkbox" name="isRequired" class="isRequired" <c:if test="${bodyMeasurementAttribute.isRequired == true}">checked</c:if>  /></td>
					</tr>
					
					
					<tr>
						<td><span class="required">*</span>Status</td>
						<td><select name="activeStatus" id="activeStatus">
								<option value="">--select status--</option>
								<option <c:if test="${bodyMeasurementAttribute.activeStatus == true}"> selected </c:if> value="1" >Active</option>
								<option <c:if test="${bodyMeasurementAttribute.activeStatus == false}"> selected </c:if> value="0">InActive</option>
						</select></td>
					</tr>
					
					<tr>
						<td><span class="required">*</span> Description:</td>
						<td><textarea name="description" id="description">${bodyMeasurementAttribute.description}</textarea></td>
					</tr>


				</tbody>
			</table>
		</form>

	</div>
</div>

<script language="javaScript">
var nonEmptyList = [ 
							['Attribute Name','name'],
							['Active Status','activeStatus']
						    ];
						    
function addFormSubmit(){
	var tForm = document.bodyMeasurementAttrForm;
	
	
    tForm.action='saveBodyMeasurementAttribute.htm';

	tForm.submit();
}


function editFormSubmit(){
 var tForm = document.bodyMeasurementAttrForm;
if(showRequiredAlert(nonEmptyList) ){
		return;
	}
tForm.action='updateBodyMeasurementAttribute.htm';

	tForm.submit();
}

</script>
<%@ include file="includes/footer.jsp"%>