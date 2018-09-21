$(document).ready(function(){	

	jqUpdateSize();
	
	$('[data-toggle="tooltip"]').tooltip();
	$('[data-toggle="popover"]').popover();
	

	//Switch delivery address
	$('.addressCard.thumbnail').click(function(){
	    if($(this).hasClass('active')){
	    }
	    else {
	    	var obj=$(this); 
	    	var id=$(this).attr("data-id");
	    	 var pinCode=$(this).attr("data-pincode");	 
	    	
	    	 $.ajax({
	    			url : "checkDelivery.htm",
	    			type : "POST",
	    			data : {
	    				"pincode" :pinCode,
	    				"prodId" :0
	    			},
	    			success : function(data) {
	    				var arr = JSON.parse(data);
	    				$("#selAddressId").val(id);
	    				
	    				$('.addressCard.thumbnail').removeClass('active');
	    		        $(obj).addClass('active');
	    		        if (arr.ISAVAIL == true) 
	    				{
	    					$("#proceedBut").removeClass("disabled");
	    					
	    				} 
	    				else 
	    				{
	    					$("#proceedBut").addClass("disabled");

	    					alert(arr.MESSAGE);
	    				}
	    			}
	    		});	

	    	 
	    	 
	        
	    }
	 });


	
	
	
	//custom design
		$("#customDesignSubmit").click( function(event){
			event.preventDefault();
			var selElement=$('#custDesElem .selected');
			var selDes="";
			for(var i=0;i<selElement.length;i++)
				{
				var obj=selElement[i];
				selDes+=$(obj).data("specid")+",";
				}
			$("#custDesign").val(selDes);
			var source=$(this).data("source");
			var desCount=$("#productDesignCount").val();
			var selDesigns=selDes;
			var formData=$("#buyProductFrm").serialize();
			var sizeId=$("#sizeId").val();
			var obj=$(this);
			if(parseInt(sizeId)==0 || sizeId=="")
				{
				$(".size-selector").addClass("error-border");
				return;
				}
					$.ajax({
								url : "addToCart.htm",
								type : "POST",
								data : formData,
								success : function(data) {
									var arr = JSON.parse(data);
									if (arr.STATUS == "SUCCESS") 
									{
									if(source=="buynow-but")
										{
										window.location="cart.htm";
										}
									else{
						 			$(".cart").children(".hidden-xs").html(""+arr.COUNT+" items - RS. "+arr.TOTAL_PRICE);
						 			$(".alertBox").html('<div class="alert alert-success" data-close="auto">'+arr.MESSAGE+'</div>');
						 			} 
									$("#product-design-modal").modal("hide");
									}
									else{
							 			$(".alertBox").html('<div class="alert alert-danger" data-close="auto">'+arr.MESSAGE+'</div>');

									}

								}
							});	
			
		});
		

		

//Label

  $('input').each(function() {
    if ($(this).val() != '') $(this).parent('.hasLabel').addClass('active');
  });
  
  $(document).on('focus', 'input', function() {
	    $(this).parent('.hasLabel').addClass('active');
	  });


  $(document).on('focus', 'textarea', function() {
	    $(this).parent('.hasLabel').addClass('active');
	  });

  
	  $(document).on('blur','input', function() {
	    if ($(this).val().length == 0) {
	      $(this).parent('.hasLabel').removeClass('active');
	    }
	  });
	  $(document).on('blur','textarea', function() {
		    if ($(this).val().length == 0) {
		      $(this).parent('.hasLabel').removeClass('active');
		    }
		  });







//slideshow
	$(".regular, .regular2").slick({
        dots: false,
        variableWidth: true,
        infinite: true,
        slidesToShow: 5,
        slidesToScroll: 1,
        responsive: [
		    {
		      breakpoint: 1200,
		      settings: {
		        slidesToShow: 5
		      }
		    },
		    {
		      breakpoint: 991,
		      settings: {
		        slidesToShow: 3
		      }
		    },
		    {
		      breakpoint: 768,
		      settings: {
		        slidesToShow: 2
		      }
		    },
		    {
		      breakpoint: 640,
		      settings: {
		        slidesToShow: 1
		      }
		    },
		    {
		      breakpoint: 480,
		      settings: {
		        slidesToShow: 1
		      }
		    }

	    ]

      });


	/* Custom checkbox */
	$('.i-checks').iCheck({
	    checkboxClass: 'icheckbox_square-green',
	    radioClass: 'iradio_square-green'
	});



	$('.btn-number').click(function(e){
	    e.preventDefault();
	    
	    
	    fieldName = $(this).attr('data-field');
	    type      = $(this).attr('data-type');
	    var input = $("input[name='"+fieldName+"']");
	    var currentVal = parseInt(input.val());
	    if (!isNaN(currentVal)) {
	        if(type == 'minus') {
	            
	            if(currentVal > input.attr('min')) {
	                input.val(currentVal - 1).change();
	            } 
	            if(parseInt(input.val()) == input.attr('min')) {
	                $(this).attr('disabled', true);
	            }

	        } else if(type == 'plus') {

	            if(currentVal < input.attr('max')) {
	                input.val(currentVal + 1).change();
	            }
	            if(parseInt(input.val()) == input.attr('max')) {
	                $(this).attr('disabled', true);
	            }

	        }
	    } else {
	        input.val(0);
	    }
	});
	$('.input-number').focusin(function(){
	   $(this).data('oldValue', $(this).val());
	});
	$('.input-number').change(function() {
	    
	    minValue =  parseInt($(this).attr('min'));
	    maxValue =  parseInt($(this).attr('max'));
	    valueCurrent = parseInt($(this).val());
	    
	    name = $(this).attr('name');
	    if(valueCurrent >= minValue) {
	        $(".btn-number[data-type='minus'][data-field='"+name+"']").removeAttr('disabled')
	    } else {
	        alert('Sorry, the minimum value was reached');
	        $(this).val($(this).data('oldValue'));
	    }
	    if(valueCurrent <= maxValue) {
	        $(".btn-number[data-type='plus'][data-field='"+name+"']").removeAttr('disabled')
	    } else {
	        alert('Sorry, the maximum value was reached');
	        $(this).val($(this).data('oldValue'));
	    }
	    
	    
	});
	$(".input-number").keydown(function (e) {
	        // Allow: backspace, delete, tab, escape, enter and .
	        if ($.inArray(e.keyCode, [46, 8, 9, 27, 13, 190]) !== -1 ||
	             // Allow: Ctrl+A
	            (e.keyCode == 65 && e.ctrlKey === true) || 
	             // Allow: home, end, left, right
	            (e.keyCode >= 35 && e.keyCode <= 39)) {
	                 // let it happen, don't do anything
	                 return;
	        }
	        // Ensure that it is a number and stop the keypress
	        if ((e.shiftKey || (e.keyCode < 48 || e.keyCode > 57)) && (e.keyCode < 96 || e.keyCode > 105)) {
	            e.preventDefault();
	        }
	    });


	//Nice scroll
	$(".scroll").niceScroll({cursorcolor:"#029AE4", touchbehavior:true}).resize();



	//
	$('#styleCarousel').carousel({
	    interval: false
	}); 



}); // END document.ready();



window.setTimeout(function() {
    $('[data-close="auto"]').fadeTo(500, 0).slideUp(500, function(){
        $(this).remove(); 
    });
}, 4000);

function menuHeirarchyLoader(obj)
{

	var menuId=$(obj).children('a').data("menuid");
	var html='';
	var currData=$(obj).find('.dropdown-menu').html(); 
if(currData.trim().length==0)
	{
	
	$(obj).find('.dropdown-menu').html("<div class='loader'></div>");

	
	$.ajax({
		url : "getChildMenu.htm",
		type : "POST",
		async:false,
		data : {
			"menuId" :menuId
		},
		success : function(data) 
		{
			var arr = JSON.parse(data);
			var level1Arr=(arr.level1);
			
			for(var i=0; i< level1Arr.length;i++)
				{
				var level1Obj=level1Arr[i];
				
				if(typeof level1Obj.name!="undefined")
					{
				html+='<li class="col-sm-3"><ul>';
				html+='<li class="dropdown-header"><a href="listProducts.htm?catId='+level1Obj.id+'">'+level1Obj.name+'</a></li>';
				
				var level2Arr=level1Obj.level2;
				for(j=0;j<level2Arr.length;j++)
					{
					var level2Name=level2Arr[j].name;
					var level2Id=level2Arr[j].id;
					if(typeof level2Name!="undefined")
						{
					html+='<li><a href="listProducts.htm?catId='+level2Id+'">'+level2Name+'</a></li>';
					}
					}
				html+='</ul></li>';
				
				}
				
				}
			
			 
		}
	});
	
		
	
$(obj).find('.dropdown-menu').html(html);


	}


};



function jqUpdateSize(){
		var width = $(this).width(); 
		var height = $(this).height(); 
		
		if(width>991){	
            

            $('.dropdown-submenu').hover(
                
               function () {
                    if ($(this).hasClass('active')) {
                        $(this).removeClass('active').find('ul').hide();
                        $(this).removeClass('active').find('ul').css({ display : "block"});
                    }
                    else{
                       $(this).addClass('active').find('ul').show(); 
                       menuHeirarchyLoader(this);
                       
                       
                    } 
               }, 
                
               function () {
                  $('.dropdown-submenu').removeClass('active').find('ul').hide();
               }
            );
            $('body').on('click', function(event){
                $('.dropdown-submenu').removeClass('active').find('ul').hide();
            });




            //
			$('.menuIcon').click(function() {
				if($('.menudd').is(':hidden')){
					$('.menudd').addClass('show');
				}
				else{
					$('.menudd').removeClass('show');
				}
			});
			$('.toggle-menu').click(function() {
				if($(this).next().hasClass('expand')){
					
					$(".toggle-menu").removeClass('on');
					$('.nav-tabs').removeClass('expand');
					
				}
				else{
					$(this).addClass('on').next().addClass('expand');
				}
			});

            //
			var i = 0;
		    $('body').on('click', '.nav-tabs li', function (event) {
		        var html = $(this).html();
		        $(this).parent().find('li').removeClass('active');
		        $(this).parent().prepend('<li role="presentation" class="active">' + html + '</li>');
		        $(this).parent().removeClass('expand');
		        $(".toggle-menu").removeClass('on');
		        $(this).remove();
		    });


		}
        if(width<=768){
            $('ul.mega-dropdown-menu').on('click', function(event){
            	$(this).parent().addClass('blocked');
                event.stopPropagation();
            });
            $('.dropdown-submenu').unbind("click").on('click', function(event){
            	if ($(this).hasClass('active')) {
                	$('.dropdown-submenu').removeClass('active').find('ul').hide();
                }
                else{
                	$('.dropdown-submenu').removeClass('active').find('ul').hide();
                   $(this).addClass('active').find('ul').show(); 
                   menuHeirarchyLoader(this);
                }  
            });


        }

		
		
	};
$(window).resize(jqUpdateSize);
