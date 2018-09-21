<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>



<div class="paddingDiv">
	<div class="styleDiv">

		<section>
		<div id="styleCarousel" class="carousel slide" data-ride="carousel"  data-wrap="false">
			<a class="sc-control left" href="#styleCarousel" data-slide="prev"><span
				class="ion-ios-arrow-back"></span> <em>Previous</em>
			</a> <a class="sc-control right" href="#styleCarousel" data-slide="next"><em>Next</em>
				<span class="ion-ios-arrow-forward"></span>
			</a>
			<div class="carousel-inner" style="overflow: visible" id="scrollwrapper">
				<c:set var="oldDesignAttrId" value="0" />
				<c:forEach var="prodToDesignAttrSpec" varStatus="counter"
					items="${productDesignsColl}">
					<c:set var="isRecom" value="" />
					<c:if
						test="${prodToDesignAttrSpec.designAttributeSpecification.designAttribute.id!=oldDesignAttrId}">
						<c:if test="${oldDesignAttrId != 0}">
			</div>
			<!-- /.row -->
		</div>
		</c:if>
		<div class="<c:if test="${counter.count==1}">active</c:if> item">
			<header class="text-center">
			<h3>${prodToDesignAttrSpec.designAttributeSpecification.designAttribute.name}</h3>
			</header>
			<div class="row scroll">
				</c:if>

				<c:if test="${viewTools.isSelectedDesign(custDesign,prodToDesignAttrSpec.designAttributeSpecification.id)== true}">
					<c:set var="isRecom" value="selected" />
				</c:if>

				<div class="col-md-4 col-sm-6" id="custDesElem">
					<a href="javascript:void(0)"
						data-specid="${prodToDesignAttrSpec.designAttributeSpecification.id}"
						class="productStyle ${isRecom}"> <img
						src="${viewTools.getDesAttrImageUrl()}${prodToDesignAttrSpec.designAttributeSpecification.image}">
						<span>${prodToDesignAttrSpec.designAttributeSpecification.name}</span>
						  <span class="small free">FREE</span>
					</a>
				</div>
				<c:set var="oldDesignAttrId"
					value="${prodToDesignAttrSpec.designAttributeSpecification.designAttribute.id}" />

				</c:forEach>
		
			</div>
		</div>
		</section>
	</div>
	<!-- /.styleDiv -->
</div>
<!-- /.paddingDiv -->
<script>
$('.productStyle').click(function(){
    $(this).closest('.row').find('.productStyle').removeClass('selected');
    $(this).addClass('selected');
 });
 
 $(function() {
      // Cycles to the next item
      $(".productStyle").click(function() {
         $("#styleCarousel").carousel('next');
      });
      
   });
   
//Nice scroll
//$(".scroll").niceScroll({cursorcolor:"#029AE4", touchbehavior:true}).resize();
 
 
$('#styleCarousel').carousel({
    interval: false
}); 



</script>

