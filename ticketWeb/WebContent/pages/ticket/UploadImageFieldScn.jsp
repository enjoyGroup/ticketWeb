<%@ include file="/pages/include/checkLogin.jsp"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="java.util.*"%>
<jsp:useBean id="uploadImageFieldForm" class="th.go.ticket.app.enjoy.form.UploadImageFieldForm" scope="session"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=EDGE" />
	<title>Upload รูปเพื่อแสดง Zone สนาม</title>
	<%@ include file="/pages/include/enjoyInclude.jsp"%>
	<script src="<%=jsURL%>/jquery.AjaxFileUpload.js"></script>
	<style>
		.headerRow:hover{
		    cursor: pointer;
		}
	</style>
	<script type="text/javascript">
		
		var gv_service 		= null;
		var gv_url 			= '<%=servURL%>/EnjoyGenericSrv';
		
		$(document).ready(function(){
			gp_progressBarOn();
			
			gv_service = gv_url + "?service=" + $("#service").val();
			
			 $('input[type="file"]').ajaxfileupload({
				   'params': {'ac':'sss'},
			       'action': gv_service + "&pageAction=upload",           
			   'onComplete': function(response) {
				   		gp_progressBarOff();
				   		lp_uploadReturn(response);
			         
			       },
			       'onStart': function() {
			    	   gp_progressBarOn();
			       },
			       'submit_button' :  $('#btnUpload')
			 });
			
			 gp_progressBarOff();
			
		});
		
		function lp_uploadReturn(ao_val){//alert(JSON.stringify(av_val));
			var jsonObj 			= null;
        	var status				= null;
        	var errMsg				= null;
	        
	        try{//alert(ao_val);
	        	jsonObj = ao_val;
        		status	= jsonObj.status;
        		
        		//alert(status);alert(jsonObj.errMsg);
        		if(status=="SUCCESS"){
        			location.reload();
        		}else{
        			errMsg = jsonObj.errMsg;
        			alert(errMsg);
        		}
        		
        		
	        }catch(e){
	        	alert("lp_uploadReturn :: " + e);
	        }
	        
	    }
		
  </script>
</head>
<body>
	<form id="frm" method="post" action="<%=servURL%>/EnjoyGenericSrv">
		<input type="hidden" id="service" 		name="service" 		value="servlet.UploadImageFieldServlet" />
		<div id="menu" style="width: 100%;background: black;">
			<%@ include file="/pages/menu/menu.jsp"%>
		</div>
		<section class="vbox">
			<section>
				<section class="hbox stretch">
					<section id="content">
						<section class="vbox">
							<section class="scrollable padder">
								<div class="alert alert-block alert-error fade in">
					            	<h4 class="alert-heading">Upload รูปเพื่อแสดง Zone สนาม</h4>
					          	</div>
					          	<div class="row">
									<div class="col-sm-12">
										<section class="panel panel-default">
											<div class="panel-body" align="center">
												<!-- Begin contents -->
												<div style="margin-top: 30px;" align="center">
													<table border="0"  width="100%" cellspacing="1" cellpadding="0">
														<tr valign="middle">
											                <td width="10%">เลือกแผนผังสนาม :</td>
											                <td width="20%">
											                    <input type="file" name="datafile" id="datafile" />
											                </td>
											                <td width="70%">
											                    <input type="button" name="btnUpload" id="btnUpload"  value="Upload" class="btn" />
											                    <input type="reset" name="btnClear" id="btnClear"  value="Clear" class="btn" />
											                </td>
											            </tr> 
														<tr>
											                <td colspan="3" >
											                    <img alt="<%=uploadImageFieldForm.FILE_NAME%>" 
											                    	 title="<%=uploadImageFieldForm.FILE_NAME%>"
											                    	 src="/ticketWeb/upload/<%=uploadImageFieldForm.getImages()%>" 
											                    	 border="0" 
											                    	 width="651px" 
											                    	 height="376px" />
											                </td>
											            </tr>
											        </table>
												</div>
												<!-- End contents -->
											</div>
										</section>
									</div>
								</div>
							</section>
						</section>
					</section>
					<a href="#" class="hide nav-off-screen-block" data-toggle="class:nav-off-screen" data-target="#nav"></a>
				</section>
			</section>
		</section>
		<iframe name="ttestt" 
			src="" 
			scrolling="no"  
			frameborder="0" 
			width="0" 
			height="0">
	</iframe>
	</form>
	<div align="center" class="FreezeScreen" style="display:none;">
        <center>
        	<img id="imgProgress" valign="center" src="<%=imgURL%>/loading36.gif" alt="" />
        	<span style="font-weight: bold;font-size: large;color: black;">Loading...</span>
        </center>
    </div>
</body>
</html>