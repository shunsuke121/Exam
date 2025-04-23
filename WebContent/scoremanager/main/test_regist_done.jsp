<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/common/base.jsp">
  <c:param name="title">成績登録完了</c:param>
  <c:param name="scripts"></c:param>

  <c:param name="content">
    <h2>成績登録完了</h2>
    <p>成績の登録が完了しました。</p>

    <p>
      <a href="${pageContext.request.contextPath}/main/TestList.action">
        成績参照へ戻る
      </a>
    </p>
  </c:param>
</c:import>
