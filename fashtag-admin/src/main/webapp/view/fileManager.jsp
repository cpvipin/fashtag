<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" dir="ltr" lang="en" xml:lang="en">
<head>

<script type="text/javascript" src="js/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.8.16.custom.min.js"></script>
<link rel="stylesheet" type="text/css" href="css/jquery-ui-1.8.16.custom.css" />
<script type="text/javascript" src="js/jquery.bgiframe-2.1.2.js"></script>
<script type="text/javascript" src="js/jstree/jquery.tree.min.js"></script>
<script type="text/javascript" src="js/ajaxupload.js"></script>
<style type="text/css">
body {
	padding: 0;
	margin: 0;
	background: #F7F7F7;
	font-family: Verdana, Arial, Helvetica, sans-serif;
	font-size: 11px;
}
img {
	border: 0;
}
#container {
	padding: 0px 10px 7px 10px;
	height: 340px;
}
#menu {
	clear: both;
	height: 29px;
	margin-bottom: 3px;
}
#column-left {
	background: #FFF;
	border: 1px solid #CCC;
	float: left;
	width: 20%;
	height: 320px;
	overflow: auto;
}
#column-right {
	background: #FFF;
	border: 1px solid #CCC;
	float: right;
	width: 78%;
	height: 320px;
	overflow: auto;
	text-align: center;
}
#column-right div {
	text-align: left;
	padding: 5px;
}
#column-right a {
	display: inline-block;
	text-align: center;
	border: 1px solid #EEEEEE;
	cursor: pointer;
	margin: 5px;
	padding: 5px;
}
#column-right a.selected {
	border: 1px solid #7DA2CE;
	background: #EBF4FD;
}
#column-right input {
	display: none;
}
#dialog {
	display: none;
}
.button {
	display: block;
	float: left;
	padding: 8px 5px 8px 25px;
	margin-right: 5px;
	background-position: 5px 6px;
	background-repeat: no-repeat;
	cursor: pointer;
}
.button:hover {
	background-color: #EEEEEE;
}
.thumb {
	padding: 5px;
	width: 105px;
	height: 105px;
	background: #F7F7F7;
	border: 1px solid #CCCCCC;
	cursor: pointer;
	cursor: move;
	position: relative;
}
</style>
</head>
<body>
<div id="container">
  <div id="menu"><a id="create" class="button" style="background-image: url('image/folder.png');">Folder</a>
  <a id="delete" class="button" style="background-image: url('image/edit-delete.png');">
  Delete</a>
  <a id="move" class="button" style="background-image: url('image/edit-cut.png');">Move</a>
  <a id="copy" class="button" style="background-image: url('image/edit-copy.png');">Copy</a>
  <a id="rename" class="button" style="background-image: url('image/edit-rename.png');">Rename</a>
  <a id="upload" class="button" style="background-image: url('image/edit-upload.png');">Upload</a>
  <a id="refresh" class="button" style="background-image: url('image/edit-refresh.png');">Refresh</a></div>
  <div id="column-left"></div>
  <div id="column-right"></div>
</div>
<script type="text/javascript"><!--
$(document).ready(function() { 
	(function(){
		var special = jQuery.event.special,
			uid1 = 'D' + (+new Date()),
			uid2 = 'D' + (+new Date() + 1);
	 
		special.scrollstart = {
			setup: function() {
				var timer,
					handler =  function(evt) {
						var _self = this,
							_args = arguments;
	 
						if (timer) {
							clearTimeout(timer);
						} else {
							evt.type = 'scrollstart';
							jQuery.event.handle.apply(_self, _args);
						}
	 
						timer = setTimeout( function(){
							timer = null;
						}, special.scrollstop.latency);
	 
					};
	 
				jQuery(this).bind('scroll', handler).data(uid1, handler);
			},
			teardown: function(){
				jQuery(this).unbind( 'scroll', jQuery(this).data(uid1) );
			}
		};
	
		special.scrollstop = {
			latency: 300,
			setup: function() {
	 
				var timer,
						handler = function(evt) {
	 
						var _self = this,
							_args = arguments;
	 
						if (timer) {
							clearTimeout(timer);
						}
	 
						timer = setTimeout( function(){
	 
							timer = null;
							evt.type = 'scrollstop';
							jQuery.event.handle.apply(_self, _args);
	 
						}, special.scrollstop.latency);
	 
					};
	 
				jQuery(this).bind('scroll', handler).data(uid2, handler);
	 
			},
			teardown: function() {
				jQuery(this).unbind('scroll', jQuery(this).data(uid2));
			}
		}; 
	})();
	
	$('#column-right').bind('scrollstop', function() {
		$('#column-right a').each(function(index, element) {
			
			var height = $('#column-right').height();
			var offset = $(element).offset();
			var tree = $.tree.focused();
			
			if ((offset.top > 0) && (offset.top < height) ) {
				$.ajax({
					url: 'getActualImage.htm',
					type : "GET",
					data : {
						'image' : $(element).find('input[name=\'image\']').attr('value'),
						'selDir':$(tree.selected).attr('directory')
					},
					success: function(html) {
						var resp = JSON.parse(html);
						
						$(element).find('img').replaceWith('<img src="' + resp.imgUrl + '" alt="" title="" width="100" height="100" />');
 				}
				});
			}
		});
	});
	
	

	
	
	$('#column-left').tree({
		data: { 
			type: 'json',
			async: true, 
			opts: { 
				method: 'post', 
				url: 'getDirectories.htm'
			} 
		},
		selected: 'top',
		ui: {		
			theme_name: 'classic',
			animation: 700
		},	
		types: { 
			'default': {
				clickable: true,
				creatable: false,
				renameable: false,
				deletable: false,
				draggable: false,
				max_children: -1,
				max_depth: -1,
				valid_children: 'all'
			}
		},
		callback: {
			beforedata: function(NODE, TREE_OBJ) { 
				console.log(TREE_OBJ);
				if (NODE == false) {
					TREE_OBJ.settings.data.opts.static = [ 
						{
							data: 'image',
							attributes: { 
								'id': 'top',
								'directory': ''
							}, 
							state: 'closed'
						}
					];
					
					return { 'directory': '' } 
				} else {
					TREE_OBJ.settings.data.opts.static = false;  
					
					return { 'directory': $(NODE).attr('directory') } 
				}
			},		
			onselect: function (NODE, TREE_OBJ) {

				$.ajax({
					url: 'getFiles.htm',
					type: 'post',
					dataType: 'json',
					data: 'directory=' + encodeURIComponent($(NODE).attr('directory')),
					success: function(json) {
						
						html = '<div>';				
						if (json) {
							for (i = 0; i < json.length; i++) {
								html += '<a><img src="image/no_image-100x100.jpg" alt="" title="" /><br />' + ((json[i]['filename'].length > 15) ? (json[i]['filename'].substr(0, 15) + '..') : json[i]['filename']) 
								+ '<br />' + json[i]['size'] 
								+ '<input type="hidden" name="image" value="' + json[i]['filename'] + '" /></a>';
							}
						}
						
						html += '</div>';
						
						$('#column-right').html(html);

						$('#column-right').trigger('scrollstop');	
					},
					error: function(xhr, ajaxOptions, thrownError) {
						alert(thrownError + "\r\n" + xhr.statusText + "\r\n" + xhr.responseText);
					}
				});
			}
		}
	});	


	
	
	$('#column-right a').live('click', function() {
		if ($(this).attr('class') == 'selected') {
			$(this).removeAttr('class');
		} else {
			$('#column-right a').removeAttr('class');
			
			$(this).attr('class', 'selected');
		}
	});
	$('#column-right a').live('dblclick', function() {
		parent.$("#${field}").attr('value',$(this).find('input[name=\'image\']').attr('value'));
		parent.$("#${thumb}").replaceWith('<img src="' + $(this).find('img').attr('src')+'" alt="" id="${thumb}" />');
		parent.$('#dialog').dialog('close');
		parent.$('#dialog').remove();	
	});
	
	
	
	
	new AjaxUpload('#upload', {
		action: 'fileManagerUpload.htm',
		name: 'image',
		autoSubmit: false,
		responseType: 'json',
		onChange: function(file, extension) {
			var tree = $.tree.focused();
			
			if (tree.selected) {
				this.setData({'directory': $(tree.selected).attr('directory')});
			} else {
				this.setData({'directory': ''});
			}
			
			this.submit();
		},
		onSubmit: function(file, extension) {
			$('#upload').append('<img src="view/image/loading.gif" class="loading" style="padding-left: 5px;" />');
		},
		onComplete: function(file, json) {
			console.log(json);
			if (json.success) {
				var tree = $.tree.focused();
					
				tree.select_branch(tree.selected);
							}
			
			if (json.error) {
				alert(json.error);
			}
			
			$('.loading').remove();	
		}
	});
	
	
	
		
});
//--></script>
</body>
</html>