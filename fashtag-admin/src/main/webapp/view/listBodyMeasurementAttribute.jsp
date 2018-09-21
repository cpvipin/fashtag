<%@ include file="includes/header.jsp"%>


<div class="breadcrumb">
	<a href="dashboard.htm">Home</a>:: <a href="listBodyMeasurementAttribute.htm">Body Measurement Attribute</a>
</div>

<div class="box">
	<div class="heading">
		<h1>
			<img src="image/module.png" alt="" /> Body Measurement Attribute
		</h1>
		<div class="buttons">
			<a href="addBodyMeasurementAttribute.htm" class="button">Insert</a>
		</div>
	</div>


	<div class="content">
		<table class="list">
			<thead>
				<tr>
				<td class="left">Number</td>
				<td class="right">Name</td>
				<td class="right">Gender</td>
					<td class="right">Active Status</td>
					<td class="right">Is required</td>
					<td class="right">Action</td>
				</tr>
			</thead>

			<tbody>
				<form action="listBodyMeasurementAttribute.htm" name="" method="GET">
					<tr class="filter">
					<td>&nbsp;</td>
						<td>Name: <input type="text" name="name" id="name" /></td>
						<td>&nbsp;</td>
						<td><input type="hidden" name="activeStatus" value="1" />&nbsp;</td>
						<td align="right"><input type="submit" class="button"
							value="Search"></td>

					</tr>
				</form>
				<c:forEach var="listVar" items="${bodyMeasurementAttrList}" varStatus="counter">
					<tr>
					<td class="left"> ${counter.count}</td>
					<td class="right">${listVar.name}</td>
					<td class="right">${listVar.gender}</td>
					<td class="right">${listVar.activeStatus}</td>
					<td class="right">${listVar.isRequired}</td>
					<td class="right">
						[ <a href="editBodyMeasurementAttribute.htm?id=${listVar.id}">Edit</a> ]
						[ <a href="javaScript:void(0)" onClick="javaScript:confirmDelete('${listVar.id}')">Delete</a>
							]
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>

		<%@ include file="includes/pagination.jsp"%>
	</div>
</div>



<script>
	function confirmDelete(id){
		if(confirm('Are you sure you want to delete body measurement attribute ?'))
		{
			window.location = 'deleteBodyMeasurementAttribute.htm?id='+id;
		}
	}
	

	function loadMorePages(url) {
		var name = $("#name").val();
		window.location = 'listBodyMeasurementAttribute.htm?' + url + "&name=" + name;
	}
	
</script>
<%@ include file="includes/footer.jsp"%>