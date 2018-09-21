<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
                            
 <header class="widgetHeader">
                                <h3>Matching Products</h3>
                            </header>
                            <div class="widgetContent">
                            
                            
                                <ul>
                                
                                <c:forEach items="${matchingProducts}" var="matchingProd">
                                   	 <li>
                                        <div class="product-box">
                                            <a href="productDetails.htm?prodId=${matchingProd.id}" class="prodImg">
                                                <img src="${viewTools.getProdImageUrl()}${matchingProd.defaultImage}" alt="">
                                            </a>
                                            <div class="prodDetails">
                                                <h5><a href="productDetails.htm?prodId=${matchingProd.id}">${viewTools.getSubStr(matchingProd.name,15)}</a></h5>
                                                <p><strong>Rs. ${matchingProd.offerPrice}</strong><strike>Rs. ${matchingProd.actualPrice} </strike><span>(${viewTools.getOffPercentage(matchingProd.actualPrice,matchingProd.offerPrice)}% off)</span></p>     
                                            </div>
                                            <footer>
                                                <ul class="row no-margin flist">
                                                    <!-- <li class="col-xs-6"><a href="#" class="likeThis"><span class="ion-heart"></span> ${matchingProd.totalLikes}</a></li> -->
                                                    <li class="col-xs-6"><a href="productDetails.htm?prodId=${matchingProd.id}" class="cartThis">
                                                    <span class="ion-android-cart"></span></a>
                                                    </li>
                                                </ul>
                                            </footer>
                                        </div><!-- /.product-box -->
                                    </li>
                                    
                                    </c:forEach>
                                </ul>
                            </div>                           
                            
                            
