<%@ include file="includes/header.jsp"%>
<div class="breadcrumb">
<a href="dashboard.htm">Home</a>
:: <a href="listOrders.htm">Orders</a>
</div>

<div class="box">
<div class="heading">
<h1><img src="image/module.png" alt="" />Orders</h1>
		<div class="buttons">
		<c:set var="formUrl" value="javaScript:addFormSubmit();" />
		
		<c:if test="${not empty size.id}">
		<c:set var="formUrl" value="javaScript:editFormSubmit();" />
		</c:if>
			 
		</div>
	</div>
	
	
	
	
	<div class="content">
      <div class="vtabs"><a class="" href="#tab-order">Order Details</a>
                
                <a href="#tab-product">Products</a><a href="#tab-history">Order History</a>
              </div>
      <div style="display: none;" id="tab-order" class="vtabs-content">
        <table class="form">
          <tbody><tr>
            <td>Order ID:</td>
            <td>${orderProduct.order.id}</td>
          </tr>
         
                    <tr>
            <td>Date Added:</td>
            <td>${viewTools.formatDate(orderProduct.order.dateAdded)}</td>
          </tr>
         
        </tbody></table>
      </div>
      
          
            <div style="display: none;" id="tab-product" class="vtabs-content">
        <table class="list">
          <thead>
            <tr>
              <td class="left">Product</td>
              <td class="left">Image</td>
              <td class="right">Quantity</td>
              <td class="right">Unit Price</td>
              <td class="right">Size</td>
              <td class="right">Total</td>
            </tr>
          </thead>
          <tbody>
          	            <tr>
              <td class="left"><a href="editProduct.htm?id=${orderProduct.product.id}">${orderProduct.product.name}</a>
                </td>
                <td><img src="${viewTools.getProdImageUrl()}${orderProduct.product.defaultImage}" /></td>
              <td class="right">${orderProduct.quantity}</td>
              <td class="right">${orderProduct.unitPrice}</td>
              <td class="right">${orderProduct.size.name}</td>
              <td class="right">${orderProduct.totalAmount}</td>


            </tr>
                                  </tbody>
                  </table>
              </div>
      <div style="display: none;" id="tab-history" class="vtabs-content">
        <div id="history"><table class="list">
  <thead>
    <tr>
      <td class="left"><b>Date Added</b></td>
      <td class="left"><b>Comment</b></td>
      <td class="left"><b>Status</b></td>
     
    </tr>
  </thead>
  <tbody>
  
  				<c:forEach var="listVar" items="${orderProduct.order.orderHistory}" varStatus="counter">
        <tr>
      <td class="left">${viewTools.formatDate(listVar.dateAdded)}</td>
      <td class="left">${listVar.comment}</td>
      <td class="left">${listVar.ordersStatus.name}</td>     
    </tr>
  </c:forEach>
          </tbody>
</table>
</div>

 
      </div>
          </div>
          
          
          
          
</div>

<script type="text/javascript"><!--
$('.vtabs a').tabs();
//--></script> 

<%@ include file="includes/footer.jsp"%>