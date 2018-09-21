<div class="row">
                    <div class="col-md-3 col-sm-1 col-xs-6 pr0 relative catIcon">
                        <div class="blackbg">
                              <h4 class="dropdown text-left">
                                <span class="hidden-sm">Categories</span> 
                            </h4>
                        </div>
                        <div class="menudd dropdown  mega-dropdown">
                            <button class="btn btn-default dropdown-toggle" type="button" data-toggle="dropdown">
                            <span class="ion-android-arrow-dropdown"></span></button>
    <ul class="dropdown-menu lg-visible  mega-dropdown-menu">
                                <c:forEach var="catObj" items="${parentCategoryList}" varStatus="counter">
<li  class="dropdown-submenu">
<a href="javascript:void(0)" class="dropdown-toggle" data-toggle="dropdown" data-menuid="${catObj.id}">
<span><img src="${viewTools.getProdImageUrl()}/icons/${catObj.icon}"></span>${catObj.name} <span class="ion-chevron-right"></span></a>
 <ul class="dropdown-menu dropdown-menu-large row"></ul>
</li>
	</c:forEach> </ul>
                        </div>
                    </div>
                    
                    <div class="col-md-9 col-sm-11 col-xs-6 mainNav">
                        <nav class="navbar navbar-default1">
                          <div class="container-fluid no-padding">
                            <!-- Brand and toggle get grouped for better mobile display -->
                            <div class="navbar-header no-padding">
                              <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-2" aria-expanded="false">
                                <span class="sr-only">Toggle navigation</span>
                                <span class="icon-bar"></span>
                                <span class="icon-bar"></span>
                                <span class="icon-bar"></span>
                              </button>
                            </div>

                            <!-- Collect the nav links, forms, and other content for toggling -->
                            <div class="collapse navbar-collapse no-padding" id="bs-example-navbar-collapse-2">
                              <ul class="nav navbar-nav">                                
                                 <li class="current"><a href="index.htm">Home</a></li>
                                 <li><a href="index.htm">How it works</a></li>
                                <li><a href="index.htm">How to Measure</a></li>
                              </ul>
                            </div><!-- /.navbar-collapse -->
                          </div><!-- /.container-fluid -->
                        </nav>
                    </div>
                </div>
                <div class="dividr"></div>