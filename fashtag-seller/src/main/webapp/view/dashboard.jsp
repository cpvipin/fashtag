
<%@ include file="includes/header.jsp"%>
		<div id="content">
			<div class="breadcrumb">
<a href="dashboard.htm">Home</a>
			</div>
			<div class="box">
				<div class="heading">
					<h1>
						<img src="image/home.png" alt=""> Dashboard
					</h1>
				</div>
				<div class="content">
					<div class="overview">
						<div class="dashboard-heading">Overview</div>
						<div class="dashboard-content">
							<table>
								<tbody>
									<tr>
										<td>Total Sales:</td>
										<td>-</td>
									</tr>
									<tr>
										<td>Total Sales This Year:</td>
										<td>-</td>
									</tr>
									<tr>
										<td>Total Orders:</td>
										<td>-</td>
									</tr>
									<tr>
										<td>No. of Customers:</td>
										<td>-</td>
									</tr>
									
								</tbody>
							</table>
						</div>
					</div>
					<div class="statistic">
						<div class="range">
							Select Range: <select id="range"
								onchange="getSalesChart(this.value)">
								<option value="day">Today</option>
								<option value="week">This Week</option>
								<option value="month">This Month</option>
								<option value="year">This Year</option>
							</select>
						</div>
						<div class="dashboard-heading">Statistics</div>
						<div class="dashboard-content">
							<div id="report"
								style="width: 390px; height: 170px; margin: auto; padding: 0px; position: relative;">
								<canvas class="base" width="390" height="170"></canvas>
								<canvas class="overlay" width="390" height="170"
									style="position: absolute; left: 0px; top: 0px;"></canvas>
								<div class="tickLabels" style="font-size: smaller">
									<div class="xAxis x1Axis" style="color: #545454">
										<div class="tickLabel"
											style="position: absolute; text-align: center; left: 16px; top: 159px; width: 16px">00</div>
										<div class="tickLabel"
											style="position: absolute; text-align: center; left: 32px; top: 159px; width: 16px">01</div>
										<div class="tickLabel"
											style="position: absolute; text-align: center; left: 47px; top: 159px; width: 16px">02</div>
										<div class="tickLabel"
											style="position: absolute; text-align: center; left: 63px; top: 159px; width: 16px">03</div>
										<div class="tickLabel"
											style="position: absolute; text-align: center; left: 79px; top: 159px; width: 16px">04</div>
										<div class="tickLabel"
											style="position: absolute; text-align: center; left: 95px; top: 159px; width: 16px">05</div>
										<div class="tickLabel"
											style="position: absolute; text-align: center; left: 110px; top: 159px; width: 16px">06</div>
										<div class="tickLabel"
											style="position: absolute; text-align: center; left: 126px; top: 159px; width: 16px">07</div>
										<div class="tickLabel"
											style="position: absolute; text-align: center; left: 142px; top: 159px; width: 16px">08</div>
										<div class="tickLabel"
											style="position: absolute; text-align: center; left: 158px; top: 159px; width: 16px">09</div>
										<div class="tickLabel"
											style="position: absolute; text-align: center; left: 173px; top: 159px; width: 16px">10</div>
										<div class="tickLabel"
											style="position: absolute; text-align: center; left: 189px; top: 159px; width: 16px">11</div>
										<div class="tickLabel"
											style="position: absolute; text-align: center; left: 205px; top: 159px; width: 16px">12</div>
										<div class="tickLabel"
											style="position: absolute; text-align: center; left: 221px; top: 159px; width: 16px">13</div>
										<div class="tickLabel"
											style="position: absolute; text-align: center; left: 236px; top: 159px; width: 16px">14</div>
										<div class="tickLabel"
											style="position: absolute; text-align: center; left: 252px; top: 159px; width: 16px">15</div>
										<div class="tickLabel"
											style="position: absolute; text-align: center; left: 268px; top: 159px; width: 16px">16</div>
										<div class="tickLabel"
											style="position: absolute; text-align: center; left: 284px; top: 159px; width: 16px">17</div>
										<div class="tickLabel"
											style="position: absolute; text-align: center; left: 299px; top: 159px; width: 16px">18</div>
										<div class="tickLabel"
											style="position: absolute; text-align: center; left: 315px; top: 159px; width: 16px">19</div>
										<div class="tickLabel"
											style="position: absolute; text-align: center; left: 331px; top: 159px; width: 16px">20</div>
										<div class="tickLabel"
											style="position: absolute; text-align: center; left: 347px; top: 159px; width: 16px">21</div>
										<div class="tickLabel"
											style="position: absolute; text-align: center; left: 362px; top: 159px; width: 16px">22</div>
										<div class="tickLabel"
											style="position: absolute; text-align: center; left: 378px; top: 159px; width: 16px">23</div>
									</div>
									<div class="yAxis y1Axis" style="color: #545454">
										<div class="tickLabel"
											style="position: absolute; text-align: right; top: 147px; right: 373px; width: 17px">-1.0</div>
										<div class="tickLabel"
											style="position: absolute; text-align: right; top: 110px; right: 373px; width: 17px">-0.5</div>
										<div class="tickLabel"
											style="position: absolute; text-align: right; top: 73px; right: 373px; width: 17px">0.0</div>
										<div class="tickLabel"
											style="position: absolute; text-align: right; top: 36px; right: 373px; width: 17px">0.5</div>
										<div class="tickLabel"
											style="position: absolute; text-align: right; top: -1px; right: 373px; width: 17px">1.0</div>
									</div>
								</div>
								<div class="legend">
									<div
										style="position: absolute; width: 117px; height: 38px; top: 9px; right: 9px; opacity: 0.85; background-color: rgb(255, 255, 255);">
									</div>
									<table
										style="position: absolute; top: 9px; right: 9px;; font-size: smaller; color: #545454">
										<tbody>
											<tr>
												<td class="legendColorBox"><div
														style="border: 1px solid #ccc; padding: 1px">
														<div
															style="width: 4px; height: 0; border: 5px solid rgb(237, 194, 64); overflow: hidden"></div>
													</div></td>
												<td class="legendLabel">Total Orders</td>
											</tr>
											<tr>
												<td class="legendColorBox"><div
														style="border: 1px solid #ccc; padding: 1px">
														<div
															style="width: 4px; height: 0; border: 5px solid rgb(175, 216, 248); overflow: hidden"></div>
													</div></td>
												<td class="legendLabel">Total Customers</td>
											</tr>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
					<div class="latest">
						<div class="dashboard-heading">Latest 10 Orders</div>
						<div class="dashboard-content">
							<table class="list">
								<thead>
									<tr>
										<td class="right">Order ID</td>
										<td class="left">Customer</td>
										<td class="left">Status</td>
										<td class="left">Date Added</td>
										<td class="right">Total</td>
										<td class="right">Action</td>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="listVar" items="${ordersList}" varStatus="counter">
					<tr>
					<td class="left"> ${counter.count}</td>
						<td>${listVar.customer.fullName}</td>
						<td>${listVar.ordersStatus.name}</td>
						<td>${viewTools.formatDate(listVar.dateAdded)}</td>
						<td>${listVar.totalAmount}</td>
						<td>
						[ <a href="viewOrders.htm?id=${listVar.id}">View</a> ]
							]
						</td>
					</tr>
				</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
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
		<a href="">Fashtag</a> © 2016 All Rights Reserved.
	</div>
</body>
</html>