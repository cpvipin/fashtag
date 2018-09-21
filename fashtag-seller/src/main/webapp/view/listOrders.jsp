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
				<td class="left">No: </td>
				<td class="left">Order Number</td>
				<td class="left">Product Name</td>
					<td class="right">Order Status</td>
					<td class="left">Quantity</td>
					<td class="left">Size</td>
					<td class="left">Unit Price</td>
					<td class="left">Total Amount</td>
					<td class="right">Order Date</td>
					<td class="right">Action</td>
				</tr>
			</thead>

			<tbody>
				<form action="listOrders.htm" name="" method="GET">
					<tr class="filter">
						<td></td><td></td>
						<td>Order Status:
						<select name="orderStatus" id="orderStatus">
            <option value="0" >select status</option>
           <c:forEach var="listVar" items="${ordersStatusColl}" varStatus="counter"> 
           <option value="${listVar.id}">${listVar.name}</option>
            </c:forEach>
            </select>
						</td>
<td>Order Date:</td>
                <td>
                			<input type="text" id="orderDate" name="orderDate" size="20" value=""/>					        	
			  		<img src="image/calendar.gif" id="orderDate_trigger" style="cursor: pointer;" title="Order Date"/> 
				  		<script type="text/javascript">
							  Calendar.setup(
							    {
							      inputField  : "orderDate",
							      ifFormat    : "%d/%m/%Y", 
							      button      : "orderDate_trigger"
							    }
								  );
						</script>  
				   </td>						<td colspan="4">&nbsp;</td>
						<td align="right"><input type="submit" class="button"
							value="Search"></td>

					</tr>
				</form>
				<c:forEach var="listVar" items="${ordersList}" varStatus="counter">
					<tr>
					<td class="left"> ${counter.count}</td>
					<td class="right">${listVar.order.id}</td>
						<td class="right">${listVar.product.name}</td>
						<td class="right">${listVar.order.ordersStatus.name}</td>
						<td class="right">${listVar.quantity}</td>
						<td class="right">${listVar.size.name}</td>
						<td class="right">${listVar.unitPrice}</td>
						<td class="right">${listVar.totalAmount}</td>
						<td class="right">${viewTools.formatDate(listVar.order.dateAdded)}</td>
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