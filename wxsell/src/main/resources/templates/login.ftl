<head>
<meta charset="utf-8">
<title>卖家后端管理系统</title>
<link
	href="https://cdn.bootcss.com/twitter-bootstrap/3.3.5/css/bootstrap.min.css"
	rel="stylesheet">
</head>

<body>
<div class="container-fluid">
	<div class="row-fluid">
		<div class="span12">
			<form role="form" method="post" action="/user/login">
				<div class="control-group">
					 <label class="control-label" for="inputEmail">账号</label>
					<div class="controls">
						<input  name="accountName" type="text" />
					</div>
				</div>
				<div class="control-group">
					 <label class="control-label" for="inputPassword">密码</label>
					<div class="controls">
						<input id="inputPassword" name="password" type="password" /><br>
					</div>
				</div>
				<div class="control-group">
					<div class="controls">
						 <br> <button type="submit" class="btn">登陆</button>
					</div>
				</div>
			</form>
		</div>
	</div>
</div>
	
</body>