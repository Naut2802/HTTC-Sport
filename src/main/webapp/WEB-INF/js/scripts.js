/*!
    * Start Bootstrap - SB Admin v7.0.7 (https://startbootstrap.com/template/sb-admin)
    * Copyright 2013-2023 Start Bootstrap
    * Licensed under MIT (https://github.com/StartBootstrap/startbootstrap-sb-admin/blob/master/LICENSE)
    */
    // 
// Scripts
// 

window.addEventListener('DOMContentLoaded', event => {

    // Toggle the side navigation
    const sidebarToggle = document.body.querySelector('#sidebarToggle');
    if (sidebarToggle) {
        // Uncomment Below to persist sidebar toggle between refreshes
        // if (localStorage.getItem('sb|sidebar-toggle') === 'true') {
        //     document.body.classList.toggle('sb-sidenav-toggled');
        // }
        sidebarToggle.addEventListener('click', event => {
            event.preventDefault();
            document.body.classList.toggle('sb-sidenav-toggled');
            localStorage.setItem('sb|sidebar-toggle', document.body.classList.contains('sb-sidenav-toggled'));
        });
    }

});
function showToast(message, isSuccess) {
	var toastClass = isSuccess ? 'toast-success' : 'toast-error';
	var toast = document.createElement('div');
	toast.classList.add('toastMsg', toastClass);
	toast.innerText = message;
	document.body.appendChild(toast);

	console.log(document.body.appendChild(toast));
	setTimeout(function() {
		toast.style.opacity = '1';
	}, 100);

	setTimeout(function() {
		toast.style.opacity = '0';
		setTimeout(function() {
			document.body.removeChild(toast);
		}, 1500);
	}, 3000);
}
