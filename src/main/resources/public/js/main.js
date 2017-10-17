let titles = {
	"/cuentas": "Cuentas",
	"/indicadores": "Indicadores",
	"/indicadores/nuevo": "Crear indicador",
	"/methodologies": "Metodologías",
	"/login": "Iniciar sesión"
};

function setCurrentPage() {
	let pathname = document.location.pathname;
	
	let title = titles[pathname];
	document.title = title !== undefined ? "Metryx \u2022 " + title : "Metryx";
	
	let page = pathname.substring(1);
	let slash = page.indexOf("/");
	if(slash != -1) page = page.substring(0, slash);
	$("#" + page + "-navitem").addClass("active");
}

$(function()    {
    $("#combo-empresas, #combo-periodos").on("change", function()    {
        var empresa = $("#combo-empresas").val();
        var periodo = $("#combo-periodos").val();
        if(empresa != 0 && periodo != 0)  {
            $.ajax({
                url: "./empresas/" + empresa + "/cuentas?periodo=" + periodo,
                method: "get",
                success: function(respuesta)    {
                    var cuentas = JSON.parse(respuesta);
                    console.log(cuentas);
                    var celdas = "";
                    for(var i=0; i < cuentas.length; i++) {
                        celdas += "<tr><td>" + cuentas[i].companyName + "</td><td>"+ cuentas[i].cuenta.nombre + "</td><td>" + cuentas[i].period + "</td><td>" + cuentas[i].value + "</td></tr>";
                    }
                    $("#cuentas tbody").html(celdas);
                },
                error: function(error)  {
                    console.log(error);
                }
            });
        }
    });
});

function main() {
	setCurrentPage();
}

main();