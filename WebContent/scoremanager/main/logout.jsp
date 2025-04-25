<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/common/base.jsp">
  <c:param name="title">ログアウト</c:param>
  <c:param name="scripts"></c:param>
  <c:param name="content">
    <section class="container mt-5">
      <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">ログアウト</h2>
      <p>ログアウトしました。</p>
      <p><a href="../scoremanager/login.jsp">ログイン画面へ</a></p>
    </section>
  </c:param>
</c:import>
