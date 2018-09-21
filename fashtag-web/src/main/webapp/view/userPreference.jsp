<%@ include file="includes/header.jsp"%>

<div class="container-fluid navMainOuter">
	<div class="grey-bg"></div>
	<div class="container">

		<%@ include file="includes/inner_page_menu.jsp"%>

		<div class="row">
			<div class="col-md-12">
				<ol class="breadcrumb">
					<li><a href="idex.htm">Home</a>
					</li>
					<li class="active">My Account</li>
				</ol>
			</div>
		</div>
		<div class="row myAccount">
			<div class="col-md-3 col-xs-3 no-padding">
				<!-- required for floating -->
				<!-- Nav vtabs -->
				<ul class="nav nav-tabs tabs-left">
				
					<li class="<c:if test="${resource=='ORDERS'}">active</c:if>"><a href="userPreference.htm?res=ORDERS">My Orders</a></li>
					<li class="<c:if test="${resource=='RETURNS'}">active</c:if>"><a	href="userPreference.htm?res=RETURNS">My Returns</a>
					</li>
					<li  class="<c:if test="${resource=='WISHLIST'}">active</c:if>"><a href="userPreference.htm?res=WISHLIST" >My Wishlist</a>
					</li>
					<li class="<c:if test="${resource=='PROFILE'}">active</c:if>">
					<a href="userPreference.htm?res=PROFILE">My	Profiles</a>
					</li>
					<li class="<c:if test="${resource=='ADDRESS'}">active</c:if>"><a href="userPreference.htm?res=ADDRESS">My Address</a>
					</li>
					<li class="<c:if test="${resource=='INFO'}">active</c:if>"><a href="userPreference.htm?res=INFO">My Info</a>
					</li>
				</ul>
			</div>
			
			<div class="col-md-9 col-xs-9">
				<!-- required for floating -->
				<!-- vtab contents -->
				<div class="tab-content">
<c:if test="${resource=='ORDERS'}">
<div class="tab-pane active" id="ordersVtab">
                                <div class="row">
                                    <div class="col-md-12 vtcTitle">
                                        <h3>My Orders</h3>
                                    </div>
                                    <div class="col-md-12">
                                        <div class="table-responsive">
                                            <table class="table">
                                            
                                            <c:if test="${viewTools.getCollectionSize(respColl)==0}">
										<tr>
										<td colspan="3">
										<p>
										No products has been ordered so far. 
										</p>
										</td>
										</tr>
										</c:if>
										
										<c:forEach var="orderObj" items="${respColl}">
                                                <tr>
										<c:forEach var="prodObj" items="${orderObj.orderProduct}" varStatus="counter">
										<c:set var="isDefault" value="" />
										<c:if test="${counter.count==1}">
                                                    <td width="100px">
                                                        <a href="#" class="order-imgs" data-toggle="modal" data-target=".orderImages">
                                                        <img src="${viewTools.getProdImageUrl()}${prodObj.product.defaultImage}" width="70px">
                                                        <span>${viewTools.getCollectionSize(orderObj.orderProduct)}</span>
                                                        </a>
                                                    </td>
										</c:if>
										</c:forEach>
                                                    <td width="60%">
                                                        <p>Order ID : ${orderObj.id} </p>
                                                        <p>Status : ${orderObj.ordersStatus.name}</p>
                                                        <p>Total Amount: Rs.${orderObj.totalAmount} </p>
                                                    </td>
                                                    <td>
                                         <c:if test="${orderObj.ordersStatus.name=='DELIVERED'}">           

                                                        <a href="userPreference.htm?res=RETURNORDER&orderid=${orderObj.id}"
                                                           class="btn bnt-text">RETURN</a>
										</c:if>
                                                    </td>
                                                    <td>
                                        <c:if test="${orderObj.ordersStatus.name=='PROCESSING'}">           
                                                        <a href="cancelOrder.htm?orderid=${orderObj.id}" class="btn bnt-text">CANCEL</a>
                                        </c:if>            
                                                    </td>
                                                </tr>
    </c:forEach>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                            </div>
</c:if>				


<c:if test="${resource=='RETURNS'}">
<div class="tab-pane active" id="ordersVtab">
                                <div class="row">
                                    <div class="col-md-12 vtcTitle">
                                        <h3>My Returns</h3>
                                    </div>
                                    <div class="col-md-12">
                                        <div class="table-responsive">
                                            <table class="table">
										<c:if test="${viewTools.getCollectionSize(respColl)==0}">
										<tr>
										<td colspan="3">
										<p>
										No products has been returned so far . 
										</p>
										</td>
										</tr>
										</c:if>
										
										<c:forEach var="returnObj" items="${respColl}">
                                                <tr>
										<c:forEach var="prodObj" items="${returnObj.returnProduct}" varStatus="counter">
										<c:set var="isDefault" value="" />
										<c:if test="${counter.count==1}">
                                                    <td width="100px">
                                                        <a href="#" class="order-imgs" data-toggle="modal" data-target=".orderImages">
                                                        <img src="${viewTools.getProdImageUrl()}${prodObj.product.defaultImage}" width="70px">
                                                        <span>${viewTools.getCollectionSize(returnObj.returnProduct)}</span>
                                                        </a>
                                                    </td>
										</c:if>
										</c:forEach>
                                                    <td width="60%">
                                                        <p>Status : ${returnObj.returnStatus.name}</p>
                                                        <p>Total Amount: Rs.${returnObj.totalAmount} </p>
                                                    </td>
                                                    <td>
                                        &nbsp;
                                                    </td>
                                                    <td>
                                       &nbsp;           
                                                    </td>
                                                </tr>
    </c:forEach>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                            </div>
</c:if>
				
<c:if test="${resource=='WISHLIST'}">
					<div class="tab-pane <c:if test="${resource=='WISHLIST'}">active</c:if>"
						id="wishlistVtab">
						<div class="row">
							<div class="col-md-12 vtcTitle">
								<h3>My Wishlist</h3>
							</div>
							<div class="col-md-12">
								<div class="table-responsive">
									<table class="table">
									
									<c:if test="${viewTools.getCollectionSize(respColl)==0}">
										<tr>
										<td colspan="3">
										<p>
										No products found in your wish list. 
										</p>
										</td>
										</tr>
										</c:if>
										
										<c:forEach var="wishProdObj" items="${respColl}">

											<tr>
												<td><a href="#" class="order-imgs" data-toggle="modal"
													data-target=".orderImages"> <img
														src="${viewTools.getProdImageUrl()}${wishProdObj.defaultImage}" width="70px">
												</a></td>
												<td width="60%">
													<p>Name : ${wishProdObj.name}</p>
													<p>Price : ${wishProdObj.offerPrice}</p></td>

												<td><a
													href="productDetails.htm?prodId=${wishProdObj.id}"
													class="btn bnt-text">Add To Cart</a></td>
											</tr>

										</c:forEach>
									</table>
								</div>
							</div>
						</div>
					</div>
</c:if>

					
<c:if test="${resource=='PROFILE'}">
					<div class="tab-pane <c:if test="${resource=='PROFILE'}">active</c:if>" id="measureVtab">
						<div class="row">
							<div class="col-md-12 vtcTitle">
								<h3>My Profiles (Body Measurements)</h3>
								<a href="#" class="add-new pull-right"  data-toggle="modal" data-target="#profileEnter"><span
									class="ion-person-add" id="add-new-profile"></span>
								</a>
							</div>
							<div class="col-md-12">
								<div class="table-responsive">
									<table class="table measurements">
									<c:forEach var="custProfObj" items="${respColl}">
									<c:set var="isDefault" value="" />
									
									<c:if test="${custProfObj.isDefault==true}"><c:set var="isDefault" value="checked" /></c:if>
										<tr>
											<td class="<c:if test="${custProfObj.isDefault==true}">default</c:if>">
												<div class="circle">
													<span class="ion-person"></span>
												</div>
												<p>${custProfObj.name}</p> <label> 
												<input type="checkbox" ${isDefault} data-profileid="${custProfObj.id}"  class="isDefault" name="isDefault"> make it default  </label></td>
											
											
											<td>
												<table width="100%" class="styles">
												<c:forEach var="measurementObj" items="${custProfObj.customerMeasurements}">
													<tr>
														<td>${measurementObj.bodyMeasurementAttribute.name} : ${measurementObj.value} ${measurementObj.unit}</td>
													</tr>
												</c:forEach>
												</table></td> 
											<td width="40"><a href="#" data-id="${custProfObj.id}" class="btn-icon btn-edit profile-edit"><span
													class="ion-edit"></span>
											</a> <a href="" data-profileid="${custProfObj.id}"  class="btn-icon btn-del profile-del"><span	class="ion-android-delete"></span>
											</a></td>
										</tr>
										
										</c:forEach>
										
									</table>
								</div>
							</div>
						</div>
					</div>
					
</c:if>					
<c:if test="${resource=='ADDRESS'}">					
					<div class="tab-pane <c:if test="${resource=='ADDRESS'}">active</c:if>"  id="addressVtab">
						<div class="row">
						
						
							<div class="col-md-12 vtcTitle">
								<h3>My Address</h3>
				<a href="#"  data-toggle="modal" data-target="#addAddressModal" class="add-new pull-right addNewAddressBut"><span class="ion-ios-location"></span></a>				
							</div>
							
						
					
							<div class="col-md-12">

								<div class="borderBox">
<c:if test="${viewTools.getCollectionSize(respColl)==0}">
									<table class="table no-border">
										<tr>
										<td colspan="3">
										<p>
No address found in your address book. 
<a href="#"  data-toggle="modal" data-target="#addAddressModal" class="addNewAddressBut">
Add new address</a>				

										</p>
										</td>
										</tr>
									</table>
									
										</c:if>
					
									<!-- Nav tabs -->
									<ul class="nav nav-tabs sleekTabs" role="tablist">
							<c:forEach var="addressObj" items="${respColl}" varStatus="counter">
									
										<li role="presentation" <c:if test="${counter.count==1}"> class="active" </c:if>><a href="#name${counter.count}"
											aria-controls="name${counter.count}" role="tab" data-toggle="tab">${addressObj.fullName}</a>
										</li>
										
										
							</c:forEach>
							
									</ul>

									<!-- Tab panes -->
									<div class="tab-content">
									
									<c:forEach var="addressObj" items="${respColl}" varStatus="counter">
										<div role="tabpanel" class="tab-pane <c:if test="${counter.count==1}"> active </c:if>" id="name${counter.count}">
											<div class="table-responsive">
												<table class="table no-border">
												
										
												
												
													<tr>
													<input type="hidden" id="address-fullName${addressObj.id}" value="${addressObj.fullName}" /> 
														<td> Name :</td>
														<td>${addressObj.fullName}</td>
													</tr>
													<tr>
													<input type="hidden" id="address-address${addressObj.id}" value="${addressObj.address}" />
														<td>Address :</td>
														<td>${addressObj.address}</td>
													</tr>
													<tr>
													<input type="hidden" id="address-locality${addressObj.id}" value="${addressObj.locality}" />
														<td>Locality/Town :</td>
														<td>${addressObj.locality}</td>
													</tr>
													<tr>
													<input type="hidden" id="address-statename${addressObj.id}" value="${addressObj.state.name}" />
													<input type="hidden" id="address-stateid${addressObj.id}" value="${addressObj.state.id}" />
														<td>State:</td>
														<td>${addressObj.state.name}</td>
													</tr>
													<tr>
													<input type="hidden" id="address-pinCode${addressObj.id}" value="${addressObj.pinCode}" />
														<td>Pin Code :</td>
														<td>${addressObj.pinCode}</td>
													</tr>
													<tr>
													<input type="hidden" id="address-mobile${addressObj.id}" value="${addressObj.mobile}" />
														<td>Mobile Number :</td>
														<td>${addressObj.mobile}</td>
													</tr>
													<tr>
														<td colspan="2"> <input type="button" class="btn btn-default edit-address" data-id="${addressObj.id}" value="Edit address" />
														<input type="button" class="btn btn-default delete-address"  data-id="${addressObj.id}" value="Delete address" /></td>
													</tr>
												
												</table>
											</div>
										</div>
				</c:forEach>
				
										
									</div>

								</div>
								<!-- /.borderBox -->

							</div>
					
									
							
						</div>
					</div>
</c:if>	
					
<c:if test="${resource=='INFO'}">	
					<div class="tab-pane <c:if test="${resource=='INFO'}">active</c:if>" id="profileVtab">
						<div class="row">
							<div class="col-md-12 vtcTitle">
								<h3>My INFO</h3>
							</div>
							<div class="col-md-12">
								<div class="table-responsive">
								<form action="updateUserInfo.htm" method="post" name="userInfoFrm" id="userInfoFrm">
									<table class="table">
										<tr>
											<td>Name :</td>
											<td><span>${CUSTOMER.fullName}</span>
											<div class="form-group hidden">
									<input type="text" class="form-control" value="${CUSTOMER.fullName}" name="fullName" id="fullName"  >
											</div>
											</td>
										</tr>
										<tr>
											<td>Email :</td>
											<td><span>${CUSTOMER.email}</span>
											<div class="form-group hidden">
									<input type="text" class="form-control" value="${CUSTOMER.email}" name="email" id="email"  >
											</div>
											</td>
										</tr>
										<tr>
											<td>Mobile Number :</td>
											<td>
											<span>${CUSTOMER.phone}</span>
											<div class="form-group hidden">
									<input type="text" class="form-control" value="${CUSTOMER.phone}" name="phone" id="phone"  >
											</div>
											</td>
										</tr>	
									<tr><td colspan="2">
									<input type="button" value="Edit Info" data-action="edit" id="editInfoBut" />
									</td></tr>	
										
									</table>
								</form>
								
								
								
								<form action="updatePassword.htm" method="post" name="updatePassFrm" id="updatePassFrm">
									<table class="table">
									
										<tr>
											<td>Old Password :</td>
											<td>
									<input type="password" class="form-control" value="" name="oldPass" id="oldPass"  >
											
											</td>
										</tr>
										<tr>
											<td>New Password:</td>
											<td>
											
									<input type="password" class="form-control" value="" name="newPass" id="newPass"  >
											
											</td>
										</tr>	
										
											<tr>
											<td>Confirm Password:</td>
											<td>
											
									<input type="password" class="form-control" value="" name="confirmPass" id="confirmPass"  >
											
											</td>
										</tr>	
										
										
									<tr><td colspan="2">
									<input type="submit" value="Update Password" data-action="edit" id="editInfoBut" />
									</td></tr>	
										
									</table>
								</form>
								
								

								</div>
							</div>
						</div>
					</div>
</c:if>			


<c:if test="${resource=='RETURNORDER'}">	
					<div class="tab-pane active" id="returnTab">
                                <div class="row">
                                    <div class="col-md-12 vtcTitle">
                                        <h3>Return Order</h3>
                                    </div>
                                    <form action="returnOrder.htm" name="returnOrderFrm" id="returnOrderFrm" >
                                    <div class="col-md-12">
                                        <div class="table-responsive">
                                            <table class="table">
                                            
                                            <tr>
                                            <td colspan="2"><input type="hidden" name="orderid" value="${orders.id}" />OrderId: ${orders.id}</td>
                                            <td colspan="2">Total Amount: ${orders.totalAmount}</td>
                                            <td colspan="4">Order Date: ${viewTools.formatDate(orders.dateAdded)}</td>
                                            
                                            </tr>
                                            <tr>
                                            <td>Select</td>
                                            <td>Product</td>
                                            <td>Image</td>
                                            <td>Size</td>
                                            <td>Quantity</td>
                                            <td>Price</td>
                                            <td>Total</td>
                                            <td>Total</td>
                                            </tr>

										<c:forEach var="orderObj" items="${respColl}">
										<tr>
                                            <td><input type="checkbox" name="selProdRet" value="${orderObj.id}" /></td>
                                            <td>${orderObj.product.name}</td>
                                            <td>${orderObj.product.defaultImage}</td>
                                             <td>${orderObj.size.name}</td>
                                            <td>${orderObj.quantity}</td>
                                            <td>${orderObj.unitPrice}</td>
                                            <td>${orderObj.totalAmount}</td>
                                            </tr>

  									</c:forEach>
  									
  									<tr>
                                            <td colspan="8"><input type="submit" name="" value="Return Selected Products" /></td>
                                            </td>
                                            </tr>
  									
                                            </table>
                                        </div>
                                    </div>
                                    
                                    </form>
                                </div>
                            </div>
</c:if>	





				</div>
			</div>

		</div>
	</div>
</div>






<c:if test="${resource=='PROFILE'}">
<!-- profile enter -->
<div class="modal measurePopup fade bs-example-modal-lg" tabindex="-1"  role="dialog" aria-labelledby="profileEnter" id="profileEnter">
  <div class="modal-dialog modal-lg" role="document">
      <div class="modal-content">
         <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span class="ion-android-close"></span></button>
        <div class="modal-header text-center">
            <h4>Body Measurement</h4>
        </div>
        <div class="row">
            <div class="col-md-6 col-md-push-6">
                <div class="paddingDiv">

					<div id="custProfileContent">
					
					
					
					</div>
                </div><!-- /.paddingDiv -->
            </div><!-- /.col-md-6 -->
            <div class="col-md-6 col-md-pull-6">
                <div class="paddingDiv measureImg">
                   <figure>
                       <img src="img/measure.png" alt="" class="img-responsive">
                   </figure>
                </div><!-- /.paddingDiv -->
            </div><!-- /.col-md-6 -->
        </div><!-- /.row -->
    </div><!-- /.modal-content -->
  </div>
</div>
<!-- /. profile enter -->
</c:if>

<c:if test="${resource=='ADDRESS'}">
<!-- add address -->
<div class="modal measurePopup fade bs-example-modal-lg" tabindex="-1"  role="dialog" aria-labelledby="addAddressModal" id="addAddressModal">
  <div class="modal-dialog modal-lg" role="document">
      <div class="modal-content">
         <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span class="ion-android-close"></span></button>
        <div class="modal-header text-center">
            <h4>Add Address</h4>
        </div>
        <div class="row">
            <div class="col-md-12">
                <div class="paddingDiv">
<form  method="post" name="addAddressFrm" id="addAddressFrm" >
                        
                   <input type="hidden" name="addressId" id="addressId" value="0" /> 
                     <div class="form-group hasLabel"><label for="fullName">Name</label>
					<input type="text" class="form-control" value="" name="fullName" id="fullName"  >
					</div>
					
					<div class="form-group hasLabel"><label for="locality">Locality/Town</label>
					<input type="text" class="form-control" value="" name="locality" id="locality"  >
					</div>
					
					<div class="form-group hasLabel">
					<select class="form-control" value="" name="state" id="state" >
					<option value="0">Select State</option>
					<c:forEach var="stateObj" items="${stateColl}" varStatus="counter">
					<option value="${stateObj.id}">${stateObj.name}</option>
					</c:forEach>
					</select>
					</div>
					
					<div class="form-group hasLabel"><label for="address">Address</label>
					<textarea class="form-control"  name="address" id="address"  ></textarea>
					</div>
					
					<div class="form-group hasLabel"><label for="pinCode">Pin Code</label>
					<input type="text" class="form-control" value="" name="pinCode" id="pinCode"  >
					</div>
					
					<div class="form-group hasLabel"><label for="mobile">Mobile</label>
					<input type="text" class="form-control" value="" name="mobile" id="mobile"  >
					</div>
					
					
					
             <button type="submit" id="add-address" data-action="add" class="btn btn-primary">Save Address</button>
                    </form>
                </div><!-- /.paddingDiv -->
            </div><!-- /.col-md-12 -->
        </div><!-- /.row -->
    </div><!-- /.modal-content -->
  </div>
</div>
<!-- /. Add address -->
</c:if>


<c:if test="${resource=='ORDERS'}">
<!--  product return modal -->
<div class="modal measurePopup fade bs-example-modal-lg" tabindex="-1"  role="dialog" aria-labelledby="returnProdModal" id="returnProdModal">
  <div class="modal-dialog modal-lg" role="document">
      <div class="modal-content">
         <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span class="ion-android-close"></span></button>
        <div class="modal-header text-center">
            <h4>Return Product</h4>
        </div>
        <div class="row">
            <div class="col-md-12">
                <div class="paddingDiv" id="returnProdContent">
                
   </div><!-- /.paddingDiv -->
            </div><!-- /.col-md-12 -->
        </div><!-- /.row -->
    </div><!-- /.modal-content -->
  </div>
</div>             
                
                
<!-- Modal order products-->
<div class="modal fade orderImages" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-body">
        <div id="orderImages" class="carousel slide" data-ride="carousel">

  <!-- Wrapper for slides -->
  <div class="carousel-inner" role="listbox">
 <c:forEach var="orderObj" items="${respColl}">
 <c:forEach var="prodObj" items="${orderObj.orderProduct}" varStatus="counter">
    <div class="item <c:if test="${counter.count==1}">active</c:if>">
      <img src="${viewTools.getProdImageUrl()}${prodObj.product.defaultImage}" alt="${prodObj.product.name}">
    </div>
</c:forEach>
</c:forEach>    
  </div>

  <!-- Controls -->
  <a class="left carousel-control" href="#orderImages" role="button" data-slide="prev">
  </a>
  <a class="right carousel-control" href="#orderImages" role="button" data-slide="next">
  </a>
</div>


      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>
<!-- /. Modal order products-->

</c:if>


<c:if test="${resource=='RETURNS'}">

<!-- Modal order products-->
<div class="modal fade orderImages" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-body">
        <div id="orderImages" class="carousel slide" data-ride="carousel">

  <!-- Wrapper for slides -->
  <div class="carousel-inner" role="listbox">
 <c:forEach var="returnObj" items="${respColl}">
<c:forEach var="prodObj" items="${returnObj.returnProduct}" varStatus="counter">
    <div class="item <c:if test="${counter.count==1}">active</c:if>">
      <img src="${viewTools.getProdImageUrl()}${prodObj.product.defaultImage}" alt="${prodObj.product.name}">
    </div>
</c:forEach>
</c:forEach>    
  </div>

  <!-- Controls -->
  <a class="left carousel-control" href="#orderImages" role="button" data-slide="prev">
  </a>
  <a class="right carousel-control" href="#orderImages" role="button" data-slide="next">
  </a>
</div>


      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>
<!-- /. Modal order products-->

</c:if>


<script>
 $(document).ready(function(){
	 $('body').addClass("innerPage");
	 $('body').addClass("listingPage");
	 
	 

	 
	 $("#editInfoBut").click(function(e){
		 var action = $(this).data("action");

		 if(action=="edit")
			 {
			 $("#userInfoFrm").find("span").addClass("hidden");
			 $("#userInfoFrm").find(".form-group").removeClass("hidden");
		     $(this).data("action","update");
		 	 $(this).val("Update info");
			 }
		 else
			 {
			  
			 $("#userInfoFrm").submit();
			 
			 }
		 
	 });
	 

	 $(".addNewAddressBut").click(function(){  $("#addAddressFrm").trigger("reset"); });
	 $(".edit-address").click(function(e){
		 e.preventDefault();
		 var addressId=$(this).data("id");
		 var name=$("#address-fullName"+addressId).val();
		 var locality=$("#address-locality"+addressId).val();
		 var address=$("#address-address"+addressId).val();
		 var stateid=$("#address-stateid"+addressId).val();
		 var pinCode=$("#address-pinCode"+addressId).val();
		 var mobile=$("#address-mobile"+addressId).val();
		 $("#addAddressFrm #fullName").val(name);
		 $("#addAddressFrm #addressId").val(addressId);
		 $("#addAddressFrm #locality").val(locality);
		 $("#addAddressFrm #address").val(address);
		 $("#addAddressFrm #pinCode").val(pinCode);
		 $("#addAddressFrm #state").val(stateid);
		 $("#addAddressFrm #mobile").val(mobile);
		 $("#addAddressFrm").children(".form-group").addClass("active");
		 $("#add-address").attr("data-action","update");
		 $("#addAddressModal").modal("show");
	 });
	 
	 
	 $(".delete-address").click(function(e){
		 e.preventDefault();
		 var addressId=$(this).data("id");
		 if(confirm("Are you sure you want to delete address?"))
			 {
			 window.location="deleteAddress.htm?addressId="+addressId;
			 }
	
	 });
	 
	 $("#add-address").click(function(event){
		 event.preventDefault();
		 var action=$(this).data("action");
		 var actionUrl="";
		 if(action=="add")
			 {
			 actionUrl="addAddress.htm";
			 }
		 else
			 {
			 actionUrl="updateAddress.htm";
			 }
		 var formData = $("#addAddressFrm").serialize();
		 $.ajax({
				url : actionUrl,
				type : "POST",
				data : formData,
				success : function(data) {
					var arr = JSON.parse(data);
					if (arr.STATUS == "SUCCESS")
					{
					window.location="userPreference.htm?res=ADDRESS";							
					}			
					else
					{
					alert(arr.MESSAGE);						
					}
				}
			 });
		});		 
	 
	 $(".isDefault").on("change",function(){  
		 
		 var profileId=$(this).data("profileid");		 
		 window.location="chooseDefaultProfile.htm?profileId="+profileId;
	 });
	
	 $(".profile-del").click(function(e){
		 e.preventDefault();
		 var profileId=$(this).data("profileid");
		 if(confirm("Are you sure you want to delete profile?"))
			 {
			 window.location="deleteCustomerProfile.htm?profileId="+profileId;
			 }
		 
	 });
	 
	 
	 
	 
	 $(".profile-edit").click(function(e){ 
		 e.preventDefault(); 
		 var profileId=$(this).data("id");
		 $("#custProfileContent").html("<p>Please wait we are loading ..</p>");
		 $("#profileEnter").modal("show");
		 $.ajax({
				url : "editCustomerProfile.htm",
				type : "POST",
				data : {
					"profileId" :profileId
				},
				success : function(data) {
					var arr = JSON.parse(data);
					console.log(arr);
					if(arr.STATUS=="SUCCESS")
						{
						var sizeAttrArr=arr.measArray;
						var profileGender=arr.profileGender;
						var html="";
						html+='<form action="updateCustomerProfile.htm" method="post" name="customerProfileFrm" id="customerProfileFrm" >';
						html+='<input type="hidden" name="profileId" value="'+arr.profileId+'" />';
						html+='<input type="hidden" name="profileGender" id="profileGender"  value="'+profileGender+'" />';
						html+='<div class="row"><div class="col-xs-6"><div class="form-group"><input type="text" class="form-control" name="profileName" value="'+arr.profileName+'"/></div></div>';
						html+='<div class="col-xs-6">For whom you are buying?</div></div>';
						
						for(var i=0;i<sizeAttrArr.length;i++)
						{
						var obj=sizeAttrArr[i];
						html+='<input type="hidden" name="sizeAttrId" value="'+obj.id+'" />';
						html+='<div class="form-group hasLabel"><label for="field-'+i+'">'+obj.name+'</label>';
						html+='<input type="text" class="form-control" value="'+obj.customerValue+'" name="sizeAttrVal" id="field-'+i+'"  >';
						html+='</div>';
						}
					html+='<div class="row mts"><div class="col-xs-6"><button type="cancel" class="btn btn-default full-width">Do it later</button></div>';
					html+='<div class="col-xs-6"><button type="submit" class="btn btn-primary full-width">Submit</button></div></div>';
					html+='</form>';
					$("#custProfileContent").html(html);

				}
				}
		 		});		
	});
	 

	 $("#add-new-profile").click(function(){
		 
		 $("#custProfileContent").html("<p>Please wait we are loading ..</p>");
		 $.ajax({
				url : "getMeasurementAttribute.htm",
				type : "POST",
				data : {
					"profileGender" :1
				},
				success : function(data) {
					var arr = JSON.parse(data);
					if(arr.STATUS=="SUCCESS")
						{
						var sizeAttrArr=arr.attrColl;
						var html="";
						html+='<form action="addCustomerProfile.htm" method="post" name="customerProfileFrm" id="customerProfileFrm" >';
						html+='<input type="hidden" name="profileGender" value="1" />';
						html+='<div class="form-group hasLabel"><label for="profileName">For whom you are buying?</label>';
						html+='<input type="text" class="form-control" name="profileName" id="profileName"  >';
						html+='</div>';
						for(var i=0;i<sizeAttrArr.length;i++)
						{
						var obj=sizeAttrArr[i];
						html+='<input type="hidden" name="sizeAttrId" value="'+obj.id+'" />';
						html+='<div class="form-group hasLabel"><label for="field-'+i+'">'+obj.name+'</label>';
						html+='<input type="text" class="form-control" value="" name="sizeAttrVal" id="field-'+i+'"></div>';
						}
						html+='<div class="row mts">';
						html+='<div class="col-xs-6"><button type="submit" class="btn btn-primary full-width">Submit</button></div></div>';
						html+='</form>';
						
						$("#custProfileContent").html(html);

						}
				}
			});
	 });
 });
 </script>
<script>
            var $imageupload = $('.imageupload');
            $imageupload.imageupload();

            $('#imageupload-disable').on('click', function() {
                $imageupload.imageupload('disable');
                $(this).blur();
            })

            $('#imageupload-enable').on('click', function() {
                $imageupload.imageupload('enable');
                $(this).blur();
            })

            $('#imageupload-reset').on('click', function() {
                $imageupload.imageupload('reset');
                $(this).blur();
            });
        </script>
<script src="js/bootstrap-imageupload.js"></script>
<%@ include file="includes/footer.jsp"%>
