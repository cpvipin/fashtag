<%@ include file="includes/header.jsp"%>


<div class="breadcrumb">
	<a href="dashboard.htm">Home</a>:: <a href="listDesignAttribute.htm">Design Attribute Specification</a>
</div>

<div class="box">
	<div class="heading">
		<h1>
			<img src="image/module.png" alt="" /> Design Attribute Specification
		</h1>
		<div class="buttons">
			<a href="addDesignAttrSpec.htm" class="button">Insert</a>
		</div>
	</div>


	<div class="content">
		<table class="list">
			<thead>
				<tr>
				<td class="left">Number</td>
				<td class="right">Name</td>
					<td class="right">Design Attribute</td>
					<td class="right">Description</td>
					<td class="right">Sort Order</td>
					<td class="right">Action</td>
				</tr>
			</thead>

			<tbody>
				<form action="listDesignAttrSpec.htm" name="" method="GET">
					<tr class="filter">
					<td colspan="2">&nbsp;</td>
						<td>Design Attribute: <select name="designAttributeId" id="designAttributeId"><option value="0">select design attribute</option>'
	<c:forEach var="listVar" items="${designAttributeList}" varStatus="counter">
<option value="${listVar.id}">${listVar.name}</option>';
	</c:forEach>
</select></td>
						<td colspan="2">&nbsp;</td>
						<td align="right"><input type="submit" class="button"
							value="Search"></td>

					</tr>
				</form>
				<c:forEach var="listVar" items="${designAttributeSpecList}" varStatus="counter">
					<tr>
					<td class="left"> ${counter.count}</td>
						<td class="right">${listVar.name}</td>
						<td class="right">${listVar.designAttribute.name}</td>
						<td class="right">${listVar.description}</td>
						<td class="right">${listVar.sortOrder}</td>
						<td class="right">
						[ <a href="editDesignAttrSpec.htm?designAttributeId=${listVar.designAttribute.id}">Edit</a> ]
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>

		<%@ include file="includes/pagination.jsp"%>
	</div>
</div>



<script>
	
	

	function loadMorePages(url) {
		var name = $("#name").val();
		window.location = 'listDesignAttributes.htm?' + url + "&name=" + name;
	}
	
</script>
<%@ include file="includes/footer.jsp"%>