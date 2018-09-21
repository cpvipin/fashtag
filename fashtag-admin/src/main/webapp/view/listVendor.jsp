<%@ include file="includes/header.jsp"%>


<div class="breadcrumb">
	<a href="dashboard.htm">Home</a> :: <a href="listVendor.htm">Vendor</a>
</div>

<div class="box">
	<div class="heading">
		<h1>
			<img src="image/vendor.png" alt="" /> Vendor
		</h1>
		<div class="buttons">
			<a href="addVendor.htm" class="button">Insert</a>
		</div>
	</div>


	<div class="content">
		<table class="list">
			<thead>
				<tr>
					<td class="left">Number</td>
					<td class="left">Vendor Name</td>
					<td class="right">Email</td>
					<td class="right">Phone</td>
					<td class="right">Address</td>
					<td class="right">Status</td>
					<td class="right">Action</td>
				</tr>
			</thead>

			<tbody>
				<form action="listVendor.htm" name="" method="GET">
					<tr class="filter">
						<td>&nbsp;</td>
						<td>Name: <input type="text" name="name" id="name" /></td>

						<td align="right" colspan="5"><input type="submit"
							class="button" value="Search"></td>

					</tr>
				</form>
				<c:forEach var="listVar" items="${vendorList}" varStatus="counter">
					<tr>
						<td class="left">${counter.count}</td>
						<td class="right">${listVar.name}</td>
						<td class="right">${listVar.emailId}</td>
						<td class="right">${listVar.phone}</td>
						<td class="right">${listVar.address}</td>
						<td class="right">${listVar.activeStatus}</td>
						<td class="right">[ <a href="editVendor.htm?id=${listVar.id}">Edit</a>
							] [ <a href="javaScript:void(0)"
							onClick="javaScript:confirmDelete('${listVar.id}')">Delete</a> ]
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>

		<%@ include file="includes/pagination.jsp"%>
	</div>
</div>



<script>
	function confirmDelete(id) {
		if (confirm('Are you sure you want to delete Vendor ?')) {
			window.location = 'deleteVendor.htm?id=' + id;
		}
	}
	
	
	function loadMorePages(url) {
		var name = $("#name").val();
		window.location = 'listVendor.htm?' + url + "&name=" + name;
	}
	
	
</script>
<%@ include file="includes/footer.jsp"%>