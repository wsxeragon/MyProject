<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/import/taglib.jspf"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="tb"%>

<!DOCTYPE html>
<html>
<head>
<!-- 基础样式,页面都需要引用 -->
<%@include file="/WEB-INF/import/base.jspf"%>

<!-- 时间控件需引用样式 -->
<%@include file="/WEB-INF/import/datepicker.jspf"%>

<script type="text/javascript">
	$(function() {
		//菜单选中设置
		curMenu("menu6");
	});
</script>
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
						<li><i class="ace-icon fa fa-home home-icon"></i> <a href="#">日志管理</a></li>
						<li>系统管理</li>
						<li class="active">文本日志</li>
					</ul>
				</div>

				<div class="page-content">
					<!-- 页面设置功能 -->
					<tb:setting />

					<!-- 注:页面内容在此添加start -->
					<div class="row">
						<div class="col-xs-12">
							<!-- 搜索条件start -->
							<div class="form-group">
								<div class="col-xs-4  no-padding-left">
									<div class="input-group">
										<input class="form-control date-picker" id="date" name="date" type="text" readonly="readonly"/>
										<span class="input-group-addon">
											<i class="fa fa-calendar bigger-110"></i>
										</span>
									</div>
								</div>
								<div class="col-xs-2 no-padding-left">
									<span>状态：</span> <select name="type" id="logType">
									<option value="1">普通日志</option>
									<option value="2">错误日志</option>
									<option selected="selected" value="3">全局日志</option>
									</select>
								</div>
								<div class="no-padding-left">
									<input type="button" class="btn btn-sm btn-danger dropdown-toggle"
										id="searchButton" value="搜  索"/>
								</div>
							 </div>

							<!-- 搜索条件end -->
							<div class="table-header">日志内容</div>
							<div id="dialog-content"></div>

							<!-- div.table-responsive -->

							<!-- div.dataTables_borderWrap -->
							
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

	<!-- dataTables脚本 -->
	<tb:datatable />
	
	<!-- 弹出提示框需要引入 -->
	<script src="component/ace/components/bootbox.js/bootbox.js"></script>
	
	<!-- 日期控件需要引入 -->
	<tb:datepicker />
	
	<script type="text/javascript">
		jQuery(function($) {
			//时间控件初始化	
			$('#date').datepicker({
				todayBtn : "linked",
				autoclose : true,
				todayHighlight : true,
				language:"zh-CN"
			});
			$('#dialog-content').load("log/gettextlog",{"type":3,"date":""});
			
			$('#searchButton').on('click', function(){
				$('#dialog-content').load("log/gettextlog",{"type":$('#logType').val(),"date":$('#date').val()});
			});
		});
		
	</script>
</body>
</html>
