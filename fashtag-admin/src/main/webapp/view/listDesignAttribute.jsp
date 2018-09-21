<%@ include file="includes/header.jsp"%>


<div class="breadcrumb">
	<a href="dashboard.htm">Home</a>:: <a href="listDesignAttribute.htm">Design Attribute</a>
</div>

<div class="box">
	<div class="heading">
		<h1>
			<img src="image/module.png" alt="" /> Design Attribute
		</h1>
		<div class="buttons">
			<a href="addDesignAttribute.htm" class="button">Insert</a>
		</div>
	</div>


	<div class="content">
		<table class="list">
			<thead>
				<tr>
				<td class="left">Number</td>
				<td class="right">Name</td>
					<td class="right">Description</td>
					<td class="right">Status</td>
					<td class="right">Action</td>
				</tr>
			</thead>

			<tbody>
				<form action="listDesignAttribute.htm" name="" method="GET">
					<tr class="filter">
					<td>&nbsp;</td>
						<td>Name: <input type="text" name="name" id="name" /></td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td align="right"><input type="submit" class="button"
							value="Search"></td>

					</tr>
				</form>
				<c:forEach var="listVar" items="${designAttributeList}" varStatus="counter">
					<tr>
					<td class="left"> ${counter.count}</td>
						<td class="right">${listVar.name}</td>
						<td class="right">${listVar.description}</td>
						<td class="right">${listVar.activeStatus}</td>
						<td class="right">
						[ <a href="editDesignAttribute.htm?id=${listVar.id}">Edit</a> ]
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
		if(confirm('Are you sure you want to delete design attribute ?'))
		{
			window.location = 'deleteDesignAttribute.htm?id='+id;
		}
	}
	

	function loadMorePages(url) {
		var name = $("#name").val();
		window.location = 'listDesignAttributes.htm?' + url + "&name=" + name;
	}
	
</script>
<%@ include file="includes/footer.jsp"%>