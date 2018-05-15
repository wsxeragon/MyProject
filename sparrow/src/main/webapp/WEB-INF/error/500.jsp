<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/import/taglib.jspf"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="tb"%>

<!DOCTYPE html>
<html>
<head>
<%@include file="/WEB-INF/import/base.jspf"%>
</head>

<body class="no-skin">
	<!-- 导航菜单 -->
	<tb:navbar uc="${loginUser}" />

	<!-- 下方容器 -->
	<div class="main-container ace-save-state" id="main-container">
		<!-- 侧边菜单栏 -->
		<tb:sidebar functionItems="${loginUser.functionItemList}" />
		
		<!-- 主要功能展现内容 -->
		<div class="main-content">
			<div class="main-content-inner">
				<div class="breadcrumbs ace-save-state" id="breadcrumbs">
					<ul class="breadcrumb">
						<li><i class="ace-icon fa fa-home home-icon"></i> <a href="#">首页</a>
						</li>
						<li class="active">错误页面</li>
					</ul>
				</div>

				<div class="page-content">
					<!-- 页面设置功能 -->
					<tb:setting />

					<!-- 注:页面内容在此添加start -->
					<div class="error-container">
						<div class="well">
							<h1 class="grey lighter smaller">
								<span class="blue bigger-125">
									<i class="ace-icon fa fa-random"></i>
									500
								</span>
								系统错误
							</h1>

							<hr />
							<h4 class="lighter smaller">
								请稍后尝试或者联系维护人员处理!
								<i class="ace-icon fa fa-wrench icon-animated-wrench bigger-125"></i>
							</h4>
							
							<div class="space-20"></div>
							<hr />
							<div class="space"></div>

							<div class="center">
								<a href="javascript:history.back()" class="btn btn-grey">
									<i class="ace-icon fa fa-arrow-left"></i>
									上一步
								</a>

								<a href="#" class="btn btn-primary">
									<i class="ace-icon fa fa-tachometer"></i>
									返回首页
								</a>
							</div>
						</div>
					</div>
					
					<!-- 页面内容end -->					
				</div>
				<!-- /.page-content -->
			</div>
		</div>
		<!-- /.main-content -->

		<!-- 页脚 -->
		<tb:footer />

	</div>
	<!-- /.main-container -->

	<!-- 基础脚本 -->
	<tb:base_script />
</body>
</html>
