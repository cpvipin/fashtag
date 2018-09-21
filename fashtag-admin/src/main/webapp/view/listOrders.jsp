<%@ include file="includes/header.jsp"%>


<div class="breadcrumb">
	<a href="dashboard.htm">Home</a>:: <a href="listOrders.htm">Orders</a>
</div>

<div class="box">
	<div class="heading">
		<h1>
			<img src="image/module.png" alt="" /> Orders
		</h1>
		<div class="buttons">
			<a href="#" class="button">Insert</a>
		</div>
	</div>


	<div class="content">
		<table class="list">
			<thead>
				<tr>
				<td class="left">Order Id</td>
				<td class="right">Customer Name</td>
					<td class="right">Order Status</td>
					<td class="right">Date Added</td>
					<td class="right">Action</td>
				</tr>
			</thead>

			<tbody>
				<form action="listOrders.htm" name="" method="GET">
					<tr class="filter">
						<td>Order Id: <input type="text" name="orderId" id="orderId" value="0" /></td>
						<td>Customer Name: <input type="text" name="customerName" id="customerName" /></td>
						<td>Order Status:
						<select name="orderStatus" id="orderStatus">
            <option value="0" >select status</option>
           <c:forEach var="listVar" items="${ordersStatusColl}" varStatus="counter"> 
           <option value="${listVar.id}">${listVar.name}</option>
            </c:forEach>
            </select>
						
						
						</td>
						<td>&nbsp;</td>
						<td align="right"><input type="submit" class="button"
							value="Search"></td>

					</tr>
				</form>
				<c:forEach var="listVar" items="${ordersList}" varStatus="counter">
					<tr>
					<td class="left"> ${counter.count}</td>
						<td class="right">${listVar.customer.fullName}</td>
						<td class="right">${listVar.ordersStatus.name}</td>
						<td class="right">${viewTools.formatDate(listVar.dateAdded)}</td>
						<td class="right">
						[ <a href="viewOrders.htm?id=${listVar.id}">View</a> ]
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
		function loadMorePages(url) {
		var customerName = $("#customerName").val();
		var orderId = $("#orderId").val();
		var orderStatus = $("#orderStatus").val();

		window.location = 'listOrders.htm?' + url+ "&customerName=" + customerName+ "&orderId=" + orderId + "&orderStatus=" + orderStatus;
	}
	
</script>
<%@ include file="includes/footer.jsp"%>