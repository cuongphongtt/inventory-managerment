<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div class="right_col" role="main">
	<div class="">

		<div class="clearfix"></div>
		<div class="col-md-12 col-sm-12 col-xs-12">
			<div class="x_panel">
				<div class="x_title">
					<h2>Product Info List</h2>

					<div class="clearfix"></div>
				</div>


				<div class="x_content">
					<div class="container">
						<a href="<c:url value="/product-info/add"/>" class="btn btn-app"><i
							class="fa fa-plus"></i>Add</a>
					</div>

					<div class="container">
						<form:form modelAttribute="searchForm"
							cssClass="form-horizontal form-label-left"
							servletRelativeAction="/product-info/list/1" method="GET">
							<div class="form-group">
								<label class="control-label col-md-3 col-sm-3 col-xs-12"
									for=code>Code </label>
								<div class="col-md-6 col-sm-6 col-xs-12">
									<form:input path="code"
										cssClass="form-control col-md-7 col-xs-12" />
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-md-3 col-sm-3 col-xs-12"
									for="name">Name </label>
								<div class="col-md-6 col-sm-6 col-xs-12">
									<form:input path="name"
										cssClass="form-control col-md-7 col-xs-12" />
								</div>
							</div>
							<div class="form-group">
								<label for="id"
									class="control-label col-md-3 col-sm-3 col-xs-12">ID</label>
								<div class="col-md-6 col-sm-6 col-xs-12">
									<form:input path="id"
										cssClass="form-control col-md-7 col-xs-12" />
								</div>
							</div>
							<div class="form-group">
								<div
									class="text-center col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
									<button type="submit" class="btn btn-success">Search</button>
								</div>
							</div>

						</form:form>
					</div>

					<div class="table-responsive">
						<table class="table table-striped jambo_table bulk_action">
							<thead>
								<tr class="headings">
									<th class="column-title">#</th>
									<th class="column-title">Id</th>
									<th class="column-title">Code</th>
									<th class="column-title">Name</th>
									<th class="column-title">Image</th>
									<th class="column-title no-link last text-center" colspan="3"><span
										class="nobr">Action</span></th>
								</tr>
							</thead>

							<tbody>
								<c:forEach items="${products}" var="product" varStatus="loop">

									<c:choose>
										<c:when test="${loop.index%2==0 }">
											<tr class="even pointer">
										</c:when>
										<c:otherwise>
											<tr class="odd pointer">
										</c:otherwise>
									</c:choose>
									<td class=" ">${pageInfo.offset+loop.index+1}</td>
									<td class=" ">${product.id }</td>
									<td class=" ">${product.code }</td>
									<td class=" ">${product.name }</td>
									<td class=" "><img src="<c:url value="${product.imgUrl}"/>" width="70px" height="100px"/></td>
									<td class="text-center"><a
										href="<c:url value="/product-info/view/${product.id }"/>"
										class="btn btn-round btn-default">View</a></td>
									<td class="text-center"><a
										href="<c:url value="/product-info/edit/${product.id }"/>"
										class="btn btn-round btn-primary">Edit</a></td>
									<td class="text-center"><a href="javascript:void(0);"
										onclick="confirmDelete(${product.id});"
										class="btn btn-round btn-danger">Delete</a></td>
									</tr>
								</c:forEach>

							</tbody>
						</table>
						<ul class="pagination text-center">
							<c:forEach begin="1" end="${pageInfo.totalPages}"
								varStatus="loop">
								<c:choose>
									<c:when test="${pageInfo.indexPages==loop.index}">
										<li class="active"><a href="javascript:void(0);">${loop.index}</a></li>
									</c:when>
									<c:otherwise>
										<li><a href="javascript:void(0);"
											onclick="gotoPage(${loop.index});">${loop.index}</a></li>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</ul>
					</div>


				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
function gotoPage(page){
	 $('#searchForm').attr('action','<c:url value="/product-info/list/"/>'+page);
	 $('#searchForm').submit();
}	
	 function confirmDelete(id){
		 if(confirm('Do you want delete this record?')){
			 window.location.href = '<c:url value="/product-info/delete/"/>'+id;
		 }
	 }
	 $(document).ready(function(){
		 processMessage();
	 });
	 function processMessage(){
		 var msgSuccess = '${msgSuccess}';
		 var msgError = '${msgErrors}';
		 if(msgSuccess){
			 new PNotify({
                 title: 'Success',
                 text: msgSuccess,
                 type: 'success',
                 styling: 'bootstrap3'
             });
		 }
		 if(msgError){
			 new PNotify({
                 title: 'Error',
                 text: msgError,
                 type: 'error',
                 styling: 'bootstrap3'
             });
		 }
	 }
</script>