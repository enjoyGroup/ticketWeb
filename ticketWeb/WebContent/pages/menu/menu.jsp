<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<ul id="menu1">
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
		</ul></li>
</ul>