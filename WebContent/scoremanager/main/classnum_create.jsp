<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<c:import url="/common/base.jsp">
  <c:param name="title">クラス新規登録</c:param>
  <c:param name="scripts"></c:param>
  <c:param name="content">
    <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">クラス新規登録</h2>
    <form action="ClassNumCreateExecute.action" method="post" class="px-4">
      <div class="mb-3">
        <label for="class_num" class="form-label">クラス番号</label>
        <input type="text" class="form-control" id="class_num" name="class_num" required>
      </div>
      <button type="submit" class="btn btn-primary">登録して終了</button>
    </form>
  </c:param>
</c:import>