/*
 * @Description dataTables隐藏列、导出、打印等工具栏功能
 * @param dataTables变量
 */
function dataTableButtonSet(myTable) {
	new $.fn.dataTable.Buttons(
			myTable,
			{
				buttons : [
						{
							"extend" : "colvis",
							"text" : "<i class='fa fa-search bigger-110 blue'></i> <span class='hidden'>显示/隐藏 列</span>",
							"className" : "btn btn-white btn-primary btn-bold",
							columns : ':not(:first):not(:last)'
						},
						{
							"extend" : "copy",
							"text" : "<i class='fa fa-copy bigger-110 pink'></i> <span class='hidden'>复制到剪贴板</span>",
							"className" : "btn btn-white btn-primary btn-bold"
						},											
						{
							"extend" : "print",
							"text" : "<i class='fa fa-print bigger-110 grey'></i> <span class='hidden'>打印</span>",
							"className" : "btn btn-white btn-primary btn-bold",
							autoPrint : false,
							message : 'This print was produced using the Print button for DataTables'
						} ]
			});
	myTable.buttons().container().appendTo($('.tableTools-container'));

	// style the message box
	var defaultCopyAction = myTable.button(1).action();
	myTable
			.button(1)
			.action(
					function(e, dt, button, config) {
						defaultCopyAction(e, dt, button, config);
						$('.dt-button-info')
								.addClass(
										'gritter-item-wrapper gritter-info gritter-center white');
					});

	var defaultColvisAction = myTable.button(0).action();
	myTable
			.button(0)
			.action(
					function(e, dt, button, config) {

						defaultColvisAction(e, dt, button, config);

						if ($('.dt-button-collection > .dropdown-menu').length == 0) {
							$('.dt-button-collection')
									.wrapInner(
											'<ul class="dropdown-menu dropdown-light dropdown-caret dropdown-caret" />')
									.find('a').attr('href', '#').wrap("<li />");
						}
						$('.dt-button-collection').appendTo(
								'.tableTools-container .dt-buttons');
					});

	setTimeout(function() {
		$($('.tableTools-container')).find('a.dt-button').each(function() {
			var div = $(this).find(' > div').first();
			if (div.length == 1)
				div.tooltip({
					container : 'body',
					title : div.parent().text()
				});
			else
				$(this).tooltip({
					container : 'body',
					title : $(this).text()
				});
		});
	}, 500);
}

/*
 * @Description dataTables checkBox 选中及取消事件
 * @param dataTables变量
 */
function  dataTableCheckbox(myTable) {
	myTable.on('select', function(e, dt, type, index) {
		if (type === 'row') {
			$(myTable.row(index).node()).find('input:checkbox').prop(
					'checked', true);
		}
	});
	myTable.on('deselect', function(e, dt, type, index) {
		if (type === 'row') {
			$(myTable.row(index).node()).find('input:checkbox').prop(
					'checked', false);
		}
	});
}

/*
 * @Description 对话框标题转换,用于弹出对话框使用
 */
function dialogTitleShow(){
	$.widget("ui.dialog", $.extend({}, $.ui.dialog.prototype, {
		_title: function(title) {
			var $title = this.options.title || '&nbsp;';
			if( ("title_html" in this.options) && this.options.title_html == true )
				title.html($title);
			else title.text($title);
		}
	}));
}

(function ($) {
	//对话框标题转换
	dialogTitleShow();
	
	/*
	 * @Description 弹出对话框异步提交,主要用于添加/编辑等功能
	 * @param dialogTitle 弹出对话框显示标题,可为空,标题不显示
	 * @param dialogWidth 弹出对话框宽度,可为空,默认值为500
	 * @param dialogHeight 弹出对话框高度,可为空,默认值为500
	 * @param fragmentUrl 弹出对话框加载内容需要显示的片断url地址,可为空,如果为空直接将当前加载
	 * @param framentData 弹出对话框加载内容需要显示的片断url参数,有参数就填
	 * @param validateFunName 校验方法名,可为空,空则不做校验
	 * @param submitUrl 提交方法的url地址,不能为空
	 * @param submitData 提交方法的url参数,有参数就填
	 * @param submitFormId 提交方法的FormID,有参数就填
	 * @param submitSuccessMsg 提交方法执行成功后的提示信息,可为空,如果为空不做提示
	 * @param submitSuccessCallback 提交方法执行成功后的回调方法,可为空,如果为空不执行回调
	 */
	$.fn.dialogexcute = function(param){
		var defaults = {
				dialogWidth : 500,
				dialogHeight : 500,
				dialogTitle : ""
		},
		param = $.extend(defaults,param);
		
		var dialogName = this;
		if(typeof param.fragmentUrl !='undefined'){
			dialogName = dialogName.load(param.fragmentUrl,param.framentData);
		}
		
		var dialog = dialogName.removeClass('hide').dialog({
			modal: true,
			title: "<div class='widget-header widget-header-small'><h4 class='smaller'>"+param.dialogTitle+"</h4></div>",
			title_html: true,
			width: param.dialogWidth,
			height: param.dialogHeight,
			buttons: [ 
				{
					text: "取消",
					"class" : "btn btn-minier",
					click: function() {
						$( this ).dialog( "close" ); 
					} 
				},
				{
					text: "提交",
					"class" : "btn btn-primary btn-minier",
					click: function() {						
						if(typeof param.validateFunName =='undefined' || eval(param.validateFunName+"()")){
							var paraData = "";
							if(typeof param.submitData !='undefined'){
								paraData = param.submitData;
							}else if(typeof param.submitFormId !='undefined'){
								paraData = $('#'+param.submitFormId).serialize();
							}
							$.ajax({
				                url:param.submitUrl,
				                data:paraData,
				                type: "post", 
				                dataType:"json",
				                contentType:"application/x-www-form-urlencoded; charset=utf-8",
				                error:function(data){
				                	bootbox.alert({  
						             buttons: {  
						                ok: {  
						                     label: '确认',  
						                     className: 'btn-myStyle'  
						                 }  
						             },  
						             message: '执行操作失败:'+data.message 
						         });  
				                },
				                success:function(data){
				                   //关闭当前弹出窗口	
				                   dialogName.dialog( "close" );
				                   if (data.code=='200'||data.code=='000000') {
					                   if(typeof param.submitSuccessMsg != 'undefined'){
					                	   bootbox.alert({  
					                		   buttons: {  
					                			   ok: {  
					                				   label: '确认',  
					                				   className: 'btn-myStyle'  
					                			   }  
					                		   },  
					                		   message: param.submitSuccessMsg,  
					                		   callback: function() { 
					                			   if(typeof param.submitSuccessCallback != 'undefined'){
					                				   eval(param.submitSuccessCallback+"()");
					                			   }								                 
					                		   }
					                	   }); 
					                   }else{
					                	   if(typeof param.submitSuccessCallback != 'undefined'){
					                		   eval(param.submitSuccessCallback+"()");
					                	   }	
					                   } 
				                   
				                   } else {
				                	   bootbox.alert({  
								             buttons: {  
								                ok: {  
								                     label: '确认',  
								                     className: 'btn-myStyle'  
								                 }  
								             },  
								             message: '执行操作失败:'+data.message 
								         });
				                   }
				                }
					        });
						}					
					} 
				}
			]
		});
	};
	
	/*
	 * @Description 弹出对话框片断加载显示,主要用于显示等功能
	 * @param dialogTitle 弹出对话框显示标题,可为空,标题不显示
	 * @param dialogWidth 弹出对话框宽度,可为空,默认值为500
	 * @param dialogHeight 弹出对话框高度,可为空,默认值为500
	 * @param fragmentUrl 弹出对话框加载内容需要显示的片断url地址,不能为空
	 * @param framentData 弹出对话框加载内容需要显示的片断url参数,有参数就填
	 */
	$.fn.dialogshow = function(param){
		var defaults = {
				dialogWidth : 500,
				dialogHeight : 450,
				dialogTitle : ""
		},
		param = $.extend(defaults,param);
		
		var dialog = $( this ).load(param.fragmentUrl,param.framentData).removeClass('hide').dialog({
			modal: true,
			title: "<div class='widget-header widget-header-small'><h4 class='smaller'>"+param.dialogTitle+"</h4></div>",
			title_html: true,
			width: param.dialogWidth,
			height: param.dialogHeight,
			buttons: [ 
				{
					text: "取消",
					"class" : "btn btn-primary btn-minier",
					click: function() {
						$( this ).dialog( "close" ); 
					} 
				}
			]
		});		
	};
	
})(jQuery);



