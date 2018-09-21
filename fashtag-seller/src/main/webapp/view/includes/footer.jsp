</div>
		<!--[if IE]>
<script type="text/javascript" src="view/javascript/jquery/flot/excanvas.js"></script>
<![endif]-->
		<script type="text/javascript" src="./Dashboard_files/jquery.flot.js"></script>
		<script type="text/javascript"><!--
function getSalesChart(range) {
	$.ajax({
		type: 'get',
		url: 'index.php?route=common/home/chart&token=41c1be7657bdec2c3818fcf12dd76dd6&range=' + range,
		dataType: 'json',
		async: false,
		success: function(json) {
			var option = {	
				shadowSize: 0,
				lines: { 
					show: true,
					fill: true,
					lineWidth: 1
				},
				grid: {
					backgroundColor: '#FFFFFF'
				},	
				xaxis: {
            		ticks: json.xaxis
				}
			}

			$.plot($('#report'), [json.order, json.customer], option);
		}
	});
}

getSalesChart($('#range').val());
//--></script>
	</div>
	<div id="footer">
		<a href="http://fashtag.in">Fashtag</a> © <fmt:formatDate pattern="yyyy" value="${now}" /> All Rights Reserved.
	</div>
</body>
</html>