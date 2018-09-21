<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
                            <div class="col-md-12">
                                <div class="listHeader">
                                    <div class="pull-left">
                                        <ul class="listTitle">
                                            <li><span><img src="${CATEGORY.icon}" alt=""></span></li>
                                            <li><strong>${CATEGORY.name}</strong>   |  ${RESULT_COUNT} results</li>
											                                          
                                        </ul>
                                    </div>
                                    <div class="pull-right">
                                        <ul>
                                        <li>
                                                <select>
                                                    <option value="recent">Most Recent</option>
                                                </select>
                                            </li>
                                            <li class="pr0">
                                                <a href="javascript:void(0);" class="switchLink switchGrid current"></a>
                                            </li>
                                            <li>
                                                <a href="javascript:void(0);" class="switchLink switchList"></a>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                            <div class="clearfix"></div>
                            <div class="col-md-12">
                                <div class="row listRow mb">
                                    <c:forEach var="product" items="${PRODUCT_LIST}"> 
                                    <div class="col-md-4 col-sm-6">
                                        <div class="product-box">
                                            <a href="productDetails.htm?prodId=${product.id}" class="prodImg">
                                                <img src="${viewTools.getProdImageUrl()}${product.defaultImage}" alt="${product.name}">
                                            </a>
                                            <div class="prodDetails">
                                                <h5><a href="productDetails.htm?prodId=${product.id}">${product.name}</a></h5>
                                                <p><strong>${product.offerPrice}</strong><strike>${product.actualPrice}</strike>
                                                <span>(${viewTools.getOffPercentage(product.actualPrice,product.offerPrice)}% off)</span></p>     
                                            </div>
                                            <footer>
                                                <ul class="row no-margin flist">
                                                    <li class="col-xs-6"><a href="" class="likeThis">
                                                    <input type="hidden" name="wishProd"  value="${product.id}" />
                                                    <span class="ion-heart"></span>${product.totalLikes}</a></li>
                                                    <!-- <li class="col-xs-4"><a href="#" class="favThis"><span class="ion-star"></span>3.5</a></li> -->
                                                    <li class="col-xs-6"><a href="productDetails.htm?prodId=${product.id}" class="cartThis">
                                                    <span class="ion-android-cart"></span></a></li>
                                                </ul>
                                            </footer>
                                        </div><!-- /.product-box -->
                                    </div><!-- /.col-md-4 -->
                                    
                                    </c:forEach>
                                    
                                    
                                    
                                </div>
                            </div>
<script>
$('.switchLink').click(function(){
	$('.switchLink').removeClass('current').parents('.prodList').find('.listRow').removeClass('list-view');
	if($(this).hasClass('switchList')){
		$(this).addClass('current').parents('.prodList').find('.listRow').addClass('list-view');
	}
	else if($(this).hasClass('switchGrid')){
		$(this).addClass('current').parents('.prodList').find('.listRow').removeClass('list-view');
	}
    
 });
 
</script>    

