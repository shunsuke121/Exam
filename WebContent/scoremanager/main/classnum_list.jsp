<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/common/base.jsp">
  <c:param name="title">クラス管理</c:param>
  <c:param name="scripts"></c:param>
  <c:param name="content">
    <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">クラス一覧</h2>
    <div class="text-end px-4 mb-3">
      <a href="ClassNumCreate.action" class="btn btn-primary">新規登録</a>
    </div>
    <table class="table table-bordered mx-4">
      <thead class="table-secondary">
        <tr>
          <th>クラス番号</th>
          <th>変更</th>
        </tr>
      </thead>
      <tbody>
        <c:forEach var="cn" items="${classNums}">
          <tr>
            <td>${cn.classNum}</td>
            <td><a href="ClassNumUpdate.action?class_num=${cn.classNum}" class="btn btn-outline-secondary">変更</a></td>
          </tr>
        </c:forEach>
      </tbody>
    </table>
  </c:param>
</c:import>