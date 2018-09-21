<%@ include file="includes/header.jsp"%>
 
        <div class="container-fluid navMainOuter">
            <div class="grey-bg"></div>
            <div class="container">
            
<%@ include file="includes/inner_page_menu.jsp"%>


					<div class="row contactPage">

                    
                    <div class="col-md-12">
                        <form method="post" name="careFrm" action="care.htm?page=submit">
<input type="hidden" name="careReq" value="${careReq}" />
                            <div class="form-group chooseOption mtb">
                                <select class="form-control" disabled name="careReq" id="choose">
                                    <option value="ContactUs" <c:if test="${careReq=='contact'}">selected</c:if> >Contact Us</option>
                                    <option value="Complaints" <c:if test="${careReq=='complaint'}">selected</c:if> >Complaints</option>
                                    <option value="Feedbacks" <c:if test="${careReq=='feedback'}">selected</c:if>>Feedbacks</option>
                                </select>
                            </div>

			<c:if test="${careReq=='contact'}">
                            <h1>Contact Us</h1>
  	                        <div class="row mbl">
                                <div class="col-md-7">
                                    <div class="form-group hasLabel">
                                        <label for="yourSubject">Your Subject</label>
                                        <input type="text" class="form-control" id="yourSubject" name="commonField" >
                                    </div>
                                    <div class="form-group hasLabel">
                                        <label for="yourMessage">Your Message</label>
                                        <textarea class="form-control" name="comment" placeholder=""></textarea>
                                    </div>

                                    <div class="row mts">
                                        <div class="col-xs-6">
                                        </div>
                                        <div class="col-xs-6">
                                            <button type="submit" class="btn btn-primary full-width">Submit</button>
                                        </div>
                                    </div>
                                </div>
            
                                <div class="col-md-5">
                        
                         <div class="widget">
                            <header class="widgetHeader">
                                <h3>Address</h3>
                            </header>
                            <div class="widgetContent">
                                <ul>
                                    <li><b>Fashtag </b></li>
                                    <li>Sivas Communication, Ottapalam Shopping complex</li>
                                    <li>Ottapalam, Palakkad(Dist)</li>
                                    <li>Kerala</li>
                                    <li>Pincode: 679102</li>
                                    <li>Phone: +91 8606074321</li>
                                </ul>
                            </div>
                        </div>
                        
                    </div>
			
			
                         </div> 
 		</c:if>                          



<c:if test="${careReq=='feedback'}">
                            <h1>Feedback</h1>
<p>
Your feedback against orders will be auto learned by application 
and will be taking into account for your next order  
using our machine learning implemented technology.
</p>
                        <br>
    
                            <div class="row mbl">
                                <div class="col-md-7">
                                    <div class="form-group hasLabel">
                                        <label for="yourSubject">Order Id</label>
                                        <input type="text" class="form-control"  name="commonField" >
                                    </div>
                                    <div class="form-group hasLabel">
                                        <label for="yourMessage">Your Feedback</label>
                                        <textarea class="form-control" name="comment" placeholder=""></textarea>
                                    </div>

                                    <div class="row mts">
                                        <div class="col-xs-6">
                                        </div>
                                        <div class="col-xs-6">
                                            <button type="submit" class="btn btn-primary full-width">Submit</button>
                                        </div>
                                    </div>
                                </div>
                         </div> 
 		</c:if>                          



<c:if test="${careReq=='complaint'}">
                            <h1>Complaints</h1>
<p>
Your complaints against orders will be auto learned by application and
rectified in your next order using our machine learning implemented technology.
</p>

                            <div class="row mbl">
                                <div class="col-md-7">
                                    <div class="form-group hasLabel">
                                        <label for="yourSubject">Order Id</label>
                                        <input type="text" class="form-control"  name="commonField" >
                                    </div>
                                    <div class="form-group hasLabel">
                                        <label for="yourMessage">Description </label>
                                        <textarea class="form-control" name="comment" placeholder=""></textarea>
                                    </div>

                                    <div class="row mts">
                                        <div class="col-xs-6">
                                        </div>
                                        <div class="col-xs-6">
                                            <button type="submit" class="btn btn-primary full-width">Submit</button>
                                        </div>
                                    </div>
                                </div>
                         </div> 
 		</c:if>     

                        </form>



                    </div>
                </div>
                

                

            </div>
        </div>
        
        <script>
 $(document).ready(function(){ 
 $('body').addClass("innerPage"); 
 });
 </script>
        <%@ include file="includes/footer.jsp"%>
        
