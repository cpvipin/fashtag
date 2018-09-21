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
		
			<!--   <a onClick='<c:out value="${formUrl}" />' class="button">Save</a> 
			 <a href="listSize.htm" class="button">Cancel</a>  -->
			 
		</div>
	</div>
	
	
	
	
	<div class="content">
      <div class="vtabs"><a class="" href="#tab-order">Order Details</a>
                <a href="#tab-shipping">Shipping Details</a>
                <a href="#tab-product">Products</a><a href="#tab-history">Order History</a>
              </div>
      <div style="display: none;" id="tab-order" class="vtabs-content">
        <table class="form">
          <tbody><tr>
            <td>Order ID:</td>
            <td>${orders.id}</td>
          </tr>
         
                    <tr>
            <td>Date Added:</td>
            <td>${orders.dateAdded}</td>
          </tr>
          <tr>
            <td>Date Modified:</td>
            <td>${orders.dateModified}</td>
          </tr>
        </tbody></table>
      </div>
      
            <div style="display: none;" id="tab-shipping" class="vtabs-content">
        <table class="form">
          <tbody>                     <tr>
            <td>FullName:</td>
            <td>${orders.fullName}</td>
          </tr>
                    <tr>
            <td>Address</td>
            <td>${orders.address}</td>
          </tr>
          <tr>
            <td>Locality:</td>
            <td>${orders.locality}</td>
          </tr>
                    <tr>
            <td>State: </td>
            <td>${orders.state.name}</td>
          </tr>
                    <tr>
            <td>Pincode:</td>
            <td>${orders.pincode}</td>
          </tr>
                              <tr>
            <td>Mobile:</td>
            <td id="order-status">${orders.mobile}</td>
          </tr>
                  </tbody></table>
      </div>
            <div style="display: none;" id="tab-product" class="vtabs-content">
        <table class="list">
          <thead>
            <tr>
              <td class="left">Product</td>
              <td class="right">Quantity</td>
              <td class="right">Unit Price</td>
              <td class="right">Size</td>
              <td class="right">design attribute specification</td>
              <td class="right">Total</td>
            </tr>
          </thead>
          <tbody>
          					<c:set var="totalPrice" value="0" />
          
				<c:forEach var="listVar" items="${orders.orderProduct}" varStatus="counter">
                
          					<c:set var="totalPrice" value="${totalPrice+ listVar.totalAmount}" />
                        <tr>
              <td class="left"><a href="">${listVar.product.name}</a>
                </td>
              <td class="right">${listVar.quantity}</td>
              <td class="right">${listVar.unitPrice}</td>
              <td class="right">${listVar.size.name}</td>
              <td class="right">-</td>
              <td class="right">${listVar.totalAmount}</td>


            </tr>
            </c:forEach>
                                  </tbody>
                    <tbody id="totals">
             <tr>
              <td colspan="4" class="right">Sub-Total:</td>
              <td class="right">${totalPrice}</td>
            </tr> 
          </tbody>
                     <tbody id="totals">
            <tr>
              <td colspan="4" class="right">Flat Shipping Rate:</td>
              <td class="right">0.00</td>
            </tr>
          </tbody>
                 <tbody id="totals">
            <tr>
              <td colspan="4" class="right">Total:</td>
              <td class="right">${totalPrice}</td>
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
      <td class="left"><b>Customer Notified</b></td>
    </tr>
  </thead>
  <tbody>
  
  				<c:forEach var="listVar" items="${orders.orderHistory}" varStatus="counter">
        <tr>
      <td class="left">${viewTools.formatDate(listVar.dateAdded)}</td>
      <td class="left">${listVar.comment}</td>
      <td class="left">${listVar.ordersStatus.name}</td>
      <td class="left">${listVar.notify}</td>
    </tr>
  </c:forEach>
          </tbody>
</table>
</div>
<form action="addOrderHistory.htm" name="ordeHisFrm" id="ordeHisFrm" method="post">
<input type="hidden" name="order.id" id="orderId" value="${orders.id}" />
        <table class="form">
          <tbody><tr>
            <td>Order Status:</td>
            <td>
            <select name="ordersStatus.id" id="orderStatus">
            <option value="" >select status</option>
           <c:forEach var="listVar" items="${ordersStatusColl}" varStatus="counter"> 
           <option value="${listVar.id}">${listVar.name}</option>
            </c:forEach>
            </select></td>
          </tr>
          <tr>
            <td>Notify Customer:</td>
            <td><input name="notify" value="1" type="checkbox"></td>
          </tr>
          <tr>
            <td>Comment:</td>
            <td><textarea name="comment" cols="40" rows="8" style="width: 99%"></textarea>
              <div style="margin-top: 10px; text-align: right;">
              <input type="submit" value="Add History" id="button-history" class="button"/>
              </div></td>
          </tr>
        </tbody></table>
 </form>
 
      </div>
          </div>
          
          
          
          
</div>

<script type="text/javascript"><!--
$('.vtabs a').tabs();
//--></script> 
<script language="javaScript">
var nonEmptyList = [ 
					['order status','orderStatus'],
				    ['Comment','comment']
				];
						    
function addFormSubmit(){
	var tForm = document.ordeHisFrm;
	if(showRequiredAlert(nonEmptyList) ){
		return;
	}
	
    tForm.action='addOrderHistory.htm';

    tForm.submit();
}

</script>
<%@ include file="includes/footer.jsp"%>