<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/common/base.jsp">
  <c:param name="title">ログイン</c:param>
  <c:param name="scripts">
    <script>
      function togglePassword() {
        const pwd = document.getElementById("password");
        pwd.type = (pwd.type === "password") ? "text" : "password";
      }
    </script>
  </c:param>
  <c:param name="showSidebar">false</c:param>
  <c:param name="content">
    <section class="my-5">
      <div class="mx-auto p-4 shadow rounded" style="max-width: 400px; background-color: #f9f9f9;">

        <!-- ログイン見出し（灰色背景） -->
        <h2 class="mb-4 fw-normal text-center py-2" style="background-color: #e9ecef;">ログイン</h2>

        <!-- エラーメッセージ表示 -->
        <c:if test="${not empty error}">
          <div class="alert alert-danger text-center">${error}</div>
        </c:if>

        <form action="LoginExecute.action" method="post">
          <div class="mb-3 text-start">
            <label for="id" class="form-label">ID</label>
            <input type="text" class="form-control" name="id" id="id" value="${id}" maxlength="10"
              placeholder="半角でご入力ください">
          </div>

          <div class="mb-3 text-start">
            <label for="password" class="form-label">パスワード</label>
            <input type="password" class="form-control" name="password" id="password" maxlength="30"
              placeholder="英数字でご入力ください">
          </div>

          <!-- 中央寄せのパスワード表示 -->
          <div class="form-check d-flex justify-content-center mb-3">
            <input class="form-check-input me-2" type="checkbox" name="chk_d_ps" id="chk_d_ps" onclick="togglePassword()">
            <label class="form-check-label" for="chk_d_ps">パスワードを表示</label>
          </div>

          <div class="d-grid">
            <input type="submit" name="login" class="btn btn-primary" value="ログイン">
          </div>
        </form>
      </div>
    </section>
  </c:param>
</c:import>
