<%@ include file="includes/header.jsp"%>


<div class="breadcrumb">
	<a href="dashboard.htm">Home</a> :: <a href="listCuctomer.htm">Customer</a>
</div>

<div class="box">
	<div class="heading">
		<h1>
			<img src="image/customer.png" alt="" /> Customer
		</h1>
		<div class="buttons">
			<a href="addCustomer.htm" class="button">Insert</a>
		</div>
	</div>


	<div class="content">
		<table class="list">
			<thead>
				<tr>
				<td class="left">Number</td>
					<td class="left">Customer Name</td>
					<td class="right">Gender</td>
					<td class="right">Email</td>
					<td class="right">Phone	</td>
					<td class="right">Active Status</td>
					<td class="right">Date added</td>
					<td class="right">Date Modified</td>
					<td class="right">NewsLetter</td>
					<td class="right">Action</td>
				</tr>
			</thead>

			<tbody>
				<form action="listCustomer.htm" name="" method="GET">
					<tr class="filter">
					<td>&nbsp;</td>
						<td colspan="8">Name: <input type="text" name="name" id="name" /></td>
						<input type="hidden" name="orderBy" value="fullName" />
						<td align="right"><input type="submit" class="button"
							value="Search"></td>

					</tr>
				</form>
				<c:forEach var="listVar" items="${customerList}" varStatus="counter">
					<tr>
					<td class="left"> ${counter.count}</td>
						<td class="left">${listVar.fullName}</td>
						<td class="right">${listVar.gender}</td>
						<td class="right">${listVar.email}</td>
						<td class="right">${listVar.phone}</td>
						<td class="right">${listVar.activeStatus}</td>
						<td class="right">${listVar.dateAdded}</td>
						<td class="right">${listVar.dateModified}</td>
						<td class="right">${listVar.newsLetter}</td>
						<td class="right">
						[ <a href="editCustomer.htm?id=${listVar.id}">Edit</a> ]
						[ <a href="addCustomerBodyMeasurement.htm?custId=${listVar.id}&profileId=0">Body Measurement</a> ]
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
		if(confirm('Are you sure you want to delete customer ?'))
		{
			window.location = 'deleteCustomer.htm?id='+id;
		}
	}
	

	
	function loadMorePages(url) {
		var name = $("#name").val();
		window.location = 'listCustomer.htm?' + url + "&name=" + name;
	}
	
	
</script>
<%@ include file="includes/footer.jsp"%>