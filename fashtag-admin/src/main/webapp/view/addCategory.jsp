<%@ include file="includes/header.jsp"%>
<div class="breadcrumb">
<a href="dashboard.htm">Home</a> :: <a href="listCategory.htm">Category</a>
</div>

<div class="box">
<div class="heading">
<h1><img src="image/category.png" alt="" />Category</h1>
		<div class="buttons">
		<c:set var="formUrl" value="javaScript:addFormSubmit();" />
		
		<c:if test="${not empty category.id}">
		<c:set var="formUrl" value="javaScript:editFormSubmit();" />
		</c:if>
		
			 <a onClick='<c:out value="${formUrl}" />' class="button">Save</a> 
			 <a href="listCategory.htm" class="button">Cancel</a>
			 
		</div>
	</div>
	<div class="content">

		<form name="categoryForm" id="categoryForm" method="post">
		
		<c:if test="${not empty category.id}">
		<input type="hidden" name="id" value="${category.id}" />
		<input type="hidden" name="dateAdded" value="${viewTools.formatDate(category.dateAdded)}" />
		</c:if>
		
		
		
			<table class="form">
				<tbody>
					<tr>
						<td><span class="required">*</span> Category Name:</td>
						<td><input type="text" name="name" id="name" size="100" value="${category.name}" ></td>
					</tr>

<tr>
						<td><span class="required">*</span> Icon:</td>
						<td>
						
<div class="image">
<img src="${viewTools.getProdImageUrl()}/icons/${category.icon}" alt="" id="catIcon">
<input name="icon" value="${category.icon}" id="catIconImg" type="hidden">
<br>
<a onclick="image_upload('catIconImg', 'catIcon');">browse</a>&nbsp;&nbsp;|&nbsp;&nbsp;
</div>
						
						
						</td>
					</tr>


					<tr>
						<td>Parent:</td>
						<td><input type="hidden" name="parent.id" id="parentId" value="${category.parent.id}" />
							<input type="text" name="parentChooser" id="parentChooser"
							value="${category.parent.name}" class="ui-autocomplete-input" autocomplete="off">
						</td>
					</tr>
					<tr>
						<td><span class="required">*</span> Sort Order:</td>
						<td><input type="text" name="sortOrder" id="sortOrder" value="${category.sortOrder}"></td>
					</tr>
					<tr>
						<td><span class="required">*</span>Status</td>
						<td><select name="activeStatus" id="activeStatus">
								<option value="">--select status--</option>
								<option <c:if test="${category.activeStatus == true}"> selected </c:if> value="1" >Active</option>
								<option <c:if test="${category.activeStatus == false}"> selected </c:if> value="0">InActive</option>
						</select></td>
					</tr>


				</tbody>
			</table>
		</form>

	</div>
</div>

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
<script language="javaScript">
var nonEmptyCategoryList = [ 
							['Category Name','name'],
							['Sort Order','sortOrder'],
							['Active Status','activeStatus']
						    ];
						    
function addFormSubmit(){
	var tForm = document.categoryForm;
	if(showRequiredAlert(nonEmptyCategoryList) ){
		return;
	}
	
    tForm.action='saveCategory.htm';

	document.categoryForm.submit();
}


function editFormSubmit(){
 var tForm = document.categoryForm;
if(showRequiredAlert(nonEmptyCategoryList) ){
		return;
	}
tForm.action='updateCategory.htm';

	document.categoryForm.submit();
}


$(document).ready(
		function() {
			
			$("#parentChooser").autocomplete({
				minLength : 1,
				source : function(request, response) {
					searchKey = $("#parentChooser").val();
					var data = searchKey;
					$.ajax({
						url : "getParentCategories.htm",
						type : "POST",
						data : {
							'parentCategory' : data
						},
						success : function(data) {
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
					$("#parentId").val(selectedId);
					return true;
				}
			});
			
		});

</script>
<%@ include file="includes/footer.jsp"%>