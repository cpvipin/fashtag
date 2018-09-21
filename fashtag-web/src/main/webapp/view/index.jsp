<%@ include file="includes/header.jsp"%>
<div class="container-fluid navMainOuter">
<div class="grey-bg"></div>
<div class="container">
<%@ include file="includes/inner_page_menu.jsp"%>
                <div class="row">
                    <div class="col-md-9 col-md-offset-3 pl0">
                        <div class="row">
                            <div class="col-md-9 intro pr0">
                                <div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
                                  <!-- Wrapper for slides -->
                                  <div class="carousel-inner" role="listbox">
                                  <c:forEach var="bannerImage" varStatus="counter"  items="${bannerImages}"  >
                                        <div class="item <c:if test="${counter.count==1}">active</c:if> ">
                                          <img src="${base}/img/banners/${bannerImage.image}" alt="" class="img-responsive">
                                        </div>
                                  </c:forEach>
                                  </div>
                                  <!-- Controls -->
                                  <a class="left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev"></a>
                                  <a class="right carousel-control" href="#carousel-example-generic" role="button" data-slide="next"></a>
                                </div>
                            </div>
                            <div class="col-md-3 hidden-xs hidden-sm pl0">
                                <div class="bluebg">
                                    <ul>
                                        <li><a href="javascript:void(0)" class="btn btn-default btn-border">Custom Size</a></li>
                                        <li><a href="javascript:void(0)" class="btn btn-default btn-border">Custom Design</a></li>
                                        <li><a href="javascript:void(0)" class="btn btn-default btn-border">Free Shipping</a></li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
        </div>
        </div>        
        <div class="container-fluid display-products">
<c:set var="categoryId" value="0" />
<c:set var="categoryCounter" value="0" />
   <c:forEach var="prodObj" items="${productList}" varStatus="counter">
   <c:if test="${categoryId!=prodObj.category.id}">
      <c:if test="${categoryCounter!=0}">
						</div>
						</div>
                        </div>
                       </div>
              </div>
          </div>
                 </div>
                 </div>
    </div>
</c:if>
	
	<c:set var="categoryCounter" value="0" />
     <div class="container mtbs">
     <div class="row">
    <div class="col-md-3 absolute-sm pr0">
        <div class="panel-title blackbg">
            <h4>${prodObj.category.name }</h4>
            <a href="listProducts.htm?catId=${prodObj.category.id}" class="pull-right absolute"><span class="ion-ios-arrow-forward"></span></a>
        </div>
    </div>
    <div class="col-md-9 col-sm-12 pl0">
        <div class="tab-wrapper  hidden-sm hidden-xs">
            <!-- Nav tabs -->
            <a href="#" class="pull-right absolute toggle-menu hidden-md hidden-lg"><span class="ion-android-menu"></span></a>
            <ul class="nav nav-tabs" role="tablist">                            
                <li role="presentation" class="active"><a href="#shirt-bs" aria-controls="best-sellers" role="tab" data-toggle="tab">Most Popular</a></li>
            </ul>
            </div>
    </div>
</div>
<div class="dividr"></div>
                <div class="row porduct-listing">
                    <div class="col-md-12">
                        <!-- Tab panes -->
                            <div class="tab-content productTab">
                                <div role="tabpanel" class="tab-pane active" id="shirt-bs">
                                    <div class="row">
                                        <div class="featured hidden-sm hidden-xs">
                                            <div class="product-box">
                                                <a href="productDetails.htm?prodId=${prodObj.id}" class="prodImg">
                                                    <img src="${viewTools.getProdImageUrl()}${prodObj.defaultImage}" alt="${prodObj.name}">
                                                </a>
                                                <div class="prodDetails">
                                                    <h5><a href="productDetails.htm?prodId=${prodObj.id}">${viewTools.getSubStr(prodObj.name,20)}</a></h5>
                                                    <p><strong>Rs. ${prodObj.offerPrice}</strong><strike>Rs. ${prodObj.actualPrice}
                                                     </strike><span>(${viewTools.getOffPercentage(prodObj.actualPrice,prodObj.offerPrice)}% off)</span></p>     
                                                </div>
                                                <footer>
                                                    <ul class="row no-margin flist">
<li class="col-xs-6"><a href="" class="likeThis" >
<input type="hidden" name="wishProd"  value="${prodObj.id}" />
<span class="ion-heart"></span>${prodObj.totalLikes}</a></li>
<!-- <li class="col-xs-4"><a href="javascript:void(0)" class="favThis"><span class="ion-star"></span>3.5</a></li> -->
<li class="col-xs-5"><a href="productDetails.htm?prodId=${prodObj.id}" class="cartThis"><span class="ion-android-cart"></span></a></li>
                                                    </ul>
                                                </footer>
                                            </div><!-- /.product-box -->
                                        </div>
                        <div class="col-md-12 prod-fullWidth">
                                            <div class="slider">
                                                <div class="regular slick">                  
   </c:if>
   <c:if test="${categoryId==prodObj.category.id }">
	<c:set var="categoryCounter" value="1" />
                                                    <div class="product-box">
                                                        <a href="productDetails.htm?prodId=${prodObj.id}" class="prodImg">
                                                            <img src="${viewTools.getProdImageUrl()}${prodObj.defaultImage}" alt="${prodObj.name }">
                                                        </a>
                                                        <div class="prodDetails">
                                                            <h5><a href="productDetails.htm?prodId=${prodObj.id}">
                                                            ${viewTools.getSubStr(prodObj.name,20)}
                                                            </a></h5>
                                                            <p><strong>Rs. ${prodObj.offerPrice}</strong><strike>Rs. ${prodObj.actualPrice }
                                                             </strike><span>(${viewTools.getOffPercentage(prodObj.actualPrice,prodObj.offerPrice)}% off)</span></p>     
                                                        </div>
                                                        <footer>
                                                            <ul class="row no-margin flist">
                                <li class="col-xs-6"><a href="#" class="likeThis"><input type="hidden" name="wishProd"  value="${prodObj.id}" />
                                <span class="ion-heart"></span>${prodObj.totalLikes}</a></li>
                                                                 <!-- <li class="col-xs-4"><a href="#" class="favThis"><span class="ion-star"></span>3.5</a></li> -->
                                                                <li class="col-xs-6"><a href="#" class="cartThis"><span class="ion-android-cart"></span></a></li>
                                                            </ul>
                                                        </footer>
                                                    </div><!-- /.product-box -->
  </c:if>
<c:set var="categoryId" value="${prodObj.category.id}" />            
 </c:forEach>
</div>
						</div>
                        </div>
                       </div>
              </div>
          </div>
                                                   </div>
                                            </div>
                                        </div>
        </div>
<%@ include file="includes/footer.jsp"%>
