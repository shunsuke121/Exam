<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/common/base.jsp">
  <c:param name="title">成績管理</c:param>
  <c:param name="scripts"></c:param>

  <c:param name="content">
    <h2>成績管理</h2>

    <!-- 🔍 検索フォーム -->
    <form action="${pageContext.request.contextPath}/main/TestRegist.action"
          method="get" class="mb-3">

      入学年度：
      <select name="f1">
        <option value="">--------</option>
        <c:forEach var="y" items="${years}">
          <option value="${y}" ${y == f1 ? 'selected' : ''}>${y}</option>
        </c:forEach>
      </select>

      クラス：
      <select name="f2">
        <option value="">--------</option>
        <c:forEach var="c" items="${classNums}">
          <option value="${c.classNum}" ${c.classNum == f2 ? 'selected' : ''}>${c.classNum}</option>
        </c:forEach>
      </select>

      科目：
      <select name="f3">
        <option value="">--------</option>
        <c:forEach var="s" items="${subjects}">
          <option value="${s.cd}" ${s.cd == f3 ? 'selected' : ''}>${s.name}</option>
        </c:forEach>
      </select>

      回数：
      <select name="f4">
        <option value="">--------</option>
        <c:forEach begin="1" end="2" var="i">
          <option value="${i}" ${i == f4 ? 'selected' : ''}>${i}</option>
        </c:forEach>
      </select>

      <label><input type="checkbox" name="f5" ${f5 != null ? 'checked' : ''}/> 在学中のみ</label>

      <button type="submit" class="btn btn-secondary">検索</button>
    </form>

    <!-- 成績入力 -->
    <c:if test="${not empty testList}">
      <form action="${pageContext.request.contextPath}/main/TestRegistExecute.action"
            method="post">

        <table class="table table-bordered">
          <thead>
            <tr><th>入学年度</th><th>クラス</th><th>学生番号</th><th>氏名</th><th>点数</th></tr>
          </thead>
          <tbody>
            <c:forEach var="t" items="${testList}">
              <tr>
                <td>${t.entYear}</td>
                <td>${t.classNum}</td>
                <td>${t.student.no}</td>
                <td>${t.student.name}</td>
                <td>
                  <input type="number" name="point_${t.student.no}"
                         value="${t.point}" min="0" max="100" class="form-control w-50 d-inline">
                </td>
              </tr>
            </c:forEach>
          </tbody>
        </table>

        <!-- 検索条件保持 -->
        <input type="hidden" name="f1" value="${f1}">
        <input type="hidden" name="f2" value="${f2}">
        <input type="hidden" name="f3" value="${f3}">
        <input type="hidden" name="f4" value="${f4}">

        <button type="submit" class="btn btn-primary">登録して終了</button>
      </form>
    </c:if>

    <!-- データなし -->
    <c:if test="${empty testList && not empty f1}">
      <p>条件に一致する学生が存在しません。</p>
    </c:if>
  </c:param>
</c:import>
