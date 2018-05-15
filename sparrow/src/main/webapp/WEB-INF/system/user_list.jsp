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
		curMenu("menu2");
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
						<li><i class="ace-icon fa fa-home home-icon"></i> <a href="#">首页</a></li>
						<li>系统管理</li>
						<li class="active">用户管理</li>
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
								<span>登录名：</span><input type="search" placeholder="" id="operLoginSearch">&nbsp;&nbsp; 
								<span>用户名：</span><input type="search" placeholder="" id="operNameSearch">&nbsp;&nbsp; 
								<span>联系电话：</span><input type="search" placeholder="" id="operPhoneSearch">&nbsp;&nbsp;							
							</div>
							<div class="form-group">
								<div class="col-xs-4  no-padding-left">
									<div class="input-daterange input-group">
										<input type="text" class="input-sm form-control" id="createdateStart" placeholder="注册开始时间"/>
										<span class="input-group-addon">
											<i class="fa fa-exchange"></i>
										</span>
	
										<input type="text" class="input-sm form-control" id="createdateEnd" placeholder="注册结束时间"/>
									</div>
								</div>
								<div class="no-padding-left">
									<span>状态：</span> <select name="statusSearch" id="operStatusSearch">
									<option selected="selected" value="-1">全部</option>
									<option value="1">正常</option>
									<option value="2">禁用</option>
									</select>&nbsp;&nbsp;&nbsp;&nbsp;
	
									<button class="btn btn-sm btn-danger dropdown-toggle"
										id="searchButton" style="display:none">搜 索</button>&nbsp;&nbsp;
									<button class="btn btn-sm btn-danger dropdown-toggle"
										id="addButton" onclick="buttonAdd()" style="display:none">新 增</button>
								</div>								 
							</div>
							<!-- 搜索条件end -->

							<div class="clearfix">
								<div class="pull-right tableTools-container"></div>
							</div>
							<div class="table-header">用户信息列表</div>

							<!-- div.table-responsive -->

							<!-- div.dataTables_borderWrap -->
							<div>
								<table id="table-list"
									class="table table-striped table-bordered table-hover">
									<thead>
										<tr>
											<th>ID</th>
											<th>登录名</th>
											<th>用户名</th>
											<th>联系电话</th>
											<th class="hidden-480">状态</th>
											<th>创建时间</th>
											<th>操作</th>
										</tr>
									</thead>
									<tbody>
									</tbody>
								</table>
							</div>
						</div>
					</div>
					
					<!-- 列表显示/编辑内容div定义 -->
					<div id="dialog-content" class="hide"></div>
					
					<!-- 删除用户确认框内容 -->
					<!-- #dialog-confirm -->
					<div id="dialog-delete" class="hide">
						<div class="alert alert-info bigger-110">
							删除当前用户：<label id="del-name"></label>
						</div>

						<div class="space-6"></div>

						<p class="bigger-110 bolder center grey">
							<i class="ace-icon fa fa-hand-o-right blue bigger-120"></i>
							您确定操作吗?
						</p>
					</div>
					<!-- #dialog-confirm -->
					
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
	<script src="js/tools.js"></script>
	<script type="text/javascript">
		jQuery(function($) {
			showButtons(${USER_LIST_BUTTONS});			
			var myTable = $('#table-list')
					.DataTable(
							{
								"bAutoWidth" : false,
								"bProcessing" : true, //DataTables载入数据时，是否显示‘进度’提示 		
								"bFilter" : false,
								"aLengthMenu" : [ 10, 20, 30 ], //更改显示记录数选项  
								"bPaginate" : true, //是否显示（应用）分页器  
								"bServerSide" : true,
								"sAjaxSource" : "user/list",
								"fnServerParams" : function(aoData) {
									aoData.push({
										name : "operLogin",
										value : $("#operLoginSearch").val()
									}, {
										name : "operName",
										value : $("#operNameSearch").val()
									}, {
										name : "operPhone",
										value : $("#operPhoneSearch").val()
									}, {
										name : "startTime",
										value : $("#createdateStart").val()
									}, {
										name : "endTime",
										value : $("#createdateEnd").val()
									}, {
										name : "operStatus",
										value : $("#operStatusSearch").val()
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
											"mData" : "operId",
											"bSortable" : false,
											"bVisible" : false
										},
										{
											"aTargets" : [ 1 ],
											"mData" : "operLogin",
											"bSortable" : true,
											"sDefaultContent" : "",
											"bVisible" : true
										},
										{
											"aTargets" : [ 2 ],
											"mData" : "operName",
											"bSortable" : true,
											"sDefaultContent" : "",
											"bVisible" : true
										},
										{
											"aTargets" : [ 3 ],
											"mData" : "operPhone",
											"bSortable" : true,
											"sDefaultContent" : "",
											"bVisible" : true
										},
										{
											"aTargets" : [ 4 ],
											"mData" : "operStatus",
											"mRender" : function(data, type,
													full) {
												if (data == '1') {
													return '<lable style="color:blue">正常</lable>';
												} else if (data == '2') {
													return '<lable style="color:red">禁用</lable>';
												}
											},
											"bSortable" : true,
											"sDefaultContent" : "",
											"bVisible" : true
										},{
											"aTargets" : [ 5 ],
											"mData" : "operCreatedate",
											"bSortable" : true,
											"sDefaultContent" : "",
											"bVisible" : true
										},{
											"aTargets" : [ 6 ],
											"mRender" : function(data, type,
													full) {
												return '<div class="hidden-sm hidden-xs action-buttons">'
															+'<a class="blue" title="查看" onclick="buttonShow('+full.operId+')"><i class="ace-icon fa fa-search-plus bigger-130"></i></a>'
															+'<a class="green" title="编辑" onclick="buttonEdit('+full.operId+')"><i class="ace-icon fa fa-pencil bigger-130"></i></a>'
															+'<a class="black" title="密码重置" onclick="buttonReset('+full.operId+')"><i class="ace-icon fa fa-unlock bigger-130"></i></a>'
															+'<a class="red" title="删除" onclick="buttonRemove('+full.operId+','+'\''+full.operLogin+'\''+')"><i class="ace-icon fa fa-trash-o bigger-130"></i></a>'
														+'</div>';
											},
											"bSortable" : false
										} ],						
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
		
		//编辑/添加对话框执行成功后的回调方法
		function submitSuccessCallback(){
			//重新加载列表信息,触发搜索操作	 
			$("#searchButton").click();
		}
		
		//添加按钮事件
		function buttonAdd(){
		   window.location="<%=basePath%>user/get?operId=-1&type=1";
		}
		
		//查看按钮事件		
		function buttonShow(id){
			$("#dialog-content").dialogshow({
			  dialogHeight:550,
		      fragmentUrl:"<%=basePath%>user/get",
		      dialogTitle:"用户信息查看",
		      framentData:{"operId":id,"type":3}
		   });
		} 
		
		//编辑按钮事件
		function buttonEdit(id){
			window.location="<%=basePath%>user/get?type=2&operId="+id;
		}
		
		//删除按钮事件
		function buttonRemove(id,name){
			$( "#del-name" ).html(name);
			$("#dialog-delete").dialogexcute({
		      dialogTitle:"用户信息删除",
		      dialogWidth:350,
		      dialogHeight:250,
		      submitUrl:"<%=basePath%>user/remove",
		      submitData:{"operId":id},
		      submitSuccessCallback:"submitSuccessCallback"
		   });
		}
		
		//密码重置事件
		function buttonReset(id){
			 $("#dialog-content").dialogexcute({
		      fragmentUrl:"<%=basePath%>user/get",
		      dialogTitle:"用户密码重置",
		      framentData:{"operId":id,"type":4},
		      validateFunName:"userPasswordFormValidate",
		      submitUrl:"<%=basePath%>user/resetPassword",
		      submitFormId:"userPasswordForm",
		      submitSuccessMsg:"重置密码执行成功!",
		   });
		}
		
	</script>
</body>
</html>
