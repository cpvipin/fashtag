<%@ include file="includes/header.jsp"%>


<div class="breadcrumb">
	<a href="dashboard.htm">Home</a> :: <a href="listCategory.htm">Category</a>
</div>

<div class="box">
	<div class="heading">
		<h1>
			<img src="image/category.png" alt="" /> Category
		</h1>
		<div class="buttons">
			<a href="addCategory.htm" class="button">Insert</a>
		</div>
	</div>


	<div class="content">
		<table class="list">
			<thead>
				<tr>
				<td class="left">Number</td>
					<td class="left">Category Name</td>
					<td class="right">Sort Order</td>
					<td class="right">Status</td>
					<td class="right">Action</td>
				</tr>
			</thead>

			<tbody>
				<form action="listCategory.htm" name="" method="GET">
					<tr class="filter">
					<td>&nbsp;</td>
						<td colspan="3">Name: <input type="text" name="name" id="name" /></td>
						
						<td align="right"><input type="submit" class="button"
							value="Search"></td>

					</tr>
				</form>
				<c:forEach var="listVar" items="${categoryList}" varStatus="counter">
					<tr>
					<td class="left"> ${counter.count}</td>
						<td class="left">
						<c:if test="${not empty listVar.parent.id}">
						 	${viewTools.getCategoryParents(listVar.id)}
						 </c:if> ${listVar.name} </td>
						<td class="right">${listVar.sortOrder}</td>
						<td class="right">${listVar.activeStatus}</td>
						<td class="right">
						[ <a href="editCategory.htm?id=${listVar.id}">Edit</a> ]
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
		if(confirm('Are you sure you want to delete category ?'))
		{
			window.location = 'deleteCategory.htm?id='+id;
		}
	}
	

	
	function loadMorePages(url) {
		var name = $("#name").val();
		window.location = 'listCategory.htm?' + url + "&name=" + name;
	}
	
	
</script>
<%@ include file="includes/footer.jsp"%>