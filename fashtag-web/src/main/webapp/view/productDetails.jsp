<%@ include file="includes/header.jsp"%>
 <script>
 $('body').addClass("innerPage");
 $('body').addClass("listingPage");
 </script>
        <div class="container-fluid navMainOuter">
            <div class="grey-bg"></div>
            <div class="container">
            
<%@ include file="includes/inner_page_menu.jsp"%>
	
					<div class="row">
                    <div class="col-md-12">
                        <ol class="breadcrumb">
                          ${viewTools.getBreadCrumb(product.category.id)}
                        </ol>
                    </div>
<form id="buyProductFrm" method="post">
                    <div class="col-md-9 pl0 col-md-push-3">
                        <div class="row">
                            <div class="col-md-5">
                                <div class="sp-loading"><div class="loader"></div><br>LOADING IMAGES</div>
                                <div class="sp-wrap">
               <c:forEach var="productImg" items="${productImages}">                    
                                    <a href="${viewTools.getProdImageUrl()}${productImg.image}"><img src="${viewTools.getProdImageUrl()}${productImg.image}" alt=""></a>
                </c:forEach>
                                </div>
                            </div>
                            <div class="col-md-7 clear-md">
                                <div class="pd-main">
                                    <header>
                                    <input type="hidden" name="edit" id="edit" value="${edit}" />
                                    <input type="hidden" name="productId" id="prodId" value="${product.id}" />
                                    <input type="hidden" name="sizeId" id="sizeId" value="${selSizeId}" />
                                    <input type="hidden" name="productDesignCount" id="productDesignCount" value="${productDesignCount}" />
                                    <input type="hidden" name="custDesign" id="custDesign" value="${selectedDesigns}" />
                                     
                                        <h2>${product.name}</h2>
                                        <p><strong>${product.offerPrice}</strong><strike>${product.actualPrice}</strike><span>(${viewTools.getOffPercentage(product.actualPrice,product.offerPrice)}% off)</span> </p> 
                                        <ul class="flist">
                                            <li><a href="#" class="likeThis">
                                            <input type="hidden" name="wishProd"  value="${product.id}" />
                                            <span class="ion-heart"></span>${product.totalLikes}</a></li>
                                            <!-- <li><a href="#" class="favThis"><span class="ion-star"></span>3.5</a></li> -->
                                        </ul> 
                                        <p class="small"  style="clear:both;">Item Code: ${product.id} &nbsp; &nbsp; |  &nbsp;  &nbsp; Seller : <a href="javascript:void(0)">${product.vendor.name}</a></p>  
                                    </header>
                                    <div class="pd-select">
                                        <div class="size-selector">
                                             <h4>Select Size 
                                     <c:if test="${viewTools.getCollectionSize(productSizes)>1}">
                                     &nbsp;  &nbsp;<span class="small">Not Sure?</span>
                                     <a href="#" class="see-sizes small" data-toggle="modal" data-target=".sizeChart">See Size Chart</a>
                                     </c:if>
                                     </h4>
                                            <ul>
<c:forEach var="productSize" items="${productSizes}">

<c:set var="selClass" value="" />
<c:if test="${productSize.id == selSizeId}">
<c:set var="selClass" value="selected" />
</c:if>

<c:choose>
<c:when test="${productSize.displayText=='CUSTOM'}">
<li class="custom  ${selClass}">
<a href="javascript:void(0)" data-size="${productSize.id}" class="product-size-button" data-toggle="popover" data-placement="top"
data-trigger="hover" title="Custom size" 
data-content="">
Custom</a></li>
</c:when>
<c:otherwise>
<li class="${selClass}">
<a href="javascript:void(0)" data-size="${productSize.id}" 
data-toggle="tooltip" class="product-size-button" data-placement="top" title="${productSize.name}">
${productSize.displayText}
</a></li>
</c:otherwise>
</c:choose>
                                                 
</c:forEach>                                                
                                                
                                            </ul>
                                        </div>
                                        <div class="qty-selector">
                                            <h4>Quantity</h4>
                                            <div class="input-group">
                                              <span class="input-group-addon">
                                                    <button type="button" class="btn btn-number"  data-type="minus" data-field="quantity">-</button>
                                              </span>
                         
                                              <input type="text" value="${selQty}" name="quantity" class="form-control input-number" value="1" min="1" max="${product.quantity}">
                                              <span class="input-group-addon">
                                                    <button type="button" class="btn btn-number" data-type="plus" data-field="quantity">+</button>
                                              </span>
                                            </div>
                                        </div>
                                        <div>
                                        
                                        </div>
                                        <div class="btns">
                                            <a class="btn btn-lg btn-primary" href="javascript:void(0)" id="buynow-but"><span class="ion-bag ion-android-cart"></span> Buy Now</a>
                                            <a class="btn btn-lg btn-default"  href="javascript:void(0)"  id="addcart-but"><span class="ion-bag"></span> Add to Cart</a>
                                        </div>
                                    </div>
                                    <footer>
                                        <h4>Check Delivery</h4>
                                        <div class="input-group">
                                            <input type="text" class="form-control" id="deliveryPincode" name="deliveryPincode"  placeholder="pincode">
                                            <span class="input-group-addon">
                                        <a href="javascript:void(0)" id="pincode-but" ><span class="ion-android-arrow-forward"></span></a>
                                            
                                            </span>
                                        </div>
                                        
                                         <ul class="delivery-detials"></ul>
                                    </footer>
                                </div>
                            </div>
                        </div><!-- /.row ***product detial*** -->
                        <div class="row">
                            <div class="col-md-12">
                                <div class="row mtb">
                                    <div class="col-md-12">
                                        <div class="desc">
                                            <h3>Description</h3>
                                            <p>${product.description}</p>
                                        </div>
                                    </div>
                                </div>
                                
                                
                                <div id="similar-products"><div class="loader"></div></div>
                            
                            
                            
                            </div>
                        </div><!-- /.row ***product listing*** -->
                    </div>
                          
                          
                          
                          <div class="col-md-3 col-md-pull-9">
                        <div class="widget display-sm hidden-xs hidden-sm" id="matching-products">
                       <div class="loader"></div>
                        </div><!-- *** matching products*** -->
                        
                        
                        
                         <!-- <div class="widget display-ads hidden-xs hidden-sm">
                            
                            <div class="widgetContent">
                                <ul>
                                    <li><a href="#"><img src="img/ad1.jpg"></a></li>
                                </ul>
                            </div>
                        </div> -->
                        
                    </div>
                    
                    </form>
                </div>
                
<!-- Product Style modal -->
<div class="modal stylePopup fade bs-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="styleModal" id="product-design-modal">
  <div class="modal-dialog modal-lg" role="document">
    <div class="modal-content" >
        <div class="modal-header">
            <h4>${product.name}</h4>
            <div class="pull-right">
        <button class="btn btn-cancel" data-dismiss="modal" aria-label="Close">Skip</button>
                <button class="btn btn-primary" data-source="" id="customDesignSubmit">Save</button>
            </div>
        </div>
        <div class="row1"  id="product-designs">
  			
  			<div class="loader"></div>
  
        </div><!-- /.row1 -->
    </div><!-- /.modal-content -->
  </div>
</div>
                

<!-- Size Chart  modal -->
<div class="modal sizeChart fade bs-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="loginModal" id="">
  <div class="modal-dialog modal-lg" role="document">
    

    <div class="modal-content">
         <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span class="ion-android-close"></span></button>
        <div class="modal-header text-center">
            <h4>Size Options</h4>
        </div>

        <div class="row">
            
            <div class="col-md-12">
            
                <div class="paddingDiv">
                <div class="table-responsive">
                <table class="table table-bordered sizeChartTable">
                    <tr>
                        <th width="40%" class="text-right">Size</th>
                        <th><a href="javascript:void(0)" class="product-size-button" data-toggle="tooltip" data-placement="top" title="Small">S</a></th>
                        <th><a href="javascript:void(0)" class="product-size-button" data-toggle="tooltip" data-placement="top" title="Medium">M</a></th>
                        <th><a href="javascript:void(0)" class="product-size-button" data-toggle="tooltip" data-placement="top" title="Large">L</a></th>
                        <th><a href="javascript:void(0)" class="product-size-button" data-toggle="tooltip" data-placement="top" title="Extra Large">XL</a></th>
                    </tr>
                    <tr>
                        <td class="text-right">Chest (Inch)</td>
                        <td>32</td>
                        <td>36</td>
                        <td>40</td>
                        <td>44</td>  
                    </tr>
                    <tr>
                        <td class="text-right">Waist (Inch)</td>
                        <td>28</td>
                        <td>32</td>
                        <td>36</td>
                        <td>40</td>  
                    </tr>  
                     <tr>
                        <td class="text-right">Hip (Inch)</td>
                        <td>34</td>
                        <td>38</td>
                        <td>42</td>
                        <td>46</td>  
                    </tr>  
                    <tr>
                        <td class="text-right">Length (Inch) </td>
                        <td>39</td>
                        <td>40</td>
                        <td>41</td>
                        <td>42</td>  
                    </tr>                                     
                </table>
                </div>
                </div><!-- /.paddingDiv -->


            </div><!-- /.col-md-12 -->

        </div><!-- /.row -->

    </div><!-- /.modal-content -->
  </div>
</div>

            </div>
        </div>
 <script src="js/smoothproducts.min.js"></script>
      <script src="js/jquery.nicescroll.min.js"></script>
       <script>
 $(document).ready(function(){
 
 var prodId=$("#prodId").val();		
 

 $('.sp-wrap').smoothproducts();
 
 $.ajax({
		url : "customSizeAvailablity.htm",
		type : "POST",
		data : {"prodId":prodId},
		success : function(data) {
			var arr = JSON.parse(data);			
			if (arr.status == "success") 
			{
				$(".custom").children("a").attr("data-content",arr.message);
				if(arr.action==false)
					{
					$(".custom").addClass("disabled");
					}
			}
			else
			{
				$(".custom").addClass("disabled");
			}
			
		}
 });
 
 $("#product-designs").load("productDesigns.htm?productId="+prodId+"&custDesign="+$("#custDesign").val());	
 $("#similar-products").load("similarProducts.htm?prodId="+prodId);
 $("#matching-products").load("matchingProducts.htm?prodId="+prodId);	

 
 $(".product-size-button").click(function(e){ 
	 e.preventDefault();

	 if(!$(this).parent("li").hasClass("disabled"))
		 {
	 $(this).parent().parent("ul").children("li").removeClass("selected");
	 $(this).parent("li").addClass("selected");
	var selSize=$(this).data("size");
	$("#sizeId").val(selSize);
		 }
	
 });
 
	$("#addcart-but , #buynow-but").click(function(event){
		event.preventDefault();
		var desCount=$("#productDesignCount").val();
		var sizeId=$("#sizeId").val();
		if(parseInt(sizeId)==0 || sizeId=="")
		{
		alert("Please select size");
		return;
		}
		
		if(parseInt(desCount)>0)
			{
		$("#product-design-modal").modal("show");
		$("#customDesignSubmit").data("source",$(this).attr("id"));
		
			}
		else
			{
		var desCount=$("#productDesignCount").val();
		var selDesigns=$("#custDesign").val();
		var formData=$("#buyProductFrm").serialize();
		var sizeId=$("#sizeId").val();
		var obj=$(this);
		
		
		
				$.ajax({
							url : "addToCart.htm",
							type : "POST",
							data : formData,
							success : function(data) {
								var arr = JSON.parse(data);
								if (arr.STATUS == "SUCCESS") 
								{
								if($(obj).attr("id")=="buynow-but")
									{
									window.location="cart.htm";
									}
								else{
					 			$(".cart").children(".hidden-xs").html(""+arr.COUNT+" items - RS. "+arr.TOTAL_PRICE);
					 			$(".alertBox").html('<div class="alert alert-success" data-close="auto">'+arr.MESSAGE+'</div>');
					 			} 
								}
								
								else{
						 			$(".alertBox").html('<div class="alert alert-warning" data-close="auto">'+arr.MESSAGE+'</div>');

								}

							}
						});	
			}
		
		
			
		});

	
	
	$("#pincode-but").click(function(event){
var pincode=$("#deliveryPincode").val();
var prodId=$("#prodId").val();
var obj=$(this);
event.preventDefault();

$.ajax({
			url : "checkDelivery.htm",
			type : "POST",
			data : {
				"pincode" :pincode,
				"prodId" :prodId
			},
			success : function(data) {
				var arr = JSON.parse(data);
				if (arr.STATUS == "SUCCESS") 
				{
				$(".delivery-detials").html("<li class='success'>"+arr.MESSAGE+"</li>");
									
				} 
				else 
				{
					$(".delivery-detials").html("<li class='danger'>"+arr.MESSAGE+"</li>");
					
				}
			}
		});		
	});
	
	
	
 });
 </script>
<%@ include file="includes/footer.jsp"%>
      