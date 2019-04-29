(function($){
	
	/* Initializing Bootstrap custom file input */
	bsCustomFileInput.init();	
	
	/* Initializing jQuery dataTable */
	$("#itemsList").DataTable({
		lengthMenu: [[5, 10, 20, -1], ['5 records', '10 records', '20 records', 'All records']],
		pageLenth: 5
		
	});
	
	/* Toggling checkboxes */
	$("#select-all-ckbx").on("click", function(evt){
		var selectAllCkbx = $(this);
		var alzSelectedCkbx = $("#itemsList .alz-selected");
		var type = selectAllCkbx.val();
			
		if(selectAllCkbx.is(":checked")) {
			alzSelectedCkbx.prop("checked", true);
		} else {
			alzSelectedCkbx.prop("checked", false);
		}
		
		buildQueryString(type);
	});
	
	
	/* Deleting selected product */
	$("#itemsList .alz-selected").on("click", function(){
		var type = $("#select-all-ckbx").val();
		buildQueryString(type);
	});
	
	/* Closing collapse */
	$("#btn-close-collapse").on('click', function(evt) {
		$("#collapse-form").collapse('hide');
	});
	
	$("#itemsList .switch-input").on("click", function(evt){
		evt.preventDefault();
		
		var title = '';
		
		var type = $("#select-all-ckbx").val();		
		var checkbox = $(this);		
		var value = checkbox.val();
		var url = alz.context + "/manage/{items}/" + value + "/activate";		
		
		switch(type) {
			case 'product':
				url = url.replace('{items}', 'products');
				title = 'Product';
				break;
				
			case 'category':
				url = url.replace('{items}', 'categories');
				title = 'Category';
				break;
		}
		
		$.ajax({
			type: 'get',
			url: url,			
			success: function(response, status) {
				if(response === 'success') {
					checkbox.prop('checked', checkbox.is(":checked") ? false : true);
					toastr.success(title + ' activation/deactivation succeed', title + ' Activation');
					
				} else {
					toastr.error(title + ' activation/deactivation failed', title + ' Activation');
					console.log(response);					
				}
			}
		});		
	});
	
	/* Launching modal form for product editing */
	$("#itemsList .btn-edit").on("click", function(evt){
		evt.preventDefault();
		
		var type = $("#select-all-ckbx").val();
		var href = $(this).attr("href");
		
		$.ajax({
			type: 'get',
			contentType: 'application/json;charset=utf-8',
			dataType: 'json',
			url: href,			
			success: function(item, status) {
				setItemFormFieldsValues(type, item);
				
				$("#collapse-form").collapse('show');
			}
		});				
	});	
	
	/* Deleting single item */
	$("#itemsList .btn-delete").on("click", function(evt){
		evt.preventDefault();	
		
		var type = $("#select-all-ckbx").val();
		var href = $(this).attr("href");
		$("#btn-confirmation-delete").attr("href", href);
		$("#confirmationModalDialog").modal();
	});
	
	/* Deleting selected items */
	$("#btn-delete-selected-items").on("click", function(evt){
		evt.preventDefault();	
		
		var href = $(this).attr("href");
		$("#btn-confirmation-delete").attr("href", href);
		$("#confirmationModalDialog").modal();
	});
	
	/* Deleting all items */
	$("#btn-delete-all").on("click", function(evt){
		evt.preventDefault();	
		
		var href = $(this).attr("href");
		$("#btn-confirmation-delete").attr("href", href);
		$("#confirmationModalDialog").modal();
	});
	
	function buildQueryString(type){
		var urlPattern = alz.context + "/manage/{items}/[queryString]/delete/selected";
		
		switch(type){
			case 'category':
				urlPattern = urlPattern.replace('{items}', 'categories');
				break;
				
			case 'product':
				urlPattern = urlPattern.replace('{items}', 'products');
				break;
		}
		
		setQueryString("#itemsList .alz-selected:checked", "#btn-delete-selected-items", urlPattern);
	}
	
	function getSelectedItemsIds(selectedCkbxSelector) {
		var selectedCkbx = $(selectedCkbxSelector);
		var queryString = "";
		
		if(selectedCkbx.length > 0) {
			for (var i = 0; i < selectedCkbx.length; i++) {
				queryString = queryString + $(selectedCkbx.get(i)).val();
				
				if(i < selectedCkbx.length -1) {
					queryString = queryString + "_";  
				}
			}			
		}		
		return queryString; 
	}
		
	/**
	 * selectedCkbxSelector : jQuery selector for checked checkboxes
	 * 
	 * btnDeleteSelectedItemsSelector : jQuery selector for the "Delete Selected Items" button
	 * 
	 * urlPattern: The url of the page to where ids will be carried(must contain the string [queryString] inside
	 */
	function setQueryString(selectedCkbxSelector, btnDeleteSelectedItemsSelector, urlPattern){
		var btnDeleteSelectedItems = $("#btn-delete-selected-items");
		var queryString = getSelectedItemsIds(selectedCkbxSelector);
		
		if(queryString !== "")			
			btnDeleteSelectedItems.attr("href", urlPattern.replace("[queryString]", queryString));
		else
			btnDeleteSelectedItems.attr("href", "#");
	}
	
	function setItemFormFieldsValues(type, item) {
		switch(type){
			case 'category':
				setCategoryFormFieldsValues(item);
				break;
				
			case 'product':
				setProductFormFieldsValues(item);
				break;
		}
	}
	
	function setProductFormFieldsValues(product) {
		var form = $("#form-add");
		
		form.find("#productId").val(product.productId);
		form.find("#name").val(product.name);
		form.find("#brand").val(product.brand);
		form.find("#views").val(product.views);
		form.find("#quantity").val(product.quantity);
		form.find("#unitPrice").val(product.unitPrice);
		form.find("#code").val(product.code);
		form.find("#purchases").val(product.purchases);
		form.find("#category").val(product.category.categoryId);
		form.find("#supplier").val(product.supplier.supplierId);
		form.find("#image").val(product.image);
		form.find("#description").val(product.description);
	}
	
	function setCategoryFormFieldsValues(category) {
		var form = $("#form-add");
		
		console.log(form);
		console.log(category);
		
		form.find("#categoryId").val(category.categoryId);
		form.find("#name").val(category.name);
		form.find("#active").val(category.active);
		form.find("#imgUrl").val(category.imgUrl);
		form.find("#description").val(category.description);
	}
	
	function resetForm(frm) {
		$.each(frm.find(".alz-ctr"), function(index, value){
			$(value).val("");
		});
	}
	
	function resetProductFormFields() {
		resetForm($("#form-add-product"));
	}
	
	function resetCategoryFormFields() {
		resetForm($("#form-add-category"));
	}
}(jQuery))