<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
<%@include file="/WEB-INF/views/common/head.jsp"%>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

    <%@ include file="/WEB-INF/views/common/header.jsp" %>
    <!-- Left side column. contains the logo and sidebar -->
    <%@ include file="/WEB-INF/views/common/sidebar.jsp" %>

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
                Utilisateurs
            </h1>
        </section>

                <!-- Main content -->
                <section class="content">
                    <span>
                        ${ErrorMessage}
                    </span>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="box">
                                <div class="box-body no-padding">
                                    <table class="table table-striped">
                                        <tr>
                                            <th style="width: 10px">#</th>
                                            <th>Client</th>
                                            <th>Vehicule</th>
                                            <th>Date de debut</th>
                                            <th>Date de fin</th>
                                        </tr>
                                        <tr>
                                            <td>${reservation.id}.</td>
                                            <td>${client.nom} ${client.prenom}</td>
                                            <td>${vehicle.modele} ${vehicle.constructeur}</td>
                                            <td>${reservation.debut}</td>
                                            <td>${reservation.fin}</td>
                                        </tr> 
                                    </table>
                                </div>
                                <strong>Champs pour modifier vos informations</strong></br>
                                <form class="form-horizontal" method="post">
                                <div>
                                    <div class="form-group">
                                        <label for="vehicleid" class="col-sm-2 control-label">Vehicule</label>
                                        <div class="col-sm-10">
                                            <select class="form-control" id="car" name="vehicleid">
                                                <option  value=${vehicle.id}>${vehicle.constructeur} ${vehicle.modele}</option>
                                                <c:forEach items="${vehicles}" var="vehicle2">
                                                        <option value=${vehicle2.id}>${vehicle2.constructeur} ${vehicle2.modele}</option>
                                            </c:forEach>
                                            </select>
                                        </div>
                                    </div>
    
                                    <div class="form-group">
                                        <label for="debut" class="col-sm-2 control-label">Debut</label>
                                        <div class="col-sm-10">
                                            <input type="date" class="form-control" id="debut" name="debut" value=${reservation.debut} min="1980-01-01" max="2060-03-01">
                                            
                                        </div>
                                    </div>
    
                                    <div class="form-group">
                                        <label for="fin" class="col-sm-2 control-label">Fin</label>
                                        <div class="col-sm-10">
                                            <input type="date" class="form-control" id="fin" name="debut" value=${reservation.fin} min="1980-01-01" max="2060-03-01">
                                        </div>
                                    </div>
                                </div>
                                <!-- /.box-body -->
                                <div class="box-footer">
                                    <button type="submit" class="btn btn-info pull-right">Update</button>
                                </div>
                            </form>
                            </div>
                            <!-- /.box -->
                        </div>
                        <!-- /.col -->
                    </div>
                </section>
        <!-- /.content -->
    </div>

    <%@ include file="/WEB-INF/views/common/footer.jsp" %>
</div>
<!-- ./wrapper -->

<%@ include file="/WEB-INF/views/common/js_imports.jsp" %>
</body>
</html>
