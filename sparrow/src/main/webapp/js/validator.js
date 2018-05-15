/*
 * @Description 树型菜单校验
 * @param param 树ID
 */
$.validator.addMethod("treeSelect",function(value,element, param){
	if(param != ""){
		$(element).val();
		var nodes = $.fn.zTree.getZTreeObj(param).getCheckedNodes();
		var output = '';
		var sep = '';
		for ( var i in nodes){
			output += sep+nodes[i].id;
			sep = ',';
		}

		$(element).val(output);
		if(nodes.length <= 0){
			return false;
		}
	}
	return true;
	
},"请选择树内容!");