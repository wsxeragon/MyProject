function showButtons(buttons){
	for(var i = 0;i<buttons.length;i++)
	{
		var btn = buttons[i].id;
		$("#"+btn).show();
	}
}