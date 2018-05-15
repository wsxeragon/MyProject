<%@ tag body-content="scriptless" trimDirectiveWhitespaces="true"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ attribute name="uc" required="true" rtexprvalue="true"
	type="com.inphase.sparrow.entity.system.User"%>

<div id="navbar" class="navbar navbar-default ace-save-state">
	<div class="navbar-container ace-save-state" id="navbar-container">
		<div class="navbar-header pull-left">
			<!-- #section:basics/navbar.layout.brand -->
			<a href="#" class="navbar-brand"> <small> <i
					class="fa fa-leaf"></i> 管理框架 </small> </a>
		</div>

		<!-- #section:basics/navbar.dropdown -->
		<div class="navbar-buttons navbar-header pull-right" role="navigation">
			<ul class="nav ace-nav">
				<li class="red" id="alertBlock" style="display:none" >
					<a href="pwdupdate" id="msg">
					${modPwdMsg}
					</a>
				</li>
				<!-- #section:basics/navbar.user_menu -->
				<li class="light-blue dropdown-modal"><a data-toggle="dropdown"
					href="#" class="dropdown-toggle"> <img class="nav-user-photo"
						src="component/ace/assets/avatars/user.jpg" alt="Jason's Photo" /> <span
						class="user-info"> <small >欢迎光临,</small> ${uc.operLogin} </span> <i
						class="ace-icon fa fa-caret-down"></i> </a>

					<ul
						class="user-menu dropdown-menu-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">
						<li><a href="logout"> <i class="ace-icon fa fa-power-off"></i>
								退出 </a></li>
					</ul></li>

				<!-- /section:basics/navbar.user_menu -->
			</ul>
		</div>

		<!-- /section:basics/navbar.dropdown -->
	</div>
	<!-- /.navbar-container -->
</div>

<script type="text/javascript">
if("${modPwdMsg}" != null && "${modPwdMsg}" != "")
{
	$("#alertBlock").css({"display":"block"});
}
</script>
