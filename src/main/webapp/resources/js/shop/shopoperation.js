/**
 * 
 */
$(function(){
	var initUrl = 'getshopinitinfo';
	var registerShopUrl='o2o/shop/addShop';
	getShopInitInfo();
	function getShopInitInfo(){
		$.getJSON(initUrl,function(data){
			if(data.success){
				var tempHtml="";
				var tempAreaHtml="";
				data.shopCategoryList.map(function(item,index){
					tempHtml+="<option data-id ='"+item.shopCategoryId+"'>"
					+item.shopCategoryName+"</option>";
				});
				data.areaList.map(function(item,index){
					tempAreaHtml+="<option data-id ='"+item.areaId+"'>"
					+item.areaName+"</option>";
				});
				$("#shop-category").html(tempHtml);
				$("#area").html(tempAreaHtml);
			}
		});
	}
		$("#submit").click(function(){
			var shop ={};
			shop.shopName= $("#shop_name"),val();
			shop.shopAddr= $("#shop_addr"),val();
			shop.photo= $("#photo"),val();
			shop.shopDsc= $("#shop_dsc"),val();
			shop.shopcategory={
				shopCategoryId : $("#shop_category").find('option').not(function(){
					return !this.selected;
				}).data("id")	
			};
			shop.area={
				areaId : $("#area").fin('option').not(function(){
					return !this.selected;
				}).data("id")
			};
			var shopImg = $("#shop_img")[0].files[0];
			var kaptcha = $("#kaptcha_img").val();
			var formData = new FormData;
			formData.append('shopImg',shopImg);
			formData.append('shopStr',JSON.stringify(shop));
			if(!kaptcha){
				$.toast("请输入验证码");
				return;
			}
			formData.append('kaptcha',kaptcha);
			$.ajax({
				url : registerShopUrl,
				type : "POST",
				data : formData,
				contentType : false,
				processData : false,
				cache : false,
				success:function(data){
					if(data.success){
						$.toast("提交成功");
					}else{
						$.toast("提交失败",data.errorMsg);
					}
					$("#kaptcha_img").click();
				}
			})
		})
})