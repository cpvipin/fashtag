$(document).ready(function() {
					$("#signInFrm")
							.submit(
									function(event) {
										var formData = $("#signInFrm").serialize();
										event.preventDefault();
										var obj=$(this);

										var userId = $("#user_id").val();
										$(".alert-danger").remove();
										$(obj).parent(".tab-pane").append("<div class='loader'></div>");
										$.ajax({
													url : "signIn.htm",
													type : "POST",
													data : formData,
													success : function(data) {
														var arr = JSON
																.parse(data);

														if (arr.status == "SUCCESS") {

															if (arr.isFirstLogin == true)
																{
																
																$("#loginModal").modal('hide');
																$("#changePassModal").modal('show');
																$("#userId").val(userId);

																}
															else
															{
																window.location = arr.backUrl;
															}
														} 
														else 
														{
															$(obj).parent(".tab-pane").find(".loader").remove();
															var errHtml='<div class="alert alert-danger">';
								                            errHtml+='<strong> Error! </strong>'; 
								                            errHtml+=arr.message+'</div>';
															$("#login").append(errHtml);
															
														}
													}
												});
									});

					$("#changePasswordFrm").submit(function(event) {
						var formData = $("#changePasswordFrm").serialize();
						event.preventDefault();
						var obj=$(this);
						$(obj).parent(".tab-pane").append("<div class='loader'></div>");
						$.ajax({
							url : "setUserPassword.htm",
							type : "POST",
							data : formData,
							success : function(data) {
								var arr = JSON.parse(data);
								if (arr.status == "SUCCESS") {

									if (arr.isFirstLogin == true)
										{
										$("#changePassModal").modal('hide');
										$("#sizeEnter").modal('show');
										var userId=arr.userId;
										
										$.ajax({
											url : "getCustomerSizeAttribute.htm",
											type : "POST",
											data : {
												"userId" :userId
											},
											success : function(data) {
												var arr = JSON.parse(data);
												if(arr.STATUS=="SUCCESS")
													{
													var sizeAttrArr=arr.sizeAttributeColl;
													if(arr.STATUS=="SUCCESS")
													{
					var sizeAttrArr=arr.sizeAttributeColl;
					var html="";
					html+='<input type="hidden" name="profileId" value="'+arr.profileId+'" />';
					html+='<input type="hidden" name="userId" value="'+userId+'" />';
					html+='<div class="row"><div class="col-xs-6"><div class="form-group"><input type="text" class="form-control" name="profileName" value="'+arr.profileName+'"/></div></div>';
					html+='<div class="col-xs-6">For whom you are buying?</div></div>'
															for(var i=0;i<sizeAttrArr.length;i++)
																{
																var obj=sizeAttrArr[i];
																var labelActive="";
																if(obj.customerValue>0){ labelActive="active";}
					html+='<input type="hidden" name="sizeAttrId" value="'+obj.id+'" />';
					html+='<div class="form-group hasLabel '+labelActive+'"><label for="field-'+i+'">'+obj.name+'</label>';
					html+='<input type="text" class="form-control" value="'+obj.customerValue+'" name="sizeAttrVal" id="field-'+i+'"  >';
					html+='</div>';
																}
				html+='<div class="row mts"><div class="col-xs-6"><button type="submit" class="btn btn-default full-width">Do it later</button></div>';
				html+='<div class="col-xs-6"><button type="submit" class="btn btn-primary full-width">Submit</button></div></div>';
							$("#sizeContent").html(html);

					}
													
					
													}
												
												
											}
										});
										
										
									}
									else
									{
										window.location = arr.backUrl;
									}
								} else {
									window.location = arr.backUrl;
								}
							}
						});
					});
					
					
					
					$("#signUpFrm")
					.submit(
							function(event) {
								var formData = $("#signUpFrm").serialize();
								event.preventDefault();
								var obj=$(this);
								$(obj).parent(".tab-pane").append("<div class='loader'></div>");
								$(".alert-danger").remove();
								$.ajax({
									url : "signUp.htm",
									type : "POST",
									data : formData,
									success : function(data) {
										var resp = JSON.parse(data);
										if(resp.STATUS=="ERROR")
										{
											$(obj).parent(".tab-pane").find(".loader").remove();
											var errHtml='<div class="alert alert-danger">';
				                              errHtml+='<strong> Warning! </strong>'; 
				                              errHtml+=resp.MESSAGE+'</div>';
											$("#signUpFrm").append(errHtml);
											
										}
										else
											{
											$("#loginModal").modal('hide');
											$("#sizeEnter").modal('show');
											var userId=resp.USERID;
											$.ajax({
												url : "getCustomerSizeAttribute.htm",
												type : "POST",
												data : {
													"userId" :userId
												},
												success : function(data) {
													var arr = JSON.parse(data);
													var sizeAttrArr=arr.sizeAttributeColl;
													if(arr.STATUS=="SUCCESS")
													{
					var sizeAttrArr=arr.sizeAttributeColl;
					var html="";
					html+='<input type="hidden" name="profileId" value="'+arr.profileId+'" />';
					html+='<input type="hidden" name="userId" value="'+userId+'" />';
					html+='<div class="row"><div class="col-xs-6"><div class="form-group"><input type="text" class="form-control" name="profileName" value="'+arr.profileName+'"/></div></div>';
					html+='<div class="col-xs-6">For whom you are buying?</div></div>'
															for(var i=0;i<sizeAttrArr.length;i++)
																{
																var obj=sizeAttrArr[i];
																var labelActive="";
																if(obj.customerValue>0){ labelActive="active";}
					html+='<input type="hidden" name="sizeAttrId" value="'+obj.id+'" />';
					html+='<div class="form-group hasLabel '+labelActive+'"><label for="field-'+i+'">'+obj.name+'</label>';
					html+='<input type="text" class="form-control" value="'+obj.customerValue+'" name="sizeAttrVal" id="field-'+i+'"  >';
					html+='</div>';
																}
				html+='<div class="row mts"><div class="col-xs-6"><button type="cancel" class="btn btn-default full-width">Do it later</button></div>';
				html+='<div class="col-xs-6"><button type="submit" class="btn btn-primary full-width">Submit</button></div></div>';
				$("#sizeContent").html(html);
					}
															
										
												}
											});
											
											
											
											}
										
									}
								});
								
							});
					
					
	$(".likeThis").on("click", function(event){
    event.preventDefault();
	var fullName=$("#loggedInCustomer").val();
	if(typeof fullName!="undefined")
		{
		var prodId=$(this).children("input").val();
		$.ajax({
			url : "addWishList.htm",
			type : "POST",
			data : { "prodId":prodId },
			success : function(data) {
				var resp = JSON.parse(data);

				if (resp.STATUS == "SUCCESS") {

					alert(resp.MESSAGE);
					
				}
				else
					{
					alert(resp.MESSAGE);
					}
				
			}
		});
		}
	else
		{
		$("#loginModal").modal('show');
		}
	});
	
		
	
	
	$("#forgotPassFrm")
	.submit(
			function(event) {
				var formData = $("#forgotPassFrm")
						.serialize();
				event.preventDefault();
				var obj=$(this);
				
				$(obj).parent(".tab-pane").append("<div class='loader'></div>");
				$(".alert").remove();
				
				$.ajax({
							url : "forgotPassword.htm",
							type : "POST",
							data : formData,
							success : function(data) {
								var arr = JSON
										.parse(data);
								
								if (arr.STATUS == "SUCCESS") {
									$(obj).parent(".tab-pane").find(".loader").remove();
									var sucHtml='<div class="alert alert-success">';
									sucHtml+='<strong> DONE ! </strong>'; 
									sucHtml+=arr.MESSAGE+'</div>';
									$("#forgotPassFrm").append(sucHtml);
								} 
								else 
								{
									$(obj).parent(".tab-pane").find(".loader").remove();
									var errHtml='<div class="alert alert-danger">';
		                              errHtml+='<strong> Warning! </strong>'; 
		                              errHtml+=arr.MESSAGE+'</div>';
									$("#forgotPassFrm").append(errHtml);
									
								}
							}
						});
			});
	
	
/*profile drop down */
	
	
	$(".profileDD ul li a").click(function(event) {
		event.preventDefault();
		var id=$(this).data("id");
		if(parseInt(id)>0)
			{
		$(".profileDD").children("a").html($(this).text()+'<span class="caret"></span>');
		$.ajax({
			url : "setUserProfile.htm",
			type : "POST",
			data : { "profileId":id},
			success : function(data) {
				var arr = JSON.parse(data);
			}
				});
			}
		
	});
});