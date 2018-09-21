<%@ include file="includes/header.jsp"%>
<script>
 	 $('body').addClass("innerPage");
	 $('body').addClass("listingPage");
 </script>
        <div class="container-fluid navMainOuter">
            <div class="grey-bg"></div>
            <div class="container">
            
<%@ include file="includes/inner_page_menu.jsp"%>



					<div class="row">
                    <div class="col-md-12">
                        <ol class="breadcrumb">
                          ${viewTools.getBreadCrumb(CATEGORY_ID)}
                        </ol>
                    </div>
                    
                    <div class="col-md-9 pl0 col-md-push-3">
                        <div class="row">
                            <div class="col-md-12 intro listPageSlider">
                                <div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
                                  <!-- Wrapper for slides -->
                                  <div class="carousel-inner" role="listbox">
                                    <c:forEach var="bannerImage" varStatus="counter"  items="${bannerImages}"  >
                                        <div class="item <c:if test="${counter.count==1}">active</c:if> ">
                                          <img src="${base}/img/banners/${bannerImage.image }" alt="" class="img-responsive">
                                        </div>
                                       </c:forEach> 
                                  </div>
                                  <!-- Controls -->
                                  <a class="left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev"></a>
                                  <a class="right carousel-control" href="#carousel-example-generic" role="button" data-slide="next"></a>
                                </div>   
                            </div>
                        </div><!-- /.row ***Carousel*** -->
                        
                        <div class="row prodList"></div><!-- /.row ***product listing*** -->                        
                        
                        
                    </div>
                    <div class="col-md-3 col-md-pull-9">
                       
                        <div class="widget filter priceFilter">
                            <header class="widgetHeader">
                                <h3>FILTER BY PRICE</h3>
                            </header>
                            <div class="widgetContent">
                                <ul>
                                    <li class="red"><div class="i-checks"><label> <input type="checkbox" name="priceFilter" value="0_500"> <i class="ion-checkmark"></i> 0 -500</label></div></li>
                                    <li class="blue"><div class="i-checks"><label> <input type="checkbox" name="priceFilter" value="500_1000"> <i class="ion-checkmark"></i> 500 - 1000</label></div></li>
                                    <li class="green"><div class="i-checks"><label> <input type="checkbox" name="priceFilter" value="1000_2500"> <i class="ion-checkmark"></i> 1000 - 2500</label></div></li>
                                    <li class="orange"><div class="i-checks"><label> <input type="checkbox" name="priceFilter" value="2500"> <i class="ion-checkmark"></i> 2500 - More</label></div></li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
<input type="hidden" name="catId" id="catId" value="${CATEGORY_ID}" />

       <%@ include file="includes/footer.jsp"%>
      