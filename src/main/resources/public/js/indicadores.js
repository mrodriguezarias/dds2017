$(function()    {
    var nombreIndicadorSeleccionado;

    $("#indicadores").on("change", function()   {
        $("#modificar-indicador, #borrar-indicador").prop("disabled", false);
        nombreIndicadorSeleccionado = $(this).val()[0];
    });

    $("#agregar-indicador").click(function(){
        $("#titulo-modal-indicador").html("Crear Indicador");
        $("#nombre").val("");
        $("#descripcion").val("");
        $("#formula").val("");
        guardarIndicador("post");
    });

    $("#borrar-indicador").on("click", function(e) {
        e.preventDefault();
        $.ajax({
            url: $(location).attr("href") + "/" + nombreIndicadorSeleccionado,
            method: "delete",
            success: function() { actualizarIndicadores(); }
        });
    });

    $("#modificar-indicador").on("click", function()    {
        $("#titulo-modal-indicador").html("Editar Indicador");
        $.ajax({
                url: $(location).attr("href") + "/" + nombreIndicadorSeleccionado,
                method: "get",
                success: function(respuesta)    {
                    indicador = JSON.parse(respuesta);
                    $("#nombre").val(indicador.name);
                    $("#descripcion").val(indicador.description);
                    $("#formula").val(indicador.formula);
                }
        });
        guardarIndicador("put", nombreIndicadorSeleccionado);
    });

});

function guardarIndicador(method, nombreViejo) {
    $("#guardar").on("click", function()    {
        $.ajax({
            url: $(location).attr("href"),
            method: method,
            data: {
                nombre: $("#nombre").val(),
                nombreViejo: nombreViejo,
                descripcion: $("#descripcion").val(),
                formula: $("#formula").val()
            },
            success: function(respuesta)    {
                $("#modal-indicador").modal("hide");
                actualizarIndicadores();
            }
        });
    });
}

function actualizarIndicadores()    {
    $.get("/api/indicadores", function(respuesta)    {
        let indicadores = JSON.parse(respuesta);
        let options = "";
        for(var i=0; i<indicadores.length; i++) {
            options += '<option value="'+ indicadores[i].name + '">' + indicadores[i].name + '</option>';
        }
        $("#indicadores").html(options);
    });
}