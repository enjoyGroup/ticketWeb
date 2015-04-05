<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@ page import="th.go.ticket.app.enjoy.bean.UserDetailsBean,
				 th.go.ticket.app.enjoy.bean.UserPrivilegeBean,
				 th.go.ticket.app.enjoy.bean.PagesDetailBean,
				 th.go.ticket.app.enjoy.main.Constants"%>
<%
	final String servURL1		= Constants.SERV_URL;
	UserDetailsBean userDeatil 	= (UserDetailsBean) request.getSession().getAttribute("userBean");
%>
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
	<%
		UserPrivilegeBean 			userPrivilegeBean 		= null;
		PagesDetailBean 			pagesDetailBean 		= null;
		for(int i=0;i<userDeatil.getUserPrivilegeList().size();i++){		
			userPrivilegeBean = userDeatil.getUserPrivilegeList().get(i);
	%>
			<li><a href="#"><%=userPrivilegeBean.getPrivilegeName()%></a>
				<ul>
					<%
					for(int j=0;j<userPrivilegeBean.getPagesDetail().size();j++){
						pagesDetailBean = userPrivilegeBean.getPagesDetail().get(j);
					%>	
						<li><a href="<%=pagesDetailBean.getPathPages()%>"><%=pagesDetailBean.getPageNames()%></a></li>
					<%
					}
					%>
				</ul>	
			</li>	
	<%		
		}
	%>
 	<li><a href="/ticketWeb/EnjoyGenericSrv?service=servlet.ChangePasswordServlet&pageAction=new">เปลี่ยนรหัสผ่าน</a></li>
 	<li><a href="#">ออกจากระบบ</a></li>
  	<!-- 
	<li><a href="#">รายงาน</a>
		<ul>
			<li><a href="/ticketWeb/EnjoyGenericSrv?service=servlet.SummaryRevenueOfYearServlet&pageAction=new">รายงานสรุปรายได้ประจำปี</a></li>
			<li><a href="/ticketWeb/EnjoyGenericSrv?service=servlet.DetailRevenueOfYearServlet&pageAction=new">รายงานแสดงรายละเอียดรายได้ประจำปี</a></li>
			<li><a href="#">รายงานแสดงรายละเอียดต่างๆ ภายใน Match</a></li>
		</ul></li>
	<li><a href="#">Menu2</a></li>
	<li><a href="#">Menu3</a>
		<ul>
			<li><a href="#">Menu3a</a></li>
			<li><a href="#">Menu3b</a></li>
		</ul></li>
	<li><a href="#">Menu4</a>
		<ul>
			<li><a href="#Menu4a">Menu4a(go to anchor)</a></li>
			<li><a href="#" onclick="alert('Menu4b');return false;">Menu4b(alert)</a></li>
		</ul></li>
	<li><a href="#">Menu5</a>
		<ul>
			<li><a href="#">Menu5a</a></li>
			<li><a href="#">Menu5b</a>
				<ul>
					<li><a href="#">Menu5b-i</a></li>
					<li><a href="#">Menu5b-ii</a></li>
				</ul></li>
			<li><a href="#">Menu5c</a></li>
		</ul>
	</li>
	 -->	
</ul>
</div>