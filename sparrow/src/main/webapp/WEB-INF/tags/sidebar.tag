<%@ tag body-content="scriptless" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ attribute name="functionItems" required="true" rtexprvalue="true" type="java.util.List" %>

<script type="text/javascript">
//当前页面选中菜单设置
function curMenu(menu){
	$("li#"+menu).addClass("active");
	$("li#"+menu).parents("li").addClass("active open");
	$("li#"+menu).parents("li").parents("li").addClass("active open");
}
</script>

<!-- #section:basics/sidebar -->
<div id="sidebar" class="sidebar responsive ace-save-state">
	<ul class="nav nav-list">
		<!-- 第一级子菜单start -->
		<c:forEach items="${functionItems}" var="functionFist">
			<li id="menu${functionFist.funnId}" class=""><c:choose>
					<c:when test="${empty functionFist.funnUrl}">
						<a href="#" class="dropdown-toggle">
					</c:when>
					<c:otherwise>
						<a href="${functionFist.funnUrl}">
					</c:otherwise>
				</c:choose> <i class="menu-icon fa ${functionFist.funnImage}"></i> <span
				class="menu-text"> ${functionFist.funnName} </span> <c:if
					test="${!empty functionFist.children}">
					<b class="arrow fa fa-angle-down"></b>
				</c:if> </a> <b class="arrow"></b> <!-- 第二级子菜单start --> <c:if
					test="${!empty functionFist.children}">
					<ul class="submenu">
				</c:if> <c:forEach items="${functionFist.children}" var="functionSecond">
					<li id="menu${functionSecond.funnId}" class=""><c:choose>
							<c:when test="${empty functionSecond.funnUrl}">
								<a href="#" class="dropdown-toggle">
							</c:when>
							<c:otherwise>
								<a href="${functionSecond.funnUrl}">
							</c:otherwise>
						</c:choose> <i class="menu-icon fa fa-caret-right"></i>
						${functionSecond.funnName} <c:if
							test="${!empty functionSecond.children}">
							<b class="arrow fa fa-angle-down"></b>
						</c:if> </a> <b class="arrow"></b> <!-- 第三级子菜单start --> <c:if
							test="${!empty functionSecond.children}">
							<ul class="submenu">
						</c:if> <c:forEach items="${functionSecond.children}" var="functionThird">
							<li id="menu${functionThird.funnId}" class=""><a
								href="${functionThird.funnUrl}"> <i
									class="menu-icon fa fa-caret-right"></i>
									${functionThird.funnName} </a> <b class="arrow"></b></li>
						</c:forEach> <c:if test="${!empty functionSecond.children}">
	</ul>
	</c:if>
	<!-- 第三级子菜单end -->

	</li>
	</c:forEach>
	<c:if test="${!empty functionFist.children}">
		</ul>
	</c:if>
	<!-- 第二级子菜单end -->

	</li>
	</c:forEach>
	<!-- 第一级子菜单end -->
	</ul>
	<!-- /.nav-list -->

	<!-- #section:basics/sidebar.layout.minimize -->
	<div class="sidebar-toggle sidebar-collapse" id="sidebar-collapse">
		<i id="sidebar-toggle-icon"
			class="ace-icon fa fa-angle-double-left ace-save-state"
			data-icon1="ace-icon fa fa-angle-double-left"
			data-icon2="ace-icon fa fa-angle-double-right"></i>
	</div>

	<!-- /section:basics/sidebar.layout.minimize -->
</div>