<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/common/base.jsp">
  <c:param name="title">成績参照</c:param>
  <c:param name="scripts"></c:param>
  <c:param name="content">
    <section class="container mt-5">
      <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">成績参照</h2>

      <!-- ▼ 科目名表示（追加） -->
      <c:if test="${not empty subjectName}">
        <div class="px-4 my-3">
          <h3 class="h4">【${subjectName}】の成績一覧</h3>
        </div>
      </c:if>

      <!-- ▼ 成績一覧テーブル -->
      <div class="px-4">
        <table class="table table-bordered align-middle bg-white">
          <thead class="table-secondary">
            <tr>
              <th>学生番号</th>
              <th>氏名</th>
              <th>科目</th>
              <th>回数</th>
              <th>点数</th>
            </tr>
          </thead>
          <tbody>
            <c:forEach var="test" items="${testList}">
              <tr>
                <td>${test.student.no}</td>
                <td>${test.student.name}</td>
                <td>${test.subject.name}</td>
                <td>${test.no}</td>
                <td>${test.point}</td>
              </tr>
            </c:forEach>
          </tbody>
        </table>
      </div>
    </section>
  </c:param>
</c:import>
