<html>
<head>
<meta charset="utf-8">
<title>卖家商品列表</title>
<link
	href="https://cdn.bootcss.com/twitter-bootstrap/3.3.5/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet" href="/css/style.css">
</head>
<body>
	<div id="wrapper" class="toggled">

		<#--边栏sidebar--> 
		<#include "../common/nav.ftl">

		</#--主要内容content-->
		<div id="page-content-wrapper">
			<div class="container-fluid">
				<div class="row clearfix">
					<div class="col-md-12 column">
						<form role="form" method="post" action="/seller/category/save">
							<div class="form-group">
								<label>名字</label>
								<input name="categoryName" type="text" class="form-control" value="${(productCategory.categoryName)!''}"/>
							</div>
							<div class="form-group">
								<label>type</label>
								<input name="categoryType" type="text" class="form-control" value="${(productCategory.categoryType)!''}"/>
							</div>
								<input hidden type="text" name="categoryId" value="${(productCategory.categoryId)!''}">
							<button type="submit" class="btn btn-default">提交</button>
						</form>
					</div>
				</div>
			</div>

		</div>
	</div>
</body>
</html>