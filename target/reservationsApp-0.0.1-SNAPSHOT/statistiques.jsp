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
<title>R�servations des salles</title>
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
<link rel="stylesheet" href="css/c3/c3.min.css">
<link rel="stylesheet" href="css/morrisjs/morris.css">
<link rel="stylesheet"
	href="css/scrollbar/jquery.mCustomScrollbar.min.css">
<link rel="stylesheet" href="css/metisMenu/metisMenu.min.css">
<link rel="stylesheet" href="css/metisMenu/metisMenu-vertical.css">
<link rel="stylesheet" href="style.css">
<link rel="stylesheet" href="css/responsive.css">
<script src="js/vendor/jquery-1.12.4.min.js"></script>
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
				<%@include file="template/breadcomes/statistiquesForm.jsp"%>
			</div>
		</div>

		<!-- debut main -->
		<div class="container-fluid">
			<div class="row">
				<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
					<div class="charts-single-pro responsive-mg-b-30">
						<div class="alert-title">
							<h2>R�servations par salle</h2>
						</div>
						<div id="pie-chart">
							<iframe class="chartjs-hidden-iframe" tabindex="-1"
								style="display: block; overflow: hidden; border: 0px; margin: 0px; inset: 0px; height: 100%; width: 100%; position: absolute; pointer-events: none; z-index: -1;"></iframe>
							<canvas id="piechart"
								style="display: block; width: 505px; height: 505px;" width="505"
								height="505"></canvas>
						</div>
					</div>
				</div>
				<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
					<div class="sparkline7-list responsive-mg-b-30">
						<div class="sparkline7-hd">
							<div class="main-spark7-hd">
								<h1>
									R�servations <span class="c3-ds-n">par mois</span>
								</h1>
							</div>
						</div>
						<div class="sparkline7-graph">
							<div id="slineChart"></div>
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
	<script src="js/charts/Chart.js"></script>
	<script src="js/c3-charts/d3.min.js"></script>
	<script src="js/c3-charts/c3.min.js"></script>
	<!-- debut code js -->
	<script src="js/c3-charts/c3-active.js"></script>
	<script src="js/charts/rounded-chart.js"></script>
	<!-- fin code js -->
	<script src="js/morrisjs/raphael-min.js"></script>
	<script src="js/morrisjs/morris.js"></script>
	<script src="js/morrisjs/morris-active.js"></script>
	<script src="js/sparkline/jquery.sparkline.min.js"></script>
	<script src="js/sparkline/jquery.charts-sparkline.js"></script>
	<script src="js/sparkline/sparkline-active.js"></script>
	<script src="js/plugins.js"></script>
	<script src="js/main.js"></script>
	<script src="js/tawk-chat.js"></script>
</body>
		</html>
	</c:when>
	<c:otherwise>
		<%@include file="forms/error404.jsp"%>
	</c:otherwise>
</c:choose>