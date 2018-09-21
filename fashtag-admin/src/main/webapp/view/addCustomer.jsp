<%@ include file="includes/header.jsp"%>
<div class="breadcrumb">
<a href="dashboard.htm">Home</a> :: <a href="listCustomer.htm">Customer</a>
</div>

<div class="box">
<div class="heading">
<h1><img src="image/customer.png" alt="" />Customer</h1>
		<div class="buttons">
		<c:set var="formUrl" value="javaScript:addFormSubmit();" />
		
		<c:if test="${not empty customer.id}">
		<c:set var="formUrl" value="javaScript:editFormSubmit();" />
		</c:if>
		
			 <a onClick='<c:out value="${formUrl}" />' class="button">Save</a> 
			 <a href="listCustomer.htm" class="button">Cancel</a>
			 
		</div>
	</div>
	<div class="content">
      <div id="htabs" class="htabs"><a class="selected" style="display: inline;" href="#tab-general">General</a>
                <a class="" style="display: inline;" href="#tab-transaction">Transactions</a>
                <a class="" style="display: inline;" href="#tab-ip">IP Addresses</a></div>
      <form action="saveCustomer.htm" method="post" id="customerForm" name="customerForm">
      
		<c:if test="${not empty customer.id}">
		<input type="hidden" name="id" value="${customer.id}" />
		<input type="hidden" name="dateAdded" value="${viewTools.formatDate(customer.dateAdded)}" />
		</c:if>
        <div style="display: block;" id="tab-general">
          <div id="vtabs" class="vtabs"><a class="selected" href="#tab-customer">General</a>
                    
         			<c:set var="addressCount" value="0" />
                    <c:forEach var="listVar" items="${addressColl}" varStatus="counter">
            <a href="#tab-address-${counter.count-1}" id="address-${counter.count-1}">
            Address ${counter.count}&nbsp;<img src="view/image/delete.png" alt="" 
            onclick="$(\'#vtabs a:first\').trigger(\'click\'); $(\'#address-${counter.count-1}').remove(); 
            $(\'#tab-address-${counter.count-1}').remove(); return false;" />
            </a>
          			<c:set var="addressCount" value="${counter.count}" />
            </c:forEach>
            
                            
			<span id="address-add">Add Address&nbsp;<img src="image/add.png" alt="" onclick="addAddress();"></span></div>
          <div style="display: block;" id="tab-customer" class="vtabs-content">
            <table class="form">
              <tbody><tr>
                <td><span class="required">*</span> Full Name:</td>
                <td><input name="fullName" id="fullName" value="${customer.fullName}" type="text">
                  </td>
              </tr>
              <tr>
                <td><span class="required">*</span> Gender:</td>
                <td>
                <select name="gender" id="gender">
                <option value="">--select--</option>
                <option  <c:if test="${customer.gender==0}" > selected</c:if>   value="1">Male</option>
                <option  <c:if test="${customer.gender==1}" > selected</c:if> value="0">Female</option>
                </select>
                </td>
              </tr>
              <tr>
                <td><span class="required">*</span> E-Mail:</td>
                <td><input name="email" id="email" value="${customer.email}" type="text">
                  </td>
              </tr>
              <tr>
                <td><span class="required">*</span> Telephone:</td>
                <td><input name="phone" id="phone" value="${customer.phone}" type="text">
                  </td>
              </tr>
              <tr>
                <td><span class="required">*</span> Password:</td>
                <td><input name="password"  id="password" value="${customer.password}" type="password">
                  </td>
              </tr>
              <tr>
                <td>Newsletter:</td>
                <td><select name="newsletter" id="newsletter">
                      <option value="">--select--</option>
                      <option <c:if test="${customer.newsLetter==true}" > selected</c:if> value="1">Enabled</option>
                    <option  <c:if test="${customer.newsLetter==false}" > selected</c:if> value="0">Disabled</option>
                                      </select></td>
              </tr>
              <tr>
                <td>Active Status:</td>
                <td><select name="activeStatus" id="activeStatus">
                                      <option value="">--select--</option>
                                        <option  <c:if test="${customer.activeStatus==true}" > selected</c:if> value="1">active</option>
                    <option   <c:if test="${customer.activeStatus==false}" > selected</c:if> value="0">inactive</option>
                                      </select></td>
              </tr>
              
             
            </tbody></table>
          </div>
            <input type="hidden" name="addressCount" id="addressCount" value="${addressCount}" />
            
            <c:forEach var="listVar" items="${addressColl}" varStatus="counter">
            
  <div  id="tab-address-${counter.count-1}" class="vtabs-content">
<table class="form">
<tbody>
<tr>
<td>Full Name</td>
<td><input name="fullName${counter.count-1}" type="text" value="${listVar.fullName}"></td>
</tr>
<tr>
<td><span class="required">*</span> Pin Code:</td>
<td><input name="pincode${counter.count-1}" value="${listVar.pinCode}" type="text">
</td>
</tr>
<tr>
<td><span class="required">*</span> Locality:</td>
<td><input name="locality${counter.count-1}" value="${listVar.locality}" type="text">
</td>
</tr>
<tr>
<td><span class="required">*</span> State:</td>
<td><select name="state${counter.count-1}" id="" ><option value="0">--select--</option>

<c:forEach var="item" items="${stateList}"  varStatus="counterSpec">
<option <c:if test="${item.id==listVar.state.id}" > selected</c:if> value="${item.id}">${item.name}</option>
</c:forEach>

</select></td>
</tr>
<tr>
<td><span class="required">*</span> Address:</td>
<td><textarea name="address${counter.count-1}" id="address${counter.count-1}">${listVar.address}</textarea>
</td>
</tr>
<tr>
<td><span class="required">*</span> Mobile:</td>
<td><input name="mobile${counter.count-1}" value="${listVar.mobile}" type="text">
</td>
</tr>
<tr>
<td>Is Default</td>
<td><input type="checkbox" name="isDefault${counter.count-1}" value="true" <c:if test="${listVar.isDefault==true}">checked</c:if> /></td>
</tr>
</tbody></table>
</div>  










            </c:forEach>
            
            </div>
                            
                            
                            
                <div style="display: none;" id="tab-transaction">
          
        </div>
        
        
                <div style="display: none;" id="tab-ip">
          <table class="list">
            <thead>
              <tr>
                <td class="left">IP</td>
                <td class="left">Date Added</td>
                <td class="right">Action</td>
              </tr>
            </thead>
            <tbody>
                                     <c:forEach var="listVar" items="${ipColl}" varStatus="counter">
                                      <tr>
                <td class="left">${listVar.ipAddress}</td>
                <td class="left">${listVar.dateAdded}</td>
                <td class="right"><b>[</b> <a id="" onclick="addBlacklist('');">Add Blacklist</a> <b>]</b></td>
              </tr>
                         </c:forEach>
                                        </tbody>
          </table>
        </div>
      </form>
    </div>
</div>

</div>


<script type="text/javascript"><!--
$('.htabs a').tabs();
$('.vtabs a').tabs();
//--></script> 
<script type="text/javascript"><!--

function addAddress() {
var address_row = $("#addressCount").val();
var html="";
html+='<div  id="tab-address-'+address_row+'" class="vtabs-content" style="display: none;">';
            html+='<table class="form">';
              html+='<tbody>';
              html+='<tr>';
                html+='<td>Full Name</td>';
                html+='<td><input name="fullName'+address_row+'" type="text"></td>';
              html+='</tr>';
              html+='<tr>';
                html+='<td><span class="required">*</span> Pin Code:</td>';
                html+='<td><input name="pincode'+address_row+'" value="" type="text">';
                  html+='</td>';
              html+='</tr>';
               html+='<tr>';
                html+='<td><span class="required">*</span> Locality:</td>';
                html+='<td><input name="locality'+address_row+'" value="" type="text">';
                  html+='</td>';
              html+='</tr>';
              html+='<tr>';
                html+='<td><span class="required">*</span> State:</td>';
html+='<td><select name="state'+address_row+'" id="" ><option value="0">--select--</option>';
<c:forEach var="item" items="${stateList}"  varStatus="counterSpec">
html+='<option value="${item.id}">${item.name}</option>';
</c:forEach>

html+='</select></td>';
              html+='</tr>';
              html+='<tr>';
                html+='<td><span class="required">*</span> Address:</td>';
                html+='<td><textarea name="address'+address_row+'" id="address'+address_row+'"></textarea>';
                  html+='</td>';
              html+='</tr>';
               html+='<tr>';
html+='<td><span class="required">*</span> Mobile:</td>';
                html+='<td><input name="mobile'+address_row+'" value="" type="text">';
                  html+='</td>';
              html+='</tr>';
              
              html+='<tr>';
              html+='<td>Is Default</td>';
              html+='<td><input type="checkbox" name="isDefault'+address_row+'" value="true" /></td>';
            html+='</tr>';
            
            html+='</tbody></table>';
          html+='</div>';          
 $('#tab-general').append(html);
$('#address-add').before('<a href="#tab-address-' + address_row + '" id="address-' + address_row + '">Address ' + parseInt(address_row)+parseInt(1) + '&nbsp;<img src="view/image/delete.png" alt="" onclick="$(\'#vtabs a:first\').trigger(\'click\'); $(\'#address-' + address_row + '\').remove(); $(\'#tab-address-' + address_row + '\').remove(); return false;" /></a>');
$('.vtabs a').tabs();
 $('#address-' + address_row).trigger('click');
 address_row++;
 $("#addressCount").attr("value",address_row);
}
//--></script> 




<script language="javaScript">
var nonEmptyList = [ 
							['Full Name','fullName'],
							['Gender','gender'],
							['Email Id','email'],
							['Password','password'],
							['News Letter','newsletter'],
							['Active Status','activeStatus']
						    ];
						    
function addFormSubmit(){
	var tForm = document.customerForm;
	if(showRequiredAlert(nonEmptyList) ){
		return;
	}
	tForm.action='saveCustomer.htm';
    tForm.submit();
}


function editFormSubmit(){
	var tForm = document.customerForm;
	if(showRequiredAlert(nonEmptyList) ){
		return;
	}
tForm.action='updateCustomer.htm';

	tForm.submit();
}


</script>
<%@ include file="includes/footer.jsp"%>