/*
 * File				: CommonUtils.js
 * Description	: Common utility js functions
 */
 
 /**
  * Functions are...
  *	1) isEmpty()
  * 	2) trim()
  * 	3) eMailChk()
  * 	4) isValidDateRange()
  * 	5) compareDates()
  * 	6) isValidDateFormat()
  * 	7) isValidMonthDigit()
  * 	8) isDayDigitValidInMonthOfYear()
  * 	9) getDaysInMonth()
  *    10) isLeapYear()
  *	   11) isContainSpecialChars()
  *	   12) arraysToCSV()
  *    13) numbersonly()	
  *    14) printPage()
  *    15) autoSizeIFrame()	
  *	   16) isValidPhoneNumber()
  *	   17) tabsOnly()
  *	   18) formatNumber()
  */
 
/**
 * function for  checking empty string
 * @ param aTextField - a String variable
 * return boolean
 */

function isEmpty(aTextField) {
	if(aTextField == null){
		return false;
	}
	
	var re = /\s/g; //Match any white space including space, tab, form-feed, etc.
	var str = aTextField.replace(re, "");
	if (str.length == 0) {
		return true;
	} else {
		return false;
	}
}
// End of isEmpty()

/**
 * function for  trim leading and trailing blank spaces from a string variable
 * @ param aTextField - a String variable
 * return trimmed String 
 */
function trim(inputString) {
   var retValue = inputString;
   var ch = retValue.substring(0, 1);
   while (ch == " ") {    // Check for spaces at the beginning of the string
	  retValue = retValue.substring(1, retValue.length);
	  ch = retValue.substring(0, 1);
   }
   ch = retValue.substring(retValue.length-1, retValue.length);
   while (ch == " ") {   // Check for spaces at the end of the string
	  retValue = retValue.substring(0, retValue.length-1);
	  ch = retValue.substring(retValue.length-1, retValue.length);
   }
   
   return retValue; // Return the trimmed string back
} // Ends the "trim" function


/** 
 *  Function eMailChk is used to verify if the given value is a possible valid email address.
 *  This function  makes sure the email address has one (@), atleast one (.).
 *   It also makes sure that there are no spaces, extra '@'s, ( ' )s, ( " )s
 *   or a (.) just before or after the @. 
 *   It also makes sure that there is atleast one (.) after the @.
 */

function eMailChk(str) {

		var at="@";
		var dot=".";
		var quot = "\'" ;
		var dblQuote = "\"" ;
		var lat=str.indexOf(at);
		var lstr=str.length;
		var ldot=str.indexOf(dot);
		// check for empty string
		if(isEmpty(str)) {
			return false;
		}
		if (str.length < 3)
	 	{
	   		return false;
	 	}
		// check for @ symbol 
		if (str.indexOf(at)==-1 || str.indexOf(at)==0 || str.indexOf(at)==lstr){
		   return false;
		}
		//	check for (.) symbol 
		if (str.indexOf(dot)==-1 || str.indexOf(dot)==0 || str.indexOf(dot)==lstr){
			return false;
		}

		 if (str.indexOf(at,(lat+1))!=-1){
			return false;
		 }

		 if (str.substring(lat-1,lat)==dot || str.substring(lat+1,lat+2)==dot){
			return false;
		 }

		 if (str.indexOf(dot,(lat+2))==-1){
			return false;
		 }
		
		 if (str.indexOf(" ")!=-1){
			return false;
		 }
		if (str.indexOf(quot)>-1 || str.indexOf(dblQuote)>-1){
			return false;
		}

		 return true					
	}
	
//global date format "dd/mm/yyyy"
var gDateRegEx = /^(\d\d{1,2})\/(\d\d{1,2})\/(\d\d\d\d)$/;

var dtErMsg = "Please input a valid date in \'dd/mm/yyyy\' format";


/**
 * This method will return true if the date range is valid
 */
 
 function isValidDateRange( aStartDateFld,aEndDateFld) {
 	var alertMsg= "\'End date\' cannot be earlier than \'Start date\'" ; 	
	return(isValidDateRangeWithAlert( aStartDateFld,aEndDateFld,alertMsg ));
 }
 
function isValidDateRangeWithAlert( aStartDateFld,aEndDateFld,alertMsg ) {		
	
	if ( ( isEmpty( aStartDateFld.value ) ) && ( isEmpty( aEndDateFld.value ) ) ){	
	return true;
	}
	
	if( ! isEmpty(aStartDateFld.value ) ){
		var tValidStartFlag = isValidDateFormat( aStartDateFld.value );
		if ( !tValidStartFlag ) {
			aStartDateFld.focus();
			aStartDateFld.value='';
			return false;
		}
	}
	if( ! isEmpty(aEndDateFld.value ) ){
		var tValidEndFlag = isValidDateFormat( aEndDateFld.value );
		if ( !tValidEndFlag ) {
			aEndDateFld.focus();
			aEndDateFld.value='';
			return false;
		} 
		}
	if ( (! isEmpty( aStartDateFld.value ) ) && ( ! isEmpty(aEndDateFld.value ) ) ){	
		//start date must not be later than end date	
		var tStartDate = new Date(convertDateIndianToUs(aStartDateFld.value));
		var tEndDate = new Date(convertDateIndianToUs(aEndDateFld.value));
		var tNow = new Date();
	    
		//check whether the end date < start date
		var tDateDiff = compareDates( tEndDate, tStartDate );
	
		if ( tDateDiff < 0 ) {
	
			alert( alertMsg );
			aEndDateFld.focus();
			return false;
		} 
	  }
	return true;
}

/**
 * compares first date with last date
 * @param date  first date
 * @param date  last date
 * @return int  0 if equal
 *              < 0 if ( first < second )
 *              > 0 if ( first > second )
 *              null if any one of the dates is null
 */
 function compareDates( aFirstDate, aLastDate ) {

		if ( aFirstDate == null || aLastDate == null ) {
			return null;
		}
		var tDiff = aFirstDate - aLastDate;
		return tDiff;
 }
 
 function isFutureDate(aDateValue){ 
 	var today = new Date();  	
 	if(compareDates(today,new Date(convertDateIndianToUs(aDateValue))) >= 0){
		return false;
 	}
 	return true;
 }
 
  function isFutureDateWithTodayValue(todayValue, aDateValue) {
 	if(compareDates(new Date(convertDateIndianToUs(todayValue)), new Date(convertDateIndianToUs(aDateValue))) >= 0){
		return false;
 	}
 	return true;
  }
 
 /**
 *   Use this method if the date format is known as mm/dd/yyyy
 */
  function isDateFuture(aDateValue){ 
 	var today = new Date();  	
 	if(compareDates(today,new Date(aDateValue)) >= 0){
		return false;
 	}
 	return true;
 }
 
/**
 * @param string a date value  say 21/01/1999
 * @return boolean true if a valid date format else false
 */
function isValidDateFormat( aDateValue ) {
 
	//alert( 'input date = ' + aDateValue );
     
	if ( aDateValue == null ) {
		alert( dtErMsg );
		return false;
	}
     
	var tDateFlds = aDateValue.match( gDateRegEx );    
     
	if ( tDateFlds == null ) {
		alert( dtErMsg );
		return false;            
	}
     
	var tMonth = tDateFlds[2];
	if ( tMonth == null ) {
		alert( 'Invalid Month : '+dtErMsg );
		return false;
	} 
     
	var tDay = tDateFlds[1];
	if ( tDay == null ) {
		alert( 'Invalid Day: '+dtErMsg );
		return false;
	}
     
	var tYear = tDateFlds[3];
	if ( tYear == null ) {
		alert( 'Invalid Year: '+dtErMsg );
		return false;
	}
     
	//alert( 'date flds  mm, dd, yyyy: ' + tMonth + ', ' + tDay + ', ' + tYear );
     
	if ( ! isValidMonthDigit( tMonth ) ) {
		alert( 'Invalid Month \'' + tMonth + '\' ' +
			': Please input a valid month in the range 1..12 ' );
		return false;
	}
     
	if ( ! isDayDigitValidInMonthOfYear( tDay, tMonth, tYear ) ) {
         
		var tDayCount = getDaysInMonth( tMonth, tYear );
		alert( 'Invalid Day \'' + tDay + '\' ' +
			': Please input a valid day in the range 1..' + tDayCount );
		return false;
	}
	return true;   
}
 
/**
 * accecpts month as 2 digit value
 */
function isValidMonthDigit( aMonth ) {
     
	if ( aMonth == null ) {
		return false;
	}
     
	if ( aMonth <= 0 || aMonth > 12 ) {
		//must be 1..12
		return false;
	}
	return true;
}
 
/**
 * checks whether the day is valid for the given month and year
 */
function isDayDigitValidInMonthOfYear( aDay, aMonth, aYear ) {
     
	if ( aDay == null || aMonth == null || aYear == null ) {
		return false;
	}
	var tDayCount = getDaysInMonth( aMonth, aYear );
	if ( tDayCount == null ) {
		return false;
	}
	if ( aDay <= 0 || aDay > tDayCount ) {
		return false;
	}
	return true;
}
 
/**
 *  GET NUMBER OF DAYS IN MONTH
 */
function getDaysInMonth( aMonth, aYear )  {
     
	var tDays;
     
	//convert to string 
	var tSrtMon = '' + aMonth;
     
	switch ( tSrtMon ) {
		case '1'  : // JAN
		case '01' : // JAN   should be valid if month is '1' or '01'
		case '3' : // MAR
		case '03' : // MAR
		case '5' : // MAY
		case '05' : // MAY
		case '7' : // JUL
		case '07' : // JUL
		case '8' : // AUG
		case '08' : // AUG
		case '10': // OCT
		case '12': // DEC
			tDays = 31;
			break;
         
		case '4' : // APR
		case '04' : // APR
		case '6' : // JUN
		case '06' : // JUN
		case '9' : // SEP
		case '09' : // SEP
		case '11': // NOV
			tDays = 30;
			break;
 
		case '2' : // FEB
		case '02' : // FEB
			tDays = 28;
			if ( isLeapYear( aYear ) ) {
				tDays = 29;
			}
			break;
             
		 default:
			tDays = -1;
			break;
	}//end of switch()
     
	return (tDays);   
}
 
/**
 * CHECK TO SEE IF YEAR IS A LEAP YEAR
 */
function isLeapYear ( aYear ) {
  
	if ( ( (aYear % 4) == 0 ) && ( ( aYear % 100 )!= 0 ) || ( ( aYear % 400 ) == 0 ) ) {
		return true;
	} 
	return false;    
}
 
/**
 * compares first date with last date
 * @param date  first date
 * @param date  last date
 * @return int  0 if equal
 *              < 0 if ( first < second )
 *              > 0 if ( first > second )
 *              null if any one of the dates is null
 */
 function compareDates( aFirstDate, aLastDate ) {
     
	if ( aFirstDate == null || aLastDate == null ) {
		return null;
	}
	var tDiff = aFirstDate - aLastDate;
	//alert( 'F # L # diff = ' + aFirstDate + ' # ' + aLastDate + ' # ' + tDiff );
	return tDiff;
 }

 /**
 * @param string a date value  say 01/21/1999
 * @return boolean true if a valid date format else false
 */
function isValidDateFormatNoAlert( aDateValue ) {
      
	if ( aDateValue == null ) {
		return false;
	}
  
	var tDateFlds = aDateValue.match( gDateRegEx );      
	if ( tDateFlds == null ) {
		return false;            
	}     
	var tMonth = tDateFlds[2];
	if ( tMonth == null ) {
		return false;
	}    
	var tDay = tDateFlds[1];
	if ( tDay == null ) {
		return false;
	}     
	var tYear = tDateFlds[3];
	if ( tYear == null ) {
		return false;
	}     
	if ( ! isValidMonthDigit( tMonth ) ) {
		return false;
	}     
	if ( ! isDayDigitValidInMonthOfYear( tDay, tMonth, tYear ) ) {        
		var tDayCount = getDaysInMonth( tMonth, tYear );
		return false;
	}
	return true;   
}

function showSelectWindow(action, field, headerKey, headerValue,extraParam) {
	var name = field.id;
	var value=field.value;
	var url = action + '?parentFieldName=' + name + '&parentFieldValue=' + value + '&headerKey=' + headerKey + '&headerValue=' + headerValue +extraParam;	
	window.open(url,'EducationERP','left=20,top=20,width=346,height=410,toolbar=no, location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=yes');
}

	
	function isInteger(fieldVal){
		var re = /\s/g;
		fieldVal = fieldVal.replace(re, "*");
		fieldVal = fieldVal.replace(".", "*");
		fieldVal = fieldVal.replace("x", "*");
		fieldVal = fieldVal.replace("X", "*");
		if(isContainSpecialChars(fieldVal,"*")){
			return false;
		}		
		if(isNaN(fieldVal)){
			return false;
		}
		var res = parseInt(fieldVal);
		if(isNaN(res)){
			return false;
		}
		if(res < 0){
			return false;
		}
		return true;
	}
	
	function isFloat(fieldVal){
		var re = /\s/g;
		fieldVal = fieldVal.replace(re, "*");
		fieldVal = fieldVal.replace("x", "*");
		fieldVal = fieldVal.replace("X", "*");
		if(isContainSpecialChars(fieldVal,"*")){
			return false;
		}
		fieldVal = trim(fieldVal);
		if(isNaN(fieldVal)){
			return false;
		}
		var res = parseFloat(fieldVal);
		if(isNaN(res)){
			return false;
		}
		return true;
	}
	
	function isContainSpecialChars(checkString){
		var iChars = "!@#$%^&*()+=-[]\\\';,./{}|\":<>? ";
		for (var i = 0; i < checkString.length; i++) {
			if (iChars.indexOf(checkString.charAt(i)) != -1) {			
				return true;
			}
		 }   	
		return false;
	}
	
	function isContainSpecialChars(checkString,pattern){
		var iChars = pattern;
		for (var i = 0; i < checkString.length; i++) {
			if (iChars.indexOf(checkString.charAt(i)) != -1) {			
				return true;
			}
		 }   	
		return false;
	}
	
	function showRequiredAlert(nonEmptyList){
		for(i=0; i < nonEmptyList.length;i++){
			var desc = nonEmptyList[i][0];
			
			var element = nonEmptyList[i][1];
			
			var formElement = document.getElementById(trim(element));
			if(isEmpty(formElement.value)){				
				alert(desc+" cannot be Empty!");
				formElement.focus();
				return true;
			}			
		}
		return false;
	}
	
	function showNonEmptySelectAlert(nonEmptyList){		
		for(i=0; i < nonEmptyList.length;i++){	
			var desc = nonEmptyList[i][0];
			var element = nonEmptyList[i][1];
			var formElement = document.getElementById(trim(element));
			if(isEmpty(formElement.value) || (formElement.value == "-1") ){				
				alert("Please select "+desc+"!");
				formElement.focus();
				return true;
			}			
		}
		return false;
	}
	
  function addZero(vNumber){ 
    return ((vNumber < 10) ? "0" : "") + vNumber 
  } 
        
  function formatDate(vDate, vFormat){ 
    var vDay              = addZero(vDate.getDate()); 
    var vMonth            = addZero(vDate.getMonth()+1); 
    var vYearLong         = addZero(vDate.getFullYear()); 
    var vYearShort        = addZero(vDate.getFullYear().toString().substring(3,4)); 
    var vYear             = (vFormat.indexOf("yyyy")>-1?vYearLong:vYearShort) 
    var vHour             = addZero(vDate.getHours()); 
    var vMinute           = addZero(vDate.getMinutes()); 
    var vSecond           = addZero(vDate.getSeconds()); 
    var vDateString       = vFormat.replace(/dd/g, vDay).replace(/MM/g, vMonth).replace(/y{1,4}/g, vYear) 
    vDateString           = vDateString.replace(/hh/g, vHour).replace(/mm/g, vMinute).replace(/ss/g, vSecond) 
    return vDateString 
  } 
  
	function convertDateUsToIndian(mmddyyyy){
		var mm = mmddyyyy.substring(0,2);
		var dd = mmddyyyy.substring(3,5);
		var yy = mmddyyyy.substring(6,10);		
		var ddmmyyyy = dd + '/' + mm + '/' + yy;		
		return(ddmmyyyy);		
	}
	function convertDateIndianToUs(ddmmyyyy){
		var dd = ddmmyyyy.substring(0,2);
		var mm = ddmmyyyy.substring(3,5);
		var yy = ddmmyyyy.substring(6,10);		
		var mmddyyyy = mm + '/' + dd + '/' + yy;		
		return(mmddyyyy);		
	}
	
	function arraysToCSV(array1){
		var csv="";
		var com=",";
		for (var i=0; i<array1.length;i++){
			csv =csv+com+array1[i];
		}
		return csv;
	}
	
	function formatDecimal(amount)
	{		
		var i = 0;
		if(isNaN(amount)) {
		 	i = 0.00; 
		 }else{
		 	i=parseFloat(amount);
		 }

		return i.toFixed(2);
	/*	if(isNaN(i)) {
		 i = 0.00; 
		 }		
		i = Math.abs(i);
		i = parseInt((i + .005) * 100);
		i = i / 100;
		s = new String(i);
		if(s.indexOf('.') < 0) { s += '.00'; }
		if(s.indexOf('.') == (s.length - 2)) { s += '0'; }		
		return s; */
	}
	
	 /**
   	   * Resticting fields to number
   	   * Sample: <input type="text" name="txtNumeric"  onkeyPress="return  numbersonly(this,event)"> 
   	   * eg: <@macro.formRowTextSize name="hallSchedule.patternRow" value= "#{hallSchedule.patternRow}"
   	   *	 		size="3" extra="maxlength='3' onkeyPress='return  numbersonly(this,event)'"/>
   	   * If it is a time field,put the 3rd parameter as 1 ,then "." will be automatically appended to the 
   	   * field after 2 chars eg:onkeyPress='return  numbersonly(this,event,1)'
   	   *
   	   **/
   function numbersonly(myfield, e, isTime,dec){
   		
		var key;
		var keychar;
		
		if (window.event)
		   key = window.event.keyCode;
		else if (e)
		   key = e.which;
		else
		   return true;
		keychar = String.fromCharCode(key);
		
		// control keys
		if ((key==null) || (key==0) || (key==8) || 
		    (key==9) || (key==13) || (key==27) )
		   return true;
		
		// numbers
		else if ((isTime!=1)&&(("0123456789.").indexOf(keychar) > -1)){
			//blocking multiple decimal points
			if(keychar == "." && myfield.value.indexOf(".") > -1) {
				return false;
			}
		   return true;
		}
		else if ((isTime==1)&&(("0123456789").indexOf(keychar) > -1)){
			if((myfield.value.length==2)){
				myfield.value=myfield.value+".";
			}
		   return true;
		}
			
		// decimal point jump
		else if (dec && (keychar == "."))
		   {
		   myfield.form.elements[dec].focus();
		   return false;
		   }
		else
		   return false;
	}
	
	function printPage()
	{ 
	  printReportPage(true,"");
	}
	
	function printReportPage(showHeader,subHeader)
	{ 
	  var disp_setting="toolbar=yes,location=no,directories=yes,menubar=yes,"; 
	      disp_setting+="scrollbars=yes,width=650, height=600, left=100, top=25"; 
	   var content_vlue = document.getElementById("print_content").innerHTML; 	  
	   var docprint=window.open("","",disp_setting); 
	   docprint.document.open(); 
	   docprint.document.write('<html><head><title>.:: Technasuite ::.</title>'); 	   
	   docprint.document.write("<link rel='stylesheet' type='text/css' href='css/app.css'>");
	   docprint.document.write("<link rel='stylesheet' type='text/css' href='css/print.css'>");
	   docprint.document.write('</head><body onLoad="self.print()"><center>');
	 if(showHeader==true){
	   docprint.document.write("<table border='0' width='100%' align='center'><tr>");
	   docprint.document.write("<td rowspan='2' width='50' align='right'><img src='images/institution_logo_old.jpg' width='50' height='50' border='0'/></td>");
	   docprint.document.write("<td class='pagehead'>Technasuite</td></tr>");
	   docprint.document.write("<td class='pagehead'></td></tr>");
	   docprint.document.write("<tr><td/><td class='pageSubhead' >");	   
	   docprint.document.write(subHeader);
	   docprint.document.write("</td></tr>");
	   docprint.document.write("<tr><td colspan='2'><hr></td></tr></table>");
	  }
	  	content_vlue = skipDocumentWriteLines(content_vlue);
	   docprint.document.write(content_vlue);	   
	   docprint.document.write('</center></body></html>'); 
	   docprint.document.close(); 
	   docprint.focus(); 
	}
	
	//author: Jijo Lawrence
	//this method is used while page printing. This method is used for skipping the 'document.write' lines, which normally cause duplicate printing of elements
	function skipDocumentWriteLines(innerText){
		try{
			if(innerText!=undefined && innerText!=''){
				return replaceAll(innerText, 'document.write[^\w*|\w*]*;', '');
			}
		}catch(err){
		}
		return innerText;
	}
	
	function autoSizeIFrame(frameId){		
    	if (!window.opera && !document.mimeType && document.all){
			document.getElementById(frameId).style.height=this.document.body.offsetHeight+"px";            
        } else{
            document.getElementById(frameId).style.height=this.document.body.scrollHeight+"px"
        }
    } 
 
 	function alphaNumericOnly(o){
		  o.value=o.value.toUpperCase().replace(/([^0-9A-Z])/g,"");
	}
	
 	function disableKey(event) {
	 	if (!event) event = window.event;
	  	if (!event) return;
	  	var keyCode = event.keyCode ? event.keyCode : event.charCode;
	  	if (keyCode == 116) {
	   		window.status = "F5 key detected! Attempting to disabling default response.";
	   		window.setTimeout("window.status='';", 2000);
	 		if (event.preventDefault) event.preventDefault();
			if (document.all && window.event && !event.preventDefault) {
	     		event.cancelBubble = true;
	     		event.returnValue = false;
	     		event.keyCode = 0;
	   		}
			return false;
	  	}
	}
	
	function setEventListener(eventListener) {
  		if (document.addEventListener) document.addEventListener('keypress', eventListener, true);
  		else if (document.attachEvent) document.attachEvent('onkeydown', eventListener);
  		else document.onkeydown = eventListener;
  	}
  	
 	function unsetEventListener(eventListener) {
 		if (document.removeEventListener) document.removeEventListener('keypress', eventListener, true);
  		else if (document.detachEvent) document.detachEvent('onkeydown', eventListener);
  		else document.onkeydown = null;
	}
	
	function isValidPhoneNumber(phoneNumber){
		var alphabets = "abcdefghijklmnpqstuvwxyz";
		var specialChars = ".~`!#$%^&*=[]\\';{}|\":<>?";
		if(isContainSpecialChars(phoneNumber,specialChars)||isContainSpecialChars(phoneNumber,alphabets)){			
			return false;
		}
		return true;
	}
	
	/*Method to Recognize A Tab Key Press Event */
   
	function tabsOnly(event) {
		if(event.keyCode == 9) {
			return true;
		} else {
			return false;
		}
		
	}
	

	function formatNumber(num){
		return formatNumber(num,2);
	}
	
	/** 
	 *  Method to Format Numbers.
	 * 	Parameters : num - Number which is to be formatted
	 *				 decimalPlaces - No of decimal places of result.				
	 * 	Usage : formatNumber(25.789, 1) -> 25.8
	 *		  : formatNumber(245, 2) -> 245.00		
 	 */
	function formatNumber(num,decimalPlaces){
		num=String(num)
		if (num.indexOf('.') == -1) {
			num += '.00';
		}
		var deci= num.split('\.')[1].length;
		var c= Number(num);
		var expo= (Math.pow(10,deci))
		var result=((Math.round(c*expo)/expo).toFixed(2));
		return result;
	}
	
	//return 1 if IE
	function checkBrowser(){
		var browser=navigator.appName;
		var b_version=navigator.appVersion;
		var version=parseFloat(b_version);
		if (browser=="Microsoft Internet Explorer"){
			return 1;
		}else{
			return 0;
		}
	}
	
	//author : jijo lawrence
	function replaceAll(text, strA, strB){
	    return text.replace( new RegExp(strA,"g"), strB );    
	}
	
	function numberToString(num){
		return replaceAll(num, ',', '');
	}	
	
	function showNumericCannotZeroAlert(numericFields){
		
		for(i=0; i < numericFields.length;i++){	
			var desc = numericFields[i][0];
			var element = numericFields[i][1];
			var formElement = document.getElementById(element);
			if(isNaN(formElement.value) || isEmpty(formElement.value) || parseFloat(formElement.value) <= 0 ){				
				alert("Please enter valid " + desc);
				formElement.value="";
				formElement.focus();
				return true;
			}			
		}
		return false;
	}