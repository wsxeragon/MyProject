<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/import/taglib.jspf"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="tb"%>

<!DOCTYPE html>
<html>
<head>
<!-- 基础样式,页面都需要引用 -->
<%@include file="/WEB-INF/import/base.jspf"%>

<!-- 树型菜单样式 -->
<link rel="stylesheet" href="component/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">

<script type="text/javascript">
	$(function() {
		//菜单选中设置
		curMenu("menu3");
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
						<li class="active">角色管理</li>
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
								<span>权限组名：</span><input type="search" placeholder="" id="roleNameSearch">&nbsp;&nbsp;
								<button class="btn btn-sm btn-danger dropdown-toggle"
									id="searchButton" style="display:none">搜 索</button>&nbsp;&nbsp;
								<button class="btn btn-sm btn-danger dropdown-toggle"
									id="addButton" style="display:none" onclick="buttonAdd()">新 增</button> 
							</div>
							<!-- 搜索条件end -->

							<div class="clearfix">
								<div class="pull-right tableTools-container"></div>
							</div>
							<div class="table-header">角色信息列表</div>

							<!-- div.table-responsive -->

							<!-- div.dataTables_borderWrap -->
							<div>
								<table id="table-list"
									class="table table-striped table-bordered table-hover">
									<thead>
										<tr>
											<th>ID</th>
											<th>权限组名称</th>
											<th>权限组说明</th>
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
							删除当前权限：<label id="del-name"></label>
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
	<script src="js/tools.js"></script>
	<script type="text/javascript">
		jQuery(function($) {
			showButtons(${ROLE_LIST_BUTTONS});
			var myTable = $('#table-list')
					.DataTable(
							{
								"bAutoWidth" : false,
								"bProcessing" : true, //DataTables载入数据时，是否显示‘进度’提示 		
								"bFilter" : false,
								"aLengthMenu" : [ 10, 20, 30, 40], //更改显示记录数选项  
								"bPaginate" : true, //是否显示（应用）分页器  
								"bServerSide" : true,
								"sAjaxSource" : "role/list",
								"fnServerParams" : function(aoData) {
									aoData.push({
										name : "roleName",
										value : $("#roleNameSearch").val()
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
											"mData" : "roleId",
											"bSortable" : false,
											"bVisible" : false
										},
										{
											"aTargets" : [ 1 ],
											"mData" : "roleName",
											"bSortable" : true,
											"sDefaultContent" : "",
											"bVisible" : true
										},
										{
											"aTargets" : [ 2 ],
											"mData" : "roleDescription",
											"bSortable" : false,
											"sDefaultContent" : "",
											"bVisible" : true,
											"mRender": function ( data, type, full ) {
												if(data.length>30){
									            	return data.substring(0,30)+"...";
									            }else{
									            	return data;
									            };
										     }										
										},{
											"aTargets" : [ 3 ],
											"mRender" : function(data, type, full) {
												return '<div class="hidden-sm hidden-xs action-buttons">'
															+'<a class="blue" title="查看人员" onclick="buttonShow('+full.roleId+')"><i class="ace-icon fa fa-search-plus bigger-130"></i></a>'
															+'<a class="green" title="编辑" onclick="buttonEdit('+full.roleId+')"><i class="ace-icon fa fa-pencil bigger-130"></i></a>'
															+'<a class="red" title="删除" onclick="buttonRemove('+full.roleId+','+'\''+full.roleName+'\''+')"><i class="ace-icon fa fa-trash-o bigger-130"></i></a>'
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
		});
		
		//编辑/添加对话框执行成功后的回调方法
		function submitSuccessCallback(){
			//重新加载列表信息,触发搜索操作	 
			$("#searchButton").click();
		}	
			
		//添加按钮事件
		function buttonAdd(){
		   $("#dialog-content").dialogexcute({
		   	  dialogTitle:"角色信息添加",
		   	  dialogWidth:600,
		      dialogHeight:500,
		      fragmentUrl:"role/get",		      
		      framentData:{"roleId":-1,"type":1},
		      validateFunName:"roleFormValidate",
		      submitUrl:"role/save",
		      submitFormId:"roleForm",
		      submitSuccessMsg:"新增角色执行成功!",
		      submitSuccessCallback:"submitSuccessCallback"
		   });
		}
		
		//查看人员按钮事件		
		function buttonShow(id){
			$("#dialog-content").dialogshow({
		      fragmentUrl:"role/getUser",
		      dialogTitle:"角色人员信息查看",
		      dialogWidth:600,
		      dialogHeight:500,
		      framentData:{"roleId":id}
		   });
		} 
		
		//编辑按钮事件
		function buttonEdit(id){
		   $("#dialog-content").dialogexcute({
		      fragmentUrl:"role/get",
		      dialogTitle:"用户信息编辑",
		      framentData:{"roleId":id,"type":2},
		      validateFunName:"roleFormValidate",
		      submitUrl:"role/update",
		      submitFormId:"roleForm",
		      submitSuccessMsg:"修改权限执行成功!",
		      submitSuccessCallback:"submitSuccessCallback"
		   });
		}
		
		//删除按钮事件
		function buttonRemove(id,name){
			$( "#del-name" ).html(name);
			$("#dialog-delete").dialogexcute({
		      dialogTitle:"权限信息删除",
		      dialogWidth:350,
		      dialogHeight:250,
		      submitUrl:"role/remove",
		      submitData:{"roleId":id},
		      submitSuccessCallback:"submitSuccessCallback"
		   });
		}
		
	</script>
</body>
</html>
