<%@ include file="includes/header.jsp"%>
 
        <div class="container-fluid navMainOuter">
            <div class="grey-bg"></div>
            <div class="container">
            
<%@ include file="includes/inner_page_menu.jsp"%>


					<div class="row minHeight">

	<h1>Tracking Order</h1>
                        <p>
     You can track your order here. In case of delaying your order don't hesitate to contact us -Email: ${viewTools.getSupportEmail()} or call at ${viewTools.getSupportPhone()}                    
                        </p>
                        <br>

                        <div class="form-group chooseOption mtb">
                        <form action="trackOrder.htm" method="post" >
                            <div class="form-group hasLabel">
                                        <label for="new-pass">Enter Order Id</label>
                                        <input type="text" class="form-control" name="orderId">
                            </div>
                            <div class="form-group hasLabel">
                                        <input type="submit" class="btn btn-primary" value="Track Order" >
                           </div>        
                                    
                       </form>
                        </div>


                        <ul class="timeline col-xs-10 col-xs-offset-2 col-md-offset-1 mt mbl">
                        
                        <c:forEach var="orderHis" items="${orderHisColl}">      
                            <li class="">
                                <div class="timelineBox">
                                    <div class="timelineTitle">
                                        <h4>${orderHis.ordersStatus.name}</h4>
                                        <p class="small">${viewTools.formatDate(orderHis.dateAdded)}&nbsp;</p>
                                    </div>
                                    <div class="timelineContent">
                                        <p class="small">
                                        ${orderHis.comment}
                                        .</p>
                                    </div>
                                </div>
                            </li>
                        </c:forEach>
                        
                        </ul>
                        
                        
                </div>
                

                

            </div>
        </div>
        
        <script>
 $(document).ready(function(){ 
 $('body').addClass("innerPage"); 
 });
 </script>
        <%@ include file="includes/footer.jsp"%>
        
