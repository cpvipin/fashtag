<%@ include file="includes/header.jsp"%>
<style>
#tab-designAttribute-specification img {
	width: 100px;
	height: 100px;
}
</style>
<div class="breadcrumb">
	<a href="dashboard.htm">Home</a> :: <a href="listProduct.htm">Add
		Design Attribute</a>
</div>

<div class="box">
	<div class="heading">
		<h1>
			<img src="image/module.png" alt="" />DesignAttribute
		</h1>
		<div class="buttons">
			<c:set var="formUrl" value="javaScript:addFormSubmit();" />

			<c:if test="${designAttrSpecCount>0}">
				<c:set var="formUrl" value="javaScript:editFormSubmit();" />
			</c:if>

			<a onClick='<c:out value="${formUrl}" />' class="button">Save</a> <a
				href="listDesignAttribute.htm" class="button">Cancel</a>

		</div>
	</div>
	<div class="content">

		<form name="designAttributeSpecForm" id="designAttributeSpecForm"
			method="post">

			<!-- Design Attribute Specification -->
			<input type="hidden" name="designAttributeSpecCount"
				id="designAttributeSpecCount" value="${designAttrSpecCount}" />
			<div id="tab-designAttribute-specification">
				<table id="design_attributes_spec" class="list">

					<tr>
						<td class="left">Design Attribute :</td>
						<td class="left"><select name="design_attribute"
							id="design_attribute" <c:if test="${designAttrSpecCount>0}">disabled</c:if> ><option value="0">select
									design attribute</option>'
								<c:forEach var="listVar" items="${designAttributeList}"
									varStatus="counter">
									<option
										<c:if test="${designAttrId == listVar.id}"> selected </c:if>
										value="${listVar.id}">${listVar.name}</option>';
	</c:forEach>
						</select>
						<td colspan="3"></td>
						</td>
					</tr>


					<thead>
						<tr>
							<td class="right">Image</td>
							<td class="right">name</td>
							<td class="right">Description</td>
							<td class="right">Sort Order</td>
							<td class="left">Action</td>
							<td></td>
						</tr>
					</thead>

					<c:forEach var="listVar" items="${designAttributeSpecList}"
						varStatus="counter">


						<tbody id="design-attribute-spec-row${counter.count}">
							<tr>
								<td class="left"><div class="image">
										<img src="${viewTools.getDesAttrImageUrl()}${listVar.image}" alt=""
											id="spec_thumb${counter.count-1}" /> <input type="hidden"
											name="spec_image${counter.count-1}" value=""
											id="spec_image${counter.count-1}" /><br /> <a
											onclick="image_upload('spec_image${counter.count-1}', 'spec_thumb${counter.count}');">browse</a>&nbsp;&nbsp;|&nbsp;&nbsp;
										<a onclick="" href="javaScript:void(0)">clear</a>
									</div>
								</td>
								<td class="right"><input type="text"
									name="spec_name_${counter.count-1}" value="${listVar.name}" />
								</td>
								<td class="right"><textarea
										name="spec_description_${counter.count-1}">${listVar.description }</textarea>
								</td>
								<td class="right"><input type="text"
									name="spec_sort_order_${counter.count-1}"
									value="${listVar.sortOrder }" />
								</td>
								<td class="left"><a
									onclick="removeSpec('design-attribute-spec-row${counter.count-1}')"
									class="button">Remove</a>
								</td>
							</tr>
						</tbody>

					</c:forEach>

					<tfoot>















						<tr>
							<td colspan="4"></td>
							<td class="left"><a onclick="addDesignAttributeSpec();"
								class="button">Add Design Attribute Specification</a>
							</td>
						</tr>
					</tfoot>
				</table>
			</div>



		</form>

	</div>
</div>



<script type="text/javascript" src="js/jquery-ui-1.8.16.custom.min.js"></script>
<link rel="stylesheet" type="text/css" href="css/jquery-ui-1.8.16.custom.css" />
<script type="text/javascript" src="js/jquery.bgiframe-2.1.2.js"></script>
<script type="text/javascript" src="js/jstree/jquery.tree.min.js"></script>
<script type="text/javascript" src="js/ajaxupload.js"></script>



<script language="javaScript">

function addFormSubmit(){

	var tForm = document.designAttributeSpecForm;
	
	
    tForm.action='saveDesignAttrSpec.htm';

    tForm.submit();
}


function editFormSubmit(){
 var tForm = document.designAttributeSpecForm;

tForm.action='updateDesignAttributeSpec.htm';

	tForm.submit();
}

</script>



<script type="text/javascript">var no_image="/image/no_image-100x100.jpg";
var image_row=0;

function removeSpec(specRow)
{
	var newCount = parseInt($('#designAttributeSpecCount').val())-1;
	
	document.getElementById(specRow).remove();
    $('#designAttributeSpecCount').attr("value",newCount);

}


function addDesignAttributeSpec() {
	var design_attribute_spec_row = $('#designAttributeSpecCount').val();
	
	html  = '<tbody id="design-attribute-spec-row' + design_attribute_spec_row + '">';
	html += '  <tr>'; 
    
    
    html +='<td class="left"><div class="image"><img src="'+no_image+'" alt="" id="spec_thumb'+design_attribute_spec_row + '" />';
	 html+='<input type="hidden" name="spec_image'+design_attribute_spec_row + '" value="" id="spec_image' + design_attribute_spec_row + '" /><br />';
	 html+='<a onclick="image_upload(\'spec_image' + design_attribute_spec_row + '\', \'spec_thumb' + design_attribute_spec_row + '\');">browse</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a onclick="$(\'#thumb' + image_row + '\').attr(\'src\', \'+no_image+\'); $(\'#image' + image_row + '\').attr(\'value\',\'\');">clear</a></div></td>';
	 
	 
    html += '    <td class="right"><input type="text" name="spec_name_'+design_attribute_spec_row+'" value="" /></td>';
    html += '    <td class="right"><textarea name="spec_description_' + design_attribute_spec_row + '"></textarea></td>';
    html += '    <td class="right"><input type="text" name="spec_sort_order_' + design_attribute_spec_row + '" value="" /></td>';

    html += '    <td class="right"><select name="spec_is_recommended_' + design_attribute_spec_row + '"><option>--select--</option><option value="">true</option><option value="">false</option></select></td>';
	html += '    <td class="left"><a onclick="removeSpec(\'design-attribute-spec-row' + design_attribute_spec_row  + '\')" class="button">Remove</a></td>';
	html += '  </tr>';	
    html += '</tbody>';
	
	$('#design_attributes_spec tfoot').before(html);
	design_attribute_spec_row++;
    $('#designAttributeSpecCount').attr("value",design_attribute_spec_row);

}
</script>



<script type="text/javascript">
function image_upload(field, thumb) {
	$('#dialog').remove();
	
	$('#content').prepend('<div id="dialog" style="padding: 3px 0px 0px 0px;"><iframe src="fileManager.htm?field='+encodeURIComponent(field)+'&thumb='+encodeURIComponent(thumb)+'" style="padding:0; margin: 0; display: block; width: 100%; height: 100%;" frameborder="no" scrolling="auto"></iframe></div>'); 
	$('#dialog').dialog({
		title: 'Image Manager',
		close: function (event, ui) 
		{ },	
		bgiframe: false,
		width: 800,
		height: 400,
		resizable: false,
		modal: false
	}); 
}
</script>


<%@ include file="includes/footer.jsp"%>