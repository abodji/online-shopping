(function($){
	
	/* Initializing Bootstrap custom file input */
	bsCustomFileInput.init();	
	
	/* Initializing jQuery dataTable */
	$("#itemsList").DataTable({
		lengthMenu: [[5, 10, 20, 50, -1], ['5 records', '10 records', '20 records', '50 records', 'All records']],
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
				
			case 'user':
				url = url.replace('{items}', 'users');
				title = 'User';
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
	
	/* Launching modal form for item editing */
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
	
	/* Launching modal box for adding new category */
	$("#add-category").on("click", function(evt) {
		evt.preventDefault();
		
		$("#modalNewCategory").modal();
	});
	
	$("#add-supplier").on("click", function(evt) {
		evt.preventDefault();
		
		$("#modalNewSupplier").modal();		
	});
	
	$("#modalNewCategory #btn-save-category").on("click", function(evt) {
		evt.preventDefault();
		
		sendModalData('#modalNewCategory form');
	});
	
	$("#modalNewSupplier #btn-save-supplier").on("click", function(evt) {
		evt.preventDefault();
		
		sendModalData('#modalNewSupplier form');
		
		/*
		var frm = $('#modalNewSupplier form');
		var data = {};
		$.each(frm.find(".alz-ctr"), function(index, value){
			var input = $(value);
			data[input.attr('name')] = input.val();
			delete data['undefined'];
		});
		
		var type = frm.attr("method");
		var href = frm.attr("action");
		
		$.ajax({
			type: type,
			data: data,
			url: href,			
			success: function(response, status) {
				
				if(response === 'success') {					
					
					toastr.success('Supplier added successfully.', 'New Supplier', {"timeOut" : 5000, "progressBar": true, "closeButton": true});
					
				} else if(response === 'failure:hasErrors') {
					toastr.error("Fail to add new supplier, there are some errors, please fill well the form and re-submit!", "Fail to add new supplier", {"timeOut" : 5000, "progressBar": true, "closeButton": true});
				} else if(response === 'failure:alreadyExist') {
					toastr.error("The supplier you want to add already exists, please fill well the form and re-submit!", "Fail to add new supplier");
				} else {
					toastr.error("Internal server error", "Fail to add new supplier");
				}
			}
		});		*/		
	});
	
	$(".btn-close-and-reload").on("click", function(evt) {
		window.location.reload();	
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
	
	function sendModalData(formCssSelector) {
		var frm = $(formCssSelector);
		var data = {};
		$.each(frm.find(".alz-ctr"), function(index, value){
			var input = $(value);
			data[input.attr('name')] = input.val();
			delete data['undefined'];
		});
		
		var type = frm.attr("method");
		var href = frm.attr("action");
		var title = href.indexOf('supplier') > 0 ? 'supplier' : 'category';
		
		
		$.ajax({
			type: type,
			data: data,
			url: href,			
			success: function(response, status) {
				toastr.options = {"timeOut" : 5000, "progressBar": true, "closeButton": true};
				
				if(response === 'success') {					
					
					toastr.success(title + ' added successfully.', 'New ' + title);
					
				} else if(response === 'failure:hasErrors') {
					toastr.error("Fail to add new " + title + ", there are some errors, please fill well the form and re-submit!", "Fail to add new " + title);
				} else if(response === 'failure:alreadyExist') {
					toastr.error("The " + title + " you want to add already exists, please fill well the form and re-submit!", "Fail to add new " + title);
				} else {
					toastr.error("Internal server error", "Fail to add new " + title);
				}
			}
		});	
	}
	
	function buildQueryString(type){
		var urlPattern = alz.context + "/manage/{items}/[queryString]/delete/selected";
		
		switch(type){
			case 'category':
				urlPattern = urlPattern.replace('{items}', 'categories');
				break;
				
			case 'product':
				urlPattern = urlPattern.replace('{items}', 'products');
				break;
				
			case 'supplier':
				urlPattern = urlPattern.replace('{items}', 'suppliers');
				break;
			
			case 'address':
				urlPattern = urlPattern.replace('{items}', 'addresses');
				break;
				
			case 'country':
				urlPattern = urlPattern.replace('{items}', 'countries');
				break;
				
			case 'privilege':
				urlPattern = urlPattern.replace('{items}', 'privileges');
				break;
				
			case 'role':
				urlPattern = urlPattern.replace('{items}', 'roles');
				break;
			
			case 'user':
				urlPattern = urlPattern.replace('{items}', 'users');
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
				
			case 'supplier':
				setSupplierFormFieldsValues(item);
				break;
			
			case 'address':
				setAddressFormFieldsValues(item);
				break;
				
			case 'country':
				setCountryFormFieldsValues(item);
				break;
				
			case 'privilege':
				setPrivilegeFormFieldsValues(item);
				break;
				
			case 'role':
				setRoleFormFieldsValues(item);
				break;
			
			case 'user':
				setUsertFormFieldsValues(item);
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
		
		form.find("#categoryId").val(category.categoryId);
		form.find("#name").val(category.name);
		form.find("#active").val(category.active);
		form.find("#imgUrl").val(category.imgUrl);
		form.find("#description").val(category.description);
	}
	
	function setSupplierFormFieldsValues(supplier) {
		var form = $("#form-add");
		
		form.find("#supplierId").val(supplier.supplierId);
		form.find("#name").val(supplier.name);
		form.find("#email").val(supplier.email);
		form.find("#phone").val(supplier.phone);
	}
	
	function setAddressFormFieldsValues(address) {
		var form = $("#form-add");
		
		form.find("#addressId").val(address.addressId);
		form.find("#addressType").val(address.addressType);
		form.find("#city").val(address.city);
		form.find("#company").val(address.company);
		form.find("#country").val(address.country.countryId);
		form.find("#stateProvinceRegion").val(address.stateProvinceRegion);
		form.find("#street").val(address.street);
		form.find("#zipCode").val(address.zipCode);
	}
	
	function setCountryFormFieldsValues(country) {
		var form = $("#form-add");
		
		form.find("#countryId").val(country.countryId);
		form.find("#code").val(country.code);
		form.find("#codeIso2").val(country.codeIso2);
		form.find("#capital").val(country.capital);
		form.find("#continent").val(country.continent);
		form.find("#localName").val(country.localName);
		form.find("#name").val(country.name);
		form.find("#region").val(country.region);
	}
	
	function setPrivilegeFormFieldsValues(privilege) {
		var form = $("#form-add");
		
		form.find("#privilegeId").val(privilege.privilegeId);
		form.find("#name").val(privilege.name);
		form.find("#description").val(privilege.description);
	}
	
	function setRoleFormFieldsValues(role) {
		var form = $("#form-add");
		
		form.find("#roleId").val(role.roleId);
		form.find("#name").val(role.name);
		form.find("#description").val(role.description);
		//form.find("#privileges").val(role.privileges);
	}
	
	function setUsertFormFieldsValues(user) {
		var form = $("#form-add");
		
		form.find("#userId").val(user.userId);
		form.find("#username").val(user.username);
		form.find("#firstName").val(user.firstName);
		form.find("#lastName").val(lastName.capital);
		form.find("#email").val(user.email);
		form.find("#password").val(user.password);
		form.find("#phone").val(user.phone);
		form.find("#region").val(user.region);
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