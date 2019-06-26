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
						<table class="table table-bordered table-hover">
							<thead>
								<tr>
									<th>类目Id</th>
									<th>名字</th>
									<th>type</th>
									<th>修改时间</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
								<#list categoryList as category>
								<tr>
									<td>${category.categoryId}</td>
									<td>${category.categoryName}</td>
									<td>${category.categoryType}</td>
									<td>${category.updateTime}</td>
									<td><a href="/seller/category/index?categoryId=${category.categoryId}">修改</a></td>
								</tr>
								</#list>
							</tbody>
						</table>
					</div>
					
				</div>
			</div>

		</div>
	</div>
</body>
</html>




