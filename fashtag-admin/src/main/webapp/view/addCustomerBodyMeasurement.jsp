<%@ include file="includes/header.jsp"%>

<div class="breadcrumb">
	<a href="dashboard.htm">Home</a> :: <a href="listProduct.htm">Add
		Customer Body Measurement</a>
</div>

<div class="box">
	<div class="heading">
		<h1>
			<img src="image/module.png" alt="" />Customer Body Measurement
		</h1>
		<div class="buttons">
			<c:set var="formUrl" value="javaScript:addFormSubmit();" />
			<a onClick='<c:out value="${formUrl}" />' class="button">Save</a> <a
				href="listDesignAttribute.htm" class="button">Cancel</a>

		</div>
	</div>
	<div class="content">

		<form name="bodyMeasurementForm" id="bodyMeasurementForm"
			method="post">

			<!-- Design Attribute Specification -->
			
			
			
			
			
			
			
					
					
					
					
			<input type="hidden" name="bodyMeasurementCount"
				id="bodyMeasurementCount" value="${bodyMeasurementCount}" />
				<input type="hidden" id="customerId" name="customerId" value="${customer.id}" />
				<table>
					<tr>
						<td class="left">Customer Name :</td>
						<td class="left"><label><b>${customer.fullName}</b></label> </td>
						<td>Customer Profiles:</td>
						<td>
						
												<select name="customerProfileId" id="customerProfileId">
												<option value="0">--select profile--</option>
						<c:forEach var="custProfile" items="${customerProfileList}" varStatus="counter">
						
						<option value="${custProfile.id}" <c:if test="${customerProfileId==custProfile.id}">selected</c:if>>
						 
						${custProfile.name}</option>
						</c:forEach>
												</select>
						
						</td>
						</td>
					</tr></table>
			<div id="tab_body_measurement">
				<table id="body_measurement" class="list">

				


					<thead>
						<tr>
							<td class="right">Measurement Attribute</td>
							<td class="right">Value</td>
							<td class="right">Unit</td>
							<td class="left">Action</td>
							<td></td>
						</tr>
					</thead>


<c:forEach var="custMeasure" items="${customerMeasurementList}" varStatus="counter">
				<tbody id="body-measurement-row${counter.count-1}">
<tr>
<td class="right"><select name="measurement_attr${counter.count-1}"><option value="0">--select --</option>

<c:forEach var="item" items="${bodyMeasurementAttributeList}"  varStatus="counterSpec">
<option <c:if test="${custMeasure.bodyMeasurementAttribute.id==item.id }">selected</c:if> value="${item.id}">${item.name}</option>
</c:forEach>

</select></td>
<td class="right"><input type="text" name="value${counter.count-1}" value="${custMeasure.value}" /></td>
<td class="right"><select name="unit${counter.count-1}"><option selected value="cm">cm</option></select></td>
<td class="left"><a onclick="removeBodyMeasurement(\'body-measurement-row${counter.count-1}'\')" class="button">Remove</a></td>
</tr>
</tbody>
</c:forEach>
					
					

					<tfoot>

						<tr>
							<td colspan="3"></td>
							<td class="left"><a onclick="addBodyMeasurement();"
								class="button">Add customer body measurement</a>
							</td>
						</tr>
					</tfoot>
				</table>
			</div>



		</form>

	</div>
</div>

<script language="javaScript">

function addFormSubmit(){

	var tForm = document.bodyMeasurementForm;
	tForm.action='saveCustomerBodyMeasurement.htm';
    tForm.submit();
}


function editFormSubmit(){
var tForm = document.bodyMeasurementForm;
tForm.action='updateDesignAttributeSpec.htm';
tForm.submit();
}

</script>



<script type="text/javascript">var no_image="/image/no_image-100x100.jpg";

function removeBodyMeasurement(row)
{
	var newCount = parseInt($('#bodyMeasurementCount').val())-1;
	document.getElementById(row).remove();
    $('#bodyMeasurementCount').attr("value",newCount);

}


function addBodyMeasurement() {
	var body_measurement_row = $('#bodyMeasurementCount').val();
	html  = '<tbody id="body-measurement-row'+body_measurement_row+'">';
	html += '<tr>'; 
	html += '<td class="right"><select name="measurement_attr'+body_measurement_row+'"><option value="0">--select --</option>';
	
	<c:forEach var="item" items="${bodyMeasurementAttributeList}"  varStatus="counterSpec">
	html+='<option value="${item.id}">${item.name}</option>';
	</c:forEach>

	html+='</select></td>';
	
	html += '<td class="right"><input type="text" name="value'+body_measurement_row+'" value="" /></td>';
	html += '<td class="right"><select name="unit'+body_measurement_row+'"><option value="cm">cm</option</select></td>';
	html += '<td class="left"><a onclick="removeBodyMeasurement(\'body-measurement-row'+body_measurement_row+'\')" class="button">Remove</a></td>';
	html += '</tr>';	
	html += '</tbody>';
	
	$('#body_measurement tfoot').before(html);
	body_measurement_row++;
    $('#bodyMeasurementCount').attr("value",body_measurement_row);

}


$(document)
.ready(
		function() {
			$("#customerProfileId").change(function(){  
				var custId=$("#customerId").val();
				var profileId=$("#customerProfileId").val();
				window.location="addCustomerBodyMeasurement.htm?custId="+custId+"&profileId="+profileId;
			
			});
			
			
		}
		);
</script>





<%@ include file="includes/footer.jsp"%>