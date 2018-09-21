<%@ include file="includes/header.jsp"%>

<div class="container-fluid navMainOuter">
	<div class="grey-bg"></div>
	<div class="container">

		<%@ include file="includes/inner_page_menu.jsp"%>

		<form name="addressChoose" id="addressChoose" action="confirmAddress.htm" method="post">
		<input type="hidden" name="selAddressId" id="selAddressId" value="${selAddressId}" /> 
			<div class="row">

				<div class="col-md-8">
					<h2 class="cartTitle">Confirm Delivery Address</h2>
									<div class="tab-pane" id="addressVtab">
						<div class="row">
							<div class="col-md-12 vtcTitle">
								<a href="#" data-toggle="modal" data-target="#addAddressModal" class="add-new pull-right"><span
									class="ion-ios-location"></span>
								</a>
							</div>
							<div class="col-md-12">

								<div class="borderBox">



									<!-- Tab panes -->
									
									<c:forEach var="addressObj" items="${addressColl}" varStatus="counter">
									
									<c:if test="${counter.count%2!=0 }" >
									<c:if test="${counter.count!=1 }" >
									</div>
									</c:if>
									<div class="row">
                                    </c:if>	
										
									<div class="col-sm-6">
                                                <div data-id="${addressObj.id}" data-pincode="${addressObj.pinCode}"
                                                 class="thumbnail addressCard">
                                                  <div class="caption">
                                                    <h3>Address ${counter.count}</h3>
                                                    <div class="table-responsive">
                                                        <table class="table no-border">
                                                            <tr>
                                                                <td>Name :</td>
                                                                <td>${addressObj.fullName}</td>
                                                            </tr>
                                                            <tr>
                                                                <td>Address : </td>
                                                                <td>${addressObj.address}</td>
                                                            </tr>
                                                            <tr>
                                                                <td>Locality/Town :</td>
                                                                <td>${addressObj.locality}</td>
                                                            </tr>
                                                            <tr>
                                                                <td>City/District :</td>
                                                                <td>Austin</td>
                                                            </tr>
                                                            <tr>
                                                            <input type="hidden" id="address-stateid${addressObj.id}"
																value="${addressObj.state.id}" />
                                                                <td>State:</td>
                                                                <td>${addressObj.state.name}</td>
                                                            </tr>
                                                            
                                                            <tr>
                                                           <td>Pin Code :</td>
															<td>${addressObj.pinCode}</td>
                                                            </tr>
                                                            <tr>
															<td>Mobile Number :</td>
															<td>${addressObj.mobile}</td>
														</tr>
														
                                                        </table>
                                                    </div>
                                                    <hr>
                                                    <ul class="delivery-detials">
                                                        
                                                    </ul>
                                                    <hr>
                                                  </div>
                                                </div>
                                              </div>	
										</c:forEach>
										
					<c:choose>	
						<c:when test="${viewTools.getCollectionSize(addressColl)%2==0 && viewTools.getCollectionSize(addressColl)>0}">
						</div> <!-- /- row -->	
						<div class="row">
						<div class="col-sm-6">
                                                <div class="thumbnail addNew">
                                                  <div class="caption">
                                                    <p><a href="#"  data-toggle="modal" data-target="#addAddressModal" class="btn btn-primary text-center add-new-btn" role="button">+</a></p>
                                                    <h5>Add New Address</h5>
                                                  </div>
                                                </div>
                                              </div>
                          </div>
                        </c:when>
                        <c:otherwise>  
                        
                        <c:if test="${viewTools.getCollectionSize(addressColl)==0}">
                        <div class="row">
                        </c:if>
                        
                        <div class="col-sm-6">
                                                <div class="thumbnail addNew">
                                                  <div class="caption">
                                                    <p><a href="#" data-toggle="modal" data-target="#addAddressModal" class="btn btn-primary text-center add-new-btn" role="button">+</a></p>
                                                    <h5>Add New Address</h5>
                                                  </div>
                                                </div>
                                              </div>
                                              
                        </div> <!-- /- row -->
                        </c:otherwise>		
							
					</c:choose>				
									
									
									
									
									
									
									

									
								</div>
								<!-- /.borderBox -->
							</div>
						</div>
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
								<td><strong>Order Total</strong>
								</td>
								<td class="text-right"><strong>Rs. ${totalPrice}</strong>
								</td>
							</tr>
							<tr class="">
								<td colspan="2"><input type="submit"
									class="btn btn-primary full-width" id="proceedBut"  value="proceed to Payment" /></td>
							</tr>
						</table>
					</div>
				</div>
			</div>
		</form>


	</div>
</div>



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
<form  method="post" name="addAddressFrm" id="addAddressFrm"  >
                        
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
					
					
					
             <button type="submit" id="add-address"  class="btn btn-primary">Save Address</button>
                    </form>
                </div><!-- /.paddingDiv -->
            </div><!-- /.col-md-12 -->
        </div><!-- /.row -->
    </div><!-- /.modal-content -->
  </div>
</div>
<!-- /. Add address -->






<%@ include file="includes/footer.jsp"%>

<script src="js/smoothproducts.min.js"></script>
<script src="js/jquery.nicescroll.min.js"></script>

<script>
 $(document).ready(function(){
 $('body').addClass("innerPage");
 $("#add-address").click(function(event){
	 event.preventDefault();

	 var formData = $("#addAddressFrm").serialize();
	 $.ajax({
			url : "addAddress.htm",
			type : "POST",
			data : formData,
			success : function(data) {
				var arr = JSON.parse(data);
				if (arr.STATUS == "SUCCESS")
				{
				window.location="chooseDeliveryAddress.htm";							
				}			
				else
				{
					if(typeof (arr.MESSAGE)!="undefined")
						{
						alert(arr.MESSAGE);
						}
					else
						{
						alert("Something went wrong, please try again");
						}
				}
			}
		 });
	});
 });
 </script>