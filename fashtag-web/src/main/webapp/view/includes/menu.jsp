<div class="container-fluid navMainOuter">
            <div class="grey-bg"></div>
            <div class="container">
                <div class="row">
                    <div class="col-md-3 col-sm-1 col-xs-6 pr0 relative catIcon">
                        <div class="blackbg">
                              <h4 class="dropdown text-left">
                                <span class="hidden-sm">Categories</span> 
                            </h4>
                        </div>
                        <div class="menudd dropdown">
                            <button class="btn btn-default dropdown-toggle" type="button" data-toggle="dropdown">
                            <span class="ion-android-arrow-dropdown"></span></button>
    <ul class="dropdown-menu lg-visible">
                                <c:forEach var="catObj" items="${parentCategoryList}" varStatus="counter">
<li  class="dropdown-submenu">
<a href="#" class="dropdown-toggle" data-toggle="dropdown" data-menuid="${catObj.id}">
<span><img src="img/menu/${catObj.icon}"></span>${catObj.name} <span class="ion-chevron-right"></span></a>
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
                                <li><a href="index.htm">Home</a></li>
                                <li><a href="#">How it works</a></li>
                                <li><a href="#">How to Measure</a></li>
                              </ul>
                            </div><!-- /.navbar-collapse -->
                          </div><!-- /.container-fluid -->
                        </nav>
                    </div>
                </div>
                <div class="dividr"></div>
                <div class="row">
                    <div class="col-md-9 col-md-offset-3 pl0">
                        <div class="row">
                            <div class="col-md-9 intro pr0">

                                <div id="carousel-example-generic" class="carousel slide" data-ride="carousel">

                                  <!-- Wrapper for slides -->
                                  <div class="carousel-inner" role="listbox">
                                        <div class="item active">
                                          <img src="img/banner.jpg" alt="" class="img-responsive">
                                        </div>
                                        <div class="item">
                                          <img src="img/banner.jpg" alt="" class="img-responsive">
                                        </div>
                                  </div>

                                  <!-- Controls -->
                                  <a class="left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev"></a>
                                  <a class="right carousel-control" href="#carousel-example-generic" role="button" data-slide="next"></a>
                                </div>

                                
                            </div>
                            <div class="col-md-3 hidden-xs hidden-sm pl0">
                                <div class="bluebg">
                                    <ul>
                                        <li  class="btn btn-default btn-border">Custom Size</li>
                                        <li  class="btn btn-default btn-border">Custom Design</li>
                                        <li  class="btn btn-default btn-border">Free Stitching</li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>