<%@ include file="includes/header.jsp"%>
 
        <div class="container-fluid navMainOuter">
            <div class="grey-bg"></div>
            <div class="container">
            
<%@ include file="includes/inner_page_menu.jsp"%>

			<form>
                <div class="row">
               <input type="hidden" name="uId" id="uId" value="${viewTools.getLoggedInCustomerName()}" />
                    <div class="col-md-8">
                        <h2 class="cartTitle">Cart <span>${count}</span></h2>
                        <span class="pull-right"><strong>Total : Rs. ${totalPrice}</strong></span>
                        <div class="cartTable table-responsive">
                        
                        <c:if test="${empty count or count==0}"> <p><center><br/>No Products found in your cart</center> </p></c:if>
                        <c:forEach var="orderProduct" items="${orderProducts}">
                        
                            <table class="table cart-table">
                                <tr>
                                    <td width="100"  class="hidden-xs">
                                        <a href="productDetails.htm?prodId=${orderProduct.product.id}&edit=true" class="prodImg">
                                            <img src="${viewTools.getProdImageUrl()}${orderProduct.product.defaultImage}" alt="${orderProduct.product.name}" width="140px">
                                        </a>
                                    </td>
                                    <td width="">
                                        <table class="table">
                                            <tr>
                                                <td width="70%">
                                                    <div class="prodDetails">
                                                        <h4><a href="productDetails.htm?prodId=${orderProduct.product.id}">${orderProduct.product.name}</a></h4>
                                                        <ul class="addons">
                                                            <li><strong>Size : </strong> ${orderProduct.size.name}</li>
                                                            <li><strong>Qty : </strong> ${orderProduct.quantity}</li>
                                                       </ul>
                                                    </div> 
                                                </td>
                                                <td width="30%" class="text-right">
                                                    <p><strong>Rs. ${orderProduct.unitPrice}</strong></p>
                                                </td>
                                            </tr>
                                            <tr class="borderTop">
                                                <td width="70%">
                                                    <a href="productDetails.htm?prodId=${orderProduct.product.id}&edit=true" class="cart-edit"><span class="ion-edit"></span>&nbsp; &nbsp;<em>Edit Item</em></a>
                                                    <a href="#" data-prodid="${orderProduct.product.id}" class="selected-designs" ><span class="ion-ios-download"></span>&nbsp; &nbsp;<em>Selected Designs</em></a>
                                                </td>
                                                <td width="30%" class="text-right">
                                                    <a href="productDetails.htm?prodId=${orderProduct.product.id}&edit=true" data-prodid="${orderProduct.product.id}" class="removeThis removeItem" ><span class="ion-close"></span>&nbsp; &nbsp;<em>Remove </em></a>
                                                </td>
                                            </tr>
                                        </table>
                                    </td>
                                </tr>
                            </table>
                           
                         </c:forEach>  
                            
                        </div>
                    </div>
                    <div class="col-md-4 cartRight">
                        <h4>Price Details</h4>
                        <div class="cartTable table-responsive">
                            <table class="table table-bordered cart-table">
                                <tr>
                                    <td>Bag Total</td>
                                    <td class="text-right">Rs. ${totalPrice}</td>
                                </tr>
                                <tr>
                                    <td>Delivery</td>
                                    <td class="text-right">FREE</td>
                                </tr>
                                <tr class="total">
                                    <td><strong>Order Total</strong></td>
                                    <td class="text-right"><strong>Rs. ${totalPrice}</strong></td>
                                </tr>
                                <tr class="">
                                    <td colspan="2">
                                        <a href="#" class="btn btn-primary full-width <c:if test="${count==0}">disabled</c:if>" id="cartProceedBut">Place Order</a>
                                    </td>
                                </tr>
                            </table>
                        </div>
                    </div>
                </div>
                </form>
                
                
          </div>
        </div>



<!-- productDesigns Modal-->
<div class="modal fade cartProdDesigns" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-body">
        <div id="cartProdDesigns" class="carousel slide" data-ride="carousel">

  <!-- Carousel -->
  <ol class="carousel-indicators">
    <li data-target="#cartProdDesigns" data-slide-to="0" class="active"></li>
    <li data-target="#cartProdDesigns" data-slide-to="1"></li>
    <li data-target="#cartProdDesigns" data-slide-to="2"></li>
  </ol>

  <!-- Wrapper for slides -->
  <div class="carousel-inner" role="listbox">
   
   <p> please wait while loading selected designs .. </p>
   
  </div>

  <!-- Controls -->
  <a class="left carousel-control" href="#cartProdDesigns" role="button" data-slide="prev">
  </a>
  <a class="right carousel-control" href="#cartProdDesigns" role="button" data-slide="next">
  </a>
</div>


      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>






<%@ include file="includes/footer.jsp"%>
 
   <script src="js/smoothproducts.min.js"></script>
      <script src="js/jquery.nicescroll.min.js"></script>
      
       <script>
 $(document).ready(function(){
 $('body').addClass("innerPage");
 
 
 $("#cartProceedBut").click(function(e)
		 {
	 e.preventDefault();
	 var uId=$("#uId").val();
	 if(!uId)
	 {
	 $("#loginModal").modal("show");
	 }
 	else
	 {
	 window.location="chooseDeliveryAddress.htm";
	 }
	 });
 
 $(".removeItem").click(function(e){ 
	 e.preventDefault();
	 var selProdId=$(this).data("prodid");
	 var thisObj=$(this);

	 $.ajax({
			url : "removeCartProduct.htm",
			type : "POST",
			async:false,
			data : {
				"prodId" :selProdId
			},
			success : function(data) 
			{
				window.location="cart.htm";
			}
			
			 
	 });
	 
 });
 
 
 
 $(".selected-designs").click(function(e){ 
	 e.preventDefault();
	 var selProdId=$(this).data("prodid");

	 	
 	$.ajax({
		url : "getCartProdDesigns.htm",
		type : "POST",
		async:false,
		data : {
			"prodId" :selProdId
		},
		success : function(data) 
		{
			var resp= JSON.parse(data);
			var selDesigns=resp.selDesigns;
			var html="";
			
			if(resp.STATUS=="ERROR")
				{
				alert(resp.MESSAGE);
				}
			else
				{
				
				
				
				if(selDesigns.length==0)
					{
				alert("No custom designs found for this product.");
				$(".cartProdDesigns").modal("hide");

				return;
					}
				
				$("#cartProdDesigns").children(".carousel-inner").html("<p>please wait</p>");
				 $(".cartProdDesigns").modal("show");
					
				
				for(var i=0;i<selDesigns.length;i++)
					{
					
					var obj=selDesigns[i];
					var active="";
					if(i==0){ active="active";}
					html+='<div class="item '+active+'"><div>'+obj.desAttrName+'</div><img src="'+obj.image+'" alt=""></div>';
					
					
					
					}
				
				
				$("#cartProdDesigns").children(".carousel-inner").html(html);
			
				}
			
			
		}
			
 			});
 	 
	 
		 });
 
	
 });
 </script>