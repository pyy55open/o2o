/**
 * 
 */
$(function(){
	var shopId = getQueryString("shopId");
	var isEdit = shopId?true:false;
	var initUrl = '/o2o/shopadmin/getshopinitinfo';
	var registerShopUrl='/o2o/shopadmin/addshop';
	var shopInfoUrl = '/o2o/shopadmin/getshopbyid?shopId='+shopId;
	var editShopUrl = '/o2o/shopadmin/modifyShop';
	if(isEdit){
		getShopInfo(shopId);
	}else{
		getShopInitInfo();
	}
	//根据id获取店铺信息，店铺信息修改页面
	function getShopInfo(shopId){
		$.getJSON(shopInfoUrl, function(data) {
			if (data.success) {
				// 若访问成功，则依据后台传递过来的店铺信息为表单元素赋值
				var shop = data.shop;
				$('#shop-name').val(shop.shopname);
				$('#shop-addr').val(shop.shopaddr);
				$('#phone').val(shop.phone);
				$('#shop-desc').val(shop.shopdesc);
				// 给店铺类别选定原先的店铺类别值
				var shopCategory = '<option data-id="'
						+ shop.shopCategory.shopCategoryid + '" selected>'
						+ shop.shopCategory.shopCategoryname + '</option>';
				var tempAreaHtml = '';
				// 初始化区域列表
				data.areaList.map(function(item, index) {
					tempAreaHtml += '<option data-id="' + item.areaid + '">'
							+ item.areaname + '</option>';
				});
				$('#shop-category').html(shopCategory);
				// 不允许选择店铺类别
				$('#shop-category').attr('disabled', 'disabled');
				$('#area').html(tempAreaHtml);
				// 给店铺选定原先的所属的区域
				$("#area option[data-id='" + shop.area.areaid + "']").attr(
						"selected", "selected");
			}
		});
	}
	//获取店铺类别、区域列表等基础信息，店铺注册页面
	function getShopInitInfo(){
		$.getJSON(initUrl,function(data){
			if(data.success){
				var tempHtml="";
				var tempAreaHtml="";
				data.shopCategoryList.map(function(item,index){
					tempHtml+="<option data-id ='"+item.shopCategoryid+"'>"
					+item.shopCategoryname+"</option>";
				});
				data.areaList.map(function(item,index){
					tempAreaHtml+="<option data-id ='"+item.areaid+"'>"
					+item.areaname+"</option>";
				});
				$("#shop-category").html(tempHtml);
				$("#area").html(tempAreaHtml);
			}
		});
	}
		$("#submit").click(function(){
			var shop ={};
			shop.shopname= $("#shop-name").val();
			shop.shopaddr= $("#shop-addr").val();
			shop.phone= $("#phone").val();
			shop.shopdesc= $("#shop-dsc").val();
			shop.shopCategory={
					shopCategoryid : $("#shop-category").find('option').not(function(){
					return !this.selected;
				}).data("id")	
			};
			shop.area={
					areaid : $("#area").find('option').not(function(){
					return !this.selected;
				}).data("id")
			};
			var shopImg = $("#shop_img")[0].files[0];
			var kaptcha = $("#kaptcha").val();
			var formData = new FormData();
			formData.append('imgStr',shopImg);
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
						$.toast("提交失败",data.msg);
					}
					$("#kaptcha_img").click();
				}
			})
		})
})