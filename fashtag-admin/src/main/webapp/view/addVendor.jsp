<%@ include file="includes/header.jsp"%>
<div class="breadcrumb">
<a href="dashboard.htm">Home</a> :: <a href="listVendor.htm">Vendor</a>
</div>

<div class="box">
<div class="heading">
<h1><img src="image/vendor.png" alt="" />Vendor</h1>
		<div class="buttons">
		<c:set var="formUrl" value="javaScript:addFormSubmit();" />
		<c:if test="${not empty vendor.id}">
		<c:set var="formUrl" value="javaScript:editFormSubmit();" />
		</c:if>
		
			 <a onClick='<c:out value="${formUrl}" />' class="button">Save</a> 
			 <a href="listVendor.htm" class="button">Cancel</a>
			 
		</div>
	</div>
	<div class="content">

		<form name="vendorForm" id="vendorForm" method="post">
		
		<c:if test="${not empty vendor.id}">
		<input type="hidden" name="id" value="${vendor.id}" />
		<input type="hidden" name="dateAdded" value="${viewTools.formatDate(vendor.dateAdded)}" />
		</c:if>
		
		
		
			<table class="form">
				<tbody>
					<tr>
						<td><span class="required">*</span> Vendor Name:</td>
						<td><input type="text" name="name" id="name"  value="${vendor.name}" ></td>
					</tr>

					
					<tr>
						<td>Email Id/UserName:</td>
						<td><input type="text" name="emailId" id="emailId"  value="${vendor.emailId}" ></td>
					</tr>
					
					
					<tr>
						<td>Password:</td>
						<td><input type="password" name="password" id="password"  value="" ></td>
					</tr>
					
						
					<tr>
						<td><span class="required">*</span> Phone:</td>
						<td><input type="text" name="phone" id="phone"  value="${vendor.phone}" ></td>
					</tr>
					
					
					<tr>
						<td><span class="required">*</span> Address:</td>
						<td><textarea name="address" id="address">${vendor.address}</textarea></td>
					</tr>
					
					<tr>
						<td><span class="required">*</span>Status</td>
						<td><select name="activeStatus" id="activeStatus">
								<option value="">--select status--</option>
								<option <c:if test="${vendor.activeStatus == true}"> selected </c:if> value="1" >Active</option>
								<option <c:if test="${vendor.activeStatus == false}"> selected </c:if> value="0">InActive</option>
						</select></td>
					</tr>


				</tbody>
			</table>
		</form>

	</div>
</div>

<script language="javaScript">
var nonEmptyList = [ 
							['Vendor Name','name'],
							['Phone','phone'],
							['password','password'],
							['Address','address']
						    ];
						    
function addFormSubmit(){
	var tForm = document.vendorForm;
	if(showRequiredAlert(nonEmptyList) ){
		return;
	}
	

	tForm.action='saveVendor.htm';
	tForm.submit();
}


function editFormSubmit(){
 var tForm = document.vendorForm;
if(showRequiredAlert(nonEmptyList) ){
		return;
	}
	tForm.action='updateVendor.htm';
	tForm.submit();
}
</script>
<%@ include file="includes/footer.jsp"%>