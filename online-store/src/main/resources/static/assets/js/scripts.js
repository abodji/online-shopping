(function($){
	var i = $("#pourCpteVirement input");
	i.on("click", function() {
		var radio = $(this);
		if(radio.val() == "OT") {
			$("#cpteVirement").removeClass("alz-hidden");
		} else {
			$("#cpteVirement").addClass("alz-hidden");
		}
	});
	
	/* Deleting selected items */
	$(".delete-selected-items").on("click", function(){
		var selected = $(".alz-list .alz-selected:checked");
		
		if(selected.length > 0) {
			var sel = [];
			
			for (var i = 0; i < selected.length; i++) {
				sel[i] = $(selected.get(i)).val();
			}
			
		}
	});
	
	(function(){
		$('#save-client-btn').on('click', function (evt) {
			
			evt.preventDefault();
			
			var frm = $('#save-client-form');
			var data = {};
			$.each(frm.find(".alz-ctr"), function(index, value){
				var input = $(value);
				data[input.attr('name')] = input.val();
				delete data['undefined'];
			});
			
			saveRequestData(frm, data, 'client');	
		})
	})();
		
	function saveRequestData(form, data, type) {
		$.ajax({
			contentType: 'application/json;charset=utf-8',
			type: form.attr('method'),
			url: form.attr('action'),
			dataType: 'json',
			data: JSON.stringify(data),
			
			success: function(resp) {
				displayMsgBox(form, resp);
			}
		});
	}
	
	
	function displayMsgBox(form, resp) {
		var alertBox = form.find(".alert-message");
		alertBox.html(resp.message);
		
		if(resp.status == "OK"){
			alertBox.parent().addClass('show').removeClass('alert-danger').addClass('alert-success');
		} else {
			alertBox.parent().addClass('show').addClass('alert-danger').removeClass('alert-success');
		}
	}
}(jQuery))