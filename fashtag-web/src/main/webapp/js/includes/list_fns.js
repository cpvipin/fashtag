$(document).ready(function() {
	var catId=$("#catId").val();
	$(".prodList").html("<div class='loader'></div>");
	$(".prodList").load("listProductsContent.htm?catId="+catId);	
	$('input').on('ifChanged', function(event){
		var url="";
		if($(this).attr("name")=="priceFilter")
			{
			url+="&priceFilter=";
			$("input[name='priceFilter'").each(function(){ 

				if($(this).prop("checked"))
					{
				url+=$(this).val()+"-";
					}
			});
			}
		$(".prodList").html("<div class='loader'></div>");
		$(".prodList").load("listProductsContent.htm?catId="+catId+url);	
	});				

				});