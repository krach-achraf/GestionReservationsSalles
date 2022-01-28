<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<c:choose>
	<c:when test="${ !empty sessionScope.idAmin}">
		<html class="no-js" lang="en">

<head>
<meta charset="utf-8">
<meta http-equiv="x-ua-compatible" content="ie=edge">
<title>Réservations des salles</title>
<meta name="description" content="">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="shortcut icon" type="image/x-icon" href="img/favicon.ico">
<link
	href="https://fonts.googleapis.com/css?family=Roboto:100,300,400,700,900"
	rel="stylesheet">
<link rel="stylesheet" href="css/bootstrap.min.css">
<link rel="stylesheet" href="css/font-awesome.min.css">
<link rel="stylesheet" href="css/owl.carousel.css">
<link rel="stylesheet" href="css/owl.theme.css">
<link rel="stylesheet" href="css/owl.transitions.css">
<link rel="stylesheet" href="css/animate.css">
<link rel="stylesheet" href="css/normalize.css">
<link rel="stylesheet" href="css/meanmenu.min.css">
<link rel="stylesheet" href="css/main.css">
<link rel="stylesheet" href="css/educate-custon-icon.css">
<link rel="stylesheet" href="css/morrisjs/morris.css">
<link rel="stylesheet"
	href="css/scrollbar/jquery.mCustomScrollbar.min.css">
<link rel="stylesheet" href="css/metisMenu/metisMenu.min.css">
<link rel="stylesheet" href="css/metisMenu/metisMenu-vertical.css">
<link rel="stylesheet" href="style.css">
<link rel="stylesheet" href="css/tabs.css">
<link rel="stylesheet" href="css/responsive.css">
<script src="js/vendor/modernizr-2.8.3.min.js"></script>
</head>

<body>
	<div class="left-sidebar-pro">
		<%@include file="template/sidebar.jsp"%>
	</div>
	<div class="all-content-wrapper">
		<div class="container-fluid">
			<%@include file="template/logo.jsp"%>
		</div>

		<div class="header-advance-area">
			<div class="header-top-area">
				<%@include file="template/header/header-top.jsp"%>
			</div>
			<div class="mobile-menu-area">
				<%@include file="template/mobile-sidebar.jsp"%>
			</div>
			<div class="breadcome-area">
				<%@include file="template/breadcomes/creneauxForm.jsp"%>
			</div>
		</div>

		<!-- debut main -->
		<div class="admintab-area mg-b-15">
			<div class="container-fluid">
				<div class="admintab-wrap edu-tab1 mg-t-30">
					<ul class="nav nav-tabs custom-menu-wrap custon-tab-menu-style1">
						<li class="active"><a data-toggle="tab" href="#TabProject"
							aria-expanded="true"><span
								class="edu-icon edu-analytics tab-custon-ic"></span>Creneaux</a></li>
						<li class=""><a data-toggle="tab" href="#TabDetails"
							aria-expanded="false"><span
								class="edu-icon edu-analytics-arrow tab-custon-ic"></span>Planning</a></li>
					</ul>
					<div class="tab-content">
						<div id="TabProject"
							class="tab-pane animated flipInX custon-tab-style1 active">
							<div class="row">
								<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
									<h4>Formulaire</h4>
									<hr>
									<%@include file="forms/formulaires/form-creneaux.jsp"%> <!-- form de gestion creneaux -->
								</div>
								<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
									<h4>Liste</h4>
									<hr>
									<div class="product-status-wrap drp-lst" style="padding: 0;">
										<div class="asset-inner">
											<%@include file="forms/listes/liste-creneaux.jsp"%> <!-- liste creneaux -->
										</div>
									</div>
								</div>
							</div>

						</div>
						<div id="TabDetails"
							class="tab-pane animated flipInX custon-tab-style1">
							<div class="row">
								<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
									<h4>Formulaire</h4>
									<hr>
									<%@include file="forms/formulaires/form-planning.jsp"%> <!-- form de gestion planning (affecter salle a 1 creneau -->
								</div>
								<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
									<h4>Liste</h4>
									<hr>
									<div class="product-status-wrap drp-lst" style="padding: 0;">
										<div class="asset-inner">
											<%@include file="forms/listes/liste-planning.jsp"%> <!-- liste planning -->
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- fin main -->
		
		<div class="footer-copyright-area">
			<%@include file="template/footer.jsp"%>
		</div>
	</div>
	<script src="js/vendor/jquery-1.12.4.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/wow.min.js"></script>
	<script src="js/jquery-price-slider.js"></script>
	<script src="js/jquery.meanmenu.js"></script>
	<script src="js/owl.carousel.min.js"></script>
	<script src="js/jquery.sticky.js"></script>
	<script src="js/jquery.scrollUp.min.js"></script>
	<script src="js/counterup/jquery.counterup.min.js"></script>
	<script src="js/counterup/waypoints.min.js"></script>
	<script src="js/counterup/counterup-active.js"></script>
	<script src="js/scrollbar/jquery.mCustomScrollbar.concat.min.js"></script>
	<script src="js/scrollbar/mCustomScrollbar-active.js"></script>
	<script src="js/metisMenu/metisMenu.min.js"></script>
	<script src="js/metisMenu/metisMenu-active.js"></script>
	<script src="js/morrisjs/raphael-min.js"></script>
	<script src="js/morrisjs/morris.js"></script>
	<script src="js/morrisjs/morris-active.js"></script>
	<script src="js/sparkline/jquery.sparkline.min.js"></script>
	<script src="js/sparkline/jquery.charts-sparkline.js"></script>
	<script src="js/sparkline/sparkline-active.js"></script>
	<script src="js/calendar/moment.min.js"></script>
	<script src="js/plugins.js"></script>
	<script src="js/main.js"></script>
	<script src="js/tawk-chat.js"></script>
	<script src="//cdn.jsdelivr.net/npm/sweetalert2@11"></script>
	<script src="script/gestion-creneaux.js"></script>
	<script src="script/gestion-planning.js"></script>
</body>
		</html>
	</c:when>
	<c:otherwise>
		<%@include file="forms/error404.jsp"%>
	</c:otherwise>
</c:choose>