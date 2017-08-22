	 $(function(){ 	
		
		/* Top Menu */
		$("#topNavi .gnb > ul > li > a").mouseenter(function(){ // top메뉴 마우스오버효과
			
			$("#topNavi .gnb > ul > li > a").removeClass("on");
			$("#topNavi .gnb > ul > li > .subWrap").removeClass("subOn");
			$(".subWrap").slideDown("fast");
			$(".subBg").slideDown("fast");			
			$(".closeMenu").css("display","block");			
			
			$(this).addClass("on");
			$(this).parent().find(".subWrap").addClass("subOn");
			return false;
		}) // mouseover
		
		$(".subBg").mouseleave(function(){		     //top메뉴 영역 마우스 떠날때
			$(".subWrap").slideUp("fast");
			$(".subBg").slideUp("fast");			
			$(".closeMenu").css("display","none");
			$("#topNavi .gnb > ul > li > a").removeClass("on");
		}) // mouseleave		
		
		
		$("#topNavi .gnb > ul > li > a").focus(function(){			//top메뉴영역 포커스 들어올때			
			$("#topNavi .gnb > ul > li > a").removeClass("on");
			$("#topNavi .gnb > ul > li > .subWrap").removeClass("subOn");
			$(".subWrap").slideDown("fast");
			$(".subBg").slideDown("fast");			
			$(".closeMenu").css("display","block");			
			
			$(this).addClass("on");
			$(this).parent().find(".subWrap").addClass("subOn");
			return false;
		})
		
		
		$(".closeMenu > a").click(function(){   // 닫기
			$(".subWrap").slideUp("fast");
			$(".subBg").slideUp("fast");			
			$(".closeMenu").css("display","none");
			$("#topNavi .gnb > ul > li > a").removeClass("on");
		}) // click
		
		$(".closeMenu").focusout(function(e){
			//alert(e.target.tagName.className);
 			$(".subWrap").slideUp("fast");
			$(".subBg").slideUp("fast");			
			$(".closeMenu").css("display","none");
			$("#topNavi .gnb > ul > li > a").removeClass("on");
 		});
		 	
	}) // function


		$(document).ready(function(){

		$('button').hover(
		function(){
			$(this).stop().fadeTo('fast',0.6);
		},
		function(){
			$(this).stop().fadeTo('fast',1);
		});

		});


$(document).ready(function(){

			
// GNB
		$('#sm li:first').css({'border-left':'0'});
		$("#sm").mouseover(function(e) { $(this).addClass("over"); });
		$("#sm").mouseout(function(e) { $(this).removeClass("over"); });
		/* submenu rollover */
		$(".nav").hover(function() {
			$(this).doTimeout('hover',300, function(){
				$("#sm").slideDown({duration:300 , easing: 'easeInQuad'});
			});
		},function() {
			$(this).doTimeout('hover',300, function(){
				smMouseOut("#sm");
			});
		});
		$('.sm-close').click(function(){ $("#sm").slideUp({duration:300 , easing: 'easeInQuad'});});

		
		// roll
		var rollover = {
			newimage: function(src) {
				return src.substring(0, src.search(/(\.[a-z]+)$/) ) + '_on' + src.match(/(\.[a-z]+)$/)[0];
			},
			oldimage: function(src) {
				return src.replace(/_on\./, '.');
			},
			init: function() {
				$("#gnb li.act>a>img, #sm p.act>a>img").each( function() {
					$(this).removeClass('roll').attr( 'src', rollover.newimage($(this).attr('src')) );
				} );
				$(".roll").hover(
					function () { $(this).attr( 'src', rollover.newimage($(this).attr('src')) ); },
					function () { $(this).attr( 'src', rollover.oldimage($(this).attr('src')) ); }
				);
			}
		};
		rollover.init();

		


	});

	function smMouseOut(id){
		if ($(id).hasClass("over")){
			$(id).mouseleave(function(){
				$(id).slideUp({duration:300 , easing: 'easeInQuad'});
			});
		} else{
			$(id).slideUp({duration:300 , easing: 'easeInQuad'});
		}
	}	



//Element ID 불러쓰기
function dEI(elementID){
	return document.getElementById(elementID);
}
//이미지룰오버
function imgRollover(imgBoxID){
	var MenuCounts = dEI(imgBoxID).getElementsByTagName("img");
	for (i=0;i<MenuCounts.length;i++) {

		var numImg=MenuCounts.item(i);
		var ImgCheck = numImg.src.substring(numImg.src.length-6,numImg.src.length);
		if (ImgCheck!="on.png") {
				numImg.onmouseover = function () {
					this.src = this.src.replace(".png", "on.png");
				}
				numImg.onmouseout = function () {
					this.src = this.src.replace("on.png", ".png");
				}
			}
	}
}

//팝업레이어1
function view_show(num)
{
	dis_chk = eval("document.getElementById('dispay_view"+num+"')");
	dis_chk.style.display = "block";
	document.getElementById("footerBG").style.display = "block";
}


function view_hide(num)
{
	dis_chk = eval("document.getElementById('dispay_view"+num+"')");
	dis_chk.style.display = "none";
	document.getElementById("footerBG").style.display = "none";
}
