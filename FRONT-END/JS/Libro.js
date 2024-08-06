var url = "http://localhost:8080/api/v1/libro/";

function listarLibro() {
  var capturarFiltro = document.getElementById("inputSearch").value;
  var urlLocal = url;
  if (capturarFiltro != "") {
    urlLocal += "busquedafiltro/" + capturarFiltro;
  }

  $.ajax({
    url: urlLocal,
    type: "GET",
    success: function (result) {
      console.log(result);

      var cuerpoTabla = document.getElementById("cuerpoTabla");
      cuerpoTabla.innerHTML = "";

      for (var i = 0; i < result.length; i++) {
        var trResgistro = document.createElement("tr");

        var celdaId = document.createElement("td");
        let celdaTituloLibro = document.createElement("td");
        let celdaAutorLibro = document.createElement("td");
        let celdaIsbnLibro = document.createElement("td");
        let celdaGeneroLibro = document.createElement("td");
        let celdaEjemplaresDisponibles = document.createElement("td");
        let celdaEjemplaresOcupados = document.createElement("td");

        let celdaOpcionEditar = document.createElement("td");
        let botonEditarLibro = document.createElement("button");
        botonEditarLibro.value = result[i]["id_libro"];
        botonEditarLibro.innerHTML = "Editar";
        botonEditarLibro.onclick = function (e) {
          $('#exampleModal').modal('show');
          consultarLibroID(this.value);
        }
        botonEditarLibro.className = "btn btn-warning editar-libro";
        celdaOpcionEditar.appendChild(botonEditarLibro);

        let celdaOpcionEliminar = document.createElement("td");
        let botonEliminarLibro = document.createElement("button");
        botonEliminarLibro.value = result[i]["id_libro"];
        botonEliminarLibro.innerHTML = "Eliminar";
        botonEliminarLibro.onclick = function (e) {
          eliminarLibro(this.value);
        }
        botonEliminarLibro.className = "btn btn-danger eliminar-libro";
        celdaOpcionEliminar.appendChild(botonEliminarLibro);

        let celdaOpcionDetalles = document.createElement("td");
        let botonDetalles = document.createElement("button");
        botonDetalles.className = "btn btn-info";
        botonDetalles.innerHTML = "Detalle";
        botonDetalles.onclick = (function(libro) {
          return function() {
            verDetalles(libro);
          }
        })(result[i]);
        celdaOpcionDetalles.appendChild(botonDetalles);

        celdaId.innerText = result[i]["id_libro"];
        celdaTituloLibro.innerText = result[i]["titulo_libro"];
        celdaAutorLibro.innerText = result[i]["autor_libro"];
        celdaIsbnLibro.innerText = result[i]["isbn_libro"];
        celdaGeneroLibro.innerText = result[i]["genero_libro"];
        celdaEjemplaresDisponibles.innerText = result[i]["numero_ejemplares_disponibles"];
        celdaEjemplaresOcupados.innerText = result[i]["numero_ejemplares_ocupados"];

        trResgistro.appendChild(celdaId);
        trResgistro.appendChild(celdaTituloLibro);
        trResgistro.appendChild(celdaAutorLibro);
        trResgistro.appendChild(celdaIsbnLibro);
        trResgistro.appendChild(celdaGeneroLibro);
        trResgistro.appendChild(celdaEjemplaresDisponibles);
        trResgistro.appendChild(celdaEjemplaresOcupados);
        trResgistro.appendChild(celdaOpcionEditar);
        trResgistro.appendChild(celdaOpcionEliminar);
        trResgistro.appendChild(celdaOpcionDetalles);

        cuerpoTabla.appendChild(trResgistro);
      }
    },
    error: function (error) {
      alert("Error en la petición " + error);
    }
  });
}

function eliminarLibro(idLibro) {
  Swal.fire({
    title: "¿Estás seguro?",
    text: "¿Deseas eliminar este libro?",
    icon: "warning",
    showCancelButton: true,
    confirmButtonColor: "#3085d6",
    cancelButtonColor: "#d33",
    confirmButtonText: "Sí, eliminar",
    cancelButtonText: "Cancelar"
  }).then((result) => {
    if (result.isConfirmed) {
      $.ajax({
        url: url + idLibro,
        type: "DELETE",
        success: function (response) {
          Swal.fire({
            title: "¡Eliminado!",
            text: "El libro ha sido eliminado correctamente.",
            icon: "success"
          });
          listarLibro();
        },
        error: function (error) {
          Swal.fire("Error", "Error al eliminar el libro. " + error.responseText, "error");
        }
      });
    }
  });
}

function consultarLibroID(id) {
  $.ajax({
    url: url + id,
    type: "GET",
    success: function (result) {
      document.getElementById("id_libro").value = result["id_libro"];
      document.getElementById("titulo_libro").value = result["titulo_libro"];
      document.getElementById("autor_libro").value = result["autor_libro"];
      document.getElementById("isbn_libro").value = result["isbn_libro"];
      document.getElementById("genero_libro").value = result["genero_libro"];
      document.getElementById("numero_ejemplares_disponibles").value = result["numero_ejemplares_disponibles"];
      document.getElementById("numero_ejemplares_ocupados").value = result["numero_ejemplares_ocupados"];
    }
  });
}

function verDetalles(libro) {
  Swal.fire({
    title: 'Detalles del Libro',
    html: `
      <p><strong>ID:</strong> ${libro.id_libro}</p>
      <p><strong>Título:</strong> ${libro.titulo_libro}</p>
      <p><strong>Autor:</strong> ${libro.autor_libro}</p>
      <p><strong>ISBN:</strong> ${libro.isbn_libro}</p>
      <p><strong>Género:</strong> ${libro.genero_libro}</p>
      <p><strong>Ejemplares Disponibles:</strong> ${libro.numero_ejemplares_disponibles}</p>
      <p><strong>Ejemplares Ocupados:</strong> ${libro.numero_ejemplares_ocupados}</p>
    `,
    icon: 'info',
    confirmButtonText: 'Cerrar'
  });
}

function actualizarLibro() {
  var id_libro = document.getElementById("id_libro").value;
  let formData = {
    "titulo_libro": document.getElementById("titulo_libro").value,
    "autor_libro": document.getElementById("autor_libro").value,
    "isbn_libro": document.getElementById("isbn_libro").value,
    "genero_libro": document.getElementById("genero_libro").value,
    "numero_ejemplares_disponibles": document.getElementById("numero_ejemplares_disponibles").value,
    "numero_ejemplares_ocupados": document.getElementById("numero_ejemplares_ocupados").value
  };

  if (validarCampos()) {
    $.ajax({
      url: url + id_libro,
      type: "PUT",
      contentType: "application/json",
      data: JSON.stringify(formData),
      success: function (result) {
        Swal.fire({
          title: "¡Excelente!",
          text: "Se guardó correctamente",
          icon: "success"
        });
        listarLibro();
      },
      error: function (error) {
        Swal.fire({
          title: "¡Error!",
          text: "No se guardó",
          icon: "error"
        });
      }
    });
  } else {
    Swal.fire({
      title: "¡Error!",
      text: "Llene todos los campos correctamente",
      icon: "error"
    });
  }
}

function registrarLibro() {
  let formData = {
    "titulo_libro": document.getElementById("titulo_libro").value,
    "autor_libro": document.getElementById("autor_libro").value,
    "isbn_libro": document.getElementById("isbn_libro").value,
    "genero_libro": document.getElementById("genero_libro").value,
    "numero_ejemplares_disponibles": document.getElementById("numero_ejemplares_disponibles").value,
    "numero_ejemplares_ocupados": document.getElementById("numero_ejemplares_ocupados").value
  };

  if (validarCampos()) {
    $.ajax({
      url: url,
      type: "POST",
      contentType: "application/json",
      data: JSON.stringify(formData),
      success: function (result) {
        Swal.fire({
          title: "¡Excelente!",
          text: "Se guardó correctamente",
          icon: "success"
        });
        limpiarLibro();
        listarLibro();
      },
      error: function (error) {
        Swal.fire("Error", "Error al guardar, " + error.responseText, "error");
      }
    });
  } else {
    Swal.fire({
      title: "¡Error!",
      text: "Llene todos los campos correctamente",
      icon: "error"
    });
  }
}

function limpiarLibro() {
  document.getElementById("id_libro").value = "";
  document.getElementById("titulo_libro").value = "";
  document.getElementById("autor_libro").value = "";
  document.getElementById("isbn_libro").value = "";
  document.getElementById("genero_libro").value = "";
  document.getElementById("numero_ejemplares_disponibles").value = "";
  document.getElementById("numero_ejemplares_ocupados").value = "";
}

function validarCampos() {
  return document.getElementById("titulo_libro").value.trim() !== "" &&
    document.getElementById("autor_libro").value.trim() !== "" &&
    document.getElementById("isbn_libro").value.trim() !== "" &&
    document.getElementById("genero_libro").value.trim() !== "" &&
    document.getElementById("numero_ejemplares_disponibles").value.trim() !== "" &&
    document.getElementById("numero_ejemplares_ocupados").value.trim() !== "";
}
