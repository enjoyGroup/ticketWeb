function isBlank(objValue){
    if(objValue != null && objValue != undefined && objValue != ''){
        return false;
    }else{
        return true;
    }
}

$(document).ready(function(){
	 $(".numberOnly").keypress(function(event){
	        return onKeyPressNumber(event);
	    });
	    $(".numberOnly").keydown(function(event){
	        return onKeyDownNumber(event);
	    });
	    $(".numberOnly").keyup(function(event){
	        return onKeyUpNumber(event);
	    });
	    
	    $(".moneyOnly").keypress(function(event){
	        return onKeyUpMoney(event);
	    });
	    $(".moneyOnly").keydown(function(event){
	        return onKeyDownMoney(event);
	    });
	    $(".moneyOnly").keyup(function(event){
	        return onKeyUpMoney(event);
	    });
});

//Example gp_replaceComma(78,500.00);
function gp_replaceComma(av_val){
	
	var lv_ret = av_val;
	
	if(av_val!=null && av_val!=""){
		lv_ret = av_val.replace(/,/g,"");
	}

	return lv_ret;
}

function gp_format(av_obj, decimals){
	return gp_number_format(av_obj, decimals, '.', ',');
}

function gp_number(av_obj){
	return gp_number_format(av_obj, 0, '.', '');
}

//Example gp_number_format(av_obj, 2, ".", ",");
function gp_number_format (av_obj, decimals, dec_point, thousands_sep) {
    
    var exponent    = "";
    var numberstr   = null;
    var eindex      = null;
    var temp        = null;
    var sign        = null;
    var integer     = null;
    var fractional  = null;
    var number      = av_obj.value.replace(/,/g,"");
    var msgVal      = "";

    if(gp_trim(number)==""){
        return true;
    }
    
    numberstr   = number.toString ();
    eindex      = numberstr.indexOf ("e");
    
    if (eindex > -1) {
        exponent = numberstr.substring (eindex);
        number = parseFloat (numberstr.substring (0, eindex));
    }
       
    if (decimals != null) {
        temp    = Math.pow (10, decimals);
        number  = Math.round (number * temp) / temp;
    }
    
    sign        = number < 0 ? "-" : "";
    integer     = (number > 0 ? Math.floor (number) : Math.abs (Math.ceil (number))).toString ();
    
    fractional  = number.toString ().substring (integer.length + sign.length);
    dec_point   = dec_point != null ? dec_point : ".";
    fractional  = decimals != null && decimals > 0 || fractional.length > 1 ? (dec_point + fractional.substring (1)) : "";
    
    if (decimals != null && decimals > 0) {
        for (i = fractional.length - 1, z = decimals; i < z; ++i) {
            fractional += "0";
        }
    }
    
    thousands_sep = (thousands_sep != dec_point || fractional.length == 0) ? thousands_sep : null;
    if (thousands_sep != null && thousands_sep != "") {
        for (i = integer.length - 3; i > 0; i -= 3){
            integer = integer.substring (0 , i) + thousands_sep + integer.substring (i);
        }
    }
    
    msgVal = sign + integer + fractional + exponent;
    if(msgVal.indexOf("NaN") > -1){
        return false;
    }
    
    av_obj.value = msgVal;
    return true;
    
    //av_obj.value = sign + integer + fractional + exponent;
    //return sign + integer + fractional + exponent;
}

function gp_format_str(av_val, decimals){
	return gp_number_format_str(av_val, decimals, '.', ',');
}

function gp_number_format_str (av_val, decimals, dec_point, thousands_sep) {
    
    var exponent    = "";
    var numberstr   = null;
    var eindex      = null;
    var temp        = null;
    var sign        = null;
    var integer     = null;
    var fractional  = null;
    var number      = av_val.replace(/,/g,"");
    var msgVal      = "";

    if(gp_trim(number)==""){
        return true;
    }
    
    numberstr   = number.toString ();
    eindex      = numberstr.indexOf ("e");
    
    if (eindex > -1) {
        exponent = numberstr.substring (eindex);
        number = parseFloat (numberstr.substring (0, eindex));
    }
       
    if (decimals != null) {
        temp    = Math.pow (10, decimals);
        number  = Math.round (number * temp) / temp;
    }
    
    sign        = number < 0 ? "-" : "";
    integer     = (number > 0 ? Math.floor (number) : Math.abs (Math.ceil (number))).toString ();
    
    fractional  = number.toString ().substring (integer.length + sign.length);
    dec_point   = dec_point != null ? dec_point : ".";
    fractional  = decimals != null && decimals > 0 || fractional.length > 1 ? (dec_point + fractional.substring (1)) : "";
    
    if (decimals != null && decimals > 0) {
        for (i = fractional.length - 1, z = decimals; i < z; ++i) {
            fractional += "0";
        }
    }
    
    thousands_sep = (thousands_sep != dec_point || fractional.length == 0) ? thousands_sep : null;
    if (thousands_sep != null && thousands_sep != "") {
        for (i = integer.length - 3; i > 0; i -= 3){
            integer = integer.substring (0 , i) + thousands_sep + integer.substring (i);
        }
    }
    
    msgVal = sign + integer + fractional + exponent;
    if(msgVal.indexOf("NaN") > -1){
        return false;
    }
    
    return msgVal;
}

function gp_trim(str) {
    return str.replace(/^\s+|\s+$/g,"");
}

function gp_validNumberKey(e){
//    var keyFormat = /^\d/g;
    var keyFormat = /[^\d\.]/g;
    var charVal = String.fromCharCode(e.keyCode);
    
    return !keyFormat.test(charVal);
}

function gp_validNumberKeyMinus(e){
//    var keyFormat = /^\d/g;
    var keyFormat = /[^\d\.\-]/g;
    var charVal = String.fromCharCode(e.keyCode);
    
    return !keyFormat.test(charVal);
}

function gp_clearMsg(ao_obj){
    ao_obj.value = "";
}

function gp_sanitizeURLString(av_val){
    var la_symbol   = ["%" , "<" , ">" , "#" , "{" , "}" , "|" , "\\" , "^" , "~" , "[" , "]" , "`" , ";" , "/" , "?" , ":" , "@" , "=" , "&" , "$"];
    var la_replace  = ["%25", "%3C", "%3E", "%23", "%7B", "%7D", "%7C", "%5C", "%5E", "%7E", "%5B", "%5D", "%60", "%3B", "%2F", "%3F", "%3A", "%40", "%3D", "%26", "%24"];
    var lv_return   = "";
    var lv_char		= null;

    loop1:for(var i=0;i<av_val.length;i++){
        lv_char = av_val.substr(i, 1);
        loop2:for(var j=0;j<la_symbol.length;j++){
            if(lv_char.indexOf(la_symbol[j]) > -1){
                lv_char = lv_char.split(la_symbol[j]).join(la_replace[j]);
                break loop2;
            }
        }
        lv_return = lv_return + lv_char;
    }
    
    return lv_return;
}

function gp_rowTableIndex(ao_obj){
    var lv_index            = 0;
    var lv_tagName          = "";
    var lo_obj              = ao_obj;
    
    lv_tagName  = lo_obj.tagName.toUpperCase();
    while (lv_tagName != "TR") {
        lo_obj      = lo_obj.parentNode;
        lv_tagName  = lo_obj.tagName.toUpperCase();
    }
    
    lv_index = lo_obj.rowIndex;
    
    return lv_index;
}

function gp_rowTableObj(ao_obj){
    var lv_tagName          = "";
    var lo_obj              = ao_obj;
    
    lv_tagName  = lo_obj.tagName.toUpperCase();
    while (lv_tagName != "TR") {
        lo_obj      = lo_obj.parentNode;
        lv_tagName  = lo_obj.tagName.toUpperCase();
    }
    
    return lo_obj;
}

//av_val ==> dd/MM/yyyy
function gp_toDate(av_val){
    var dateArray   = null;;
    var d           = null;
    
    try{
        dateArray   = av_val.split("/");
        d           = new Date(dateArray[2], dateArray[1], dateArray[0], 0, 0, 0, 0);
    }catch(e){
        d = null;
    }
    
    return d;
}

function gp_checkAmtOnly(ao_obj){
	try{
		if(gp_trim(ao_obj.value)==""){
			ao_obj.value = "0.00";
		}
		
		if(gp_format(ao_obj, 2)==false){
			alert("กรุณาระบุตัวเลขเท่านั้น");
			ao_obj.value = "0.00";
			//ao_obj.focus().select();
			eval("$('#" + ao_obj.id + "').focus().select();");
			return false;
		}
		
		if(gp_replaceComma(ao_obj.value).length > 15){
			alert("ระบุได้สูงสุด 14 ตัวอักษร");
			//ao_obj.select();
			eval("$('#" + ao_obj.id + "').focus().select();");
			return false;
		}
		
		return true;
		
	}catch(e){
		alert("gp_checkAmtOnly :: " + e);
	}
}

function gp_progressBarOn(){
    
    $(".FreezeScreen").fadeIn();
}

function gp_progressBarOff(){
	
	$(".FreezeScreen").fadeOut("slow");
}

function gp_checkDate(ao_obj){
    var allowBlank 	= true;
    var minYear 	= 2500;
    //var maxYear 	= (new Date()).getFullYear();
    var errorMsg 	= "";
    var field 		= gp_trim(ao_obj.value);

    // regular expression to match required date format
    re = /^(\d{1,2})\/(\d{1,2})\/(\d{4})$/;
	
    try{
	    if(field != '') {
			
	    	if(field.length==8){
	    		field 			= field.substring(0, 2) + "/" + field.substring(2, 4) + "/" + field.substring(4, 8);
	    		ao_obj.value 	= field;
	    	}
	    	
	      if(regs = field.match(re)) {
	        if(regs[1] < 1 || regs[1] > 31) {
	          errorMsg = "กรอกวันที่ผิด: " + regs[1];
	        } else if(regs[2] < 1 || regs[2] > 12) {
	          errorMsg = "กรอกเดือนผิด: " + regs[2];
	        } else if(regs[3] < minYear) {
	          errorMsg = "กรอกปีผิด: " + regs[3] + " ต้องเป็น พ.ศ. เท่านั้น";
	        }
	      } else {
	        errorMsg = "กรอกรูปแบบวันที่ผิด";
	      }
	    }

	    if(errorMsg != "") {
	      alert(errorMsg);
	      ao_obj.value = "";
	      ao_obj.focus();
	      return false;
	    }
    }catch(e){
    	alert("checkDate :: " + e);
    	return false;
    }

    return true;
  }

function showCalendar() {
    
    /*
    	p_month : 0-11 for Jan-Dec; 12 for All Months.
    	p_year	: 4-digit year
    	p_format: Date format (mm/dd/yyyy, dd/mm/yy, ...)
    	p_item	: Return Item.
    */

    p_item = arguments[0];

    if (arguments[1] == null || arguments[1] == ""){
        p_month = new String(gNow.getMonth());
    }else{
        p_month = arguments[1];
    }
    
    if (arguments[2] == "" || arguments[2] == null){
        p_year = new String(gNow.getFullYear().toString());
    }else{
    	p_year = arguments[2];
    }
	
    if(arguments[3] == "1"){
    	p_format = "DD/MM/YYYY";
    }else if(arguments[3] == "2"){
    	p_format = "MM/DD/YYYY";
    }else{
        p_format="YYYY/MM/DD";
    }
    
    //vWinCal = window.open("", "calendar", "width=150,height=180,status=no,resizable=no,toolbar=no,top=250,left=450");
    // Set position for click as easier [surachai].
    p_top = arguments[4];
    p_left = arguments[5];
    
    vWinCal = window.open("", "calendar", "width=230,height=170,status=no,resizable=no,toolbar=no,top="+(p_top+70)+",left="+p_left+",screenY=400");
    vWinCal.opener = self;
    ggWinCal = vWinCal;
    vWinCal.focus();
    build(p_item, p_month, p_year, p_format);
}

function removeComma(obj) {
    var val = obj.value;
    val = val.replace(/,/g, '');
    obj.value = val;
}

function checkAllowKey(keyCode, validChar){
    var keyChar = String.fromCharCode(keyCode);
    validChar += String.fromCharCode(8);
    if (validChar.indexOf(keyChar) < 0) {
        return false;
    }
    return true;
}


function onKeyPressNumber(evt){
    var keyCode = evt.keyCode ? evt.keyCode : evt.which;

    /** CHECK SPECIAL CHARACTER **/
    /*
     * SPECIAL CHARACTER (KEY CODE & CHAR CODE)
     *  33=!       34="    35=#        36=$
     *  37=%       38=&    39='        40=(
     *  41=)       42=*    45=-        47=/
     *  58=:       59=;    60=<        62=<
     *  63=?       64=@    91=[        93=]
     *  94=^       95=_    123={       125=}
     */

    if(keyCode == 33 || keyCode == 34 || keyCode == 35 || keyCode == 36 || keyCode == 37 || keyCode == 38 || keyCode == 39 || keyCode == 40 ||
        keyCode == 41 || keyCode == 42 || keyCode == 45 || keyCode == 47 || keyCode == 58 || keyCode == 59 || keyCode == 60 || keyCode == 62 ||
        keyCode == 63 || keyCode == 64 || keyCode == 91 || keyCode == 93 || keyCode == 123 || keyCode == 125){
        return false;
    }

    /** ALLOW NUMBER **/
    /*  96-105=number(0-9) */
    /*if (keyCode > 95 && keyCode < 106) {
        return true;
    } else {*/
        return checkAllowKey(keyCode, '1234567890');
    //}
}

function onKeyDownNumber(evt){
    var keyCode = evt.keyCode ? evt.keyCode : evt.which;
    /*key 17 = Ctrl, key 86 = v, key 67 = c */
    if(keyCode == 17 || keyCode == 86 || keyCode == 67 || keyCode == 8 || keyCode == 9 || keyCode == 35 || keyCode == 36 || keyCode == 37 || keyCode == 38 || keyCode == 39 || keyCode == 40 || keyCode == 46){
        return true;
    }
    /** ALLOW NUMBER **/
    /*  96-105=number(0-9) */
    if (keyCode > 95 && keyCode < 106) {
        return true;
    } else {
        return checkAllowKey(keyCode, '1234567890');
    }
}

function onKeyUpNumber(evt){
    var keyCode = evt.keyCode ? evt.keyCode : evt.which;

    if(keyCode == 8 || keyCode == 9 || keyCode == 35 || keyCode == 36 || keyCode == 37 || keyCode == 38 || keyCode == 39 || keyCode == 40 || keyCode == 46){
        return true;
    }
    /** ALLOW NUMBER **/
 
    return checkAllowKey(keyCode, '1234567890');
   
}

function onKeyPressMoney(evt){
    var keyCode = evt.keyCode ? evt.keyCode : evt.which;

    /** CHECK SPECIAL CHARACTER **/
    /*
     * SPECIAL CHARACTER (KEY CODE & CHAR CODE)
     *  33=!       34="    35=#        36=$
     *  37=%       38=&    39='        40=(
     *  41=)       42=*    45=-        47=/
     *  58=:       59=;    60=<        62=<
     *  63=?       64=@    91=[        93=]
     *  94=^       95=_    123={       125=}
     */

    if(keyCode == 33 || keyCode == 34 || keyCode == 35 || keyCode == 36 || keyCode == 37 || keyCode == 38 || keyCode == 39 || keyCode == 40 ||
        keyCode == 41 || keyCode == 42 || keyCode == 45 || keyCode == 47 || keyCode == 58 || keyCode == 59 || keyCode == 60 || keyCode == 62 ||
        keyCode == 63 || keyCode == 64 || keyCode == 91 || keyCode == 93 || keyCode == 123 || keyCode == 125){
        return false;
    }
    return checkAllowKey(keyCode, '0123456789.,');
}

function onKeyDownMoney(evt){
    var keyCode = evt.keyCode ? evt.keyCode : evt.which;

    /*key 17 = Ctrl, key 86 = v, key 67 = c */
    if(keyCode == 17 || keyCode == 86 || keyCode == 67 || keyCode == 8 || keyCode == 9 || keyCode == 35 || keyCode == 36 || keyCode == 37 || keyCode == 38 || keyCode == 39 || keyCode == 40 || keyCode == 46 || keyCode == 144 || keyCode == 110){
        return true;
    }
    if ( (keyCode > 95 && keyCode < 106) || keyCode == 44 || keyCode == 188 || keyCode == 190 ) {
        return true;
    } else {
        return checkAllowKey(keyCode, '0123456789.,');
    }
}

function onKeyUpMoney(evt){
    var keyCode = evt.keyCode ? evt.keyCode : evt.which;

    if(keyCode == 8 || keyCode == 9 || keyCode == 35 || keyCode == 36 || keyCode == 37 || keyCode == 38 || keyCode == 39 || keyCode == 40 || keyCode == 46 || keyCode == 144 || keyCode == 110){
        return true;
    }
    return checkAllowKey(keyCode, '0123456789.,');
}

function formatNumber(obj) {
    var val = obj.value;
    val = $.trim(val);
    if (val != '') {
        val += '';
        x = val.split('.');
        x1 = x[0];
        x2 = x.length > 1 ? '.' + x[1] : '.00';
        var rgx = /(\d+)(\d{3})/;
        while (rgx.test(x1)) {
            x1 = x1.replace(rgx, '$1' + ',' + '$2');
        }
        obj.value = x1 + x2;
    }
}

function gp_validateTime(ao_obj){
	var errorMsg		= "";
	var re				= null;
	var lv_time			= "";
	var lv_timeTmp		= "";
	var lv_txtLength	= 0;

	try{
		re			= /^(\d{1,2}):(\d{2})(:00)?([ap]m)?$/;
		lv_time		= gp_trim(ao_obj.value);

		if(lv_time != "") {
			lv_txtLength = lv_time.length;
			if(lv_txtLength==3 && lv_time.indexOf(":") < 0){
				lv_timeTmp		= lv_time.substring(0, 1) + ":" + lv_time.substring(1, 3);
				ao_obj.value	= lv_timeTmp;
			}else if(lv_txtLength==4 && lv_time.indexOf(":") < 0){
				lv_timeTmp		= lv_time.substring(0, 2) + ":" + lv_time.substring(2, 4);
				ao_obj.value	= lv_timeTmp;
			}else{
				lv_timeTmp = lv_time;
			}

			if(regs = lv_timeTmp.match(re)) {
				if(!regs[4]) {
				  // 24-hour time format
				  if(regs[1] > 23) {
					errorMsg = "Invalid value for hours: " + regs[1];
				  }
				}else{
					errorMsg = "Invalid time format: " + lv_timeTmp;
				}

				if(!errorMsg && regs[2] > 59) {
				  errorMsg = "Invalid value for minutes: " + regs[2];
				}

			} else {
				errorMsg = "Invalid time format: " + lv_timeTmp;
			}
		}

		if(errorMsg != "") {
		  alert(errorMsg);
		  //ao_obj.focus();
		  return false;
		}

		return true;

	}catch(e){
		alert("gp_validateTime :: " + e);
		return false;
	}
  }
