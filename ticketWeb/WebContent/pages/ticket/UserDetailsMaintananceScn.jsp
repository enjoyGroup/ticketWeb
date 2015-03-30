<%@ include file="/pages/include/checkLogin.jsp"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>เพิ่ม Match การแข่งขัน</title>
	<%@ include file="/pages/include/enjoyInclude.jsp"%>	
	<script>
		$(document).ready(function(){
			$('#menu1').ptMenu();
		});
	</script>
</head>
<body>
	<form id="frm">
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
				            	<h4 class="alert-heading">เพิ่ม Match การแข่งขัน</h4>
				          	</div>
							<div class="row">
								<div class="col-sm-12">
									<section class="panel panel-default">
										<header class="panel-heading font-bold">รายละเอียด Match การแข่งขัน</header>
										<div class="panel-body">
								        	<table style='width:100%;' class='table'>
												<tr>
								                   	<td style='width:10%;padding:0px !important'>
														<ul class='side-panel'>
										   					<li class='topic-head'>ปีการแข่งขัน</li>
									                       <li class='side-panel-item'><a href="#">2555</a></li>
									                       <li class='side-panel-item'><a href="#">2556</a></li>
									                       <li class='side-panel-item'><a href="#">2557</a></li>
									                       <li class='side-panel-item'><a href="#">2558</a></li>
									                       <li class='add-new'><a href="#">+ เพิ่มปีการแข่งขัน</a></li>
									                     </ul>
								                   	</td>
								                   	<td style='width:70%;padding:0px !important'>
									                    <div class='sim-panel-search'>
									                        <input type='text' id="keyword" name='keyword'>
									                        <input type='submit' class='btn btn-danger' value='search' >
									                    </div>
									                    <br>
								                  		<div class='sim-panel-result' style="padding:10px;">
								                        	<table class="table sim-panel-result-table">
									                          	<tr>
										                            <th>ลำดับ</th>
										                            <th>ทีมคู่แข่งภาษาไทย</th>
										                            <th>ทีมคู่แข่งภาษาอังกฤษ</th>
										                            <th>วันที่แข่ง</th>
										                            <th>เวลาที่แข่ง</th>
										                            <th>Action</th>
									                          	</tr>
									                          	<tr class='content' >
										                            <td id='99'>1</td>
										                            <td>ทดสอบทีม</td>
										                            <td>ทดสอบทีม2</td>
										                            <td>19/03/2015</td>
										                            <td>15:15</td>
										                            <td style="text-align: center;">
											                       		<button class="btn action-add-btn btn-success" style="text-align: center;"> + </button>
											                           	<button class="btn action-del-btn btn-danger" style="text-align: center;"> - </button>
										                            </td>
									                          	</tr>
								                        	</table>
														</div>
							                   		</td>
							                 	</tr>
							               </table>	
									</section>
								</div>
							</div>
							</section>
						</section>
					</section>
					<a href="#" class="hide nav-off-screen-block"
						data-toggle="class:nav-off-screen" data-target="#nav"></a>
				</section>
			</section>
		</section>
	</form>
</body>
</html>