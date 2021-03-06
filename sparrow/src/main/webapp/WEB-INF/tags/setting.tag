<%@ tag body-content="scriptless" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>

<!-- #section:settings.box -->
<div class="ace-settings-container" id="ace-settings-container">
	<div class="btn btn-app btn-xs btn-warning ace-settings-btn"
		id="ace-settings-btn">
		<i class="ace-icon fa fa-cog bigger-130"></i>
	</div>

	<div class="ace-settings-box clearfix" id="ace-settings-box">
		<div class="pull-left width-50">
			<!-- #section:settings.skins -->
			<div class="ace-settings-item">
				<div class="pull-left">
					<select id="skin-colorpicker" class="hide">
						<option data-skin="no-skin" value="#438EB9">#438EB9</option>
						<option data-skin="skin-1" value="#222A2D">#222A2D</option>
						<option data-skin="skin-2" value="#C6487E">#C6487E</option>
						<option data-skin="skin-3" value="#D0D0D0">#D0D0D0</option>
					</select>
				</div>
				<span>&nbsp; 选择皮肤</span>
			</div>

			<!-- /section:settings.skins -->

			<!-- #section:settings.navbar -->
			<div class="ace-settings-item">
				<input type="checkbox" class="ace ace-checkbox-2 ace-save-state"
					id="ace-settings-navbar" autocomplete="off" /> <label class="lbl"
					for="ace-settings-navbar"> 固定导航条</label>
			</div>

			<!-- /section:settings.navbar -->

			<!-- #section:settings.sidebar -->
			<div class="ace-settings-item">
				<input type="checkbox" class="ace ace-checkbox-2 ace-save-state"
					id="ace-settings-sidebar" autocomplete="off" /> <label class="lbl"
					for="ace-settings-sidebar"> 固定菜单栏</label>
			</div>

			<!-- /section:settings.sidebar -->

			<!-- #section:settings.breadcrumbs -->
			<div class="ace-settings-item">
				<input type="checkbox" class="ace ace-checkbox-2 ace-save-state"
					id="ace-settings-breadcrumbs" autocomplete="off" /> <label
					class="lbl" for="ace-settings-breadcrumbs"> 固定导航条菜单栏</label>
			</div>

			<!-- /section:settings.rtl -->

			<!-- #section:settings.container -->
			<div class="ace-settings-item">
				<input type="checkbox" class="ace ace-checkbox-2 ace-save-state"
					id="ace-settings-add-container" autocomplete="off" /> <label
					class="lbl" for="ace-settings-add-container"> 切换宽窄 </label>
			</div>

			<!-- /section:settings.container -->
		</div>
		<!-- /.pull-left -->

		<div class="pull-left width-50">
			<!-- #section:basics/sidebar.options -->
			<div class="ace-settings-item">
				<input type="checkbox" class="ace ace-checkbox-2"
					id="ace-settings-hover" autocomplete="off" /> <label class="lbl"
					for="ace-settings-hover"> 子菜单悬停 </label>
			</div>

			<div class="ace-settings-item">
				<input type="checkbox" class="ace ace-checkbox-2"
					id="ace-settings-compact" autocomplete="off" /> <label class="lbl"
					for="ace-settings-compact"> 紧凑菜单栏</label>
			</div>

			<div class="ace-settings-item">
				<input type="checkbox" class="ace ace-checkbox-2"
					id="ace-settings-highlight" autocomplete="off" /> <label
					class="lbl" for="ace-settings-highlight"> 激活菜单效果</label>
			</div>

			<!-- /section:basics/sidebar.options -->
		</div>
		<!-- /.pull-left -->
	</div>
	<!-- /.ace-settings-box -->
</div>
<!-- /.ace-settings-container -->