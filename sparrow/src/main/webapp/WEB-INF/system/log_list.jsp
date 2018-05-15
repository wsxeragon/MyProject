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
		curMenu("menu5");
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
						<li class="active">操作日志</li>
					</ul>
				</div>

				<div class="page-content">
					<!-- 页面设置功能 -->
					<tb:setting />

					<!-- 注:页面内容在此添加start -->
					<div class="row">
						<div class="col-xs-12">
							<div class="form-inline">
								<div class="form-group">
									<span>操作人：</span><input type="search" placeholder="" id="operName">
									<span>操作类型：</span> 
									<select name="logMode" id="logMode">
										<option selected="selected" value="-1">全部</option>
										<option value="1">登入</option>
										<option value="2">新增</option>
										<option value="3">修改</option>
										<option value="4">删除</option>
										<option value="6">登出</option>
									</select>
								</div>
								<div class="form-group">
									<div class="input-daterange input-group">
										<input type="text" class="input-sm form-control" name="createdateStart" id="createdateStart" placeholder="开始时间"/>
										<span class="input-group-addon">
											<i class="fa fa-exchange"></i>
										</span>
										<input type="text" class="input-sm form-control" name="createdateEnd" id="createdateEnd" placeholder="结束时间"/>
									</div>
								</div>
								<button class="btn btn-sm btn-danger dropdown-toggle" id="searchButton">搜 索</button>							 
							</div>
							<!-- 搜索条件end -->

							<div class="clearfix">
								<div class="pull-right tableTools-container"></div>
							</div>
							<div class="table-header">系统操作列表</div>

							<!-- div.table-responsive -->

							<!-- div.dataTables_borderWrap -->
							<div>
								<table id="table-list"
									class="table table-striped table-bordered table-hover">
									<thead>
										<tr>
											<th>ID</th>
											<th>操作人</th>
											<th>操作类型</th>
											<th>操作内容</th>
											<th>操作时间</th>
										</tr>
									</thead>
									<tbody>
									</tbody>
								</table>
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

	<!-- dataTables脚本 -->
	<tb:datatable />

	<!-- 日期控件需要引入 -->
	<tb:datepicker />
	
	<script type="text/javascript">
		jQuery(function($) {
			showButtons();
			var myTable = $('#table-list')
					.DataTable(
							{
								"bAutoWidth" : false,
								"bProcessing" : true, //DataTables载入数据时，是否显示‘进度’提示 		
								"bFilter" : false,
								"aLengthMenu" : [ 10, 20, 30, 40 ], //更改显示记录数选项  
								"bPaginate" : true, //是否显示（应用）分页器  
								"bServerSide" : true,
								"sAjaxSource" : "log/getsystemlog",
								"fnServerParams" : function(aoData) {
									aoData.push({
										name : "startTime",
										value : $("#createdateStart").val()
									}, {
										name : "endTime",
										value : $("#createdateEnd").val()
									}, {
										name : "operName",
										value : $("#operName").val()
									}, {
										name : "logMode",
										value : $("#logMode").val()
									});
								},
								"fnServerData" : function(sSource, aoData,
										fnCallback) {
									$.ajax({
										"dataType" : "json",
										"type" : "POST",
										"url" : sSource,
										"data" : aoData,
										"success" : fnCallback
									});
								},
								"aoColumnDefs" : [
										{
											"aTargets" : [ 0 ],
											"mData" : "logId",
											"bSortable" : false,
											"bVisible" : false
										},
										{
											"aTargets" : [ 1 ],
											"mData" : "operName",
											"sDefaultContent" : "",
											"bSortable" : false,
											"bVisible" : true
										},
										{
											"aTargets" : [ 2 ],
											"mData" : "logMode",
											"bSortable" : false,
											"mRender" : function(data, type,
													full) {
												if(data == '1'){
													return '<lable style="color:blue">登入</lable>';
												}else if (data == '2') {
													return '<lable style="color:red">新增</lable>';
												} else if (data == '3') {
													return '<lable style="color:red">修改</lable>';
												} else if (data == '4'){
													return '<lable style="color:red">删除</lable>';
												} else if (data == '6'){
													return '<lable style="color:blue">登出</lable>';
												}
											},
											"sDefaultContent" : "",
											"bVisible" : true
										},
										{
											"aTargets" : [ 3 ],
											"mData" : "logContent",
											"sDefaultContent" : "",
											"bSortable" : false,
											"bVisible" : true
										},{
											"aTargets" : [ 4 ],
											"mData" : "logDatetime",
											"bSortable" : false,
											"sDefaultContent" : "",
											"bVisible" : true
										}],						
								"oLanguage" : { //国际化配置  
									"sUrl" : "other/table_cn.json"
								}
							});

			//给搜索按钮绑定点击事件
			$("#searchButton").on('click', function(e) {
				myTable.draw();
			});

			//dataTables隐藏列、导出、打印等工具栏功能
			dataTableButtonSet(myTable);
			
			//时间控件初始化
			$('.input-daterange').datepicker({
				autoclose:true,
				language:"zh-CN",
				todayHighlight:true
			});
		});
		
		function showButtons(){
			var buttons = [];
			 buttons = ${USER_LIST_BUTTONS};
			for(var i = 0;i<buttons.length;i++)
			{
				var btn = buttons[i].id;
				$("#"+btn).show();
			}
		}
	</script>
</body>
</html>
