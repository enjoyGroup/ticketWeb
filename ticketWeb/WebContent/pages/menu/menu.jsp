<%@ page import="th.go.ticket.app.enjoy.bean.UserDetailsBean"%>
<%@ page import = "th.go.ticket.app.enjoy.main.Constants"%>
<%
//Get URLs for this jsp file
final String servURL1		= Constants.SERV_URL;

UserDetailsBean userDeatil = (UserDetailsBean) request.getSession().getAttribute("userBean");

%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div class="headwrap">
	<div class="row">
    	<div class="brand span4">
        	<img src="/ticketWeb/images/logo2.png" >
        </div>
        
        <div class="span8 user"  style="margin-right:4%;">
        	<div class="font14"><img src="<%=servURL1%>/images/icon-user.jpg" alt="">ชื่อผู้ใช้งาน <span class="text_white"><%=userDeatil.getUserName() %>&nbsp;&nbsp;<%=userDeatil.getUserSurname() %></span></div>
            <div class="font12"></div>
            
            <ul>
            	<li class="date">เข้าใช้ระบบ : <span class="text_white"><%=userDeatil.getCurrentDate() %></span> </li>
            </ul>
            
        </div>
    </div><!-- container -->
</div><!-- headwrap -->
<div align="left">
	<ul id="menu1">
		<li><a href="#">ขายตั๋ว</a></li>
		<li>
			<a href="#">รายงาน</a>
			<ul>
				<li><a href="<%=servURL1%>/EnjoyGenericSrv?service=servlet.SummaryRevenueOfYearServlet&pageAction=new">รายงานรายได้ตามปี</a></li>
				<li><a href="<%=servURL1%>/EnjoyGenericSrv?service=servlet.DetailRevenueOfYearServlet&pageAction=new">รายงานรายได้ตามสโมสร</a></li>
				<li><a href="<%=servURL1%>/EnjoyGenericSrv?service=servlet.DisplayMatchServlet&pageAction=new">รายงานรานได้ตามประเภทตั๋ว</a></li>
			</ul>
		</li>
		<li>
			<a href="#">ตั้งค่าระบบ</a>
			<ul>
				<li><a href="<%=servURL1%>/pages/ticket/AddMatchScn.jsp">เพิ่ม Match การแข่งขัน</a></li>
				<li><a href="#">Menu5b</a>
					<ul>
						<li><a href="#">Menu5b-i</a></li>
						<li><a href="#">Menu5b-ii</a></li>
					</ul></li>
				<li><a href="#">Menu5c</a></li>
			</ul></li>
	</ul>
</div>