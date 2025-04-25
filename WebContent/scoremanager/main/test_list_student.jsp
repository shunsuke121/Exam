<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/common/base.jsp">
  <c:param name="title">成績参照（学生別）</c:param>
  <c:param name="scripts"></c:param>
  <c:param name="content">
    <section class="container mt-5">
      <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">成績参照（学生別）</h2>

      <!-- 検索フォーム -->
      <form action="TestListStudent.action" method="get" class="px-4 mb-4">
        <div class="row g-3 align-items-end">
          <div class="col">
            <label for="student_key" class="form-label">学生番号または氏名</label>
            <input type="text" class="form-control" id="student_key" name="student_key" value="${studentKey}">
          </div>
          <div class="col-auto">
            <button type="submit" class="btn btn-primary">検索</button>
          </div>
        </div>
      </form>

      <!-- 成績一覧表示 -->
      <c:if test="${not empty testList}">
        <table class="table table-hover px-4">
          <thead>
            <tr>
              <th>科目</th>
              <th>回数</th>
              <th>点数</th>
            </tr>
          </thead>
          <tbody>
            <c:forEach var="test" items="${testList}">
              <tr>
                <td>${test.subject.name}</td>
                <td>${test.num}</td>
                <td>${test.point}</td>
              </tr>
            </c:forEach>
          </tbody>
        </table>
      </c:if>

    </section>
  </c:param>
</c:import>
