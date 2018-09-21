<div class="pagination">
			<c:set var="nxt" value="&nbsp;&nbsp;&nbsp;&nbsp;" />
			<c:set var="prev" value="&nbsp;&nbsp;&nbsp;&nbsp;" />
			<c:set var="showAll" value="" />
			<c:set var="x" value="${0}" />
			<c:if test="${page.pageSize>0}">
				<c:set var="x" value="${page.recordCount/page.pageSize}" />
				<c:if test="${(page.recordCount%page.pageSize )>0}">
					<c:set var="x" value="${x+1}" />
				</c:if>
			</c:if>
			<c:if test="${x > 0}">
				<tr>
					<td align="center" colspan="5">
						<div id="paginationDiv">
							<table width="100%" border="0" class=tableline>
								<tr>
									<td align="right">&nbsp;</td>
									<td align="right" width="10%"><b> <c:if
												test="${(page.recordCount!= page.pageSize)}">
												<c:if test="${page.firstRow>0}">
													<a
														href="javaScript:goNextPage('${page.firstRow-10}','10','${page.orderBy}','${page.order}','${page.recordCount}')"><<
														&nbsp;Previous</a>
												</c:if>
											</c:if>
									</b></td>
									<td align="right" width="8%"><b> <c:if
												test="${(page.recordCount!= page.pageSize)}">
												<c:if test="${(page.firstRow+10 < page.recordCount)}">
													<a
														href="javaScript:goNextPage('${page.firstRow+10}','10','${page.orderBy}','${page.order}','${page.recordCount}')">Next
														&nbsp;>> </a>
												</c:if>
											</c:if>
									</b></td>
									<td align="right" width="8%"><b> <c:if
												test="${(page.recordCount < 200) && (page.firstRow+10<page.recordCount)}">
												<a
													href="javaScript:goNextPage('0','${page.recordCount}','${page.orderBy}','${page.order}','${page.recordCount}')">show
													all</a>
											</c:if>
									</b></td>
									<td align="right" width="8%">Total : ${page.recordCount}</td>
								</tr>
							</table>
						</div>
					</td>
				</tr>
				<c:choose>
					<c:when test="${page.order==0}">
						<c:set var="od" value="1" />
						<c:set var="im" value="down" />
					</c:when>
					<c:otherwise>
						<c:set var="im" value="up" />
						<c:set var="od" value="0" />
					</c:otherwise>
				</c:choose>
			</c:if>
		</div>
		
	<script>
	
	function goNextPage(sc, mc, sortBy, ord, t) {
		url = 'firstRow=' + sc + '&pageSize=' + mc + '&orderBy=' + sortBy
				+ '&order=' + ord + '&recordCount=' + t;
		loadMorePages(url);
	}
	</script>