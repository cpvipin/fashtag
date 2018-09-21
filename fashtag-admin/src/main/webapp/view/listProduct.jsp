<%@ include file="includes/header.jsp"%>


<div class="breadcrumb">
	<a href="dashboard.htm">Home</a> :: <a href="listProduct.htm">Product</a>
</div>

<div class="box">
	<div class="heading">
		<h1>
			<img src="image/category.png" alt="" /> Product
		</h1>
		<div class="buttons">
			<a href="addProduct.htm" class="button">Insert</a>
		</div>
	</div>


	<div class="content">
		<table class="list">
			<thead>
				<tr>
				<td class="left">Number</td>
				<td class="left">Product Name</td>
					<td class="left">Category Name</td>
					<td class="left">Vendor Name</td>
					<td class="right">Quantity</td>
					<td class="right">Actual Price</td>
					<td class="right">Offer Price</td>
					<td class="right">Date Available</td>
					<td class="right">Date Modified</td>
					<td class="right">Date Added</td>
					<td class="right">Total Views</td>
					<td class="right">Action</td>
				</tr>
			</thead>

			<tbody>
				<form action="listProduct.htm" name="" method="GET">
				<input type="hidden" name="orderBy" value="dateAdded" />
					<tr class="filter">
					<td>&nbsp;</td>
						<td colspan="9">Name: <input type="text" name="name" id="name" /></td>
						<td align="right"><input type="submit" class="button"
							value="Search"></td>

					</tr>
				</form>
				<c:forEach var="listVar" items="${productList}" varStatus="counter">
					<tr>
					<td class="left"> ${counter.count}</td>
					<td class="right">${listVar.name}</td>
						<td class="left">${listVar.category.name}</td>
						<td class="left">${listVar.vendor.name}</td>
						<td class="right">${listVar.quantity}</td>
						<td class="right">${listVar.actualPrice}</td>
						<td class="right">${listVar.offerPrice}</td>
						<td class="right">${viewTools.formatDate(listVar.dateAvailable)}</td>
						<td class="right">${viewTools.formatDate(listVar.dateModified)}</td>
						<td class="right">${viewTools.formatDate(listVar.dateAdded)}</td>
						<td class="right">${listVar.totalViews}</td>
						<td class="right">
						[ <a href="editProduct.htm?id=${listVar.id}">Edit</a> ]
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
		if(confirm('Are you sure you want to delete product ?'))
		{
			window.location = 'deleteProduct.htm?id='+id;
		}
	}
	

	function loadMorePages(url) {
		var name = $("#name").val();
		
		window.location = 'listProduct.htm?' + url + "&name=" + name;
	}
	
</script>
<%@ include file="includes/footer.jsp"%>