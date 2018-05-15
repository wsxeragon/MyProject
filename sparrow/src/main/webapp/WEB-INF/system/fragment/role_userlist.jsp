<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/import/taglib.jspf"%>

<div>
	<table id="roleuser-table-list"
		class="table table-striped table-bordered table-hover">
		<thead>
			<tr>
				<th>ID</th>
				<th>用户名</th>
				<th>登录名</th>
				<th>状态</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${roleUserList}" var="roleuserlist">
		<tr>
			<td>${roleuserlist.operId}</td>
			<td>${roleuserlist.operLogin}</td>
			<td>${roleuserlist.operName}</td>
			<td>
				<c:choose>
					<c:when test="${roleuserlist.operStatus == 1}">
						正常
					</c:when>
					<c:when test="${roleuserlist.operStatus == 2}">
						禁用
					</c:when>
					<c:otherwise>
						${roleuserlist.operStatus}
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
		</c:forEach>
		</tbody>
	</table>
</div>

<script type="text/javascript">
jQuery(function($) {
	var myTable = $('#roleuser-table-list').DataTable(
		{
			"bAutoWidth" : false,
			"bProcessing" : true, 
			"bFilter" : false,
			"bPaginate" : false, //是否显示（应用）分页器  
			"aoColumnDefs" : [
				{
					"aTargets" : [ 0 ],
					"bSortable" : false,
					"bVisible" : false
				},
				{
					"aTargets" : [ 1 ],
					"bSortable" : false,
					"sDefaultContent" : "",
				},
				{
					"aTargets" : [ 2 ],
					"bSortable" : false,
					"sDefaultContent" : "",
				},
				{
					"aTargets" : [ 3 ],
					"bSortable" : false,
					"sDefaultContent" : ""					
				}],		
			"oLanguage" : { //国际化配置  
				"sUrl" : "other/table_cn.json"
			}
		});
});
</script>