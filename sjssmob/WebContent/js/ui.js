$(function(){
	$('.m_btn').click(function(){
		$('.gnb_wrap').animate({'left':'0px'},100);
		$('.over').fadeIn(100);
	});
	$('.btn_closed').click(function(){
		$('.gnb_wrap').animate({'left':'-280px'},100);
		$('.over').fadeOut(100);
	});
	$('.over').click(function(){
		$('.gnb_wrap').animate({'left':'-280px'},100);
		$('.over').fadeOut(100);
	});
});

$(function() {
  var dates = $( "#from1, #to1,#from2, #to2,#SHIP_RQST_DATE" ).datepicker({
	showOn: "button",
	  buttonImage: "img/icon_date.gif",
	  buttonImageOnly: true,
  prevText: '이전 달',
  nextText: '다음 달',
  monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
  monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
  dayNames: ['일','월','화','수','목','금','토'],
  dayNamesShort: ['일','월','화','수','목','금','토'],
  dayNamesMin: ['일','월','화','수','목','금','토'],
  dateFormat: 'yy-mm-dd',
  showMonthAfterYear: true,
  yearSuffix: '년',
  onSelect: function( selectedDate ) {
  /*
	var option = this.id == "from1" ? "minDate" : "maxDate",
	  instance = $( this ).data( "datepicker" ),
	  date = $.datepicker.parseDate(
		instance.settings.dateFormat ||
		$.datepicker._defaults.dateFormat,
		selectedDate, instance.settings );
	dates.not( this ).datepicker( "option", option, date );
	*/
  }
  });


$(".tab_content").hide();
      $(".tab_content:first").show();
   
      $("ul.tabs li").click(function () {
         $("ul.tabs li").removeClass("active").css("background","#edeef1").css("color","#05074b");
         //$(this).addClass("active").css({"color": "darkred","font-weight": "bolder"});
         $(this).addClass("active").css("background","#96b2e9").css("color","#fff");
         $(".tab_content").hide()
         var activeTab = $(this).attr("rel");
         $("#" + activeTab).fadeIn()
      });
});


$(document).ready(function() {
	$('#btnTop').hide();
	$('#btnTop').unbind('click.btnTop').bind('click.btnTop', function(e) {
		e.preventDefault();

		$("html, body").animate({ scrollTop: 0 }, 500);
	});

	$(document).scroll(function() {

		var joBtnTop = $("#btnTop");
		var position = $(window).scrollTop();

		if (position > 250) { joBtnTop.fadeIn(500); }
		else if (position < 250) { joBtnTop.fadeOut(500); }

	});

});

$(function(){
	/* selectbox */
	var affi = $("div.sch_list"),
		affiBtn = affi.find(" > a"),
		affiSelect = affi.find(" > div"),
		affiElement = affi.find(" > div > ul > li > a");
	
	affiBtn.bind({
		"click": function(){affiSelect.toggleClass("on");affi.toggleClass("family_on");return false;},
		"focusout": function(){affiSelect.removeClass("on");affi.removeClass("family_on");}
	});
	affiSelect.bind({
		"focusin mouseenter": function(){affiSelect.addClass("on");affi.addClass("family_on")},
		"focusout mouseleave": function(){affiSelect.removeClass("on");affi.removeClass("family_on");}
	})
	affiElement.bind({
		"click": function(){$(this).parents().removeClass("on");affi.removeClass("family_on");}
	});

});