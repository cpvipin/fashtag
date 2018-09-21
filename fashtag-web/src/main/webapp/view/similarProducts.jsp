<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
                                
                                <c:forEach items="${similarProducts}" var="similarProd" varStatus="counter">
                                
                                <c:if test="${counter.count==1}">
                                <div class="row">
                                    <div class="col-md-12">
                                        <h3>Similar Products</h3>
                                    </div>
                                </div>
                                <div class="row listRow mb">
                                </c:if>
                                
                                
                                
                                   	 <div class="col-md-4 col-sm-6">
                                        <div class="product-box">
                                            <a href="productDetails.htm?prodId=${similarProd.id}" class="prodImg">
                                                <img src="${viewTools.getProdImageUrl()}${similarProd.defaultImage}" alt="${similarProd.name}">
                                            </a>
                                            <div class="prodDetails">
                                                <h5><a href="productDetails.htm?prodId=${similarProd.id}">${viewTools.getSubStr(similarProd.name,20)} </a></h5>
                                                <p><strong>Rs. ${similarProd.offerPrice}</strong><strike>Rs. ${similarProd.actualPrice} </strike><span>(${viewTools.getOffPercentage(similarProd.actualPrice,similarProd.offerPrice)}% off)</span></p>     
                                            </div>
                                            <footer>
                                                <ul class="row no-margin flist">
                                                    <li class="col-xs-6"><a href="#" class="likeThis"><span class="ion-heart"></span>${similarProd.totalLikes}</a></li>
                                                    <li class="col-xs-6">
                                                    <a href="productDetails.htm?prodId=${similarProd.id}" class="cartThis">
                                                    <span class="ion-android-cart"></span></a></li>
                                                </ul>
                                            </footer>
                                        </div><!-- /.product-box -->
                                    </div><!-- /.col-md-4 -->
                                    
                                    
                                    
                                    </c:forEach>
                                </div>
                               
