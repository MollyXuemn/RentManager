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
                                            <th>Constructeur</th>
                                            <th>Modele</th>
                                            <th>Nombre de place</th>
                                        </tr>
                                        <tr>
                                            <td>${vehicle.id}.</td>
                                            <td>${vehicle.constructeur}</td>
                                            <td>${vehicle.modele}</td>
                                            <td>${vehicle.nb_places}</td>
                                        </tr> 
                                    </table>
                                </div>
                                <strong>Champs pour modifier vos informations</strong></br>
                                <form class="form-horizontal" method="post">
                                <div>
                                    <div class="form-group">
                                        <label for="last_name" class="col-sm-2 control-label">Constructeur</label>
    
                                        <div class="col-sm-10">
                                            <input type="text" class="form-control" id="last_name" name="constructeur" value=${vehicle.constructeur}>
                                        </div>
                                    </div>
    
                                    <div class="form-group">
                                        <label for="first_name" class="col-sm-2 control-label">Modele</label>
    
                                        <div class="col-sm-10">
                                            <input type="text" class="form-control" id="first_name" name="modele" value=${vehicle.modele}>
                                        </div>
                                    </div>
    
                                    <div class="form-group">
                                        <label for="email" class="col-sm-2 control-label">Nombre de place</label>
    
                                        <div class="col-sm-10">
                                            <input type="text" class="form-control" id="email" name="nb_places" value=${vehicle.nb_places}>
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
