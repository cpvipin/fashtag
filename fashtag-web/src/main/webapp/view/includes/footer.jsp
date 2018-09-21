<div class="container-fluid footerMain">
            <div class="container">
                <div class="row mtb">
                    <div class="col-md-7  col-sm-6">
                        <div class="row list-parent">
                            <div class="col-md-4">
                        <h4>Fashtag</h4>
                                <ul>
                                    <li><a href="salesTerms">Sales,Delivery and Return policy</a></li>
                                    <li><a href="terms">Terms &amp; Conditions</a></li>
                                    <li><a href="privacy">Privacy Policy</a></li>
                                </ul>
                            </div>
                            <div class="col-md-4">
                                <h4>Site links</h4>
                                <ul>
                                    <li><a href="care.htm?page=contact">Contact Us</a></li>
                                    <li><a href="care.htm?page=complaint">Add Complaint</a></li>

                                    <li><a href="userPreference.htm?res=RETURNS">Return</a></li>
                                    <li><a href="trackOrder.htm">Order Status</a></li>
                                </ul>
                            </div>
                            <div class="col-md-4">
                                <h4>My Account</h4>
                                <ul>
                                   
                                    <li><a href="#" data-toggle="modal" data-target="#loginModal">Login/SignUp</a></li>
                                    <li><a href="#" data-toggle="modal" data-target="#loginModal">Forgot Password</a></li>
                                    <li><a href="userPreference.htm?res=INFO">My Account</a></li>
                                    <li><a href="userPreference.htm?res=INFO">Wishlist</a></li> 
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-5  col-sm-6">
                        <div class="row list-parent">
                            <div class="col-md-6">
                                <h4>Newsletter</h4>
                                <form action="subscribe.htm" method="post">
                                    <div class="input-group">
                                      <input type="text" class="form-control" name="subscribeEmailId" placeholder="Your Email" aria-describedby="basic-addon2">
                                      <span class="input-group-addon" id="basic-addon2"><span class="ion-android-mail"></span></span>
                                    </div>
                                </form>
                            </div>
                            <div class="col-md-6">
                                <div class="logo-footer"><img alt="" src="img/logo.png"></div>
                                <ul class="social">
                                    <li class="twitter"><a href="#"><span class="ion-social-twitter"></span></a></li>
                                    <li class="fb"><a href="#"><span class="ion-social-facebook"></span></a></li>
                                    <li class="gplus"><a href="#"><span class="ion-social-googleplus"></span></a></li>
                                    <li class="pinterest"><a href="#"><span class="ion-social-pinterest"></span></a></li>
                                </ul>
                                
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="container-fluid footerBottom">
            <div class="container">        
                <div class="row mtbs">
                    <div class="col-xs-6"> 
                        <p>&copy; 2016 &nbsp;|&nbsp; <a href="http://fashtag.in">fashtag.in</a>  &nbsp;|&nbsp; All rights reserved </p>
                    </div>
                    <div class="col-xs-6 text-right">
                        <p><a href="terms.htm">Terms</a> &nbsp;|&nbsp; <a href="privacy.htm">Policy</a></p>
                    </div>
                </div>
            </div>
        </div>







<!-- size-enter modal -->



<!-- measurements  modal -->
<div class="modal measurePopup fade bs-example-modal-lg" tabindex="-1"  role="dialog" aria-labelledby="sizeEnter" id="sizeEnter">
  <div class="modal-dialog modal-lg" role="document">
      <div class="modal-content">
        
        <div class="modal-header text-center">
            <h4>Body Measurement</h4>
        </div>
        <div class="row">
            <div class="col-md-6 col-md-push-6">
                <div class="paddingDiv">
<form action="addCustomerSize.htm" method="post" name="customerSizeFrm" id="customerSizeFrm" >

<div id="sizeContent">

</div>
                    </form>
                    
                </div><!-- /.paddingDiv -->
            </div><!-- /.col-md-6 -->
            <div class="col-md-6 col-md-pull-6">
                <div class="paddingDiv measureImg">
                   <figure>
                       <img src="img/measure.jpg" alt="" class="img-responsive">
                   </figure>
                </div><!-- /.paddingDiv -->
            </div><!-- /.col-md-6 -->
        </div><!-- /.row -->
    </div><!-- /.modal-content -->
  </div>
</div>


<!-- Change Password modal -->
<div class="modal changePass fade bs-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="changePassModal" id="changePassModal">
  <div class="modal-dialog modal-sm" role="document">
    <div class="modal-content">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span class="ion-android-close"></span></button>
        <div class="modal-header text-center">
            <h4>Change Password</h4>
        </div>
        <div class="row">
            <div class="col-md-12">
                <div class="paddingDiv">
<form action="setUserPassword.htm" method="post" name="changePasswordFrm" id="changePasswordFrm" >
                        <div class="form-group">
                                <input type="text" class="form-control" readonly name="userId" id="userId"  >
                              </div>
                              <div class="form-group">
                                <input type="password" class="form-control" name="password" id="password" placeholder="Password">
                              </div>
                              <div class="form-group">
                                <input type="password" class="form-control" name="confirmPassword" id="confirmPassword"  placeholder="Confirm Password">
                              </div>
             <button type="submit" class="btn btn-primary">Change Password</button>
                    </form>
                </div><!-- /.paddingDiv -->
            </div><!-- /.col-md-12 -->
        </div><!-- /.row -->
    </div><!-- /.modal-content -->
  </div>
</div>



<!-- Login-signup modal -->
<div class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="loginModal" id="loginModal">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
     <div class="row">
            <div class="col-md-12">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span class="ion-android-close"></span></button>
                <div class="paddingDiv">
                    <ul class="nav nav-tabs" role="tablist">
                        <li role="presentation" class="active"><a href="#login" aria-controls="login" role="tab" data-toggle="tab">Log in</a></li>
                        <li role="presentation"><a href="#signup" aria-controls="signup" role="tab" data-toggle="tab">Sign up</a></li>
                    </ul>
                  <!-- Tab panes -->
                    <div class="tab-content">
                        <div role="tabpanel" class="tab-pane active" id="login">

<form action="signIn.htm" method="post" name="signInFrm" id="signInFrm" >
                              <div class="form-group">
                                <input type="text" class="form-control"  name="user_id" id="user_id" placeholder="Email or Mobile">                              
                                </div>
                              <div class="form-group">
                                <input type="password" class="form-control"  name="password" id="password" placeholder="Password">                              
                                </div>
                              <p class="forgot-pass"><a  href="#forgotPass" aria-controls="forgotPass" role="tab" data-toggle="tab">Forgot Password?</a></p>

                              <button type="submit" class="btn btn-primary">Login</button>
                            </form>
<br/>
                        </div><!-- /#login -->
                        <div role="tabpanel" class="tab-pane" id="signup">
                            <form action="signUp.htm" name="signUpFrm" id="signUpFrm" method="post">
                              <div class="form-group">
                                <input type="text" class="form-control" id="fullName" name="fullName" placeholder="Your full Name">
                                <input type="hidden" name="gender" value="1" />
                              </div>
                                
                              <div class="form-group">
                                <input type="text" class="form-control" id="userId" name="userId" placeholder="Email or mobile">
                              </div>
                              <div class="form-group">
                                <input type="password" class="form-control" id="password" name="password" placeholder="Password">
                              </div>

                              <button type="submit" class="btn btn-primary">Sign up</button>
                              <br><br>
                            
                            
                            </form>
                           <br/>
                        </div><!-- /#signup -->
                        
                        
                        <div role="tabpanel" class="tab-pane" id="forgotPass">
                            <form action="forgotPassword.htm" name="forgotPassFrm" id="forgotPassFrm" method="post">
                              <div class="form-group">
                                <input type="text" class="form-control" id="userId" name="userId" placeholder="Registered email/mobile">
                                <p  class="forgot-pass"><a href="#login" aria-controls="login" role="tab" data-toggle="tab"> Go back to Log in.</a></p>
                              </div>
                              <button type="submit" class="btn btn-primary">Send Password</button>
                              <br><br>
                            
                            
                            </form>
                           <br/>
                        </div><!-- /#signup -->
                        
                      
                    </div><!-- /.tab-content -->

                </div><!-- /.paddingDiv -->


            </div><!-- /.col-md-7.col-md-push-5 -->
            <div class="col-md-5 col-md-pull-7 hidden">
           
                <div class="social-signup">
                    <ul>
                        <li>Custom Size Dress</li>
                        <li>Custome Designs Salwars saree blouse shawls etc</li>
                        <li>Free Stitching</li>
                        <li>Free Shipping</li>
                    </ul>
                </div>
                 
            </div><!-- /.col-md-7.col-md-push-5 -->
        </div><!-- /.row -->

    </div><!-- /.modal-content -->
  </div>
</div>


        <script src="${base}/js/includes/common.js"></script>
        <script src="${base}/js/icheck.min.js"></script>
        <!-- <script src="js/vendor/jquery-1.12.0.min.js"></script>-->
        <script src="${base}/js/slick.js"></script>
        <script src="${base}/js/plugins.js"></script>
        <script src="${base}/js/bootstrap.min.js"></script>
        <script src="${base}/js/includes/list_fns.js"></script>
        <script src="${base}/js/main.js"></script>
   		<script src="${base}/js/utils/CommonUtils.js"></script>

    </body>
</html>