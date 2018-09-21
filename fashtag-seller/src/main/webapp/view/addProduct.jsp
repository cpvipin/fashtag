<%@ include file="includes/header.jsp"%>
 <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>



<style>

#images img{
width:100px;
height:100px;
}
#tab-designAttribute-specification img{
width:100px;
height:100px;
}

.image img{
width:100px;
height:100px;
}

</style>
<div class="breadcrumb">
<a href="dashboard.htm">Home</a> :: <a href="listProduct.htm">Product</a>
</div>

<div class="box">
    <div class="heading">
      <h1><img src="image/product.png" alt=""> Products</h1>
      <div class="buttons">
     
     
     
		<c:set var="formUrl" value="javaScript:addFormSubmit();" />
		
		<c:if test="${not empty product.id}">
		
		<c:set var="formUrl" value="javaScript:editFormSubmit();" />
		</c:if>
		
			 <a onClick='<c:out value="${formUrl}" />' class="button">Save</a> 
			 <a href="listProduct.htm" class="button">Cancel</a>
			 
		</div>	 
			 
    </div>
    <div class="content">
      <div id="tabs" class="htabs">
      <a class="selected" style="display: inline;" href="#tab-general">General</a>
      <a style="display: inline;" href="#tab-data">Product Size</a>
      <a style="display: inline;" href="#tab-image">Product Images</a>
      <a style="display: inline;" href="#tab-designAttributes">Design Attributes</a>
       </div>
     
     
      <form  method="post"  id="productForm" name="productForm"  enctype="multipart/form-data">
      
      <c:if test="${not empty product.id}">
		<input type="hidden" name="id" id="productId" value="${product.id}" />
		<input type="hidden" name="dateAdded" value="${viewTools.formatDate(product.dateAdded)}" />
		<input type="hidden" name="totalViews" value="${product.totalViews}" />
		<input type="hidden" name="totalLikes" value="${product.totalLikes}" />
		<input type="hidden" name="defaultImage" value="${product.defaultImage}" />
		</c:if>


<div style="display: block;" id="tab-general">
            <table class="form">
              <tbody>
<tr>
	<td><span class="required">*</span> Category Name:</td>
	<td><input type="hidden" name="category.id" id="categoryId"	value="${product.category.id}" /> 
	<input type="text" name="categoryChooser" id="categoryChooser" value="${product.category.name}" class="ui-autocomplete-input" autocomplete="off">
	</td>
</tr>



<tr>
<td><span class="required">*</span> Product Name:</td>
<td><input type="text" name="name" id="name" value="${product.name}"></td>
</tr>

<tr>
<td><span class="required">*</span> Quantity:</td>
<td>
<input type="text" name="quantity" id="quantity" value="${product.quantity}" >
</td>
</tr>

						<tr>
							<td><span class="required">*</span>Actual Price:</td>
							<td>
							<input type="text" name="actualPrice" id="actualPrice" value="${product.actualPrice}">
							</td>
						</tr>


						<tr>
                <td><span class="required">*</span>Offer Price:</td>
                <td>
                			<input type="text" name="offerPrice" id="offerPrice" value="${product.offerPrice}" >
							
							
                  </td>
              </tr>	
              
              
              <tr>
                <td><span class="required">*</span>Date Available:</td>
                <td>
                			<input type="text" id="dateAvailable" name="dateAvailable" size="20" value="${viewTools.formatDate(product.dateAvailable)}"/>					        	
			  		<img src="image/calendar.gif" id="dateAvailable_trigger" style="cursor: pointer;" title="Available Date"/> 
				  		<script type="text/javascript">
							  Calendar.setup(
							    {
							      inputField  : "dateAvailable",
							      ifFormat    : "%d/%m/%Y", 
							      button      : "dateAvailable_trigger"
							    }
								  );
						</script>  
				   </td>
              </tr>
             		<tr>
						<td><span class="required">*</span> Description:</td>
						<td><textarea name="description" id="description">${product.description}</textarea></td>
					</tr>
					
					
<tr>
<td><span class="required">*</span>Default Image:</td>
<td>
<div class="image">
<img src="${viewTools.getProdImageUrl()}${product.defaultImage}" alt="" id="thumbDefault">
<input name="defaultFileImage" value="${viewTools.getProdImagePath()}${product.defaultImage}" id="imageDefault" type="file" />
<br>
</div>

</td>
</tr>

					
					
					
					
              
               <tr>
                <td><span class="required">*</span>Actual Colour:</td>
                <td>
                			<input type="text" name="actualColor" id="actualColor" value="${product.actualColor}" >
				   </td>
              </tr>
 </tbody></table>
</div>








                   <!-- Product Size-->
        <div style="display: none;" id="tab-data">
          <table class="form">
            <tbody>
            <tr>
              <td>Sizes:</td>
              <td>
<div class="scrollbox">
									
									
									
									
									<c:forEach var="listVar" items="${sizeList}" varStatus="counter">
									


			<div class="even">
										<input name="productSize" value="${listVar.id}" type="checkbox" 

<c:forEach var="item" items="${product.productToSize}">
  <c:if test="${item.size.id eq listVar.id}">
  checked
  </c:if>
</c:forEach>
										>
										${listVar.name}
									</div>						
									
									
					
				</c:forEach>
				
		</div> <a onclick="$(this).parent().find(':checkbox').attr('checked', true);">Select All</a> 
		/ <a onclick="$(this).parent().find(':checkbox').attr('checked', false);">Unselect All</a></td>
            </tr>
          </tbody></table>
        </div>
        
        
        
    <!-- Product image -->    
       <div id="tab-image" style="display:none">
       <input type="hidden" name="imageRow" id="imageRow" value='0' />
          <table id="images" class="list">            
            <thead>
              <tr>
                <td class="left">Image:</td>
                <td>Action</td>
              </tr>
            </thead>
            
<!-- Product image edit -->   
<c:forEach var="item" items="${product.productImages}"  varStatus="counter">
<tbody id="image-row${counter.count-1}">
<tr>
<td class="left">
<div class="image">
<img src="${viewTools.getProdImageUrl()}${item.image}" alt="" id="thumb${counter.count-1}">
</div></td>
<td class="left">
<a onclick="removeImage('image-row${counter.count-1}','${item.id}',this)" class="button saved">Remove</a>
</td>
</tr>
</tbody>
</c:forEach>
         
            
            
              <tfoot>
              <tr>
                <td></td>
                <td class="left"><a onclick="addImage();" class="button">Add Image</a></td>
              </tr>
            </tfoot>
            
            </table>
            </div> 
            
            
         
         
         
         
         
          <!-- Design Attribute -->
              <div id="tab-designAttributes">
              
          <div id="vtab-option" class="vtabs">
          <c:set var="lastDesignAttrName" value="" />
          <c:set var="attrCount" value="0" />
          
          
          <c:forEach var="item" items="${prodDesignAttributeList}"  varStatus="counter">
          <c:if test="${lastDesignAttrName!=item.designAttribute.name}">
          
          <a class="" href="#tab-option-${attrCount}" id="option-${attrCount}">
          ${item.designAttribute.name}&nbsp;
<img src="image/delete.png" alt="remove" onclick="$('#vtab-option a:first').trigger('click'); $('#option-${attrCount}').remove(); $('#tab-option-${attrCount}').remove(); return false;">
</a>

           <c:set var="attrCount" value="${attrCount+1}" />

          </c:if>
                    <c:set var="lastDesignAttrName" value="${item.designAttribute.name}" />
          
          </c:forEach>
                    <input type="hidden" name="designAttributeCount" id="designAttributeCount" value="${attrCount}" />
                
                <span id="option-add">
            	<input type="text" name="designAttributeChooser" id="designAttributeChooser" value="" 
            	class="ui-autocomplete-input" autocomplete="off">
				&nbsp;<img src="image/add.png" alt="Add Option" title="Add Option">
				</span>
		  </div>
<c:set var="lastDesignAttrName" value="" />
<c:set var="attrCount" value="0" />

<c:forEach var="attrSpec" items="${prodDesignAttributeList}"  varStatus="counter">

<c:if test="${lastDesignAttrName!=attrSpec.designAttribute.name}">
<c:if test="${lastDesignAttrName!=''}">
</tbody>
</table>
</div>
<c:set var="attrCount" value="${attrCount+1}" />
</c:if>


<div   id="tab-option-${attrCount}" class="vtabs-content">	


<table class="form"><tbody>
</c:if>

<tr><td>Design attribute Specification :</td>
<td>
<c:set var="checked" value="" />
<c:set var="isReccom" value="" />

<c:forEach var="prodAttrSpec" items="${product.productToDesignAttributeSpecification}">

<c:if test="${prodAttrSpec.designAttributeSpecification.id==attrSpec.id}">
<c:set var="checked" value="checked" />
</c:if>

<c:if test="${prodAttrSpec.isRecommended ne false && attrSpec.id==prodAttrSpec.designAttributeSpecification.id }">
<c:set var="isReccom" value="checked" />
</c:if>


</c:forEach>


<input name="designAttrSpec_${attrCount}" 
value="${attrSpec.id}" type="checkbox" ${checked} >
${attrSpec.name}

</td>
<td>Is recommended :


<input name="is_recommended_${attrCount}" value="${attrSpec.id}" id="${attrSpec.id}" type="radio" ${isReccom}></td></tr>



<c:set var="lastDesignAttrName" value="${attrSpec.designAttribute.name}" />


</c:forEach>  
  </tbody>
</table>
</div>


  </div>
                                        
        
        
        
        
        




		</form>
    </div>
  </div>

<script language="javaScript">

var nonEmptyCategoryList = [ 
							['Category','categoryId'],
							['Product Name','name'],
							['quantity','quantity'],
							['Actual Price','actualPrice'],
							['Offer Price','offerPrice'],
							['Date Available','dateAvailable'],
							['Description','description'],
							['Actual Color','actualColor']
						    ];
function addFormSubmit(){
	var tForm = document.productForm;
	if(showRequiredAlert(nonEmptyCategoryList) ){
		return;
	}
	
	
	
  tForm.action='saveProduct.htm'; 

	tForm.submit();
}


function editFormSubmit(){
 var tForm = document.productForm;
if(showRequiredAlert(nonEmptyCategoryList) ){
		return;
	}
tForm.action='updateProduct.htm';

tForm.submit();
}

</script>

<script type="text/javascript"><!--
$('#tabs a').tabs(); 
$('#languages a').tabs(); 
$('#vtab-option a').tabs();
//--></script>


<script type="text/javascript">
$(document).ready(
		function() {

			var image_row = $("#imageRow").val();
			var design_attr_row=$("#designAttributeCount").val();
			
			

			$("#designAttributeChooser").autocomplete({
				minLength : 1,
				source : function(request, response) {
					searchKey = $("#designAttributeChooser").val();
					var data = searchKey;
					$.ajax({
						url : "getDesignAttributes.htm",
						type : "POST",
						data : {
							'searchKey' : data
						},
						success : function(data) {
							
							
							
							var arr = JSON.parse(data);
							response($.map(arr, function(item) {
								return {
									value : item.name,
									id : item.id
								};
							}));
						}
					});
				},
				focus : function() {
					return true;
				},
				select : function(event, ui) {
					
					
					$.ajax({
						url : "getDesignAttrSpec.htm",
						type : "POST",
						data : {
							'designAttrId' : ui.item.id
						},
						success : function(data) {
							
							html  = '<div id="tab-option-' + design_attr_row + '" class="vtabs-content">';
							html += '	<table class="form">';
							
							var obj = jQuery.parseJSON(data);
							$.each(obj, function(key,value) {
								html += '<tr><td>Design attribute Specification :</td><td>';
					             html += '<input name="designAttrSpec_'+design_attr_row+'" value="'+value.id+'" type="checkbox" checked="checked">';
									html+=value.name;
				html += '</td><td>Is recommended : <input type="radio" name="is_recommended_'+design_attr_row+'" value="'+value.id+'" id="'+value.id+'" /></td></tr>';						

							}); 
							
							
											
											html += '</td>';
							html += '</tr></table></div>';
							
							$('#tab-designAttributes').append(html);
							
							$('#option-add').before('<a href="#tab-option-' + design_attr_row + '" id="option-' + design_attr_row + '">' 
									+ ui.item.label 
									+ '&nbsp;<img src="view/image/delete.png" alt="remove" onclick="$(\'#vtab-option a:first\').trigger(\'click\'); $(\'#option-' + design_attr_row + '\').remove(); $(\'#tab-option-' + design_attr_row + '\').remove(); return false;" /></a>');
							

							$('#vtab-option a').tabs();
							
							$('#option-' + design_attr_row).trigger('click');	
							
							design_attr_row++;
							$("#designAttributeCount").val(design_attr_row);
						}
					});
					
					
					
				}
			});
			
			
			
			$("#categoryChooser").autocomplete({
				minLength : 1,
				source : function(request, response) {
					searchKey = $("#categoryChooser").val();
					var data = searchKey;
					$.ajax({
						url : "getChildCategories.htm",
						type : "POST",
						data : {
							'searchKey' : data
						},
						success : function(data) {
							console.log(data);
							var arr = JSON.parse(data);
							console.log(arr);
							response($.map(arr, function(item) {
								return {
									value : item.name,
									id : item.id
								};
							}));
						}
					});
				},
				focus : function() {
					return true;
				},
				select : function(event, ui) {
					var selectedName = ui.item.name;
					var selectedId = ui.item.id;
					$("#categoryId").val(selectedId);
					return true;
				}
			});
			
			
		
			
		});

function removeImage(imageRow,imgId,thisObj)
{
		
	if(confirm('Are you sure you want to delete this image ?'))
		{
	
	if($(thisObj).hasClass("saved"))
		{
	
		var prodId=$("#productId").val();
		
	$.ajax({
		url : "deleteSavedImage.htm",
		type : "POST",
		data : {
			'productId' : prodId,
			'imageId':imgId
		},
		success : function(data) {
			document.getElementById(imageRow).remove();
			alert("image deleted succesfully");
		}
	});
		}
	else
		{
		var newCount = parseInt($('#imageRow').val())-1;
		$('#imageRow').attr("value",newCount);
		document.getElementById(imageRow).remove();

		}
		
		}
    
}


var no_image="/image/no_image-100x100.jpg";
function addImage() {
	image_row = $("#imageRow").val();
	 html = '<tbody id="image-row' + image_row + '">';
	 html+='<tr>';
	 html +='<td class="left"><div class="image"><img src="'+no_image+'" alt="" id="thumb'+image_row + '" />';
	 html+='<input type="file" name="productImage" value="" id="image' + image_row + '" /><br />';
	 html+='</div></td>';
	 html += '    <td class="left"><a onclick="removeImage(\'image-row' + image_row  + '\',0,this)" class="button">Remove</a></td>';
	 html += '  </tr>';
	 html += '</tbody>';
	$('#images tfoot').before(html);
	image_row++;
	$("#imageRow").val(image_row);
}
</script> 
<%@ include file="includes/footer.jsp"%>